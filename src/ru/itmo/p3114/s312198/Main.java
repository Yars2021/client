package ru.itmo.p3114.s312198;

public class Main {
    public static void main(String[] args) {
        System.out.println("Searching for the server...");
        UserInterface userInterface = new UserInterface();
        userInterface.connect("localhost", 6547);
        userInterface.sendCommand();
        userInterface.writeOutput();
        userInterface.closeConnection();
    }
}
