package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin/create-user")
public class CreateUserController {

    private final UsersService usersService;
    static final String URL_CREATE = "http://localhost:8080/rest/user/create";

    @Autowired
    public CreateUserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@ModelAttribute("message") String message) {
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("login") String login,
                                  @RequestParam("pd") String pd, ModelMap model) {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setUsername(login);
        user.setPassword(pd);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        User us = restTemplate.postForObject(URL_CREATE,requestBody,User.class);
        System.out.println(us.toString());
        return new ModelAndView("redirect:/admin", model);
    }
}
