package com.denis.security;

import com.denis.model.Role;
import com.denis.model.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static String URL_GET_USER = "http://localhost:8181/rest/user";
    private static String URL_GET_ROLE = "http://localhost:8181/rest/role";

    private static final Logger log = Logger
            .getLogger("UserDetailsServiceImpl");

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        log.info("loadUserByUsername(" + login + ");");
        RestTemplate restTemplate = new RestTemplate();
        //Getting user

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("name", login);
        URI url = builder.build().encode().toUri();
        User user = restTemplate.getForObject(url, User.class);
        //Deleting user

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        Role[] arr = restTemplate.postForObject(URL_GET_ROLE,requestBody,Role[].class);
        List<Role> r = Arrays.asList(arr);

        if (user == null) {
            throw new UsernameNotFoundException("User not Found");
        } else {
            Set<GrantedAuthority> roles = new HashSet<>(r);
            log.info("GrantedAuthority " + roles);
            UserBuilder userBuilder =
                    org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            userBuilder.authorities(roles);
            userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            log.info("Created User " + user.toString());
            log.info("Created UserDetails with role: " + Arrays.toString(arr));
            UserDetails ud = userBuilder.build();
            log.info("UserDetails: " + Arrays.toString(arr));
            return userBuilder.build();
        }
    }
}
