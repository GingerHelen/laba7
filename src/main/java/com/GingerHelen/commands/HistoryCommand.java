package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.OutputManager;

import javax.script.ScriptException;
import java.util.Queue;

/**
 * класс, реализующий команду history (вывод названия последних 8 вызванных команд)
 */
public class HistoryCommand extends Command {
    private final Queue<Command> history;
    private final OutputManager outputManager;
    public HistoryCommand(Queue<Command> history, OutputManager outputManager) {
        super("history", "вывести последние 8 команд без аргументов");
        this.history = history;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль названия последних 8 используемых команд
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     * @throws ScriptException если есть ошибки в задании элемента или команды при чтении из скрипта
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        outputManager.printlnImportantMessage("last commands:");
        history.forEach(e -> outputManager.printlnImportantMessage(e.getName()));
    }
}
