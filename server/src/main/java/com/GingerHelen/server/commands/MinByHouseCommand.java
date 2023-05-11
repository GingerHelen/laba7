package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализующий команду min_by_house (вывод любого объекта из коллекции, значение поля
 * house которого является минимальным)
 */
public class MinByHouseCommand extends Command {
    private final CollectionManager collectionManager;
    public MinByHouseCommand(CollectionManager collectionManager) {
        super("min_by_house","output any object from the collection whose house field value is minimal", Requirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль любой объект с минимальным значением поля
     * @param argument аргумент должен быть пустой, чтобы команда сработала корректно
     */
    @Override
    public Response execute(String argument, Object objArg){
        if (!argument.isEmpty() || objArg != null) {
            return new Response(ResponseCode.ERROR, "the command shouldn't contain any argument and object argument");
        }
        return new Response(ResponseCode.TEXT, "a flat with min value of house field: " + collectionManager.minByHouse());
    }
}
