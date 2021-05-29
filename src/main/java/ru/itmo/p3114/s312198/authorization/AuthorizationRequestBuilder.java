package ru.itmo.p3114.s312198.authorization;

import ru.itmo.p3114.s312198.user.UserSignature;
import ru.itmo.p3114.s312198.client_command.client_action.AbstractClientCommand;
import ru.itmo.p3114.s312198.exception.EndOfSessionException;
import ru.itmo.p3114.s312198.exception.InvalidCommandException;
import ru.itmo.p3114.s312198.util.CommandOutput;

public class AuthorizationRequestBuilder {
    public static AuthorizationRequest makeRequest(AbstractClientCommand clientCommand) throws InvalidCommandException, EndOfSessionException {
        switch (clientCommand.getCommand()) {
            case "login": case "register":
                CommandOutput commandOutput = clientCommand.execute();
                return new AuthorizationRequest(commandOutput.getOutput().get(0),
                        new UserSignature(commandOutput.getOutput().get(1), commandOutput.getOutput().get(2)));
            case "exit":
                throw new EndOfSessionException();
            default:
                throw new InvalidCommandException();
        }
    }
}
