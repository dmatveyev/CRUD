package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    protected String doGet(@ModelAttribute("message") String message) {
        return "createUser";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("login") String login,
                                  @RequestParam("pd") String pd, ModelMap model) {

        if (usersService.getByName(login) == null) {
            usersService.create(login, pd);
            return new ModelAndView("redirect:/admin", model);
        } else {
            String message = "User " + login + " has been allready exists";
            model.addAttribute("message", message);
            return new ModelAndView("redirect:/admin/create-user", model);
        }
    }
}
