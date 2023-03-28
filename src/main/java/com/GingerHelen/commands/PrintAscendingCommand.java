package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

/**
 * класс, реализующий команду print_ascending (вывод элементов коллекции в порядке возрастания по заданной сортировке
 * по умолчанию)
 */
public class PrintAscendingCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public PrintAscendingCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль элементы коллекции, упорядоченные в соответствие
     * с сортировкой по умолчанию
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        collectionManager.printAscending().forEach((k, v) -> outputManager.printlnImportantMessage(k + ": " + v));
    }
}
