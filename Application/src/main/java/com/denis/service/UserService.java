package com.denis.service;

import com.denis.model.Role;
import com.denis.model.User;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;


@Service
public class UserService {

    private static final String URL_USERS = "http://localhost:8181/rest/user/getAll";
    private static final String URL_GET_USER = "http://localhost:8181/rest/user";
    private static final String URL_CREATE = "http://localhost:8181/rest/user/create";
    private static final String URL_UPDATE ="http://localhost:8181/rest/user/update";
    private static final String URL_GET_USER_BY_ID ="http://localhost:8181/rest/user/getbyid";
    private static final String URL_DELETE = "http://localhost:8181/rest/user/delete";


    public static HttpHeaders getHeaders(){
        String plainCredentials="tom:123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public User[] getUsers(RestTemplate restTemplate) {
        HttpEntity<String> requestBody = new HttpEntity<>(getHeaders());
        return restTemplate.postForObject(URL_USERS,requestBody,User[].class);
    }

    public User getUser(String email, RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("name", email);
        URI url = builder.build().encode().toUri();
        HttpEntity<User> requestBody = new HttpEntity<>(getHeaders());
        return restTemplate.postForObject(url,requestBody, User.class);
    }

    public User create(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestBody = new HttpEntity<>(user, getHeaders());
        User us = restTemplate.postForObject(URL_CREATE, requestBody, User.class);
        return us;
    }

    public Role getUserRoles(String email) {
        RestTemplate restTemplate = new RestTemplate();
        User user = getUser(email, restTemplate);
        return user.getRole();
    }

    public void updateUser(User user, RestTemplate restTemplate) {
        HttpEntity<User> requestBody = new HttpEntity<>(user,getHeaders());
        restTemplate.postForObject(URL_UPDATE,requestBody,String.class);
    }

    public User getUserbyId(Long userid, RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER_BY_ID)
                .queryParam("id", userid);
        URI url = builder.build().encode().toUri();
        HttpEntity<User> requestBody = new HttpEntity<>(getHeaders());
        return restTemplate.postForObject(url,requestBody, User.class);
    }
    public void deletingUser(User user, RestTemplate restTemplate) {
        HttpEntity<User> requestBody = new HttpEntity<>(user,getHeaders());
        restTemplate.postForObject(URL_DELETE,requestBody,User.class);
    }
}
