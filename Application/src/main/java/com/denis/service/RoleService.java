package com.denis.service;

import com.denis.model.Role;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Service
public class RoleService {

    private static final String URL_GET_ROLE="http://localhost:8181/rest/role/byName";
    public Role getRole(String name) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_ROLE)
                .queryParam("role", name);
        URI url = builder.build().encode().toUri();
        return restTemplate.getForObject(url, Role.class);
    }
}
