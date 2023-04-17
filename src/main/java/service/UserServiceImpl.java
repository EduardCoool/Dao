package service;

import dao.UserDao;
import dbService.UsersDataSet;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUsersTable() {
        try {
            userDao.createUsersTable();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveUser(String name, String lastName) {
        userDao.saveUser(name, lastName);
    }

    public UsersDataSet getUser(String login) {
        return userDao.getUser(login);
    }
}
