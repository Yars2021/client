package ru.itmo.p3114.s312198.client_command.client_action;

import ru.itmo.p3114.s312198.command.CommandOutput;
import ru.itmo.p3114.s312198.command.actions.Status;

public class NoOperation extends AbstractClientCommand {
    public NoOperation() {
        super("nop", ".*");
    }

    @Override
    public CommandOutput execute() {
        return new CommandOutput(Status.UNDEFINED, null);
    }
}
