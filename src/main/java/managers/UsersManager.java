package managers;
import database.User;
import database.UserDAOinter;
import database.UserDAOHibernate;

import java.io.IOException;
import java.util.List;

/**
 * Управляет регистрацией и авторизацией пользователей.
 * Синглтон
 */
public class UsersManager {

    private static UsersManager usersManager;
    private final UserDAOinter userDAOinter;


    private UsersManager() {
        userDAOinter = new UserDAOHibernate();
    }

    public static UsersManager getInstance() {
        if (usersManager == null) {
            usersManager = new UsersManager();
        }
        return usersManager;
    }

    String registerUser(final User user) {
        userDAOinter.insert(user);
        return user.getId();
    }

    /**
     * Проверяет совпадение пользователя в списке зарегистрированных
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return Id пользователя, если такой пользователь был найден,
     * null если пользователь не найден
     */
    public String isRegistered(final String login, final String password) {
        return userDAOinter.getUserId(login, password);
    }

    User getRegisteredUser(final String id) {
        return userDAOinter.get(id);
    }

    public void deleteUser(final String id) {
        userDAOinter.delete(id);
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

    public List<User> getUsers(){
        return userDAOinter.getUsers();
    }

    public void createUser(final String login, final String password) {
        User user = new User();
        user.setId(String.valueOf(Math.random()));
        user.setLogin(login);
        user.setPassword(password);
        registerUser(user);
    }

    public void updateUser(User user) {
        userDAOinter.update(user);
    }
}


