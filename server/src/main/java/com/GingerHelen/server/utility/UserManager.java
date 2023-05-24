package com.GingerHelen.server.utility;

import com.GingerHelen.common.utility.User;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserManager {
    private final List<User> users;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Connection connection;
    private final Logger logger;

    private final String tableUsers;
    public UserManager(Connection connection, String tableUsers, Logger logger) {
        this.users = getUsersTable();
        this.connection = connection;
        this.tableUsers = tableUsers;
        this.logger = logger;
    }
    public boolean checkUsername(String username) {
        lock.readLock().lock();
        try {
            return users.stream().anyMatch(user -> user.getUsername().equals(username));
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean checkPassword(String username, String password) {
        lock.readLock().lock();
        try {
            return users.stream().anyMatch(user -> user.getUsername().equals(username)&&(user.getPassword().equals(PasswordEncoder.hash(password))));
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean register(User user) {
        try {
            user.setPassword(PasswordEncoder.hash(user.getPassword()));
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableUsers + "(username,password) VALUSE " +
                    "(?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();
            lock.writeLock().lock();
            users.add(user);
        } catch (SQLException e) {
            logger.error("error during writing data to database");
            return false;
        } finally {
            lock.writeLock().unlock();
        } return true;
    }

    public List<User> getUsersTable() {
        try {
            List<User> sqlUsers = new ArrayList<>();
            Statement statement = connection.createStatement();
            createUsersTableName();
            ResultSet result = statement.executeQuery("SELECT * FROM " + tableUsers);
            lock.writeLock().lock();
            while (result.next()) {
                sqlUsers.add(new User(result.getString("username"), result.getString("password")));
            } return sqlUsers;
        } catch (SQLException e) {
            logger.error("error during getting users table");
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void createUsersTableName() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXIST" + tableUsers + "(userName VARCHAR(100) NOT NULL PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL");
    }
}
