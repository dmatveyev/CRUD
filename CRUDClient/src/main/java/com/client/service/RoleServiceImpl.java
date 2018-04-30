package com.client.service;

import com.client.model.Role;
import com.client.repository.RoleRepository;
import com.client.model.User;
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
    public Role getByParam(Object... o) {
        User user = (User) o[0];
        return roleRepository.getRoleByUser(user.getId());
    }

}
