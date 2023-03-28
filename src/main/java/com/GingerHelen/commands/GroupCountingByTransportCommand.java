package com.GingerHelen.commands;

import com.GingerHelen.data.Transport;
import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import java.util.Map;

/**
 * класс, реализующий команду group_counting_by_transport (группировка элементов коллекции по значению поля transport и
 * вывод количество элементов в каждой группе)
 */
public class GroupCountingByTransportCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;
    public GroupCountingByTransportCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("group_counting_by_transport", "сгруппировать элементы коллекции по значению поля transport, " +
                "вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    /**
     * если аргумент пустой, команда проверяет коллекцию на пустоту и в положительном исходе
     * группирует элементы коллекции по значению поля transport и выводит в консоль количество
     * элементов в каждой из получившихся групп
     * @param argument должен быть пустым для корректной работы программы
     * @throws IllegalArgumentException в случае, если аргумент не пустой
     */
    @Override
    public void execute(String argument) throws IllegalArgumentException {
        if (!argument.isEmpty()) {
            throw new IllegalArgumentException("this command doesn't need an argument");
        }
        Map<Transport, Long> res = collectionManager.groupCountingByTransport();
        if (res.size() == 0) {
            outputManager.printlnImportantMessage("the collection is empty or all the elements have null value of" +
                    " transport field");
            return;
        }
        res.forEach((k, v) -> outputManager.printlnImportantMessage(k + ": " + v));
    }
}
