package com.GingerHelen.utility;

import java.io.Serializable;

public class Request implements Serializable {
    private final String commandName;
    private final String argument;
    private Object object;

    public Request(String commandName, String argument) {
        this.commandName = commandName;
        this.argument = argument;
    }

    public Request(String commandName, String argument, Object object) {
        this.commandName = commandName;
        this.argument = argument;
        this.object = object;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
