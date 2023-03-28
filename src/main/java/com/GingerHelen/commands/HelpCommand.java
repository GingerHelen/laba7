package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.OutputManager;

import java.util.ArrayList;

/**
 * класс, реализующий команду help (вывод список всех доступных команд и их краткие описания)
 */
public class HelpCommand extends Command {

    private final ArrayList<Command> commands;

    private final OutputManager outputManager;

    public HelpCommand(ArrayList<Command> commands, OutputManager outputManager) {
        super("help","вывести справку по доступным командам");
        this.commands = commands;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль справку о всех командах
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        outputManager.printlnImportantMessage("Список доступных комманд:");
        for (Command command: commands) {
            outputManager.printlnImportantMessage(command.getName() + ": " + command.getDescription());
        }
    }
}
