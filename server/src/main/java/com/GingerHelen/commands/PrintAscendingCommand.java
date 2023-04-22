package com.GingerHelen.commands;

import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду print_ascending (вывод элементов коллекции в порядке возрастания по заданной сортировке
 * по умолчанию)
 */
public class PrintAscendingCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания", Requirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль элементы коллекции, упорядоченные в соответствие
     * с сортировкой по умолчанию
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        StringBuilder response = new StringBuilder("\n");
        collectionManager.printAscending().forEach((k, v) -> response.append(k + ": " + v));
        return new Response(ResponseCode.OK, response.toString());
    }
}
