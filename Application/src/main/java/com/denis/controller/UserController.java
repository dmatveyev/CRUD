package com.denis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap modelMap) {
/*        UserDetails userDetails = (UserDetails) org.springframework.service.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("userName", userDetails.getUsername());*/
        return "user";

    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost() {
        return "user";
    }
}
