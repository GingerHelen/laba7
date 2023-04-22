package com.GingerHelen.commands;

import com.GingerHelen.data.Flat;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.Requirement;
import com.GingerHelen.utility.Response;
import com.GingerHelen.utility.ResponseCode;

/**
 * класс, реализующий команду remove_lower (удаление из коллекции всех элементов, меньших, чем заданный)
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower"," удалить из коллекции все элементы, меньшие, чем заданный", Requirement.FLAT);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда запрашивает у пользователя значения элемента и удаляет из коллекции все элементы,
     * значения которых меньше, чем заданное
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg == null) {
            return new Response(ResponseCode.ERROR, "this command doesn't need an argument, but need an object argument");
        }
        collectionManager.removeLower((Flat) objArg);
        return new Response(ResponseCode.OK);
    }
}
