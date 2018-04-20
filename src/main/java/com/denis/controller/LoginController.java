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
//@RequestMapping("/login")
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
        return "login";
    }

    @RequestMapping (method = RequestMethod.POST)
    public void doPost(ModelMap model, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.sendRedirect("/CRUD/security");
    }
}
