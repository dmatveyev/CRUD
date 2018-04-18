package controllers;

import model.User;
import model.UserSession;
import services.SessionService;
import services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebFilter (value = "/SimpleServletFilter",servletNames = {"UserServlet","AdminServlet"})
public class UserFilter implements Filter {

    private UsersService usersService;
    private SessionService sessionService;
    private FilterConfig config = null;
    private boolean active = false;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        usersService = UsersService.getInstance();
        sessionService = SessionService.getInstanse();
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
        if(req.getMethod().equalsIgnoreCase("GET")) {
            usersService = UsersService.getInstance();
            List<User> users = usersService.getUsers();
            req.setAttribute("users", users);
            req.setAttribute("uuid",req.getParameter("uuid"));
            RequestDispatcher dispatcher = req.getServletContext()
                    .getRequestDispatcher("/WEB-INF/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void doUserServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("uuid",req.getParameter("uuid"));
        RequestDispatcher dispatcher = req.getServletContext()
                .getRequestDispatcher("/WEB-INF/user.jsp");
        dispatcher.forward(req, resp);
    }

    private void CheckUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        UserSession session = sessionService.getUserIdByUuid(uuid);
            User user = usersService.getUserById(session.getUserId());
            if (user != null) {
                if (user.getRole().equals("admin")) {
                    doAdminServlet(req, resp);
                }else {
                    doUserServlet(req, resp);
                }
            }else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }



    }



    @Override
    public void destroy() {
        config = null;
    }
}
