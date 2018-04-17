package controllers;

import services.UsersService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delete-user", name = "DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        UsersService usersService = UsersService.getInstance();
        String id = req.getParameter("id");
        usersService.deleteUser(id);
        resp.sendRedirect("/CRUD/");
    }
}