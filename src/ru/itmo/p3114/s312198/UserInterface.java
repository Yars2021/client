package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.CommandOutput;
import ru.itmo.p3114.s312198.util.ConsoleReader;
import ru.itmo.p3114.s312198.util.command.CommandLineProcessor;

import java.io.IOException;
import java.net.Socket;

public class UserInterface {
    private Socket clientSocket;
    private ClientCommandWriter clientCommandWriter;
    private ClientOutputReader clientOutputReader;
    private final CommandLineProcessor commandLineProcessor = new CommandLineProcessor();

    public void connect(String host, int port) {
        Connector connector = new Connector();
        clientSocket = connector.requestConnection(host, port);

        clientCommandWriter = new ClientCommandWriter(clientSocket);
        clientOutputReader = new ClientOutputReader(clientSocket);
    }

    public void sendCommand() {
        clientCommandWriter.send(commandLineProcessor.parseInput(ConsoleReader.readLine(),false));
    }

    public void writeOutput() {
        CommandOutput commandOutput = clientOutputReader.receive();

        for (String line : commandOutput.getOutput()) {
            System.out.println(line);
        }
    }

    public void closeConnection() {
        try {
            clientSocket.close();
            clientOutputReader.close();
            clientCommandWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
