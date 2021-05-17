package ru.itmo.p3114.s312198.exception;

public class IncorrectPasswordException extends AuthorizationException {
    public IncorrectPasswordException() {
        super("The password is incorrect");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
