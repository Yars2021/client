package ru.itmo.p3114.s312198.exception;

public class EndOfSessionException extends AuthorizationException {
    public EndOfSessionException() {
        super("");
    }

    public EndOfSessionException(String message) {
        super(message);
    }
}
