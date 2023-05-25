package com.GingerHelen.server;

import com.GingerHelen.server.utility.*;
import com.GingerHelen.common.data.Flat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

/**
 * главный класс серверного приложения
 */

public class Server {
    private static final int HOST_INDEX = 0;
    private static final int PORT_INDEX = 1;
    private static final int INDEX_DB_HOSTNAME = 2;
    private static final int INDEX_DB_PORT = 3;
    private static final int INDEX_DB_NAME = 4;
    private static final int DB_USERNAME_INDEX = 5;
    private static final int DB_PASSWORD_INDEX = 6;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int NUMBER_OF_ARGS = 7;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        if (args.length != NUMBER_OF_ARGS) {
            logger.error("The program cannot be started, you need to enter host name, server port, " +
                    "db host, db port, db name, username and password in the given order");
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

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("cannot resolve driver for postgresql");
            logger.error(e.getMessage());
            return;
        }
        final String dataBaseUrl = "jdbc:postgresql://" + args[INDEX_DB_HOSTNAME] + ":" + args[INDEX_DB_PORT] + "/" + args[INDEX_DB_NAME];

        try (DatagramChannel server = DatagramChannel.open();
             Connection connection = DriverManager.getConnection(dataBaseUrl, args[DB_USERNAME_INDEX], args[DB_PASSWORD_INDEX])) {
            server.bind(address).configureBlocking(false);
            logger.info("datagram channel opened on address " + address);
            DatabaseManager databaseManager = new DatabaseManager(connection, "flatTable", "flatUsersTable", logger);
            TreeMap<Long, Flat> flats = databaseManager.getDataTable();
            UserManager userManager = new UserManager(connection, "flatUsersTable", logger);
            logger.info("collection had been read");
            CollectionManager collectionManager = new CollectionManager(flats, databaseManager);
            CommandManager commandManager = new CommandManager(collectionManager);
            RequestManager requestManager = new RequestManager(server, commandManager, new Scanner(System.in), logger, userManager);
            requestManager.start();
        } catch (ClassNotFoundException | InterruptedException e) {
            logger.error("error");
        } catch (SQLException e) {
            logger.error("error with connection to database");
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}
