package com.denis.controller;

import com.denis.model.User;
import com.denis.model.UserSession;
import com.denis.service.SessionService;
import com.denis.service.SessionServiceImpl;
import com.denis.service.UsersService;
import com.denis.service.UsersServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login", name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private UsersService usersService;
    private SessionService sessionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req
                .getRequestDispatcher("/WEB-INF/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usersService = UsersServiceImpl.getInstance();
        sessionService = SessionServiceImpl.getInstanse();
        User user = usersService.getUserByLogin(req.getParameter("login"), req.getParameter("pd"));
        sessionService.createSession(user);
        UserSession userSession = sessionService.get(user.getId());
        if (user != null) {
            switch (user.getRole()) {
                case ("user"):
                    resp.sendRedirect("/CRUD/user?uuid="+userSession.getUuid());
                    break;
                case ("admin"):
                    resp.sendRedirect("/CRUD/admin?uuid="+userSession.getUuid());
                    break;
                default:
                    resp.sendRedirect("/CRUD/greetings");
                    break;
            }
        } else {
            resp.sendRedirect("/CRUD/login");
        }
    }
}
