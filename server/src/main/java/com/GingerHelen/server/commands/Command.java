package com.GingerHelen.server.commands;

import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;

/**
 * абстрактный класс - родитель всех команд
 */
public abstract class Command {
    private final String name;
    private final String description;
    private final Requirement requirement;

    public Command(String name, String description, Requirement requirement) {
        this.name = name;
        this.description = description;
        this.requirement = requirement;
    }

    public abstract Response execute(String argument, Object objArg);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}