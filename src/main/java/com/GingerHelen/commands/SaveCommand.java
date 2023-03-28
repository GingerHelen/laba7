package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import java.io.IOException;

/**
 * класс, реализующий команду save
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    private final OutputManager outputManager;

    public SaveCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда сохраняет коллекцию в файл
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     * @throws IOException ошибка при чтении
     */
    public void execute(String argument) throws IllegalArgumentException, IOException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.save();
    }
}
