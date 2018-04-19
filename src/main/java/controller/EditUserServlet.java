package controller;

import model.User;
import service.UsersService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/edit-user", name = "EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private User user;
    private UsersService usersService;
    private String uuid;

    public EditUserServlet() {
        user = new User();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        usersService = UsersService.getInstance();
        user = usersService.getUserById(Long.parseLong(req.getParameter("user")));
        req.setAttribute("user", user);
        uuid =  req.getParameter("uuid");
        req.setAttribute("uuid",uuid);
        resp.setContentType("text/html");
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usersService = UsersService.getInstance();

        String login = req.getParameter("login");
        String password = req.getParameter("pd");
        String role = req.getParameter("role");

        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);

        usersService.updateUser(user);
        resp.sendRedirect("/CRUD/admin?uuid="+uuid);
    }
}