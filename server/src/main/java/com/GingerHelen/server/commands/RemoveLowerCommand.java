package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду remove_lower (удаление из коллекции всех элементов, меньших, чем заданный)
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower"," remove all items smaller than the specified one from the collection", Requirement.FLAT);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда запрашивает у пользователя значения элемента и удаляет из коллекции все элементы,
     * значения которых меньше, чем заданное
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if (!argument.isEmpty() || objArg == null) {
            return new Response(ResponseCode.ERROR, "this command doesn't need an argument, but need an object argument");
        }
        collectionManager.removeLower((Flat) objArg, username);
        return new Response(ResponseCode.OK);
    }
}
