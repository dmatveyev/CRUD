package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
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
import java.util.Arrays;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/delete-user")
public class DeleteController {

    private static final String URL_DELETE = "http://localhost:8080/rest/user/delete";
    private static String URL_GET_USER = "http://localhost:8080/rest/user/getbyid";
    private UsersService usersService;

    private static final Logger log = Logger
            .getLogger("DeleteController");

    @Autowired
    public DeleteController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected RedirectView doGet(@RequestParam("user") Long id) {
        log.info("Deleting user with id = " + id);
        RestTemplate restTemplate = new RestTemplate();
        //Getting user
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("id", id);
        URI url = builder.build().encode().toUri();
        User user = restTemplate.getForObject(url, User.class);

        //Deleting user
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        restTemplate.postForObject(URL_DELETE,requestBody,String.class);
        log.info("User was deleted");
        return new RedirectView("/admin");
    }
}
