package service;

import dao.*;
import model.User;
import util.*;

import java.io.*;
import java.util.List;
import java.util.Properties;

import static java.lang.String.valueOf;


/**
 * Управляет регистрацией и авторизацией пользователей.
 * Синглтон
 */
public class UsersService {

    private final String propertiesPath = "D:\\apache-tomcat-8.0.48\\webapps\\f.properties";
    private static UsersService usersService;
    private UserDAO userDAO;


    private UsersService() {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)){
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDaoFactory userDaoFactory = getDaoFactory(properties);
        userDAO = userDaoFactory.createDao();
    }

    private UserDaoFactory getDaoFactory(Properties properties) {
        UserDaoFactory userDaoFactory;
        switch (DaoFactories.valueOf(properties.getProperty("DaoFactory"))) {
            case hibernate:
                userDaoFactory = new HibernateDaoFactory();
                break;
            case jdbc:
                userDaoFactory = new JDBCDaoFactory();
                break;
            default:
                userDaoFactory = new JDBCDaoFactory();
                break;
        }
        return userDaoFactory;
    }

    public static UsersService getInstance() {
        if (usersService == null) {
            usersService = new UsersService();
        }
        return usersService;
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

