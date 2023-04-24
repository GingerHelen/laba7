package com.GingerHelen.utility;

import com.GingerHelen.data.Flat;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.NoConnectionException;
import com.GingerHelen.exceptions.ScriptException;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Locale;

/**
 * класс, принимающий команды из консоли и запускающий работу команд
 */
public class ConsoleManager {
    private final static int BUFFER_SIZE = 3048;
    private final static int ATTEMPTS = 5;
    private final SocketAddress server;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final DatagramSocket client;
    private final FlatFillerMain filler;

    public ConsoleManager(SocketAddress server, InputManager inputManager, OutputManager outputManager, DatagramSocket client,
                          FlatFillerMain filler) {
        this.server = server;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.client = client;
        this.filler = filler;
    }

    /**
     * старт чтения команд
     * @throws InvalidInputException ошибка ввода
     * @throws IOException ошибка при чтении файла
     */
    public void start() throws InvalidInputException, IOException, ClassNotFoundException, NoConnectionException {
        send(new StartRequest());
        HashMap<String, Requirement> requirements = (HashMap<String, Requirement>) receive();

        outputManager.println("The program is ready!!");
        boolean isWorking = true;
        while (isWorking) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String inputCommand = input.split(" ")[0].toLowerCase(Locale.ROOT);
                String argument = "";
                if (input.split(" ").length > 1) {
                    argument = input.replaceFirst(inputCommand + " ", "");
                }
                if (requirements.containsKey(inputCommand)) {
                    Request request = new Request(inputCommand, argument);
                    Requirement requirement = requirements.get(inputCommand);
                    if (requirement == Requirement.FLAT || requirement == Requirement.FLATARGUMENT) {
                        try {
                            request.setObject(filler.fillFlat());
                        } catch (ScriptException e) {
                            outputManager.printlnImportantMessage("invalid flat value in script");
                            inputManager.finishReadScript();
                            continue;
                        }
                    }
                    send(request);
                    Response response = (Response) receive();
                    switch (response.getResponseCode()) {
                        case OK:
                            outputManager.println("command was completed");
                            break;
                        case TEXT:
                            outputManager.printlnImportantMessage(response.getMessage());
                            break;
                        case ERROR:
                            outputManager.printlnImportantMessage("command wasn't completed");
                            outputManager.printlnImportantMessage(response.getMessage());
                            break;
                        case READ_SCRIPT:
                            inputManager.startReadScript(response.getMessage());
                            break;
                        case EXIT:
                            isWorking = false;
                    }
                } else {
                    outputManager.printlnImportantMessage("no such command as " + inputCommand);
                }
            }
        }
    }

    private void send(Object request) throws IOException {
        byte[] bytesSending = Serializer.serialize(request);
        DatagramPacket packet = new DatagramPacket(bytesSending, bytesSending.length, server);
        client.send(packet);
    }

    private Object receive() throws IOException, ClassNotFoundException, NoConnectionException {
        byte[] bytesReceiving = new byte[BUFFER_SIZE];
        DatagramPacket response = new DatagramPacket(bytesReceiving, bytesReceiving.length);
        for (int i = 1; i <= ATTEMPTS; i++) {
            try {
                client.receive(response);
                break;
            } catch (SocketTimeoutException e) {
                if (i == ATTEMPTS) {
                    throw new NoConnectionException();
                }
                outputManager.printlnImportantMessage("cannot receive server's response. retrying...");
            }
        }
        return Serializer.deserialize(bytesReceiving);
    }
}