package com.GingerHelen.server.commands;

import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

import java.util.Queue;
import java.util.StringJoiner;

/**
 * класс, реализующий команду history (вывод названия последних 8 вызванных команд)
 */
public class HistoryCommand extends Command {
    private final Queue<Command> history;

    public HistoryCommand(Queue<Command> history) {
        super("history", "вывести последние 8 команд без аргументов", Requirement.NONE);
        this.history = history;
    }

    /**
     * если аргумент пустой, команда выводит в консоль названия последних 8 используемых команд
     * @param argument должен быть пустым для корректной работы программы
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg != null) {
            new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        StringJoiner response = new StringJoiner("\n");
        response.add("last commands:");
        history.forEach(e -> response.add(e.getName()));
        return new Response(ResponseCode.TEXT, response.toString());
    }
}
