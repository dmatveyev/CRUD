package com.denis.security;

import com.denis.model.User;
import com.denis.service.RoleServiceImpl;
import com.denis.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersServiceImpl usersService;
    private RoleServiceImpl roleService;

    @Autowired //Как делать внедрение?
    public UserDetailsServiceImpl(UsersServiceImpl usersService, RoleServiceImpl roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    private static final Logger log = Logger
            .getLogger("UserDetailsServiceImpl");

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException, DataAccessException {
        log.info("loadUserByUsername(" + login + ");");
        User user = usersService.getByName(login);
        Set<GrantedAuthority> roles = new HashSet<>(roleService.getByParam(user));
        UserBuilder userBuilder =
                org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        userBuilder.authorities(roles);
        userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
        log.info("Created User " + user.toString());
        log.info("Created UserDetails with role: " + user.getRole());
        return userBuilder.build();
    }
}
