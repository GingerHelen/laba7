package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

/**
 * класс, реализущий команду info (вывод в стандартный поток информации о коллекции)
 */
public class InfoCommand extends Command {

    private final CollectionManager collectionManager;

    private final OutputManager outputManager;

    public InfoCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("info","вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д. )");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль информацию о коллекции (тип, дата инциализации, количество элементов и тд.)
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        outputManager.printlnImportantMessage(collectionManager.collectionInfo());
    }
}
