package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/delete-user")
public class DeleteController {

    private UsersService usersService;

    @Autowired
    public DeleteController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        long id = Long.parseLong(req.getParameter("user"));
        usersService.deleteUser(usersService.getUserById(id));
        resp.sendRedirect("/CRUD/admin?uuid=" + uuid);
    }
}
