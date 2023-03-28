package com.GingerHelen.commands;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.InputManager;

import javax.script.ScriptException;

/**
 * класс, реализующий команду execute_script (считывание и исполнения скрипта из указанного файла)
 */
public class ExecuteScriptCommand extends Command {
    private final InputManager inputManager;
    public ExecuteScriptCommand(InputManager inputManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла");
        this.inputManager = inputManager;
    }

    /**
     * если переданный аргумент - путь к файлу со скриптом, программа начинает читать и исполнять команды из указанного файла
     * в случае, если по указанному пути не будет найден файл, выведется соответсвующее сообщение
     * @param argument путь к файлу со скриптом
     * @throws IllegalArgumentException в случае, если аргумент пустой
     * @throws ScriptException если есть ошибки в задании элемента или команды при чтении из скрипта
     * @throws InvalidInputException ошибка ввода
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException, ScriptException, InvalidInputException {
        if(argument.isEmpty()) {
            throw new IllegalArgumentException("the command should contain the filename as an argument");
        }
        inputManager.startReadScript(argument);
    }
}
