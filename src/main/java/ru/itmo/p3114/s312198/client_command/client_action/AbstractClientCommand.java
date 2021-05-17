package ru.itmo.p3114.s312198.client_command.client_action;

import ru.itmo.p3114.s312198.util.CommandOutput;

import java.util.ArrayList;

abstract public class AbstractClientCommand {
    protected final String command;
    protected final String inputPattern;
    protected ArrayList<String> arguments;

    public AbstractClientCommand(String command, String inputPattern) {
        this.command = command;
        this.inputPattern = inputPattern;
    }

    public AbstractClientCommand(String command, ArrayList<String> arguments, String inputPattern) {
        this.command = command;
        this.arguments = arguments;
        this.inputPattern = inputPattern;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public String getInputPattern() {
        return inputPattern;
    }

    abstract public CommandOutput execute();
}