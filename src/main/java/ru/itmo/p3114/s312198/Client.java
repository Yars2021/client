package ru.itmo.p3114.s312198;

import ru.itmo.p3114.s312198.authorization.Authorizer;
import ru.itmo.p3114.s312198.util.ConsoleReader;
import ru.itmo.p3114.s312198.util.UserInterface;
import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

import java.net.SocketException;
import java.util.Locale;

public class Client {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        boolean running = true;
        String input;

        while (running) {
            try {
                userInterface.connect("localhost", 6547);
                Authorizer authorizer = new Authorizer(userInterface.getClientSocket());
                userInterface.setUserSignature(authorizer.authorize());

                System.out.println("Study group database manager\nUse \"help\" to see the command reference");
                while (running) {
                    System.out.print("> ");
                    AbstractCommand lastCommand;
                    lastCommand = userInterface.sendDataPacket();
                    if (lastCommand != null) {
                        userInterface.writeOutput();
                        if (lastCommand.getCommand().equals("exit")) {
                            running = false;
                        }
                    }
                }
            } catch (SocketException se) {
                System.out.println(se.getMessage());
                System.out.println("Type \"Y\" to reconnect or anything else to exit");
                if ((input = ConsoleReader.readLine()) == null || !"Y".equals(input.toUpperCase(Locale.ROOT))) {
                    running = false;
                } else {
                    System.out.println("Reconnecting...");
                }
            }
        }

        System.out.println("Shutting down");
        userInterface.closeConnection();
    }
}
