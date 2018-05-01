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
public class RoleService {

    private static final String URL_GET_ROLE = "http://localhost:8181/rest/role/byName";

    public Role getRole(String name) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_ROLE)
                .queryParam("role", name);
        URI url = builder.build().encode().toUri();
        HttpEntity<User> requestBody = new HttpEntity<>(getHeaders());
        return restTemplate.postForObject(url, requestBody, Role.class);
    }

    public static HttpHeaders getHeaders() {
        String plainCredentials = "tom:123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
