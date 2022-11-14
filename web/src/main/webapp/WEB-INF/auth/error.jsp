<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="reg.css">
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="col-md-8">
                <div class="navbar-header">
                    <a href="${pageContext.request.contextPath}/home" class="navbar-brand">It-Academy</a>
                </div>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/home">Главная</a></li>
                    <li><a href="hello">О нас</a></li>
                    <li><a href="#">Обратная связь</a></li>
                    <li><a href="registration">Registration</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="container-fluid">
    <img src="../image/2.jfif" style="width:100%">
</div>
</body>
</html>
