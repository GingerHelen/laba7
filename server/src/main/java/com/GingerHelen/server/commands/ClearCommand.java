package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду clear
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear the collection", Requirement.NONE);
        this.collectionManager = collectionManager;
     }

    /**
     * если аргумент пустой, команда удаляет все элементы в коллекции
     * @param argument должен быть пустым для корректной работы программы
     */
     public Response execute(String argument, Object objArg) {
            if (!argument.isEmpty() || objArg != null) {
                return new Response(ResponseCode.ERROR, "this command doesn't need an argument");
            }
            collectionManager.clear();
            return new Response(ResponseCode.OK);
     }
}
