package com.denis.service;

import com.denis.dao.UserDAO;
import com.denis.model.User;
import com.denis.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersServiceImpl implements UsersService {

    private UserDAO userDAO;

    @Autowired
    public UsersServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public long registerUser(final User user) {
        userDAO.insert(user);
        return user.getId();
    }

    @Override
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    public User createUser(final String login, final String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(UserRole.ROLE_USER.name());
        registerUser(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public User getUserById(long id) {
        return userDAO.get(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }
}


