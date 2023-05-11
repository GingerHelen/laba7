package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;


/**
 * класс, реализующий команду update (изменение элемента коллекции по заданному id)
 */
public class UpdateIdCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager){
        super("update","update the value of a collection item whose id is equal to the specified one", Requirement.FLATARGUMENT);
        this.collectionManager = collectionManager;
    }

    /**
     * если переданный аргумент - число типа Integer и в коллекции есть элемент с таким id, команда запрашивает новое
     * значение элемента через FlatFillerMain и обновляет элемент в коллекции
     * @param argument id, по которому будет изменен элемент
     */
    @Override
    public Response execute(String argument, Object objArg) {
        if (argument.isEmpty() || objArg == null) {
            return new Response(ResponseCode.ERROR, "this command needs a number argument (id) and object argument");
        }
        try {
            Integer id = Integer.parseInt(argument);
            if (!collectionManager.containsId(id)) {
                return new Response(ResponseCode.ERROR,"no element to update");
            }
            collectionManager.update(id, (Flat) objArg);
            return new Response(ResponseCode.OK);
        } catch(NumberFormatException e) {
            return new Response(ResponseCode.ERROR,"id should be a number");
        }
    }
}
