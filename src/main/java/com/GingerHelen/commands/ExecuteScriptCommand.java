package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.InputManager;

import javax.script.ScriptException;

public class ExecuteScriptCommand extends Command {
    private final InputManager inputManager;
    public ExecuteScriptCommand(InputManager inputManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.inputManager = inputManager;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if(argument.isEmpty()) {
            throw new IllegalArgumentException("the command should contain the filename as an argument");
        }
        inputManager.startReadScript(argument);
    }
}
