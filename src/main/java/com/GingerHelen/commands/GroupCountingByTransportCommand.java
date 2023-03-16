package com.GingerHelen.commands;

import com.GingerHelen.data.Transport;
import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.OutputManager;

import java.util.Map;

public class GroupCountingByTransportCommand extends Command {
    private final CollectionManager collectionManager;
    private final OutputManager outputManager;
    public GroupCountingByTransportCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("group_counting_by_transport", "сгруппировать элементы коллекции по значению поля transport, " +
                "вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }


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
