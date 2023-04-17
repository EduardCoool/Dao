package service;

import dbService.UsersDataSet;

public interface UserService {
    void createUsersTable();

    void saveUser(String login, String password);

    UsersDataSet getUser(String login);
}
