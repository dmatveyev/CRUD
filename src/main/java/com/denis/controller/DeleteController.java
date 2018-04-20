package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/delete-user")
public class DeleteController {

    private UsersService usersService;

    private static final Logger log = Logger
            .getLogger("DeleteController");

    @Autowired
    public DeleteController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("user"));
        log.info("Deleting user with id = " + id);
        usersService.deleteUser(usersService.getUserById(id));
        resp.sendRedirect("/CRUD/admin");
    }
}
