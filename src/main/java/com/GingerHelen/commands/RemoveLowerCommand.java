package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.FlatFillerMain;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * класс, реализующий команду remove_lower (удаление из коллекции всех элементов, меньших, чем заданный)
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final FlatFillerMain flatFillerMain;
    public RemoveLowerCommand(CollectionManager collectionManager, FlatFillerMain flatFillerMain) {
        super("remove_lower"," удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
        this.flatFillerMain = flatFillerMain;
    }

    /**
     * если аргумент пустой, команда запрашивает у пользователя значения элемента и удаляет из коллекции все элементы,
     * значения которых меньше, чем заданное
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     * @throws ScriptException если есть ошибки в задании элемента или команды при чтении из скрипта
     * @throws InvalidInputException ошибка при вводе
     * @throws IOException ошибка при чтении
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException, IOException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.removeLower(flatFillerMain.fillFlat());
    }
}
