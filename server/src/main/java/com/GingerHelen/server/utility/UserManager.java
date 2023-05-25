package com.GingerHelen.server.utility;

import com.GingerHelen.common.utility.User;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * класс, работающий с данными пользователя
 */
public class UserManager {
    private final List<User> users;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Connection connection;
    private final Logger logger;

    private final String tableUsers;
    public UserManager(Connection connection, String tableUsers, Logger logger) {
        this.connection = connection;
        this.tableUsers = tableUsers;
        this.logger = logger;
        this.users = getUsersTable();
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

    /**
     * метод, добавляющий данные нового пользователя в базу данных
     * @param user пользователь
     * @return boolean, который символизирует, зарегистрированы данные пользователя или нет
     */
    public boolean register(User user) {
        try {
            user.setPassword(PasswordEncoder.hash(user.getPassword()));
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + tableUsers + "(username,password) VALUES " +
                    "(?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.execute();
        } catch (SQLException e) {
            logger.error("error during writing data to database");
            e.printStackTrace();
            return false;
        } lock.writeLock().lock();
        try {users.add(user);}
        finally {
            lock.writeLock().unlock();
        } return true;
    }

    /**
     * метод, получающий таблицу с всеми пользователями в базе данных
     * @return лист с пользователями
     */
    public List<User> getUsersTable() {
        lock.writeLock().lock();
        try {
            List<User> sqlUsers = new ArrayList<>();
            Statement statement = connection.createStatement();
            createUsersTableName();
            ResultSet result = statement.executeQuery("SELECT * FROM " + tableUsers);
            while (result.next()) {
                sqlUsers.add(new User(result.getString("username"), result.getString("password")));
            } return sqlUsers;
        } catch (SQLException e) {
            logger.error("error during getting users table");
            e.printStackTrace();
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void createUsersTableName() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS " + tableUsers + " (userName VARCHAR(100) NOT NULL PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL)");
    }
}
