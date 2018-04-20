package com.denis.dao;

import com.denis.model.User;
import java.util.List;

public interface UserDAO {
    void insert(User user);

    String getUserId(String login, String password);

    User get(long id);

    void delete(User user);

    List<User> getUsers();

    void update(User user);

    User getUserByLoginPassword(String login, String password);
    User getUserByLogin(String login);
}
