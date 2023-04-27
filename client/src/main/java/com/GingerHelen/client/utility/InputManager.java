package com.GingerHelen.client.utility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import com.GingerHelen.client.exceptions.InvalidInputException;

/**
 * класс, реализующий ввод данных
 */
public class InputManager {
    private final Stack<BufferedReader> readers = new Stack<>();
    private final Stack<File> files = new Stack<>();
    private boolean scriptMode = false;
    private final OutputManager outputManager;

    public InputManager(BufferedInputStream bufferedInputStream, OutputManager outputManager) {
        readers.push(new BufferedReader(new InputStreamReader(bufferedInputStream)));
        this.outputManager = outputManager;
    }
     public String read() throws InvalidInputException, IOException {
         String input = readers.peek().readLine();
         if (input == null) {
             if (scriptMode) {
                 finishReadScript();
                 return read();
             } else {
                 throw new InvalidInputException();
             }
         } else {
             return input;
         }
     }

    /**
     * если обнаружена рекурсия (внутри одного éxecute_script несколько раз считывание одного  и того же файла), то будет выведено
     * сообщение об этом и файл не будет прочитан повторно
     * @param fileName путь к файлу со скриптом
     */
    public void startReadScript(String fileName) {
        File scriptFile = new File(fileName);
        if (files.contains(scriptFile)) {
            outputManager.printlnImportantMessage("Recursion detected in file " + files.peek().getName()
                    + ". The script " + scriptFile.getName() + " will not be executed twice!");
        } else {
            try {
                outputManager.printlnImportantMessage("Start reading from file " + scriptFile.getName() + "...");
                readers.push(new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(Paths.get(fileName))))));
                files.push(scriptFile);
                scriptMode = true;
                outputManager.muteNotifications();
            } catch (IOException e) {
                outputManager.printlnImportantMessage("Cannot find file " + scriptFile.getName());
            }
        }
    }

    public boolean getScriptMode() {
        return scriptMode;
    }

    public void finishReadScript() {
        if (scriptMode) {
            if (readers.size() == 2) {
                scriptMode = false;
                outputManager.enableNotifications();
            }
            try {
                readers.pop().close();
                outputManager.printlnImportantMessage("Reading from file " + files.pop().getName() + " was finished");
            } catch (IOException e) {
                outputManager.printlnImportantMessage("error during closing file " + files.pop().getName());
            }
        }
    }
}
