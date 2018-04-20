package com.denis.dao;

import com.denis.model.User;

import java.util.List;

public interface UserDAO {
    void insert(User user);

    User get(long id);

    void delete(User user);

    List<User> getUsers();

    void update(User user);

    User getUserByLogin(String login);
}
