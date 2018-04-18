package controllers;

import model.User;
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
    private FilterConfig config = null;
    private boolean active = false;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        usersService = UsersService.getInstance();
        this.config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String servletPath = req.getServletPath();
        switch (servletPath) {
            case ("/login"): doLoginServlet(req, resp);
            break;
            case ("/admin"): CheckUser(req, resp);
            break;
        }

    }

    private void doAdminServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getMethod().equalsIgnoreCase("GET")) {
            usersService = UsersService.getInstance();
            List<User> users = usersService.getUsers();
            req.setAttribute("users", users);
            RequestDispatcher dispatcher = req.getServletContext()
                    .getRequestDispatcher("/WEB-INF/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void CheckUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        if (id != 0) {
            User user = usersService.getUserById(id);
            if (user != null) {
                if (user.getRole().equals("admin")) {
                    doAdminServlet(req, resp);
                }else {
                    resp.sendRedirect("/CRUD/user");
                }
            }else
                doLoginServlet(req,resp);
        }else
            doLoginServlet(req,resp);
    }

    private void doLoginServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getMethod().equalsIgnoreCase("GET")){
            RequestDispatcher dispatcher = req
                    .getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(req, resp);
        }
        if(req.getMethod().equalsIgnoreCase("POST")){
            usersService = UsersService.getInstance();
            User user = usersService.getUserByLogin(req.getParameter("login"), req.getParameter("pd"));
            if (user != null) {
                switch (user.getRole()) {
                    case ("user"):
                        resp.sendRedirect("/CRUD/user");
                        break;
                    case ("admin"):
                        resp.sendRedirect("/CRUD/admin?id=" + user.getId());
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

    @Override
    public void destroy() {
        config = null;
    }
}
