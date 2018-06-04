package com.client.service;

import com.client.model.Role;
import com.client.model.User;
import com.client.model.UserRole;
import  com.client.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsersServiceImpl implements UsersService {


    private UserRepository userRepository;
    private RoleService roleService;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(final User user) {
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void delete(User user) {
      userRepository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return  userRepository.findAll();
    }

    @Override
    @Transactional
    public User create(String ... params){
        User user = new User();
        user.setUsername(params[0]);
        user.setPassword(params[1]);
        user.setEmail(params[2]);
        Role role = new Role(UserRole.ROLE_USER.name());
        role.setRole_id(roleService.getByName(UserRole.ROLE_USER.name()).getRole_id());
        user.setRole(role);
        register(user);
        return user;
    }
    /*comment in branch*/
    @Override
    @Transactional
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User getById(long id) {
        return userRepository.getOne(id);

    }

    @Override
    @Transactional
    public User getByName(String login) {
        return userRepository.findByUsername(login);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getByParam(Object... o) {
        return null;
    }
}


