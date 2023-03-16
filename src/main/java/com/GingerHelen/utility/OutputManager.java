package com.GingerHelen.utility;

import java.io.PrintStream;

public class OutputManager {
    private final PrintStream printStream;
    private MessageNotifications messageNotifications = MessageNotifications.ON;

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
