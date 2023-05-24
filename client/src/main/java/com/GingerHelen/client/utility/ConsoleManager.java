package com.GingerHelen.client.utility;

import com.GingerHelen.client.exceptions.InvalidInputException;
import com.GingerHelen.client.exceptions.NoConnectionException;
import com.GingerHelen.client.exceptions.ScriptException;
import com.GingerHelen.common.utility.*;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Locale;

/**
 * класс, принимающий команды из консоли, отправляет их на сервер, получает ответ и в приемленном виде выводит в консоль
 */
public class ConsoleManager {
    private final static int BUFFER_SIZE = 3048;
    private final static int ATTEMPTS = 5;
    private final SocketAddress server;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private final DatagramSocket client;
    private final FlatFillerMain filler;

    private HashMap<String, Requirement> commands;
    private User user;
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
     * @throws IOException ошибка при чтении/записи файла
     *  @throws ClassNotFoundException ошибка при десериализации
     *  @throws NoConnectionException сервер временно (а может не временно) недоступен
     */
    public void start() throws InvalidInputException, IOException, ClassNotFoundException, NoConnectionException {
        outputManager.println("The program is ready!!");
        authorize();
        boolean isWorking = true;
        while (isWorking) {
            String input = inputManager.read();
            if (!input.trim().isEmpty()) {
                String[] commandWithArg = getCommand(input);
                String inputCommand = commandWithArg[0];
                String argument = commandWithArg[1];

                if (commands.containsKey(inputCommand)) {
                    Request request = new Request(inputCommand, argument, user);
                    Requirement requirement = commands.get(inputCommand);
                    if (requirement == Requirement.FLAT || requirement == Requirement.FLATARGUMENT) {
                        try {
                            request.setObject(filler.fillFlat(user.getUsername()));
                        } catch (ScriptException e) {
                            outputManager.printlnImportantMessage("invalid flat value in script");
                            inputManager.finishReadScript();
                            continue;
                        }
                    }
                    send(request);
                    Response response = (Response) receive();
                    isWorking = handleResponseCode(response);
                } else {
                    outputManager.printlnImportantMessage("no such command as " + inputCommand);
                }
            }
        }
    }

    /**
     * метод, отправляющий данные через пакет datagramPacket
     * @param request запрос, который отправляется на сервер
     * @throws IOException ошибка при сериализации/отправке данных
     */
    private void send(Object request) throws IOException {
        byte[] bytesSending = Serializer.serialize(request);
        DatagramPacket packet = new DatagramPacket(bytesSending, bytesSending.length, server);
        client.send(packet);
    }

    /**
     * метод, принимающий данные с сервера
     * @return полученный объект
     * @throws IOException ошибка при десериализации/принятии данных
     * @throws ClassNotFoundException ошибка при десериализации
     * @throws NoConnectionException сервер временно недоступен
     */
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

    private boolean handleResponseCode (Response response) {
        boolean isWorking = true;
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
        return isWorking;
    }

    private String[] getCommand(String input) {
        String[] commandWithArg = new String[2];
        commandWithArg[0] = input.split(" ")[0].toLowerCase(Locale.ROOT);
        if (input.split(" ").length > 1) {
            commandWithArg[1] = input.replaceFirst(commandWithArg[0] + " ", "");
        }
        return commandWithArg;
    }

    private void authorize() throws InvalidInputException, IOException, NoConnectionException, ClassNotFoundException {
        boolean isAuthorized = false;
        do {
            outputManager.print("enter username: ");
            String newUsername = inputManager.read();
            outputManager.print("enter password: ");
            String newPassword = inputManager.read();
            send(new StartRequest(new User(newUsername, newPassword)));
            StartResponse response = (StartResponse) receive();
            if (response.getAuthorizationCode() == AuthorizationCode.AUTHORIZATION
                    || response.getAuthorizationCode() == AuthorizationCode.REGISTRATION) {
                isAuthorized = true;
                commands = response.getCommands();
                user = new User(newUsername, newPassword);
            }
            outputManager.printlnImportantMessage(response.getMessage());
        } while (!isAuthorized);
    }
}