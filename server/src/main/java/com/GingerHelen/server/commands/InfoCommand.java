package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

/**
 * класс, реализущий команду info (вывод в стандартный поток информации о коллекции)
 */
public class InfoCommand extends Command {

    private final CollectionManager collectionManager;


    public InfoCommand(CollectionManager collectionManager) {
        super("info","вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д. )",
                Requirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда выводит в консоль информацию о коллекции (тип, дата инциализации, количество элементов и тд.)
     * @param argument должен быть пустым для корректной работы программы
     */
    public Response execute(String argument, Object objArg) {
        if (!argument.isEmpty() || objArg != null) {
             new Response(ResponseCode.ERROR,"this command doesn't need an argument");
        }
        return new Response(ResponseCode.TEXT, collectionManager.collectionInfo());
    }
}
