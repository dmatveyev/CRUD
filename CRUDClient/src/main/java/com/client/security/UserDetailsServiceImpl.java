package com.client.security;


import com.client.model.User;
import com.client.service.RoleService;
import com.client.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleService roleService;

    private static final Logger log = Logger
            .getLogger("UserDetailsServiceImpl");

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException, DataAccessException {
        log.info("loadUserByUsername(" + login + ");");

        User user = usersService.getByName(login);

        if (user == null) {
            throw new UsernameNotFoundException("User not Found");
        } else {

            Set<GrantedAuthority> roles = new HashSet<>(roleService.getByParam(user));

            UserBuilder userBuilder =
                    org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            userBuilder.authorities(roles);
            userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            log.info("Created User " + user.toString());
            log.info("Created UserDetails with role: " + user.getRole());
            log.info("Created UserDetails with role: " + user.getRole());
            return userBuilder.build();
        }
    }
}
