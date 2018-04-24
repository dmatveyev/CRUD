package com.denis.controller;

import com.denis.model.User;
import com.denis.service.RoleService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/edit-user")
public class EditController {

    private UsersService usersService;
    long userid;

    private static final Logger log = Logger
            .getLogger("EditController");

    @Autowired
    public EditController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam("user") Long userid, ModelMap modelMap) {
        this.userid = userid;
        User user = usersService.getById(userid);
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected RedirectView doPost(@ModelAttribute("user") User user) {
        user.setId(userid);
        log.info("Edited user: " + user.toString());
        usersService.update(user);
        return new RedirectView("/admin");
    }
}
