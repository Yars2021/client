package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.UserInterface;
import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        System.out.println("Searching for the server...");
        UserInterface userInterface = new UserInterface();

        try {
            userInterface.connect("localhost", 6547);
            System.out.println("Study group collection manager\nUse \"help\" to see the command reference");
            while (true) {
                System.out.print("> ");
                AbstractCommand lastCommand;
                lastCommand = userInterface.sendCommand();
                if (lastCommand != null) {
                    userInterface.writeOutput();
                }
            }
        } catch (SocketException se) {
            System.out.println(se.getMessage());
            System.out.println("Shutting down");
        } finally {
            userInterface.closeConnection();
        }
    }
}
