package dao;

import dbService.UsersDataSet;

import java.sql.SQLException;

public interface UserDao {
    void createUsersTable() throws SQLException, ClassNotFoundException;

    void saveUser(String login, String password);

    UsersDataSet getUser(String login);

}
