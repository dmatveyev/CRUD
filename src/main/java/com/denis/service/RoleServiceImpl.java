package com.denis.service;

import com.denis.model.Role;
import com.denis.repository.RoleRepository;
import com.denis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void register(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(String...params) {
        return new Role(params[0]);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getById(long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByRole(name);
    }

    @Override
    public List<Role> getByParam(Object... o) {
        User user = (User) o[0];
        return roleRepository.getRoleByUser(user.getId());
    }

}
