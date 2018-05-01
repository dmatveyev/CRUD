package com.denis.controller;

import com.denis.model.User;
/*import org.springframework.service.core.userdetails.UserDetails;*/
import com.denis.service.RequestService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = Logger
            .getLogger("AdminController");
    @Autowired
    private RequestService requestService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap mapModel) {
        RestTemplate restTemplate = new RestTemplate();
/*        UserDetails userDetails = (UserDetails) org.springframework.service.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();*/
        /*String userName = userDetails.getUsername();*/
        log.info("In admin rest");
        List<User> users;
        User[] u = requestService.getUsers(restTemplate);
        users= Arrays.asList(u);
        for (User user : users) {
            log.info(user.toString());
        }
        mapModel.addAttribute("userName", "");
        mapModel.addAttribute("users", users);
        log.info("returning admin.html");
        return "admin";
    }



}
