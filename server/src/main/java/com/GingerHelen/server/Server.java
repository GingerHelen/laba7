package com.GingerHelen.server;

import com.GingerHelen.server.utility.CollectionManager;
import com.GingerHelen.server.utility.CommandManager;
import com.GingerHelen.server.utility.Parser;
import com.GingerHelen.server.utility.RequestManager;
import com.GingerHelen.common.data.Flat;
import com.google.gson.JsonSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * главный класс серверного приложения
 */

public class Server {
    private static final int HOST_INDEX = 0;
    private static final int PORT_INDEX = 1;
    private static final int FILEPATH_INDEX = 2;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int NUMBER_OF_ARGS = 3;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        if (args.length != NUMBER_OF_ARGS) {
            logger.error("The program cannot be started, you need to enter host name, server port and file path in the given order");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[PORT_INDEX]);
        } catch (NumberFormatException e) {
            logger.error("The port must be an integer number");
            return;
        }
        if (port < MIN_PORT || port > MAX_PORT) {
            logger.error("The port is a number from 1 to 65535");
            return;
        }
        InetSocketAddress address = new InetSocketAddress(args[HOST_INDEX], port);
        if (address.isUnresolved()) {
            logger.error("Address is unresolved. Check host name");
            return;
        }
        File file = new File(args[FILEPATH_INDEX]);
        Path path = Paths.get(args[FILEPATH_INDEX]);
        if (!file.exists()) {
            logger.error("file not founded");
            return;
        }

        try (DatagramChannel server = DatagramChannel.open()) {
            server.bind(address).configureBlocking(false);
            logger.info("datagram channel opened on address " + address);
            TreeMap<Long, Flat> flats = getCollection(path);
            logger.info("collection had been read");
            CollectionManager collectionManager = new CollectionManager(flats, args[FILEPATH_INDEX]);
            CommandManager commandManager = new CommandManager(collectionManager);
            RequestManager requestManager = new RequestManager(server, commandManager, new Scanner(System.in), logger);
            requestManager.start();
        } catch (JsonSyntaxException e) {
            logger.error("wrong json syntax");
        } catch (ClassNotFoundException | InterruptedException e) {
            logger.error("error");
        }
    }

    private static TreeMap<Long, Flat> getCollection(Path path) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(Files.newInputStream(path));
        BufferedReader reader = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
        StringBuilder b = new StringBuilder("\n");
        String currentLine = reader.readLine();
        while (currentLine != null) {
            b.append(currentLine);
            currentLine = reader.readLine();
        }
        return Parser.deSerialize(b.toString());
    }
}
