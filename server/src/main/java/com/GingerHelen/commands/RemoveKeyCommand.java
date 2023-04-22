package com.GingerHelen.commands;

import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду remove_key (удаление элемента коллекции по его ключу)
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager){
        super("remove_key","удалить элемент из коллекции по его ключу", Requirement.ARGUMENT);
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long и в коллекции есть элемент с таким ключом, команда
     * удаляет этот элемент из коллекции
     * @param argument key, принадлежащий элементу, который будет удален
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "this command needs a number argument (key)");
        }
        try {
            collectionManager.remove(Long.parseLong(argument));
            return new Response(ResponseCode.OK);
        } catch (NumberFormatException e) {
            return new Response(ResponseCode.ERROR, "this command needs a NUMBER argument!");
        }
    }
}
