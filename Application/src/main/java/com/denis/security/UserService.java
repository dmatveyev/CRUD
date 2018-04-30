package com.denis.security;

import com.denis.model.Role;
import com.denis.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private static String URL_GET_USER = "http://localhost:8181/rest/user";
    private static String URL_GET_ROLE = "http://localhost:8181/rest/role";
    static final String URL_CREATE = "http://localhost:8181/rest/user/create";

    public User getUser(String email, RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("name", email);
        URI url = builder.build().encode().toUri();
        return restTemplate.getForObject(url, User.class);
    }

    public void create(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        User us = restTemplate.postForObject(URL_CREATE,requestBody,User.class);
    }

    public List<? extends GrantedAuthority> getUserRoles(String email) {
        RestTemplate restTemplate = new RestTemplate();
        //Getting user

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("name", email);
        URI url = builder.build().encode().toUri();
        User user = restTemplate.getForObject(url, User.class);
        //Deleting user

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        Role[] arr = restTemplate.postForObject(URL_GET_ROLE,requestBody,Role[].class);
        List<Role> r = Arrays.asList(arr);
        return r;
    }
}
