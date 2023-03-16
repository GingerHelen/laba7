package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import javax.script.ScriptException;

public class MinByHouseCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;
    public MinByHouseCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("min_by_house","вывести любой объект из коллекции, значение поля house которого является минимальным");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }


    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("the command shouldn't contain any argument");
        }
        outputManager.printlnImportantMessage("a flat with min value of house field: " + collectionManager.minByHouse());
    }
}
