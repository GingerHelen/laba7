package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import javax.script.ScriptException;

/**
 * класс, реализующий команду show
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;

    public ShowCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль элементы коллекции, упорядоченные по ключам в строковом представлении
     * @param argument - аргумент должен быть пустой, чтобы команда сработала корректно
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     * @throws ScriptException если есть ошибки в задании элемента или команды при чтении из скрипта
     * @throws InvalidInputException - ошибка при вводе
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        outputManager.printlnImportantMessage(collectionManager.toString());
    }
}
