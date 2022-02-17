<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 17.02.2022
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List of cars</title>
    <style type="text/css">
        span{
            display: inline-block;
            width: 250px;
            margin: 5px 5px 5px 5px;
            text-align: left;
        }
    </style>
</head>
    <body>
        <div align="center">
            <a href="/home">Go home</a>
            <h2>Car list:</h2>
            <c:forEach items="${cars}" var="car">
                <h3>Info:</h3>
                <span>Registration number: ${car.registrationNumber}</span><br/>
                <span>Model: ${car.model}</span><br/>
                <span>Color: ${car.color}</span><br/>
                <span>Insurance: ${car.insurance}</span><br/>
                ***<br/>
            </c:forEach>
        </div>
    </body>
</html>
