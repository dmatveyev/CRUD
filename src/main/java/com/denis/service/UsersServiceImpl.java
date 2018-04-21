package com.denis.service;

import com.denis.model.User;
import com.denis.model.UserRole;
import com.denis.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsersServiceImpl implements UsersService {


    private UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void registerUser(final User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
      userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return  userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(final String login, final String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(UserRole.ROLE_USER.name());
        user.setEnabled(true);
        registerUser(user);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return userRepository.getOne(id);

    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}


