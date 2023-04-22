package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.ScriptException;
import com.GingerHelen.utility.RequestManager;
import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;

import java.io.IOException;

/**
 * абстрактный класс - родитель всех команд
 */
public abstract class Command {
    private final String name;
    private final String description;
    private boolean executeFlag = true;
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

    public boolean getExecuteFlag() {
        return executeFlag;
    }
    public Requirement getRequirement() {
        return requirement;
    }

    /**
     * устанавливает значение переменной executeFlag
     * @param executeFlag true - после исполнения команды программа продолжает считывать и исполнять команды, false - команды далее не считываются
     */
    public void setExecuteFlag(boolean executeFlag) {
        this.executeFlag = executeFlag;
    }
}