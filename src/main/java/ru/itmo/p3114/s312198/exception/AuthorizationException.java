package ru.itmo.p3114.s312198.exception;

import java.io.IOException;

public class AuthorizationException extends IOException {
    public AuthorizationException() {
        super("Unable to authorize");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
