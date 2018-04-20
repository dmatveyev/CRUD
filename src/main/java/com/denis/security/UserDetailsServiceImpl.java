package com.denis.security;

import com.denis.model.User;
import com.denis.model.UserRole;
import com.denis.service.UsersService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
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

        String username = user.getLogin();
        String password = user.getPassword();
        String role = user.getRole();
        // указываем роли для этого пользователя
        Set<GrantedAuthority> roles = new HashSet<>();

        switch (UserRole.valueOf(role)) {
            case USER:
                roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));
                log.info("role: " + role);
                break;
            case ADMIN:
                roles.add(new SimpleGrantedAuthority(UserRole.ADMIN.name()));
                log.info("role: " + role);
                break;
            default:
                roles.add(new SimpleGrantedAuthority(UserRole.USER.name()));

        }
        // на основании полученныйх даных формируем объект UserDetails
        // который позволит проверить введеный пользователем логин и пароль
        // и уже потом аутентифицировать пользователя
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(username,
                        password,
                        roles);
        log.info("Created UserDetails with role: " + role);

        return userDetails;
    }
}
