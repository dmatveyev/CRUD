package com.denis.controller;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.service.RoleService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/edit-user")
public class EditController {

    private UsersService usersService;
    private RoleService roleService;
    long userid;

    private static final Logger log = Logger
            .getLogger("EditController");

    @Autowired
    public EditController(UsersService usersService, RoleService roleService) {
        this.roleService = roleService;
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam("user")Long userid, ModelMap modelMap) throws ServletException, IOException {
        this.userid = userid;
        User user = usersService.getById(userid);
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(@ModelAttribute("user") User user, Model model, HttpServletResponse resp) throws ServletException, IOException {
        user.setId(userid);
        log.info("Edited user: " + user.toString());
        usersService.update(user);
        resp.sendRedirect("/admin");
    }
}
