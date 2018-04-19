package com.denis.servlet;

import com.denis.service.UsersService;
import com.denis.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
/*@WebServlet(urlPatterns = "/delete-user", name = "DeleteUserServlet")*/
public class DeleteUserServlet extends HttpServlet {

    private UsersService usersService;

    @Autowired
    public DeleteUserServlet(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        long id = Long.parseLong(req.getParameter("user"));
        usersService.deleteUser(usersService.getUserById(id));
        resp.sendRedirect("/CRUD/admin?uuid=" + uuid);
    }
}