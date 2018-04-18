package controllers;


import services.UsersService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet(urlPatterns = "/create-user", name = "createUserServlet")
public class CreateUserServlet extends HttpServlet {
    private String uuid;
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        uuid =  req.getParameter("uuid");
        req.setAttribute("uuid",uuid);
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/createUser.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pd");
        UsersService usersService = UsersService.getInstance();
        usersService.createUser(login, password);
        resp.sendRedirect("/CRUD/admin?uuid=" + uuid);
    }
}