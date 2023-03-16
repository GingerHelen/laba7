package com.GingerHelen.utility;

import com.GingerHelen.commands.*;
import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.NoSuchCommandException;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


public class CommandManager {
    private final ArrayList<Command> commands = new ArrayList<>();
    private final Queue<Command> history = new ArrayDeque<>();
    private final static int numberOfElements = 8;

    public CommandManager(CollectionManager collectionManager, FlatFillerMain flatFillerMain, InputManager inputManager, OutputManager outputManager) {
        commands.add(new ClearCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager, outputManager));
        commands.add(new HelpCommand(commands, outputManager));
        commands.add(new ExitCommand());
        commands.add(new PrintAscendingCommand(collectionManager, outputManager));
        commands.add(new GroupCountingByTransportCommand(collectionManager, outputManager));
        commands.add(new InsertCommand(collectionManager, flatFillerMain));
        commands.add(new MinByHouseCommand(collectionManager, outputManager));
        commands.add(new RemoveGreaterKeyCommand(collectionManager));
        commands.add(new RemoveKeyCommand(collectionManager));
        commands.add(new RemoveLowerCommand(collectionManager, flatFillerMain));
        commands.add(new ShowCommand(collectionManager, outputManager));
        commands.add(new UpdateIdCommand(collectionManager, flatFillerMain));
        commands.add(new ExecuteScriptCommand(inputManager));
        commands.add(new HistoryCommand(history, outputManager));
        commands.add(new SaveCommand(collectionManager, outputManager));
    }

    public boolean executeCommand(String stringCommand, String argument) throws ScriptException, InvalidInputException, NoSuchCommandException, IllegalArgumentException, IOException {
        if (commands.stream().anyMatch(e -> e.getName().equals(stringCommand))) {
            Command command = commands.stream().filter(e -> e.getName().equals(stringCommand)).findFirst().get();
            command.execute(argument);
            addToHistory(command);
            return command.getExecuteFlag();
        } else {
            throw new NoSuchCommandException();
        }
    }

    private void addToHistory(Command command) {
        if (history.size() == numberOfElements) {
            history.poll();
        }
        history.add(command);
    }
}
