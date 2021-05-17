package ru.itmo.p3114.s312198.client_command;

import ru.itmo.p3114.s312198.client_command.client_action.AbstractClientCommand;
import ru.itmo.p3114.s312198.client_command.client_action.Exit;
import ru.itmo.p3114.s312198.client_command.client_action.Login;
import ru.itmo.p3114.s312198.client_command.client_action.NoOperation;
import ru.itmo.p3114.s312198.client_command.client_action.Register;
import ru.itmo.p3114.s312198.exception.IncorrectPasswordException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ClientCommandLineProcessor {
    public AbstractClientCommand parseUserInput(String line) {
        AbstractClientCommand command = null;
        ArrayList<String> args;
        ClientCommands cmd;

        if (line == null) {
            cmd = ClientCommands.EXIT;
        } else {
            try {
                cmd = ClientCommands.valueOf(line.trim().split("\\s")[0].toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exc) {
                System.out.println(line + " is not a valid input.\nUse \"help\" to see the command reference");
                cmd = ClientCommands.NOP;
            }
        }

        switch (cmd) {
            case NOP:
                command = new NoOperation();
                break;
            case LOGIN:
                try {
                    args = requestLogin();
                    command = new Login(args);
                } catch (IncorrectPasswordException passExc) {
                    System.out.println("Passwords are different");
                    command = new NoOperation();
                }
                break;
            case REGISTER:
                try {
                    args = requestRegistration();
                    command = new Register(args);
                } catch (IncorrectPasswordException passExc) {
                    System.out.println("Passwords are different");
                    command = new NoOperation();
                }
                break;
            case EXIT:
                command = new Exit();
                break;
        }
        return command;
    }

    private String requestUsername() {
        String input;
        System.out.println("Enter your username: ");
        while ((input = System.console().readLine()) == null || input.trim().isEmpty()) {
        }
        return input;
    }

    private String requestPassword(boolean doubleCheck) throws IncorrectPasswordException {
        String input, verification;
        System.out.println("Enter your password: ");
        while ((input = Arrays.toString(System.console().readPassword())).trim().isEmpty()) {
        }
        if (doubleCheck) {
            System.out.println("Enter your password one more time: ");
            while ((verification = Arrays.toString(System.console().readPassword())).trim().isEmpty()) {
            }
            if (input.equals(verification)) {
                return input;
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            return input;
        }
    }

    private ArrayList<String> requestLogin() throws IncorrectPasswordException {
        ArrayList<String> userInput = new ArrayList<>();
        userInput.add(requestUsername());
        userInput.add(requestPassword(false));
        return userInput;
    }

    private ArrayList<String> requestRegistration() throws IncorrectPasswordException {
        ArrayList<String> userInput = new ArrayList<>();
        userInput.add(requestUsername());
        userInput.add(requestPassword(true));
        return userInput;
    }
}
