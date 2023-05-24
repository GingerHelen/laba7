package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;


/**
 * класс, реализующий команду remove_greater_key (удаление из коллекции всех элементов, ключ которых превышает заданный)
 */
public class RemoveGreaterKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super("remove_greater_key","remove from the collection all items whose key exceeds the specified one",
                Requirement.ARGUMENT);
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long, команда
     *   удаляет все элементы, ключ которых превышает заданный
     * @param argument key, превышение которого у элемента ведет к его удалению
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if (argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command needs a number argument (key)");
        }
        try {
            collectionManager.removeGreaterKey(username, Long.parseLong(argument));
            return new Response(ResponseCode.OK);
        } catch (NumberFormatException e) {
            return new Response(ResponseCode.ERROR, "this command needs a NUMBER argument!!!");
        }
    }
}
