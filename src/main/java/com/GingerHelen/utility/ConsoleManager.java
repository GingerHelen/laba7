package com.GingerHelen.utility;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.NoSuchCommandException;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.Locale;

public class ConsoleManager {
    private final CommandManager commandManager;
    private final InputManager inputManager;
    private final OutputManager outputManager;

    public ConsoleManager(CommandManager commandManager, InputManager inputManager, OutputManager outputManager) {
        this.commandManager = commandManager;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public void start() throws InvalidInputException, IOException {
        outputManager.println("The program is ready!!");
        boolean executeFlag = true;
        while (executeFlag) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                try {
                    executeFlag = commandManager.executeCommand(inputCommand, argument);
                    outputManager.println("The command completed");
                } catch (ScriptException | NoSuchCommandException | IllegalArgumentException e) {
                    inputManager.finishReadScript();
                    outputManager.printlnImportantMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    inputManager.finishReadScript();
                    outputManager.printlnImportantMessage("Wrong number format");
                }
            } else {
                outputManager.println("Please type any command. To see list of command type \"help\"");
            }
        }
    }
}