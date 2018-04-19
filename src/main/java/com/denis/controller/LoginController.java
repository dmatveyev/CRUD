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

@Controller
@RequestMapping("/login")
public class LoginController {

    private UsersService usersService;
    private SessionService sessionService;

    @Autowired
    public LoginController(UsersService usersService, SessionService sessionService) {
        this.usersService = usersService;
        this.sessionService = sessionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap model) {
        model.addAttribute("message", "Spring 3 MVC - Hello World");
        return "login";
    }

    @RequestMapping (params = {"login", "pd"}, method = RequestMethod.POST)
    public void doPost(ModelMap model, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = usersService.getUserByLogin(req.getParameter("login"), req.getParameter("pd"));
        sessionService.createSession(user);
        UserSession userSession = sessionService.get(user.getId());
        if (user != null) {
            switch (user.getRole()) {
                case ("user"):
                    resp.sendRedirect("/CRUD/user?uuid=" + userSession.getUuid());
                    break;
                case ("admin"):
                    resp.sendRedirect("/CRUD/admin?uuid=" + userSession.getUuid());
                    break;
                default:
                    resp.sendRedirect("/CRUD/greetings");
                    break;
            }
        } else {
            resp.sendRedirect("/CRUD/login");
        }
    }
}
