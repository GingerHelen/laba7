package com.GingerHelen.commands;

import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду execute_script (считывание и исполнения скрипта из указанного файла)
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        super("execute_script", "считать и исполнить скрипт из указанного файла", Requirement.ARGUMENT);
    }

    /**
     * если переданный аргумент - путь к файлу со скриптом, программа начинает читать и исполнять команды из указанного файла
     * в случае, если по указанному пути не будет найден файл, выведется соответсвующее сообщение
     * @param argument путь к файлу со скриптом
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if(argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "the command should contain the filename as an argument");
        }
        return new Response(ResponseCode.READ_SCRIPT, argument);
    }
}
