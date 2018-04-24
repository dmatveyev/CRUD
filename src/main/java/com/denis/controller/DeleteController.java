package com.denis.controller;

import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/delete-user")
public class DeleteController {

    private UsersService usersService;

    private static final Logger log = Logger
            .getLogger("DeleteController");

    @Autowired
    public DeleteController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected RedirectView doGet(@RequestParam("user") Long id, final HttpServletResponse resp) throws IOException {
        log.info("Deleting user with id = " + id);
        usersService.delete(usersService.getById(id));
        return new RedirectView("/admin");
    }
}
