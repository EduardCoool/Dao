package dao;


import dbService.UsersDataSet;

import java.sql.*;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = getPostgreConnection();
        createUsersTable();
    }

    public static Connection getPostgreConnection() {
        try {
            Class.forName("org.h2.Driver");
            String URL = "jdbc:h2:./h2db";
            String USER = "tully";
            String PASS = "tully";
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void createUsersTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("create table if not exists USERS("
                    + "ID serial PRIMARY KEY NOT NULL, "
                    + "login VARCHAR(20) NOT NULL, "
                    + "password VARCHAR(20) NOT NULL "
                    + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUser(String login, String password) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO USERS (login, password) " +
                "Values (?, ?)")) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("login = " + login + "; lastName = " + password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UsersDataSet getUser(String login) {
        UsersDataSet usersDataSet = null;
        ResultSet result;
        try (Statement stmt = connection.createStatement()) {
            result = stmt.executeQuery("select * from USERS where login='" + login + "'");
            while (result.next()) {
                usersDataSet = new UsersDataSet(result.getString("login"), result.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersDataSet;
    }
}
