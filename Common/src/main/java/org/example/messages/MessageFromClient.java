package org.example.messages;

import org.example.data.Route;

public class MessageFromClient {
    private final String commandName;
    private final String argument;
    private final Route route;

    public MessageFromClient(String commandName, String argument, Route route) {
        this.commandName = commandName;
        this.argument = argument;
        this.route = route;
    }

    public String getCommandName() {
        return commandName;
    }
    public String getArgument() {
        return argument;
    }
    public Route getRoute() {
        return route;
    }
}
