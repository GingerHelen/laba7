package com.GingerHelen.utility;

import java.io.Serializable;
import java.util.HashMap;

public class StartResponse implements Serializable {
    private final HashMap<String, Requirement> commands;

    public StartResponse(HashMap<String, Requirement> commands) {
        this.commands = commands;
    }

    public HashMap<String, Requirement> getCommands() {
        return commands;
    }
}
