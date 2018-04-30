package com.denis.controller;

import com.denis.model.Role;
import com.denis.model.User;
import com.denis.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin/edit-user")
public class EditController {

    private static final String URL_UPDATE ="http://localhost:8181/rest/user/update";
    private static final String URL_GET_USER ="http://localhost:8181/rest/user/getbyid";

    private static final Logger log = Logger
            .getLogger("EditController");
    @Autowired
    private RoleService roleServise;

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(@RequestParam("user") Long userid, @ModelAttribute("message") String message, ModelMap modelMap) {
        RestTemplate restTemplate = new RestTemplate();
        //Getting user
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_GET_USER)
                .queryParam("id", userid);
        URI url = builder.build().encode().toUri();
        User user = restTemplate.getForObject(url, User.class);
        modelMap.addAttribute("user", user);
        return "edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView doPost(@RequestParam("userid") String userid,
                                  @RequestParam("username") String username,
                                  @RequestParam("pd") String pd,
                                  @RequestParam("email") String email,
                                  @RequestParam("roles") String role,
                                  HttpServletRequest reg,
                                  ModelMap model) {
        Map<String,String[]> map = reg.getParameterMap();
        log.info("Params with modal");
        for (Map.Entry<String,String[]> entry: map.entrySet()) {
            log.info(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
        User user = new User();
        user.setId(Long.parseLong(userid));
        user.setUsername(username);
        user.setPassword(pd);
        user.setEmail(email);
        Role r = roleServise.getRole(role);
        user.setRole(r);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<User> requestBody = new HttpEntity<>(user,headers);
        restTemplate.postForObject(URL_UPDATE,requestBody,String.class);
        log.info("User was updated");

        return new ModelAndView("redirect:/admin", model);

    }
}
