package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.util.UserInterface;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Searching for the server...");
        UserInterface userInterface = new UserInterface();

        try {
            userInterface.connect("localhost", 6547);
            System.out.println("Study group collection manager\nUse \"help\" to see the command reference");
            while (true) {
                System.out.print("> ");
                userInterface.sendCommand();
                userInterface.writeOutput();
            }
        } catch (SocketException se) {
            System.out.println(se.getMessage());
            System.out.println("Shutting down");
        } finally {
            userInterface.closeConnection();
        }
    }
}
