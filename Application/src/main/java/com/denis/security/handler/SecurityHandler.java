package com.denis.security.handler;

import com.denis.model.Role;
import com.denis.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
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
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority g : authorities) {
            log.info("authoritiy: " + g.getAuthority());
        }
        GrantedAuthority a = new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name());
        log.info("GrantedAuthority a " + a.getAuthority());
        if (authorities.contains(new Role(UserRole.ROLE_ADMIN.name()))) {
            log.info("Redirecting to /admin");
            return "/admin";
        } else if (authorities.contains(new Role(UserRole.ROLE_USER.name()))) {
            log.info("Redirecting to /user");
            return "/user";
        } else {
            log.log(Level.WARNING, "Current Authorities: " +
                    Arrays.toString(authorities.toArray()));
            throw new IllegalStateException();
        }
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
