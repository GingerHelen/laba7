package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.OutputManager;

import javax.script.ScriptException;
import java.util.Queue;

public class HistoryCommand extends Command {
    private final Queue<Command> history;
    private final OutputManager outputManager;
    public HistoryCommand(Queue<Command> history, OutputManager outputManager) {
        super("history", "вывести последние 8 команд без аргументов");
        this.history = history;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        outputManager.printlnImportantMessage("last commands:");
        history.forEach(e -> outputManager.printlnImportantMessage(e.getName()));
    }
}
