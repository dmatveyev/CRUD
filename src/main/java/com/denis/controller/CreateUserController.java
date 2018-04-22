package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/create-user")
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
    protected void doPost(@RequestParam("login") String login,
                          @RequestParam("pd")String pd,
                          final HttpServletResponse resp) throws IOException {
        usersService.create(login, pd);
        resp.sendRedirect("/admin");

    }
}
