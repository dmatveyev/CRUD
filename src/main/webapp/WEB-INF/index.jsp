<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <body>
  <h1>Пользователи</h1>
  <a href="./create-user?uuid=${uuid}">Create User</a>
  <table border="1">
    <tr>
        <td>id</td>
        <td>login</td>
        <td>password</td>
        <td>role</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach var="user" items="${users}">
      <tr>
        <td><c:out value="${user.id}" /></td>
        <td><c:out value="${user.login}" /></td>
        <td><c:out value="${user.password}" /></td>
        <td><c:out value="${user.role}" /></td>
         <td><a href="./edit-user?uuid=${uuid}&user=${user.id}">edit</a></td>
        <td><a href="./delete-user?uuid=${uuid}&user=${user.id}">delete</a></td>
      </tr>
    </c:forEach>
  </table>
  </body>
</html>

