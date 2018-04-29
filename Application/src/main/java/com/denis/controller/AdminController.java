package com.denis.controller;

import com.denis.model.User;
/*import org.springframework.security.core.userdetails.UserDetails;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = Logger
            .getLogger("AdminController");
    static final String URL_USERS = "http://localhost:8181/rest/user/getAll";

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(ModelMap mapModel) {
        RestTemplate restTemplate = new RestTemplate();
/*        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();*/
        /*String userName = userDetails.getUsername();*/
        log.info("In admin rest");
        List<User> users;
        User[] u = restTemplate.getForObject(URL_USERS, User[].class);
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
