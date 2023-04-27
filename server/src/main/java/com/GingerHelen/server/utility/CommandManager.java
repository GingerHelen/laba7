package com.GingerHelen.server.utility;

import com.GingerHelen.server.commands.*;
import com.GingerHelen.common.utility.Requirement;
import com.GingerHelen.common.utility.Response;
import com.GingerHelen.common.utility.ResponseCode;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;


/**
 * класс, содержащий множество доступных пользователю команд и регулирующий их работу
 */
public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();
    private final HashMap<String, Requirement> commandsWithRequirements = new HashMap<>();
    private final Queue<Command> history = new ArrayDeque<>();
    private final static int numberOfElements = 8;
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        commands.add(new ClearCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new HelpCommand(commands));
        commands.add(new ExitCommand());
        commands.add(new PrintAscendingCommand(collectionManager));
        commands.add(new GroupCountingByTransportCommand(collectionManager));
        commands.add(new InsertCommand(collectionManager));
        commands.add(new MinByHouseCommand(collectionManager));
        commands.add(new RemoveGreaterKeyCommand(collectionManager));
        commands.add(new RemoveKeyCommand(collectionManager));
        commands.add(new RemoveLowerCommand(collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateIdCommand(collectionManager));
        commands.add(new ExecuteScriptCommand());
        commands.add(new HistoryCommand(history));

        commands.forEach(e -> commandsWithRequirements.put(e.getName(), e.getRequirement()));

        this.collectionManager = collectionManager;
    }

    /**
     * исполнить команду
     * @param stringCommand название команды
     * @param argument аргумент команды (может быть пустым)
     * @return executeFlag выполненной команды
     */
    public Response executeCommand(String stringCommand, String argument, Object objArg) {
        Response response;
        if (commands.stream().anyMatch(e -> e.getName().equals(stringCommand))) {
            Command command = commands.stream().filter(e -> e.getName().equals(stringCommand)).findFirst().get();
            response = command.execute(argument, objArg);
            addToHistory(command);
        } else {
            return new Response(ResponseCode.ERROR, "no such command");
        }
        return response;
    }

    /**
     * добавляет команду в историю в случае исполнения (в методе executeCommand)
     * @param command исполненная команда
     */
    private void addToHistory(Command command) {
        if (history.size() == numberOfElements) {
            history.poll();
        }
        history.add(command);
    }

    public HashMap<String, Requirement> getCommandsWithRequirements() {
        return commandsWithRequirements;
    }

    public boolean save() {
        try {
            collectionManager.save();
            return true;
        } catch (IOException e) {
            System.out.println("error during saving");
            return false;
        }
    }
}
