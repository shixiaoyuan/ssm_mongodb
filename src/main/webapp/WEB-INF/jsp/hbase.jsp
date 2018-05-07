<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Onlyevan
  Date: 2017/10/21
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hbase</title>
    <style>
        table{
            text-align: center;
        }
    </style>
</head>
<body>
<table border="1" cellspacing="0" align="center" width="30%">
    <tr>
        <td rowspan="2">RowKey</td>
        <td colspan="5" align="center">info</td>
    </tr>
    <tr>
        <td>Load</td>
        <td>Name</td>
        <td>PTID</td>
        <td>Time Stamp</td>
        <td>Time Zone</td>
    </tr>
    <c:forEach items="${hbase}" var="data"  varStatus="num">
        <tr>
            <td>${num.count}</td>
            <c:forEach items="${data}" var="d">
                <td>${d.value}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
</body>
</html>
