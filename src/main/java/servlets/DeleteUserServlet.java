package servlets;

import database.dao.HibernateDaoFactory;
import managers.UsersManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delete-user", name = "DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        UsersManager usersManager = new UsersManager(new HibernateDaoFactory());
        String id = req.getParameter("id");
        usersManager.deleteUser(id);
        resp.sendRedirect("/CRUD/");
    }
}