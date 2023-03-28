package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;

/**
 * класс, реализующий команду exit (без сохранения в файл)
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл!)");
    }

    /**
     * если аргумент пустой, команда завершает работу программы
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        setExecuteFlag(false);
    }
}
