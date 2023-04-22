package com.GingerHelen.utility;

import com.GingerHelen.exceptions.IllegalArgumentException;
import com.GingerHelen.exceptions.InvalidInputException;
import com.GingerHelen.exceptions.NoSuchCommandException;
import com.GingerHelen.exceptions.ScriptException;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestManager {
    private final static int BUFFER_SIZE = 3048;
    private static final int TIMEOUT = 100;
    private final static int ATTEMPTS = 5;
    private final DatagramChannel server;
    private final CommandManager commandManager;

    public RequestManager(DatagramChannel server, CommandManager commandManager) {
        this.server = server;
        this.commandManager = commandManager;
    }

    public void start() {
        byte[] bytesReceiving = new byte[BUFFER_SIZE];
        ByteBuffer wrapperReceiving = ByteBuffer.wrap(bytesReceiving);
        SocketAddress client = server.receive(wrapperReceiving);
        Object request = Serializer.deserialize(bytesReceiving);
        Object response = handleRequest(request);
        send(response, client);
    }

    private Object handleRequest(Object request) {
        if (request instanceof StartRequest) {
            return commandManager.getCommandsWithRequirements();
        }
        return commandManager.executeCommand(((Request) request).getCommandName(), ((Request) request).getArgument(),
                ((Request) request).getObject());
    }

    private boolean send(Object response, SocketAddress client) {
        byte[] bytesSending = Serializer.serialize(response);
        ByteBuffer wrapperSending = ByteBuffer.wrap(bytesSending);
        for (int attempt = 1; attempt <= ATTEMPTS; attempt++) {
            if (server.send(wrapperSending, client) == bytesSending.length) {
                return true;
            } else {
                System.out.println("Cannot send response to the client. Retrying attempt #" + attempt + " now...");
                if (attempt == ATTEMPTS) {
                    return false;
                }
                Thread.sleep(TIMEOUT);
            }
        }
        return false;
    }
}
