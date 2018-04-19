package com.denis.controller;

import com.denis.service.UsersService;
import com.denis.service.UsersServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/delete-user", name = "DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {


    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        UsersService usersService = UsersServiceImpl.getInstance();
        String uuid =  req.getParameter("uuid");
        long id = Long.parseLong(req.getParameter("user"));
        usersService.deleteUser(usersService.getUserById(id));
        resp.sendRedirect("/CRUD/admin?uuid="+uuid);
    }
}