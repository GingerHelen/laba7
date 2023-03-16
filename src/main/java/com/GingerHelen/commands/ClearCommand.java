package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
     }
     public void execute(String argument) throws IllegalArgumentException {
            if (!argument.isEmpty()) {
                throw new IllegalArgumentException("this command doesen't need an argument");
            }
            collectionManager.clear();
     }
}
