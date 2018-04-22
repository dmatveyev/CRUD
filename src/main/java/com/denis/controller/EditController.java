package com.denis.controller;

import com.denis.model.User;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    long userid;

    private static final Logger log = Logger
            .getLogger("EditController");

    @Autowired
    public EditController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(ModelMap modelMap, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userid = Long.parseLong(req.getParameter("user"));
        User user = usersService.getUserById(userid);
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(@ModelAttribute("user") User user, Model model, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> m = model.asMap();
        for (Map.Entry<String, Object> e: m.entrySet()) {
            log.info("String: " +e.getKey()+":"+ "Object:" +e.getValue().toString());
        }
        user.setId(userid);
        log.info("Edited user: " + user.toString());
        usersService.updateUser(user);
        resp.sendRedirect("/admin");
    }
}
