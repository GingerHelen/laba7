package com.GingerHelen.client;

import com.GingerHelen.client.exceptions.InvalidInputException;
import com.GingerHelen.client.exceptions.NoConnectionException;
import com.GingerHelen.client.utility.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * главный класс клиентского приложения
 */
public class Client {
    private final static int HOST_INDEX = 0;
    private final static int PORT_INDEX = 1;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int NUMBER_OF_ARGS = 3;

    public static void main(String[] args) {

        if (args.length != NUMBER_OF_ARGS) {
            System.out.println("The program cannot be started, you need to enter host name, server port in the given order");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[PORT_INDEX]);
        } catch (NumberFormatException e) {
            System.out.println("The port must be an integer number");
            return;
        }
        if (port < MIN_PORT || port > MAX_PORT) {
            System.out.println("The port is a number from 1 to 65535");
            return;
        }
        InetSocketAddress server = new InetSocketAddress(args[HOST_INDEX], port);
        if (server.isUnresolved()) {
            System.out.println("Address is unresolved. Check host name");
            return;
        }
        try (DatagramSocket client = new DatagramSocket()) {
            client.setSoTimeout(100);
            OutputManager outputManager = new OutputManager(System.out);
            InputManager inputManager = new InputManager(new BufferedInputStream(System.in), outputManager);
            FlatReader flatReader = new FlatReader(inputManager);
            FlatFillerMain flatFillerMain = new FlatFillerMain(flatReader, inputManager, outputManager);
            ConsoleManager consoleManager = new ConsoleManager(server, inputManager, outputManager, client, flatFillerMain);
            consoleManager.start();
        } catch (InvalidInputException | NoConnectionException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("error while cast server's response");
        } catch (SocketException e) {
            System.out.println("error with socket opening or setting timeout");
        } catch (IOException e) {
            System.out.println("error during reading/writing file OR receiving/sending data");
        }
    }
}
