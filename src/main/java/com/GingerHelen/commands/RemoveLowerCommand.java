package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.FlatFillerMain;

import javax.script.ScriptException;
import java.io.IOException;

public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final FlatFillerMain flatFillerMain;
    public RemoveLowerCommand(CollectionManager collectionManager, FlatFillerMain flatFillerMain) {
        super("remove_lower"," удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
        this.flatFillerMain = flatFillerMain;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException, IOException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.removeLower(flatFillerMain.fillFlat());
    }
}
