package com.denis.security.handler;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.model.UserRole;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SecurityHandler implements AuthenticationSuccessHandler {

    private static final Logger log = Logger
            .getLogger("SecurityHandler");

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);

    }

    private String determineTargetUrl(Authentication authentication) {
        log.info(authentication.getPrincipal().toString());
        log.info(authentication.getPrincipal().getClass().getCanonicalName());

        DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();

        Map<String, Object> userAtr = user.getAttributes();

        for (Map.Entry<String, Object> entry: userAtr.entrySet()){
            log.info("Key:   " + entry.getKey() + ";  Value:   " + entry.getValue());
        }

        String  email = (String) userAtr.get("email");
        log.info(email);
        log.info(authentication.getAuthorities().toString());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority g : authorities) {
            log.info("authoritiy: " + g.getAuthority());
            if (g.getAuthority().equals(UserRole.ROLE_ADMIN.name())) {
                log.log(Level.INFO, "Current Authorities: " +
                        Arrays.toString(authorities.toArray()));
                log.info("Redirecting to /admin");
                return "/admin";
            } else if (g.getAuthority().equals(UserRole.ROLE_USER.name())) {
                log.log(Level.INFO, "Current Authorities: " +
                        Arrays.toString(authorities.toArray()));
                log.info("Redirecting to /user");
                return "/user";
            } else {
                log.log(Level.WARNING, "Current Authorities: " +
                        Arrays.toString(authorities.toArray()));
                throw new IllegalStateException();
            }
        }
        return "/login";
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
