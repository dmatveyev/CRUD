package com.denis.security;

import com.denis.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class UserService {

    private static String URL_GET_USER = "http://localhost:8181/rest/user";
    private static String URL_GET_ROLE = "http://localhost:8181/rest/role";

    public User getUser(String email, RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("name", email);
        URI url = builder.build().encode().toUri();
        return restTemplate.getForObject(url, User.class);
    }

    public void create(User user) {
    }
}
