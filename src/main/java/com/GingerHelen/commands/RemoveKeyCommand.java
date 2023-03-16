package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;

import javax.script.ScriptException;

public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key","удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("this command needs a number argument (key)");
        }
        try {
            collectionManager.remove(Long.parseLong(argument));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("this command needs a NUMBER argument!!!");
        }
    }
}
