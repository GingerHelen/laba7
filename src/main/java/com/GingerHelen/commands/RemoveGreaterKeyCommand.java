package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;

import javax.script.ScriptException;

/**
 * класс, реализующий команду remove_greater_key (удаление из коллекции всех элементов, ключ которых превышает заданный)
 */
public class RemoveGreaterKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super("remove_greater_key","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long, команда
     *   удаляет все элементы, ключ которых превышает заданный
     * @param argument key, превышение которого у элемента ведет к его удалению
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
            collectionManager.removeGreaterKey(Long.parseLong(argument));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("this command needs a NUMBER argument!!!");
        }
    }
}
