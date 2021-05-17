package ru.itmo.p3114.s312198.util;

import ru.itmo.p3114.s312198.authorization.UserSignature;
import ru.itmo.p3114.s312198.util.command.actions.AbstractCommand;

public class DataPacket {
    private final AbstractCommand command;
    private final UserSignature userSignature;

    public DataPacket(AbstractCommand command, UserSignature userSignature) {
        this.command = command;
        this.userSignature = userSignature;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public UserSignature getUserSignature() {
        return userSignature;
    }
}
