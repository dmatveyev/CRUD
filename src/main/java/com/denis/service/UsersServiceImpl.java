package com.denis.service;

import com.denis.dao.UserDAO;
import com.denis.model.User;
import com.denis.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.valueOf;


/**
 * Управляет регистрацией и авторизацией пользователей.
 * Синглтон
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private UserDAO userDAO;

    @Autowired
    public UsersServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
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
        user.setRole(UserRole.ROLE_USER.name());
        registerUser(user);
        return user;
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public User getUserById(long id) {
        return  userDAO.get(id);
    }

    public User getUserByLoginPassword(String login, String password) {
        return  userDAO.getUserByLoginPassword(login, password);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }
}


