<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Onlyevan
  Date: 2017/10/3
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>mongo</title>
</head>
<body>
    <table border="1" cellspacing="0" align="center">
        <tr>
            <td>username</td>
            <td>password</td>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
