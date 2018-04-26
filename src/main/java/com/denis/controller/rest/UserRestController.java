package com.denis.controller.rest;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.service.RoleService;
import com.denis.service.UsersService;
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

    //В этом методе можно возаращать лист пользователей по имени, если параметр имени отсутствует,
    // то возвращаются все пользователи.
    @RequestMapping(value = "/rest/user")
    public User greeting(@RequestParam(value = "name", required = false) String name) {
        log.info("Search user by name: " + name);
        User user = usersService.getByName(name);
        log.info("Result: " + user.toString());
        return user;
    }

    @RequestMapping(value = "/rest/user/create")
    public User create(@RequestParam(value = "name") String name,
                       @RequestParam(value = "pd") String pd) {
        log.info("Creating user by name and password " + name + pd);
        User user = usersService.create(name, pd);
        log.info("Result: " + user.toString());
        return user;
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
    public String delete(@RequestBody User user) {
        log.info("Updating user: " + user.toString());
        String message;
        if (usersService.getById(user.getId()) != null) {
            usersService.delete(user);
            log.info("Result: " + user.toString());
            message = "User nas been deleted";
        } else {
            message = "User not found";
        }
        return message;

    }
}