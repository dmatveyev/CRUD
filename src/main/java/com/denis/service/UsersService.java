package com.denis.service;

import com.denis.model.User;
import com.denis.util.UserDaoFactory;

import java.util.List;
import java.util.Properties;

public interface UsersService {



    UserDaoFactory getDaoFactory(Properties properties);



    long registerUser(final User user);

    void deleteUser(User user);

    List<User> getUsers();

    User createUser(final String login, final String password);

    void updateUser(User user);

    User getUserById(long id);

    User getUserByLogin(String login, String password);
}
