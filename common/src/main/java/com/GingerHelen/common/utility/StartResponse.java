package com.GingerHelen.common.utility;

import java.io.Serializable;
import java.util.HashMap;

/**
 * класс, реализующий первый ответ от сервера клиенту, через который он получает сведения о доступных ему командах
 */
public class StartResponse implements Serializable {
    private final HashMap<String, Requirement> commands;

    public StartResponse(HashMap<String, Requirement> commands) {
        this.commands = commands;
    }

    public HashMap<String, Requirement> getCommands() {
        return commands;
    }
}
