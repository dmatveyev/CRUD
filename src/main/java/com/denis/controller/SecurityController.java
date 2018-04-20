package com.denis.controller;

import com.denis.model.UserRole;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/security")
public class SecurityController {

    private static final Logger log = Logger
            .getLogger("SecurityController");

    private UsersService usersService;


    @Autowired
    public SecurityController(UsersService usersService) {
        this.usersService = usersService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        log.info("role: " + role);
        {
            switch (UserRole.valueOf(role)) {
                case ROLE_USER:
                    resp.sendRedirect("/CRUD/user");
                    break;
                case ROLE_ADMIN:
                    resp.sendRedirect("/CRUD/admin");
                    break;
                default:
                    resp.sendRedirect("/CRUD/greetings");
                    break;
            }
        }
    }
}
