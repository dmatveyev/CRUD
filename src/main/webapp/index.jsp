<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <body>
  <br>
  <h1>Вход в систему</h1>
  <table>
    <caption>Пользователи</caption>
    <table>
     <c:forEach var="person" items="${items}">
     <tr>
       <td>${person.name}</td>
       <td>${person.age}</td>
       <td>${person.height}</td>
     </tr>
     </c:forEach>
   </table>
    </tr>
  </table>
  </body>
</html>

