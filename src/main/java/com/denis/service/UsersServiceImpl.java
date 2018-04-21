package com.denis.service;

import com.denis.model.User;
import com.denis.model.UserRole;
import com.denis.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersServiceImpl implements UsersService {


    private UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
      userRepository.delete(user);
    }

    @Override
    public List<User> getUsers() {
        return  userRepository.findAll();
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
        userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}


