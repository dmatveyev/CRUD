package com.denis.security;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersService usersService;

    @Autowired
    public UserDetailsServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    private static final Logger log = Logger
            .getLogger("UserDetailsServiceImpl");

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException, DataAccessException {
        log.info("loadUserByUsername(" + login + ");");
        User user = usersService.getUserByLogin(login);
        log.info("Created User " + user.toString());
        log.info("Created UserDetails with role: " + user.getRole());
        return user;
    }
}
