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



@WebServlet(urlPatterns = "/edit-user",name = "EditUserServlet")
public class EditUserServlet  extends HttpServlet {
    private User user;
    public EditUserServlet() {
        user = new User();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        user.setUserId(id);
        resp.setContentType("text/html");
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsersManager usersManager = UsersManager.getInstance();

        String login = req.getParameter("login");
        String password = req.getParameter("pd");

        user.setLogin(login);
        user.setPassword(password);

        usersManager.updateUser(user);
        resp.sendRedirect("/CRUD/");
    }
}