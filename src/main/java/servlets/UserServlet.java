package servlets;

import database.User;
import managers.UsersManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/", name = "UserServlet")
public class UserServlet extends HttpServlet {
    private UsersManager usersManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usersManager = UsersManager.getInstance();
        List<User> users = usersManager.getUsers();
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/index.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

    }
}
