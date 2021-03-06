package com.denis.security;

import com.denis.model.Role;
import com.denis.security.handler.FailureHandler;
import com.denis.security.handler.SecurityHandler;
import com.denis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = Logger.getLogger("SecurityConfig");
    private static List<String> clients = Arrays.asList("google");

    @Autowired
    private SecurityHandler securityHandler;
    @Autowired
    private FailureHandler failureHandler;
    @Autowired
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/admin/**").
                hasRole("ADMIN")
                .antMatchers("/user/**").
                access("hasRole('USER')")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login().
                userInfoEndpoint()
                .oidcUserService(this.oidcUserService())
                .and()
                .loginPage("/login")
                .successHandler(securityHandler)
                .failureHandler(failureHandler);

        http.csrf().disable();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Autowired
    private Environment env;

    private ClientRegistration getRegistration(String client) {
        String CLIENT_PROPERTY_KEY = "security.oauth2.client.";
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + "client-id");
        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + ".client-secret");

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }


    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {

        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);
            Map<String, Object> userAttr = oidcUser.getAttributes();
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            String email = (String) userAttr.get("email");
            Role role = userService.getUserRoles(email);
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            mappedAuthorities.add(role);
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

            return oidcUser;
        };
    }

}
