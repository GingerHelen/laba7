package com.GingerHelen.server.commands;

import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * класс, реализующий команду help (вывод список всех доступных команд и их краткие описания)
 */
public class HelpCommand extends Command {

    private final ArrayList<Command> commands;

    public HelpCommand(ArrayList<Command> commands) {
        super("help","output help for available commands", Requirement.NONE);
        this.commands = commands;
    }

    /**
     * если аргумент пустой, команда выводит в консоль справку о всех командах
     * @param argument должен быть пустым для корректной работы программы
     */
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg != null) {
            new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        StringJoiner response = new StringJoiner("\n");
        response.add("List of available commands:");
        for (Command command : commands) {
            response.add(command.getName() + ": " + command.getDescription());
        }
        return new Response(ResponseCode.TEXT, response.toString());
    }
}
