package com.GingerHelen.server.commands;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.common.data.Transport;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

import java.util.Map;
import java.util.StringJoiner;

/**
 * класс, реализующий команду group_counting_by_transport (группировка элементов коллекции по значению поля transport и
 * вывод количество элементов в каждой группе)
 */
public class GroupCountingByTransportCommand extends Command {
    private final CollectionManager collectionManager;

    public GroupCountingByTransportCommand(CollectionManager collectionManager) {
        super("group_counting_by_transport", "group the collection items by the value of the transport field, " +
                "output the number of items in each group", Requirement.NONE);
        this.collectionManager = collectionManager;
    }

    /**
     * если аргумент пустой, команда проверяет коллекцию на пустоту и в положительном исходе
     * группирует элементы коллекции по значению поля transport и выводит в консоль количество
     * элементов в каждой из получившихся групп
     * @param argument должен быть пустым для корректной работы программы
     */
    @Override
    public Response execute(String argument, Object objArg, String username) {
        if (!argument.isEmpty() || objArg != null) {
            new Response(ResponseCode.ERROR, "this command doesn't need an argument");
        }
        Map<Transport, Long> res = collectionManager.groupCountingByTransport();
        if (res.size() == 0) {
            return new Response(ResponseCode.OK, "the collection is empty or all the elements have null value of" +
                    " transport field");
        }
        StringJoiner response = new StringJoiner("\n");
        res.forEach((k, v) -> response.add(k + ": " + v));
        return new Response(ResponseCode.OK, response.toString());
    }
}
