package com.denis.service;

import com.denis.model.User;

import java.util.List;


public interface UsersService {

    long registerUser(final User user);

    void deleteUser(User user);

    List<User> getUsers();

    User createUser(final String login, final String password);

    void updateUser(User user);

    User getUserById(long id);

    User getUserByLogin(String login);
}
