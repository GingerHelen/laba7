package com.GingerHelen.common.utility;

import java.io.Serializable;
import java.util.Locale;

/**
 * класс модели запроса от клиента серверу, содержащая команду и ее аргументы
 */
public class Request implements Serializable {
    private final String commandName;
    private final String argument;
    private final Locale locale;
    private Object object;

    public Request(String commandName, String argument, Locale locale) {
        this.commandName = commandName;
        this.argument = argument;
        this.locale = locale;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgument() {
        return argument;
    }
    public Locale getLocale() {return locale;}

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
