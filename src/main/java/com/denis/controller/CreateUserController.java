package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
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
    protected String doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        uuid = req.getParameter("uuid");
        req.setAttribute("uuid", uuid);
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pd");
        usersService.createUser(login, password);
        resp.sendRedirect("/CRUD/admin?uuid=" + uuid);
        // TODO: 19.04.2018 Подумать с вариантом использования страниц 
    }


}
