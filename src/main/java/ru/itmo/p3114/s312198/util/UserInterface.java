package ru.itmo.p3114.s312198.util;

import ru.itmo.p3114.s312198.server_interaction.ClientCommandWriter;
import ru.itmo.p3114.s312198.server_interaction.ClientOutputReader;
import ru.itmo.p3114.s312198.util.command.CommandLineProcessor;
import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class UserInterface {
    private Socket clientSocket;
    private ClientCommandWriter clientCommandWriter;
    private ClientOutputReader clientOutputReader;
    private final CommandLineProcessor commandLineProcessor = new CommandLineProcessor();

    public void connect(String host, int port) throws SocketException {
        Connector connector = new Connector();
        clientSocket = connector.requestConnection(host, port);

        if (clientSocket == null) {
            throw new SocketException("Unable to connect");
        } else {
            System.out.println("Connected");
        }

        clientCommandWriter = new ClientCommandWriter(clientSocket);
        clientOutputReader = new ClientOutputReader(clientSocket);
    }

    public AbstractCommand sendCommand() throws SocketException {
        AbstractCommand command = commandLineProcessor.parseInput(ConsoleReader.readLine(), false);
        if (command != null) {
            clientCommandWriter.send(command);
        }
        return command;
    }

    public void writeOutput() {
        CommandOutput commandOutput = clientOutputReader.receive();

        if (commandOutput != null && commandOutput.getOutput() != null) {
            for (String line : commandOutput.getOutput()) {
                System.out.println(line);
            }
        }
    }

    public void closeConnection() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                clientOutputReader.close();
                clientCommandWriter.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
