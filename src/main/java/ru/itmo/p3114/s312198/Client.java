package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.client_command.ClientCommandLineProcessor;
import ru.itmo.p3114.s312198.util.CommandOutput;
import ru.itmo.p3114.s312198.util.ConsoleReader;
import ru.itmo.p3114.s312198.util.command.CommandLineProcessor;
import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        int port = 6547;
        boolean connecting = true;
        while (connecting) {
            System.out.println("Connecting...");
            try (Socket socket = new Socket("localhost", port)) {
                System.out.println("Use \"login\" to log in or \"register\" to create an account");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ClientCommandLineProcessor commandLineProcessor = new ClientCommandLineProcessor();
                CommandOutput commandOutput = commandLineProcessor.parseUserInput(ConsoleReader.readLine()).execute();
                AuthorizationRequest authorizationRequest =
                        new AuthorizationRequest(commandOutput.getOutput().get(0),
                                new UserSignature(commandOutput.getOutput().get(1), commandOutput.getOutput().get(2)));
                objectOutputStream.writeObject(authorizationRequest);
                try {
                    AuthorizationResponse authorizationResponse = (AuthorizationResponse) objectInputStream.readObject();
                    System.out.println(authorizationResponse.getServerMessage());
                    if (authorizationResponse.isAllowed()) {
                        CommandLineProcessor commandLineProc = new CommandLineProcessor();
                        boolean running = true;
                        while (running) {
                            AbstractCommand command = commandLineProc.parseInput(ConsoleReader.readLine(), false);
                            objectOutputStream.writeObject(new ClientDataPacket(command, authorizationRequest.getUserSignature()));
                            if ("exit".equals(command.getCommand())) {
                                running = false;
                            }
                            CommandOutput output = (CommandOutput) objectInputStream.readObject();
                            for (String line : output.getOutput()) {
                                System.out.println(line);
                            }
                        }
                    }
                } catch (ClassNotFoundException ce) {
                    ce.printStackTrace();
                }
            } catch (IOException ioe) {
                System.out.println("Lost connection to the server. Type \"Y\" to rejoin or anything else to exit");
                connecting = "Y".equalsIgnoreCase(ConsoleReader.readLine());
            }
            System.out.println("Shutting down");
        }
    }
}
