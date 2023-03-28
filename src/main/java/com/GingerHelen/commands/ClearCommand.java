package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;

/**
 * класс, реализующий команду clear
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
     }

    /**
     * если аргумент пустой, команда удаляет все элементы в коллекции
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
     public void execute(String argument) throws IllegalArgumentException {
            if (!argument.isEmpty()) {
                throw new IllegalArgumentException("this command doesn't need an argument");
            }
            collectionManager.clear();
     }
}
