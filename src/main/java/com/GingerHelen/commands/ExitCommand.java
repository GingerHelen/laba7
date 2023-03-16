package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл!)");
    }
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        setExecuteFlag(false);
    }
}
