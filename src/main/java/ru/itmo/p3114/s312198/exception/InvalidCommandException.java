package ru.itmo.p3114.s312198.exception;

public class InvalidCommandException extends AuthorizationException {
    public InvalidCommandException() {
        super("Invalid command, use \"login\" or \"register\"");
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
