<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 17.02.2022
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach items="${cars}" var="item">
        ${item}<br>
    </c:forEach>
</body>
</html>
