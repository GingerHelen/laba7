package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

public class PrintAscendingCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public PrintAscendingCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }


    @Override
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.printAscending().forEach((k, v) -> outputManager.printlnImportantMessage(k + ": " + v));
    }
}
