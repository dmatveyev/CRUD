package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("name", "Devcolibri");

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
/*

        1) вытаскиваем ResultSet из базы
        2) преобразуем данные в список экземпляров класса.
        3) отправялем список в JSP
        4) в JSP пробегаемся по списку и выводим на печать.
        формирование листе,
        ArrayList listResults= convertToList(set); - заворачиваем в
        request.setAttribute("items", listResults);

        в JSP
        <jsp:useBean id="listResults" class="java.util.ArrayList" scope="request"/>


      <table>
  <c:forEach var="person" items="${items}">
  <tr>
    <td>${person.name}</td>
    <td>${person.age}</td>
    <td>${person.height}</td>
  </tr>
  </c:forEach>
</table>


        */
    }
}
