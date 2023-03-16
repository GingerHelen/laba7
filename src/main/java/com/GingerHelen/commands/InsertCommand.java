package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.FlatFillerMain;

import javax.script.ScriptException;
import java.io.IOException;

public class InsertCommand extends Command {
    private final  CollectionManager collectionManager;

    private final FlatFillerMain flatFillerMain;
    public InsertCommand(CollectionManager collectionManager, FlatFillerMain flatFillerMain) {
        super("insert","добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.flatFillerMain = flatFillerMain;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException, IOException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("a command should contain a number argument");
        }
        try {
            Long key = Long.parseLong(argument);
            if (collectionManager.containsKey(key)) {
                throw new IllegalArgumentException("this key is already in use");
            }
            collectionManager.addToCollection(key, flatFillerMain.fillFlat());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("the argument should be a NUMBER!!!");
        }
    }
}
