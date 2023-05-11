package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду insert (добавление элемента с заданным ключом)
 */
public class InsertCommand extends Command {
    private final CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert","add a new element with the specified key", Requirement.FLATARGUMENT);
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Long и в коллекции нет элемента с таким ключом, команда запрашивает у пользователя
     * значения элемента и добавляет его под заданным ключом в коллекцию
     * @param argument key, принадлежащий новому элементу
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (argument.isEmpty() || objArg == null) {
            new Response(ResponseCode.ERROR, "a command should contain a number argument and object argument");
        }
        try {
            Long key = Long.parseLong(argument);
            if (collectionManager.containsKey(key)) {
                return new Response(ResponseCode.ERROR, "this key is already in use");
            }
            collectionManager.addToCollection(key, (Flat) objArg);
            return new Response(ResponseCode.OK);
        } catch (NumberFormatException e) {
            return new Response(ResponseCode.ERROR, "the argument should be a NUMBER!");
        }
    }
}
