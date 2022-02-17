<%--
  Created by IntelliJ IDEA.
  User: VolkovAndrey
  Date: 17.02.2022
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding Driver</title>
    <script type="text/javascript">
        function take_values() {
            var n = document.forms["myform"]["name"].value;
            var n1 = document.forms["myform"]["birthdate"].value;

            if (n == null || n === "") {
                alert("Please enter Name");
                return false;
            } else if (n1 == null || n1 === "") {
                alert("Please enter Birthdate");
                return false;
            } else {
                var http = new XMLHttpRequest();
                http.open("POST", "http://localhost:8080/drivers/create/?", true);
                http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                var params = "name=" + n + "&birthDate=" + n1;
                http.send(params);
                http.onload = function () {
                    alert(http.responseText);
                }
            }
        }
    </script>
</head>
<body>

<center>
    <form name="myform">
        Name <input type="text" name="name"><br>
        Birthdate <input type="text" name="birthdate"><br>
        <input type="button" value="Submit" onclick="return take_values()">
    </form>

</center>

</body>
</html>
