package com.denis.service;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.model.UserRole;
import com.denis.repository.UserRepository;
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
        List<Role> roles = user.getRole();
        for (Role r:roles) {
            roleService.register(r);
        }
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
        List<Role> role = new ArrayList<>();
        role.add((Role)roleService.getByName(UserRole.ROLE_USER.name()));
        user.setRole(role);
        register(user);
        return user;
    }

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
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getByParam(Object... o) {
        return null;
    }
}


