package com.denis.controller;

import com.denis.model.User;
import com.denis.model.UserSession;
import com.denis.service.SessionService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    private SessionService sessionService;

    @Autowired
    public SecurityController(UsersService usersService, SessionService sessionService) {
        this.usersService = usersService;
        this.sessionService = sessionService;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(){
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = usersService.getUserByLoginPassword(req.getParameter("username"),
                req.getParameter("password"));
        log.info("user: " + user.toString());
        sessionService.createSession(user);
        UserSession session = sessionService.get(user.getId());
        if (user != null) {
            switch (user.getRole()) {
                case ("user"):
                    resp.sendRedirect("/CRUD/user?uuid=" + session.getUuid());
                    break;
                case ("admin"):
                    resp.sendRedirect("/CRUD/admin?uuid=" + session.getUuid());
                    break;
                default:
                    resp.sendRedirect("/CRUD/greetings");
                    break;
            }
        }
    }
}
