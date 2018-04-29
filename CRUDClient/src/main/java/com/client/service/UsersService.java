package com.client.service;

import com.client.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface  UsersService{

    void register(final User t);

    void delete(User t);

    List<User> getAll();

    User create(String... params);

    void update(User t);

    User getById(long id);

    User getByName(String login);

    List<User> getByParam(Object... o);
}
