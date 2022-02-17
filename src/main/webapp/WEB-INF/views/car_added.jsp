<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 17.02.2022
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car added</title>
    <style type="text/css">
        span{
            display: inline-block;
            width: 150px;
            margin: 5px 5px 5px 5px;
            text-align: left;
        }
    </style>
</head>
<body>
    <div align="center">
        <h2>Car added</h2>
        <span>Registration number: </span><span>${car.registrationNumber}</span><br/>
        <span>Model: </span><span>${car.model}</span><br/>
        <span>Color: </span><span>${car.color}</span><br/>
        <span>Insurance: </span><span>${car.insurance}</span><br/>
        <a href="/home">Go home</a>
    </div>
</body>
</html>
