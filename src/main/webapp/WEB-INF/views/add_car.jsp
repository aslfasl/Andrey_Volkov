<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 16.02.2022
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>Add car</title>
    <style type="text/css">
        label {
            display: inline-block;
            width: 200px;
            margin: 5px 5px 5px 5px;
            text-align: left;
        }
        input[type=text], select {
            width: 200px;
        }
        button {
            margin: 5px 5px 5px 5px;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div align="center">
        <h1>Add new car</h1>

    <form:form action="add_car" method="post" modelAttribute="car">
        <form:label path="registrationNumber">Registration number</form:label>
        <form:input path="registrationNumber"/><br/>

        <form:label path="model">Model</form:label>
        <form:input path="model"/><br/>

        <form:label path="color">Color</form:label>
        <form:input path="color"/><br/>

        <form:label path="insurance">Insurance</form:label>
        <form:select path="insurance" items="${booleanList}" /><br/>

        <form:button>Save</form:button>

    </form:form>

        <a href="/home">Go home</a>
    </div>

</body>
</html>
