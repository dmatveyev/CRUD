package managers;

import database.User;
import database.dao.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Управляет регистрацией и авторизацией пользователей.
 * Синглтон
 */
public class UsersManager {

    private final String propertiesPath = "D:\\apache-tomcat-8.0.48\\webapps\\f.properties";
    private UserDaoFactory userDaoFactory;


    public UsersManager(UserDaoFactory userDaoFactory) {
        this.userDaoFactory = userDaoFactory;
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(propertiesPath)){
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private UserDaoFactory getDaoFactory(Properties properties) {
        if (properties.getProperty("DaoFactory").equals(DaoFactories.hibernate.name())) {
            return new HibernateDaoFactory();
        }
        if (properties.getProperty("DaoFactory").equals(DaoFactories.jdbc.name())) {
            return new JDBCDaoFactory();
        }
        return new JDBCDaoFactory();
    }


    private String registerUser(final User user) {
        UserDAO userDAO = userDaoFactory.createDao();
        userDAO.insert(user);
        return user.getId();
    }

    public void deleteUser(final String id) {
        UserDAO userDAO = userDaoFactory.createDao();
        userDAO.delete(id);
    }

    public List<User> getUsers() {
        UserDAO userDAO = userDaoFactory.createDao();
        return userDAO.getUsers();
    }

    public void createUser(final String login, final String password) {
        User user = new User();
        user.setId(String.valueOf(Math.random()));
        user.setLogin(login);
        user.setPassword(password);
        registerUser(user);
    }

    public void updateUser(User user) {
        UserDAO userDAO = userDaoFactory.createDao();
        userDAO.update(user);
    }
}


