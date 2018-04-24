package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String doGet(ModelMap mapModel) {
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        log.info("In admin controller");
        List<User> users = usersService.getAll();
        for (User u : users) {
            log.info(u.toString());
        }
        mapModel.addAttribute("userName", userName);
        mapModel.addAttribute("users", users);
        log.info("returning admin.html");
        return "/admin";
    }


}
