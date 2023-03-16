package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import java.io.IOException;

public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    private final OutputManager outputManager;

    public SaveCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }
    public void execute(String argument) throws IllegalArgumentException, IOException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.save();
    }
}
