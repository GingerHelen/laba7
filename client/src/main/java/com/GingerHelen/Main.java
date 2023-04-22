package com.GingerHelen;

import com.GingerHelen.data.Flat;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.utility.*;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        OutputManager outputManager = new OutputManager(System.out);
        final String filename = System.getenv("PlsSetAMaximum");
        if (filename == null || filename.isEmpty()) {
            outputManager.printlnImportantMessage("please set a PlsSetAMaximum env.variable");
            return;
        }
        File file = new File(filename);
        Path path = Paths.get(filename);
        if (!file.exists()) {
            outputManager.printlnImportantMessage("file not founded");
            return;
        }
        BufferedInputStream bf = new BufferedInputStream(Files.newInputStream(path));
        BufferedReader reader = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
        StringBuilder b = new StringBuilder("\n");
        String currentLine = reader.readLine();
        while (currentLine != null) {
            b.append(currentLine);
            currentLine = reader.readLine();
        }
        try {
            TreeMap<Long, Flat> flats = Parser.deSerialize(b.toString());
            CollectionManager collectionManager = new CollectionManager(flats, filename);
            InputManager inputManager = new InputManager(new BufferedInputStream(System.in), outputManager);
            FlatReader flatReader = new FlatReader(inputManager);
            FlatFillerMain flatFillerMain = new FlatFillerMain(flatReader, inputManager, outputManager, collectionManager);
            CommandManager commandManager = new CommandManager(collectionManager, flatFillerMain, inputManager, outputManager);
            ConsoleManager consoleManager = new ConsoleManager(commandManager, inputManager, outputManager);
            consoleManager.start();
        } catch (JsonSyntaxException e) {
            outputManager.println("wrong json syntax");
        } catch (InvalidInputException e) {
            outputManager.printlnImportantMessage("invalid input, goodbye :)");
        }
    }
}
