package com.denis.service;

import com.denis.model.Role;

import java.util.List;

public interface RoleService {

        void register(final Role t);

        void delete(Role t);

        List<Role> getAll();

        Role create(String... params);

        void update(Role t);

        Role getById(long id);

        Role getByName(String login);

        List<Role> getByParam(Object... o);

}
