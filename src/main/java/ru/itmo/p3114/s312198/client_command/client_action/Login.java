package ru.itmo.p3114.s312198.client_command.client_action;

import ru.itmo.p3114.s312198.util.CommandOutput;
import ru.itmo.p3114.s312198.util.command.actions.Status;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Login extends AbstractClientCommand {
    public Login() {
        super("login", ".+");
    }

    public Login(ArrayList<String> arguments) {
        this();
        setArguments(arguments);
    }

    private String SHA1(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(input.getBytes(StandardCharsets.UTF_8), 0, input.length());
            return DatatypeConverter.printHexBinary(messageDigest.digest());
        } catch (NoSuchAlgorithmException exc) {
            return null;
        }
    }

    @Override
    public CommandOutput execute() {
        ArrayList<String> output = new ArrayList<>();
        if (arguments == null || arguments.size() < 2) {
            return new CommandOutput(Status.INCORRECT_ARGUMENTS, null);
        } else {
            output.add(arguments.get(0));
            output.add(SHA1(arguments.get(0) + arguments.get(1)));
            return new CommandOutput(Status.OK, output);
        }
    }
}