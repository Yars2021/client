package ru.itmo.p3114.s312198.client_command.client_action;

import ru.itmo.p3114.s312198.util.CommandOutput;
import ru.itmo.p3114.s312198.util.command.actions.Status;

import java.util.ArrayList;

public class Exit extends AbstractClientCommand {
    public Exit() {
        super("exit", ".+");
    }

    public Exit(ArrayList<String> arguments) {
        this();
        setArguments(arguments);
    }

    @Override
    public CommandOutput execute() {
        return new CommandOutput(Status.OK, null);
    }
}
