package com.denis.controller;

import com.denis.model.User;
import com.denis.model.UserSession;
import com.denis.service.SessionService;
import com.denis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    private SessionService sessionService;
    private UsersService usersService;

    @Autowired
    public UserController(SessionService sessionService, UsersService usersService) {
        this.sessionService = sessionService;
        this.usersService = usersService;
    }


    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    protected String doGet(HttpServletRequest req, HttpServletResponse resp)  {
/*        String uuid = req.getParameter("uuid");
        if (CheckUser(uuid)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "forbidden";
        }else {
            return "user";
        }*/
        return "user";

    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(HttpServletRequest req, HttpServletResponse resp) {
/*        String uuid = req.getParameter("uuid");
        if (CheckUser(uuid)) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "forbidden";
        }else {
            return "user";
        }*/
        return "user";
    }

    /**
     * Инвертированный метод
     * @param uuid токен пользователя
     * @return возвращает false если пользователь имеет необходимую роль
     */
    private boolean CheckUser(String uuid)  {
        if (uuid != null) {
            UserSession session = sessionService.getSessionByUuid(uuid);
            if (session != null) {
                User user = usersService.getUserById(session.getUserId());
                if (user != null) {
                    if (user.getRole().equals("user")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
