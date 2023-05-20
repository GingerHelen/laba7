package com.GingerHelen.server.utility;

import com.GingerHelen.common.data.Flat;
import com.GingerHelen.common.utility.User;
import org.slf4j.Logger;

import java.sql.*;

public class DatabaseManager {
    private static final int NAME_INDEX = 1;
    private static final int X_INDEX = 2;
    private static final int Y_INDEX = 3;
    private static final int AREA_INDEX = 4;
    private static final int NUMBER_OF_ROOMS_INDEX = 5;
    private static final int NAME_HOUSE_INDEX = 6;
    private static final int YEAR_HOUSE_INDEX = 7;
    private static final int NUMBER_OF_FLOORS_INDEX = 8;
    private static final int NUMBER_OF_FLATS_INDEX = 9;
    private static final int NUMBER_OF_LIFTS_INDEX = 10;
    private static final int FURNISH_INDEX = 11;
    private static final int VIEW_INDEX = 12;
    private static final int TRANSPORT_INDEX = 13;
    private static final int ID_INDEX = 14;
    private static final int CREATION_DATE_INDEX = 15;
    private static final int OWNER_INDEX = 16;
    private static final int KEY_INDEX = 17;
    private final Connection connection;
    private final String tableName;
    private final String tableUsers;
    private final Logger logger;

    public DatabaseManager(Connection connection, String tableName, String tableUsers, Logger logger) {
        this.connection = connection;
        this.tableName = tableName;
        this.tableUsers = tableUsers;
        this.logger = logger;
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXIST " + tableName
                + "(id SERIAL PRIMARY KEY, "
                + "key BIGINT NOT NULL, "
                + "name VARCHAR(50) NOT NULL, "
                + "x int NOT NULL, "
                + "y BIGINT NOT NULL CHECK(Y>-808), "
                + "area BIGINT NOT NULL CHECK(area>0), "
                + "number_of_rooms BIGINT NOT NULL CHECK(number_of_rooms>0), "
                + "name_house VARCHAR(100) NOT NULL,"
                + "year_house BIGINT CHECK(year_house>0),"
                + "number_of_floors INTEGER CHECK(number_of_floors>0),"
                + "number_of_flats BIGINT CHECK(number_of_flats>0)"
                + "number_of_lifts BIGINT CHECK(number_of_lifts>0)"
                + "furnish VARCHAR(100), "
                + "view VARCHAR(100), "
                + "transport VARCHAR(50), "
                + "creation_date TIMESTAMP NOT NULL, "
                + "owner VARCHAR(100) NOT NULL,"
                + "FOREIGN KEY(owner) REFERENCES " + tableUsers + "(username))");
    }

    public void createUsersTableName() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXIST" + tableUsers + "(userName VARCHAR(100) NOT NULL PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL");
    }

    public Long insert(Long key, Flat flat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + tableName + "(id,name,x,y,"
                    + "area,number_of_rooms,name_house,"
                    + "year_house, number_of_floors, number_of_flats, number_of_lifts, furnish, view, transport,creation_date,owner,key) VALUES (default,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id");
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            return result.getLong("id");
        } catch (SQLException e) {
            logger.error("error during inserting new object");
            return null;
        }
    }

    private void prepareStatement(PreparedStatement statement, Flat flat) throws SQLException {
        statement.setString(NAME_INDEX, flat.getName());
        statement.setDouble(X_INDEX, flat.getCoordinates().getX());
        statement.setLong(Y_INDEX, flat.getCoordinates().getY());
        statement.setLong(AREA_INDEX, flat.getArea());
        statement.setLong(NUMBER_OF_ROOMS_INDEX, flat.getNumberOfRooms());
        statement.setString(NAME_HOUSE_INDEX, flat.getHouse().getName());
        if (flat.getHouse().getYear() != null) {
            statement.setString(YEAR_HOUSE_INDEX, String.valueOf(flat.getHouse().getYear()));
        } else {
            statement.setNull(YEAR_HOUSE_INDEX, Types.NULL);
        }
        statement.setDouble(NUMBER_OF_FLOORS_INDEX, flat.getHouse().getNumberOfFloors());
        statement.setDouble(NUMBER_OF_FLATS_INDEX, flat.getHouse().getNumberOfFlatsOnFloor());
        statement.setDouble(NUMBER_OF_LIFTS_INDEX, flat.getHouse().getNumberOfLifts());
        if (flat.getFurnish() != null) {
            statement.setString(FURNISH_INDEX, String.valueOf(flat.getFurnish()));
        } else {
            statement.setNull(FURNISH_INDEX, Types.NULL);
        }
        statement.setString(VIEW_INDEX, String.valueOf(flat.getView()));
        if (flat.getTransport() != null) {
            statement.setString(TRANSPORT_INDEX, String.valueOf(flat.getTransport()));
        } else {
            statement.setNull(TRANSPORT_INDEX, Types.NULL);
        }
    }

    public boolean update(long id, Flat flat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + tableName + " SET "
                    + "name=?, x=?, y=?, area=?, number_of_rooms=?, name_house=?,year_house=?, number_of_floors=?, number_of_flats=?, " +
                    "number_of_lifts=?, furnish=?, view=?, transport=? WHERE id=?");
            prepareStatement(preparedStatement, flat);
            preparedStatement.setLong(ID_INDEX, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("error during updating object from table", e);
            return false;
        }
        return true;
    }

    public boolean remove(Long key) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName +
                    "WHERE key=?");
            preparedStatement.setLong(1, key);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error("error during removing object from table");
            return false;
        }
        return true;
    }

    public boolean clear(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM" + tableName + "WHERE owner=?");
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("error during deleting objects");
            return false;
        }
        return true;
    }

    public boolean removeGreater(String username, Long key) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM" + tableName + "WHERE key>?");
            statement.setLong(1, key);
        } catch (SQLException e) {
            logger.error("error during removing greater keys");
            return false;
        }
        return true;
    }
}
