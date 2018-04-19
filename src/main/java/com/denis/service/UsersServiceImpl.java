package com.denis.service;

import com.denis.dao.UserDAO;
import com.denis.model.User;
import com.denis.util.DaoFactories;
import com.denis.util.HibernateDaoFactory;
import com.denis.util.UserDaoFactory;

import java.io.*;
import java.util.List;
import java.util.Properties;

import static java.lang.String.valueOf;


/**
 * Управляет регистрацией и авторизацией пользователей.
 * Синглтон
 */
public class UsersServiceImpl implements UsersService {

    private final String propertiesPath = "D:\\apache-tomcat-8.0.48\\webapps\\f.properties";
    private static UsersServiceImpl usersServiceImpl;
    private UserDAO userDAO;


    private UsersServiceImpl() {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)){
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDaoFactory userDaoFactory = getDaoFactory(properties);
        userDAO = userDaoFactory.createDao();
    }

    public UserDaoFactory getDaoFactory(Properties properties) {
        UserDaoFactory userDaoFactory = new HibernateDaoFactory();
        return userDaoFactory;
    }

    public static UsersServiceImpl getInstance() {
        if (usersServiceImpl == null) {
            usersServiceImpl = new UsersServiceImpl();
        }
        return usersServiceImpl;
    }

    public long registerUser(final User user) {
        userDAO.insert(user);
        return user.getId();
    }

    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public User createUser(final String login, final String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole("user");
        registerUser(user);
        return user;
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public User getUserById(long id) {
        return  userDAO.get(id);
    }

    public User getUserByLogin(String login, String password) {
        return  userDAO.getUserByLogin(login, password);
    }
}


