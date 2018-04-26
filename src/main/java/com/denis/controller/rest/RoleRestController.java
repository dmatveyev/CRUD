package com.denis.controller.rest;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.service.RoleService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleRestController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/rest/role",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Role> get(@RequestBody User user){
        roleService.getByParam(user);
        return roleService.getByParam(user);
    }
    @RequestMapping(value = "/rest/role/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String add(@RequestHeader(value="userId") String userId, @RequestBody Role role){
        User user = usersService.getById(Long.parseLong(userId));
        user.getRole().add(role);
        usersService.register(user);
        return "Role has been added to User"; }


    @RequestMapping(value = "/rest/role/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String delete(@RequestHeader(value="userId") String userId, @RequestBody Role role){
        User user = usersService.getById(Long.parseLong(userId));
        user.getRole().remove(role);
        usersService.register(user);
        return "Role has been removed from User";
    }
}
