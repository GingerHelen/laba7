package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import javax.script.ScriptException;

public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public ShowCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        outputManager.printlnImportantMessage(collectionManager.toString());
    }
}
