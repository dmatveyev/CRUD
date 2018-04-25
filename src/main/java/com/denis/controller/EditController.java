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

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
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
    protected ModelAndView doPost(@RequestParam("userid") String userid,
                                  @RequestParam("username") String username,
                                  @RequestParam("pd") String pd,
                                  @RequestParam("roles") String roles,
                                  HttpServletRequest reg,
                                  ModelMap model) {
        Map<String,String[]> map = reg.getParameterMap();
        log.info("Params with modal");
        for (Map.Entry<String,String[]> entry: map.entrySet()) {
            log.info(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
        User user = usersService.getById(Long.parseLong(userid));
        log.info("Edited user: " + user.toString());
        log.info("New user: " + user.toString());
            user.setUsername(username);
            user.setPassword(pd);
            log.info("New user to update: " + user.toString());
            usersService.update(user);
            return new ModelAndView("redirect:/admin", model);

    }
}
