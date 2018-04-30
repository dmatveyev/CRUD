package com.denis.security;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.expression.Lists;
import org.thymeleaf.expression.Sets;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

@Component
public class CustomPrincipalExtractor implements PrincipalExtractor {

    private  final Logger log = Logger
            .getLogger("CustomPrincipalExtractor");

    @Autowired
    public UserService userService;

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        log.info("Extracting user......");
        String email = (String) map.get("email");
        // Check if we've already registered this uer
        User user = userService.getUser(email, new RestTemplate());
        if (user == null) {
            // If we haven't registered this user yet, create a new one
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user = new User();
            user.setUsername((String) map.get("name"));
            user.setPassword((String) map.get("name"));
            user.setEmail((String) map.get("email"));
            // Set the default Roles for users registered via Facebook
            Role role = new Role();
            role.setRole(UserRole.ROLE_USER.name());
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRole(roles);
            userService.create(user);
        } else {
            //Необходимо сделать запрос в базу для получения ролей пользователя.
        }
        return user;
    }



}
