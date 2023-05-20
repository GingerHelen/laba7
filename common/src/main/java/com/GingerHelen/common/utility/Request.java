package com.GingerHelen.common.utility;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Locale;

/**
 * класс модели запроса от клиента серверу, содержащая команду и ее аргументы
 */
@Getter
@Setter
public class Request implements Serializable {
    private final String commandName;
    private final String argument;
    private Object object;
    private final User user;

    public Request(String commandName, String argument, User user) {
        this.commandName = commandName;
        this.argument = argument;
        this.user = user;
    }
}
