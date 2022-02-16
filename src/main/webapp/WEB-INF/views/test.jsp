<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 15.02.2022
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title place</title>
</head>
<body>
    Here are our params:
    <%=request.getAttribute("driverName")%>
    <%=request.getAttribute("driverBirthday")%>

    <a href="http://localhost:8080/drivers/get_all">Get all</a>
</body>
</html>
