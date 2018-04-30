package com.denis.controller;

import com.denis.model.User;
import com.denis.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/delete-user")
public class DeleteController {

    private static final Logger log = Logger.getLogger("DeleteController");

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    protected RedirectView doGet(@RequestParam("user") Long id) {
        log.info("Deleting user with id = " + id);
        RestTemplate restTemplate = new RestTemplate();
        User user = userService.getUserbyId(id, restTemplate);
        //Deleting user
        userService.deletingUser(user, restTemplate);
        log.info("User was deleted");
        return new RedirectView("/admin");
    }


}
