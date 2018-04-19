package com.denis.controller;

import com.denis.model.User;
import com.denis.service.SessionService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UsersService usersService;
    private SessionService sessionService;

    @Autowired
    public AdminController(UsersService usersService, SessionService sessionService) {
        this.usersService = usersService;
        this.sessionService = sessionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap mapModel, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getParameter("uuid");
        List<User> users = usersService.getUsers();
        mapModel.addAttribute("users", users);
        mapModel.addAttribute("uuid", uuid);
        return "index";
    }
}
