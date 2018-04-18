package controllers;

import model.User;
import services.UsersService;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req
                .getRequestDispatcher("/WEB-INF/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
