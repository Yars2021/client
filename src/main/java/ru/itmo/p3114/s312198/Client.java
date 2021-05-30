package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.authorization.AuthorizationRequestBuilder;
import ru.itmo.p3114.s312198.client_command.ClientCommandLineProcessor;
import ru.itmo.p3114.s312198.exception.EndOfSessionException;
import ru.itmo.p3114.s312198.exception.InvalidCommandException;
import ru.itmo.p3114.s312198.transmission.CSChannel;
import ru.itmo.p3114.s312198.transmission.structures.packet.ClientDataPacket;
import ru.itmo.p3114.s312198.transmission.structures.authorization.AuthorizationRequest;
import ru.itmo.p3114.s312198.transmission.structures.authorization.AuthorizationResponse;
import ru.itmo.p3114.s312198.command.CommandOutput;
import ru.itmo.p3114.s312198.util.ConsoleReader;
import ru.itmo.p3114.s312198.command.CommandLineProcessor;
import ru.itmo.p3114.s312198.command.actions.AbstractCommand;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        int port = 6547;
        boolean connecting = true;
        ClientCommandLineProcessor commandLineProcessor = new ClientCommandLineProcessor();
        while (connecting) {
            System.out.println("Connecting...");
            try (CSChannel channel = new CSChannel(new Socket("localhost", port))) {
                System.out.println("Use \"login\" to log in, \"register\" to create an account or \"exit\" to exit");
                try {
                    AuthorizationRequest authorizationRequest = AuthorizationRequestBuilder.makeRequest(commandLineProcessor.parseUserInput(ConsoleReader.readLine()));
                    channel.write(authorizationRequest);
                    try {
                        AuthorizationResponse authorizationResponse = (AuthorizationResponse) channel.read();
                        System.out.println(authorizationResponse.getServerMessage());
                        if (authorizationResponse.isAllowed()) {
                            CommandLineProcessor commandLineProc = new CommandLineProcessor();
                            boolean running = true;
                            while (running) {
                                AbstractCommand command = commandLineProc.parseInput(ConsoleReader.readLine(), false);
                                channel.write(new ClientDataPacket(command, authorizationRequest.getUserSignature()));
                                if ("exit".equals(command.getCommand())) {
                                    running = false;
                                }
                                CommandOutput output = (CommandOutput) channel.getObjectInputStream().readObject();
                                for (String line : output.getOutput()) {
                                    System.out.println(line);
                                }
                            }
                        }
                    } catch (ClassNotFoundException ce) {
                        System.out.println("The data packet has been damaged, unable to read");
                    }
                } catch (InvalidCommandException ice) {
                    System.out.println(ice.getMessage());
                } catch (EndOfSessionException ese) {
                    connecting = false;
                }
            } catch (IOException ioe) {
                System.out.println("Lost connection to the server. Type \"Y\" to rejoin or anything else to exit");
                connecting = "Y".equalsIgnoreCase(ConsoleReader.readLine());
            }
            System.out.println("Shutting down");
        }
    }
}