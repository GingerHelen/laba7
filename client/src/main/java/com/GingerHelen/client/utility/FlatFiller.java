package com.GingerHelen.client.utility;

import com.GingerHelen.client.exceptions.IllegalValueException;
import com.GingerHelen.client.exceptions.InvalidInputException;
import com.GingerHelen.client.exceptions.ScriptException;

import java.io.IOException;

/**
 * класс с обобщенным методом чтения некоторого поля типа T
 */
public class FlatFiller {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public FlatFiller(OutputManager outputManager, InputManager inputManager) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    /**
     * @param message сообщение, которое выводится в консоли с запросом на ввод (н-р, "введите число")
     * @param reader метод, осуществляющий чтение поля
     * @return значение типа T, которое соответствует требованиям
     * @throws InvalidInputException ошибка ввода
     * @throws ScriptException ошибка при чтении скрипта (неправильное значение поля)
     * @throws IOException ошибка при чтении файла
     */
    public <T> T fill(String message, Reader<T> reader) throws InvalidInputException, ScriptException, IOException {
        T returns;
        while (true) {
            try {
                outputManager.print(message + ": ");
                returns = reader.read();
                break;
            } catch (NumberFormatException e) {
                outputManager.printlnImportantMessage("Value must be a number");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException("Number expected");
                }
            } catch (IllegalArgumentException e) {
                outputManager.printlnImportantMessage("Choose anything from list");
                if (inputManager.getScriptMode()) {
                    throw new ScriptException("An enum value was expected");
                }
            } catch (IllegalValueException e) {
                outputManager.printlnImportantMessage(e.getMessage());
                if (inputManager.getScriptMode()) {
                    throw new ScriptException(e.getMessage());
                }
            }
        }
        return returns;
    }
}
