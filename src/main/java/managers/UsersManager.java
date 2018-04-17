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

    private static UsersManager usersManager;
    private final UserDAO userDAO;


    private UsersManager() {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("D:\\apache-tomcat-8.0.48\\webapps\\f.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDaoFactory userDaoFactory = null;
        if (properties.getProperty("DaoFactory").equals(DaoFactories.hibernate.name())) {
            userDaoFactory = new HibernateDaoFactory();
        }
        if (properties.getProperty("DaoFactory").equals(DaoFactories.jdbc.name())) {
            userDaoFactory = new JDBCDaoFactory();
        }
        userDAO = userDaoFactory.createDao();
    }

    public static UsersManager getInstance() {
        if (usersManager == null) {
            usersManager = new UsersManager();
        }
        return usersManager;
    }

    String registerUser(final User user) {
        userDAO.insert(user);
        return user.getId();
    }

    public void deleteUser(final String id) {
        userDAO.delete(id);
    }

    /**
     * Проверяет введенные данные и авторизует пользователя.
     *
     * @param login    Предполагаемый логин пользователя
     * @param password предполагаемый пароль пользователя.
     * @return Зарегистрированный или новый пользователь
     * @throws IOException Пробоасывается в случае, если есть активная сессия пользователя.
     */
    public User authorize(final String login, final String password) throws IOException {
        // TODO: 12.04.2018 Написать метод авторизации
        return null;
    }

    public List<User> getUsers() {
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
        userDAO.update(user);
    }
}


