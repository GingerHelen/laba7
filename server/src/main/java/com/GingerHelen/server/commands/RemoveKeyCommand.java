package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду remove_key (удаление элемента коллекции по его ключу)
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key","remove an item from the collection by its key", Requirement.ARGUMENT);
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long и в коллекции есть элемент с таким ключом, команда
     * удаляет этот элемент из коллекции
     * @param argument key, принадлежащий элементу, который будет удален
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if (argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command needs a number argument (key)");
        }
        try {
            collectionManager.remove(username, Long.parseLong(argument));
            return new Response(ResponseCode.OK);
        } catch (NumberFormatException e) {
            return new Response(ResponseCode.ERROR, "this command needs a NUMBER argument!");
        }
    }
}
