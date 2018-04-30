package com.denis.service;

import com.denis.model.Role;
import com.denis.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
public class UserService {

    private static final String URL_GET_USER = "http://localhost:8181/rest/user";
    private static final String URL_CREATE = "http://localhost:8181/rest/user/create";
    private static final String URL_UPDATE ="http://localhost:8181/rest/user/update";
    private static final String URL_GET_USER_BY_ID ="http://localhost:8181/rest/user/getbyid";
    private static final String URL_DELETE = "http://localhost:8181/rest/user/delete";

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
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        User us = restTemplate.postForObject(URL_CREATE, requestBody, User.class);
    }

    public Role getUserRoles(String email) {
        RestTemplate restTemplate = new RestTemplate();
        User user = getUser(email, restTemplate);
        return user.getRole();
    }

    public void updateUser(User user, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        restTemplate.postForObject(URL_UPDATE,requestBody,String.class);
    }

    public User getUserbyId(Long userid, RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER_BY_ID)
                .queryParam("id", userid);
        URI url = builder.build().encode().toUri();
        return restTemplate.getForObject(url, User.class);
    }
    public void deletingUser(User user, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        restTemplate.postForObject(URL_DELETE,requestBody,User.class);
    }
}
