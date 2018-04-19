/*
package com.denis.controller;

import com.denis.model.User;
import com.denis.model.UserSession;
import com.denis.service.SessionService;
import com.denis.service.SessionServiceImpl;
import com.denis.service.UsersService;
import com.denis.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebFilter(value = "/SimpleServletFilter", servletNames = {"UserServlet", "AdminServlet"})
@Component
public class UserFilter implements Filter {

    private UsersService usersService;
    private SessionService sessionService;
    private FilterConfig config = null;
    private boolean active = false;

    @Autowired
    public UserFilter(UsersService usersService, SessionService sessionService) {
        this.usersService = usersService;
        this.sessionService = sessionService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        CheckUser(req, resp);
    }

    private void doAdminServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("GET")) {
            String uuid = req.getParameter("uuid");
            if (req.getParameter("logout") != null) {
                sessionService.delete(sessionService.getSessionByUuid(uuid));
                resp.sendRedirect("/CRUD/login");
            } else {
                List<User> users = usersService.getUsers();
                req.setAttribute("users", users);
                req.setAttribute("uuid", uuid);
                RequestDispatcher dispatcher = req.getServletContext()
                        .getRequestDispatcher("/WEB-INF/index.jsp");
                dispatcher.forward(req, resp);
            }
        }

    }

    private void doUserServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("uuid", req.getParameter("uuid"));
        RequestDispatcher dispatcher = req.getServletContext()
                .getRequestDispatcher("/WEB-INF/user.jsp");
        dispatcher.forward(req, resp);
    }

    private void CheckUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        if (uuid != null) {
            UserSession session = sessionService.getSessionByUuid(uuid);
            if (session != null) {
                User user = usersService.getUserById(session.getUserId());
                if (user != null) {
                    if (user.getRole().equals("admin")) {
                        doAdminServlet(req, resp);
                    } else {
                        doUserServlet(req, resp);
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            } else
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);


    }


    @Override
    public void destroy() {
        config = null;
    }
}
*/
