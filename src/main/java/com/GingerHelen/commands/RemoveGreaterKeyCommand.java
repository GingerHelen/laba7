package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;

import javax.script.ScriptException;

public class RemoveGreaterKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super("remove_greater_key","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("this command needs a number argument (key)");
        }
        try {
            collectionManager.removeGreaterKey(Long.parseLong(argument));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("this command needs a NUMBER argument!!!");
        }
    }
}
