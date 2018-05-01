package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/create-user")
public class CreateUserController {

    static final String URL_CREATE = "http://localhost:8181/rest/user/create";


    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@ModelAttribute("message") String message) {
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("login") String login,
                                  @RequestParam("pd") String pd,
                                  @RequestParam("email") String email,
                                  ModelMap model) {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setUsername(login);
        user.setPassword(pd);
        user.setEmail(email);
        User us = userService.create(user);
        System.out.println(us.toString());
        return new ModelAndView("redirect:/admin", model);
    }
}
