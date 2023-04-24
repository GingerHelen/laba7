package com.GingerHelen;

import com.GingerHelen.data.Flat;
import com.GingerHelen.utility.CollectionManager;
import com.GingerHelen.utility.CommandManager;
import com.GingerHelen.utility.Parser;
import com.GingerHelen.utility.RequestManager;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;


public class Server {
    private static final int HOST_INDEX = 0;
    private static final int PORT_INDEX = 1;
    private static final int FILEPATH_INDEX = 2;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int NUMBER_OF_ARGS = 3;

    public static void main(String[] args) throws IOException {
        if (args.length != NUMBER_OF_ARGS) {
            System.out.println("Программа не может быть запущена, вам нужно указать host name, port и file path в заданном порядке");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[PORT_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("Порт должен задаваться целым числом");
            return;
        }
        if (port < MIN_PORT || port > MAX_PORT) {
            System.out.println("Порт - число от 1 до 65535");
            return;
        }
        InetSocketAddress address = new InetSocketAddress(args[HOST_INDEX], port);
        if (address.isUnresolved()) {
            System.out.println("Address is unresolved. Check host name");
            return;
        }
        File file = new File(args[FILEPATH_INDEX]);
        Path path = Paths.get(args[FILEPATH_INDEX]);
        if (!file.exists()) {
            System.out.println("file not founded");
            return;
        }

        try (DatagramChannel server = DatagramChannel.open()) {
            server.bind(address).configureBlocking(false);
            BufferedInputStream bf = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(bf, StandardCharsets.UTF_8));
            StringBuilder b = new StringBuilder("\n");
            String currentLine = reader.readLine();
            while (currentLine != null) {
                b.append(currentLine);
                currentLine = reader.readLine();
            }
            TreeMap<Long, Flat> flats = Parser.deSerialize(b.toString());
            CollectionManager collectionManager = new CollectionManager(flats, args[FILEPATH_INDEX]);
            CommandManager commandManager = new CommandManager(collectionManager);
            RequestManager requestManager = new RequestManager(server, commandManager, new Scanner(System.in));
            requestManager.start();
        } catch (JsonSyntaxException e) {
            System.out.println("wrong json syntax");
        } catch (ClassNotFoundException | InterruptedException e) {
            System.out.println("error");
        }
    }
}
