package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin/create-user")
public class CreateUserController {

    private final UsersService usersService;
    private String uuid;

    @Autowired
    public CreateUserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet() {
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected RedirectView doPost(@RequestParam("login") String login,
                                  @RequestParam("pd") String pd) {
        usersService.create(login, pd);
        return new RedirectView("/admin");
    }
}
