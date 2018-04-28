package com.client.rest;


import com.client.model.User;
import com.client.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
public class UserRestController {

    @Autowired
    private UsersService usersService;


    private static final Logger log = Logger
            .getLogger("UserRestController");

    @RequestMapping(value = "/rest/user")
    public User greeting(@RequestParam(value = "name", required = false) String name) {
        log.info("Search user by name: " + name);
        User user = usersService.getByName(name);
        log.info("Result: " + user.toString());
        return user;
    }
    @RequestMapping(value = "/rest/user/getbyid")
    public User getById(@RequestParam(value = "id") String userId) {
        log.info("Search user by ID:" + userId);
        User user = usersService.getById(Long.parseLong(userId));
        log.info("Result: " + user.toString());
        return user;
    }
    @RequestMapping(value = "/rest/user/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAll() {
        log.info("Getting All Users");
        return usersService.getAll();
    }


    @RequestMapping(value = "/rest/user/create")
    public User create(@RequestBody User user) {
        log.info("Creating user by name and password.");
        User newUser = usersService.create(user.getUsername(), user.getPassword());
        log.info("Result: " + newUser.toString());
        return newUser;
    }

    @RequestMapping(value = "/rest/user/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String update(@RequestBody User user) {
        log.info("Updating user: " + user.toString());
        User editUser = usersService.getById(user.getId());
        String message;
        if (editUser != null) {
            editUser.setUsername(user.getUsername());
            editUser.setPassword(user.getPassword());
            usersService.update(user);
            log.info("Result: " + user.toString());
            message = "User nas been updated";
        } else {
            message = "User not found";
        }
        return message;
    }

    @RequestMapping(value = "/rest/user/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User delete(@RequestBody User user) {
        log.info("Deleting user: " + user.toString());

        if (usersService.getById(user.getId()) != null) {
            usersService.delete(user);
            log.info("User nas been deleted" );
            return  new User();
        }
        return user;

    }
}