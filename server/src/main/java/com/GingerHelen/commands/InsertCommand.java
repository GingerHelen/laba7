package com.GingerHelen.commands;

import com.GingerHelen.data.Flat;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду insert (добавление элемента с заданным ключом)
 */
public class InsertCommand extends Command {
    private final  CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert","добавить новый элемент с заданным ключом", Requirement.FLATARGUMENT);
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
