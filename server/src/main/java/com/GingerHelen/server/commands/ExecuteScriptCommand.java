package com.GingerHelen.server.commands;

import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду execute_script (считывание и исполнения скрипта из указанного файла)
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand() {
        super("execute_script", "read and execute the script from the specified file", Requirement.ARGUMENT);
    }

    /**
     * если переданный аргумент - путь к файлу со скриптом, программа начинает читать и исполнять команды из указанного файла
     * в случае, если по указанному пути не будет найден файл, выведется соответсвующее сообщение
     * @param argument путь к файлу со скриптом
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if(argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "the command should contain the filename as an argument");
        }
        return new Response(ResponseCode.READ_SCRIPT, argument);
    }
}
