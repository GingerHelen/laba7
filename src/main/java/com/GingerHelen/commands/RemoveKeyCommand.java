package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;

import javax.script.ScriptException;

/**
 * класс, реализующий команду remove_key (удаление элемента коллекции по его ключу)
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key","удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long и в коллекции есть элемент с таким ключом, команда
     * удаляет этот элемент из коллекции
     * @param argument key, принадлежащий элементу, который будет удален
     * @throws IllegalArgumentException в случае, если аргумент пустой или не является число типа Long
     * @throws ScriptException если есть ошибки в задании элемента или команды при чтении из скрипта
     * @throws InvalidInputException ошибка при вводе
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (argument.isEmpty()) {
            throw new IllegalArgumentException("this command needs a number argument (key)");
        }
        try {
            collectionManager.remove(Long.parseLong(argument));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("this command needs a NUMBER argument!");
        }
    }
}
