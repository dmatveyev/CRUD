<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <body>
  <h1>Вход в систему</h1>
  <a href="./create-user">Create User</a>
  <table border="1">
    <caption>Пользователи</caption>
    <tr>
        <td>id</td>
        <td>login</td>
        <td>password</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach var="user" items="${users}">
      <tr>
        <td><c:out value="${user.userId}" /></td>
        <td><c:out value="${user.login}" /></td>
        <td><c:out value="${user.password}" /></td>
         <td><a href="./edit-user?id=${user.userId}">edit</a></td>
        <td><a href="./delete-user?id=${user.userId}">delete</a></td>
      </tr>
    </c:forEach>
  </table>
  </body>
</html>

