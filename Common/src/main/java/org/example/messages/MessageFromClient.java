package org.example.messages;

import org.example.data.Route;

public class MessageFromClient {
    private String command;
    private String argument;
    private Route route;

    public MessageFromClient(String command, String argument, Route route) {
        this.command = command;
        this.argument = argument;
        this.route = route;
    }

    public String getCommand() {
        return command;
    }
    public String getArgument() {
        return argument;
    }
    public Route getRoute() {
        return route;
    }
}
