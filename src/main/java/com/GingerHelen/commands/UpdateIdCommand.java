package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.NoSuchCommandException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.FlatFillerMain;

import javax.script.ScriptException;
import java.io.IOException;

public class UpdateIdCommand extends Command {
    private final CollectionManager collectionManager;
    private final FlatFillerMain flatFillerMain;
    public UpdateIdCommand(CollectionManager collectionManager, FlatFillerMain flatFillerMain){
        super("update","обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.flatFillerMain = flatFillerMain;
    }

    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException, IOException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("this command needs a number argument (id)");
        }
        try {
            Integer id = Integer.parseInt(argument);
            if (!collectionManager.containsId(id)) {
                throw new IllegalArgumentException("no element to update");
            }
            collectionManager.update(id, flatFillerMain.fillFlat());
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("id should be a number");
        }
    }
}
