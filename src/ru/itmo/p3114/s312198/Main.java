package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.command.actions.Add;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Connector connector = new Connector();
        Socket clientSocket = connector.requestConnection("localhost", 6547);
        ClientCommandWriter clientCommandWriter = new ClientCommandWriter(clientSocket);

        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("Arg1");
        arguments.add("Arg2");
        arguments.add("Arg3");

        try {
            try {
                clientCommandWriter.send(new Add(arguments, null));
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
