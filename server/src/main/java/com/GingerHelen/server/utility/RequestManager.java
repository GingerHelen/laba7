package com.GingerHelen.server.utility;

import com.GingerHelen.common.utility.Request;
import com.GingerHelen.common.utility.Serializer;
import com.GingerHelen.common.utility.StartRequest;
import com.GingerHelen.common.utility.StartResponse;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

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

    public RequestManager(DatagramChannel server, CommandManager commandManager, Scanner scanner, Logger logger) {
        this.server = server;
        this.commandManager = commandManager;
        this.scanner = scanner;
        this.logger = logger;
    }

    public void start() throws IOException, ClassNotFoundException, InterruptedException {
        boolean isWorking = true;
        while (isWorking) {
            byte[] bytesReceiving = new byte[BUFFER_SIZE];
            ByteBuffer wrapperReceiving = ByteBuffer.wrap(bytesReceiving);
            SocketAddress client = server.receive(wrapperReceiving);
            if (client != null) {
                logger.info("request received from " + client);
                Object request = Serializer.deserialize(bytesReceiving);
                Object response = handleRequest(request);
                send(response, client);
                logger.info("response sent to " + client);
            }
            if (System.in.available() > 0) {
                final String serverCommand = scanner.nextLine().trim().toLowerCase();
                if (serverCommand.equals("exit")) {
                    isWorking = false;
                }
                if (serverCommand.equals("save")) {
                    if (commandManager.save()) {
                        logger.info("data has been rewritten in file");
                    } else {
                        logger.error("cannot rewrite data in file");
                    }
                }
            }
        }
    }

    private Object handleRequest(Object request) {
        if (request instanceof StartRequest) {
            return new StartResponse(commandManager.getCommandsWithRequirements());
        }
        return commandManager.executeCommand(((Request) request).getCommandName(), ((Request) request).getArgument(),
                ((Request) request).getObject());
    }

    private void send(Object response, SocketAddress client) throws IOException, InterruptedException {
        byte[] bytesSending = Serializer.serialize(response);
        ByteBuffer wrapperSending = ByteBuffer.wrap(bytesSending);
        for (int attempt = 1; attempt <= ATTEMPTS; attempt++) {
            if (server.send(wrapperSending, client) == bytesSending.length) {
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
