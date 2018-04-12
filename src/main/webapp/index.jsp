<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <body>
  <br>
  <h1>Вход в систему</h1>
  <table border="1">
    <caption>Пользователи</caption>
    <tr>
        <td>id</td>
        <td>login</td>
        <td>password</td>
    </tr>
    <c:forEach var="el" items="${users}">
      <tr>
        <td>${el.userId}</td>
        <td>${el.login}</td>
        <td>${el.password}</td>
      </tr>
    </c:forEach>
  </table>
  </body>
</html>

