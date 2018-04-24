package com.denis.controller;

import com.denis.model.User;
import com.denis.service.RoleService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
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
    protected String doGet(@RequestParam("user") Long userid, @ModelAttribute("message") String message, ModelMap modelMap) {
        this.userid = userid;
        User user = usersService.getById(userid);
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@ModelAttribute("user") User user, ModelMap model) {
        user.setId(userid);
        log.info("Edited user: " + user.toString());
        if (usersService.getByName(user.getUsername()) == null) {
            usersService.update(user);
            return new ModelAndView("redirect:/admin", model);
        } else {
            String message = "User " + user.getUsername()+ " has been allready exists";
            model.addAttribute("message", message);
            model.addAttribute("user", user.getId());
            log.info(message);
            return new ModelAndView("redirect:/admin/edit-user", model);
        }
    }
}
