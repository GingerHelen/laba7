package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду show
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show","output all elements of the collection in a string representation",
                Requirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль элементы коллекции, упорядоченные по ключам в строковом представлении
     * @param argument - аргумент должен быть пустой, чтобы команда сработала корректно
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if (!argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        return new Response(ResponseCode.TEXT, collectionManager.toString());
    }
}
