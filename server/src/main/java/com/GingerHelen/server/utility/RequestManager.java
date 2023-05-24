package com.GingerHelen.server.utility;

import com.GingerHelen.common.utility.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * класс, работающий с запросами клиента и считывающий save и exit с консоли
 */
public class RequestManager {
    private final static int BUFFER_SIZE = 3048;
    private static final int TIMEOUT = 100;
    private final static int ATTEMPTS = 5;
    private final Logger logger;
    private final DatagramChannel server;
    private final CommandManager commandManager;
    private final Scanner scanner;
    private final UserManager userManager;
    private final static ExecutorService pullRequestResponse = Executors.newCachedThreadPool();

    public RequestManager(DatagramChannel server, CommandManager commandManager, Scanner scanner, Logger logger, UserManager userManager) {
        this.server = server;
        this.commandManager = commandManager;
        this.scanner = scanner;
        this.logger = logger;
        this.userManager = userManager;
    }

    public void start() throws IOException, ClassNotFoundException, InterruptedException, ExecutionException {
        boolean isWorking = true;
        while (isWorking) {
            FutureTask<ClientRequest> futureTask = new FutureTask<>(this::receive);
            new Thread(futureTask).start();
            ClientRequest request = futureTask.get();
            pullRequestResponse.submit(()->{
                Object response = handleRequest(request.getRequest());
                new Thread(()-> {
                    try {
                        send(response, request.getClientAddress());
                    } catch (IOException e) {
                        logger.error("error during sending response to the client");
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                });
            });
            if (System.in.available() > 0) {
                final String serverCommand = scanner.nextLine().trim().toLowerCase();
                if (serverCommand.equals("exit")) {
                    isWorking = false;
                }
            }
        }
    }
    private ClientRequest receive() throws IOException, ClassNotFoundException {
        byte[] bytesReceiving = new byte[BUFFER_SIZE];
        ByteBuffer wrapperReceiving = ByteBuffer.wrap(bytesReceiving);
        SocketAddress client = server.receive(wrapperReceiving);
        if (client != null) {
            logger.info("request received from " + client);
            Object request = Serializer.deserialize(bytesReceiving);
            return new ClientRequest(request, client);
        } return null;
    }

    private Object handleRequest(Object request) {
        if (request instanceof StartRequest) {
            if (!userManager.checkUsername(((StartRequest) request).getUser().getUsername())) {
                if (userManager.register(((StartRequest) request).getUser())) {
                    return new StartResponse(commandManager.getCommandsWithRequirements(),
                            AuthorizationCode.REGISTRATION, "You've been successfully registered");
                }
                return new StartResponse(AuthorizationCode.ERROR,
                        "Error during registration (cannot write your data in database");
            }
            if (userManager.checkPassword(((StartRequest) request).getUser().getUsername(), ((StartRequest) request).getUser().getPassword())) {
                return new StartResponse(commandManager.getCommandsWithRequirements(), AuthorizationCode.AUTHORIZATION,
                        "You've login in your account");
            }
            return new StartResponse(AuthorizationCode.ERROR, "Wrong password or username already in use");
        }
        if (userManager.checkPassword(((Request) request).getUser().getUsername(), ((Request) request).getUser().getPassword())) {
            return commandManager.executeCommand(((Request) request).getCommandName(), ((Request) request).getArgument(),
                    ((Request) request).getObject(), ((Request) request).getUser().getUsername());
        }
        return new Response(ResponseCode.ERROR, "commands should be executed by authorized users");
    }

    private void send(Object response, SocketAddress client) throws IOException, InterruptedException {
        byte[] bytesSending = Serializer.serialize(response);
        ByteBuffer wrapperSending = ByteBuffer.wrap(bytesSending);
        for (int attempt = 1; attempt <= ATTEMPTS; attempt++) {
            if (server.send(wrapperSending, client) == bytesSending.length) {
                logger.info("response has been sent to client on address" + client);
                return;
            } else {
                System.out.println("Cannot send response to the client. Retrying attempt #" + attempt + " now...");
                if (attempt == ATTEMPTS) {
                    return;
                }
                Thread.sleep(TIMEOUT);
            }
        }
    }
}
