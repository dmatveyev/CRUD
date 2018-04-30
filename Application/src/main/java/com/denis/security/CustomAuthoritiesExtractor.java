package com.denis.security;

import com.denis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class CustomAuthoritiesExtractor implements AuthoritiesExtractor {
    @Autowired
    public UserService userService;
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        String email = (String) map.get("email");
        User user = userService.getUser("email", new RestTemplate());
        if (user == null) {
            return Collections.<GrantedAuthority> emptyList();
        }
        return AuthorityUtils.createAuthorityList(user.getRole().stream().toArray(size -> new String[size]));
    }
}
