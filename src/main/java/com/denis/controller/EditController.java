package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/edit-user")
public class EditController {

    private UsersService usersService;
    private String uuid;
    private User user;

    @Autowired
    public EditController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap modelMap, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = usersService.getUserById(Long.parseLong(req.getParameter("user")));
        modelMap.addAttribute("user", user);
        uuid = req.getParameter("uuid");
        modelMap.addAttribute("uuid", uuid);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pd");
        String role = req.getParameter("role");
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        System.out.println("Edited user: " + user.toString());

        usersService.updateUser(user);
        resp.sendRedirect("/CRUD/admin?uuid=" + uuid);
    }
}