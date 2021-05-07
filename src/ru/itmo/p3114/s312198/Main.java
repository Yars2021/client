package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.ConsoleReader;
import ru.itmo.p3114.s312198.util.command.CommandLineProcessor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Searching for the server...");
        Connector connector = new Connector();
        Socket clientSocket = connector.requestConnection("localhost", 6547);

        if (clientSocket != null) {
            ClientCommandWriter clientCommandWriter = new ClientCommandWriter(clientSocket);
            ClientReader clientReader = new ClientReader(clientSocket);
            CommandLineProcessor commandLineProcessor = new CommandLineProcessor();

            try {
                try {
                    while (true) {
                        clientCommandWriter.send(commandLineProcessor.parseInput(ConsoleReader.readLine(), false));
                        System.out.println(clientReader.readLine());
                    }
                } finally {
                    System.out.println("Client closed");
                    clientSocket.close();
                    clientCommandWriter.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
