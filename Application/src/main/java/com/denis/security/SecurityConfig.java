package com.denis.security;


import com.denis.model.Role;
import com.denis.model.User;
import com.denis.model.UserRole;
import com.denis.security.handler.FailureHandler;
import com.denis.security.handler.SecurityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static List<String> clients = Arrays.asList("google");

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SecurityHandler securityHandler;

    private FailureHandler failureHandler;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); //- подключение реализации passwordEncoder
    }

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
                .oauth2Login()
                .loginPage("/login")
                .successHandler(securityHandler);


        http.csrf().disable();
    }

/*    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  Если нужно отключить*/
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }
    private static String CLIENT_PROPERTY_KEY
            = "security.oauth2.client.";

    @Autowired
    private Environment env;

    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + "client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY +  ".client-secret");

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


    @Bean
    public PrincipalExtractor principalExtractor(UserService userService) {
        return map -> {
            String email = (String) map.get("email");
            // Check if we've already registered this uer
            User user = userService.getUser(email, new RestTemplate());


            return user;
        };
    }


}
