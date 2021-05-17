package ru.itmo.p3114.s312198.authorization;

import java.io.Serializable;

public class AuthorizationFeedback implements Serializable {
    private final String serverMessage;
    private final boolean allowed;

    public AuthorizationFeedback(String serverMessage, boolean allowed) {
        this.serverMessage = serverMessage;
        this.allowed = allowed;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public boolean isAllowed() {
        return allowed;
    }
}
