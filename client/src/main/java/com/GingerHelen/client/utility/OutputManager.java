package com.GingerHelen.client.utility;

import java.io.PrintStream;

/**
 * класс, реализующий вывод сообщений в консоль
 */
public class OutputManager {
    private final PrintStream printStream;
    private MessageNotifications messageNotifications = MessageNotifications.ON;

    /**
     * если off, то выводятся только те сообщения, которые переданы методу printlnImportantMessage. Если on, то выводятся все сообщения
     */
    private enum MessageNotifications {
        ON,
        OFF
    }

    public OutputManager(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void println(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printlnImportantMessage(string);
        }
    }

    public void printlnImportantMessage(String string) {
        printStream.print(string + "\n");
    }

    public void print(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printStream.print(string);
        }
    }

    public void muteNotifications() {
        messageNotifications = MessageNotifications.OFF;
    }

    public void enableNotifications() {
        messageNotifications = MessageNotifications.ON;
    }
}
