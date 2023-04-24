package com.GingerHelen.commands;

import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду exit (без сохранения в файл)
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл!)", Requirement.NONE);
    }

    /**
     * если аргумент пустой, команда завершает работу программы
     * @param argument должен быть пустым для корректной работы программы
     */
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        return new Response(ResponseCode.EXIT);
    }
}
