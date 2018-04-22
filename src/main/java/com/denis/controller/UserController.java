package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    private UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }


    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap modelMap) {
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("userName", userDetails.getUsername());
        return "user";

    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost() {
        return "user";
    }
}
