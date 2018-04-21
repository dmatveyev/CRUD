package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = Logger
            .getLogger("AdminController");

    private UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap mapModel, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("In admin controller");
        List<User> users = usersService.getUsers();
        for (User u : users) {
            log.info(u.toString());
        }
        mapModel.addAttribute("users", users);
        log.info("returning admin.html");
        return "admin";
    }


}
