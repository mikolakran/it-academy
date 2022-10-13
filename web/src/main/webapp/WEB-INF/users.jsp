<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Input</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="home.css">
</head>
<br>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="col-md-10">
                <div class="navbar-header">
                    <a href="${pageContext.request.contextPath}/home" class="navbar-brand">It-Academy</a>
                </div>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="#">О нас</a></li>
                    <li><a href="#">Обратная связь</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="container-fluid" >
    <div id="men-left" class="rad col-md-2" ></div>
    <div class="col-md-2" ></div>
    <div  class=" col-md-4">
        <div>
            <h1 id="h1r" class=" text-center ">Your users</h1>
            <div class="container-fluid" ></div>
            <div class="container-fluid"></div>
        </div>
    </div>
    <div class="col-md-2" ></div>
    <div class="col-md-2" ></div>
</div>
</br>
<div class="container-fluid">
    <div class="col-md-6">
            <c:forEach var="user" items="${list}">
                <ul>
                    <li>${user.id}, ${user.userName}, ${user.password}, ${user.email}, ${user.role}
                        <a href="${pageContext.request.contextPath}/upDate?id=${user.id}" class="btn btn-primary" >  Update</a>
                        <a href="${pageContext.request.contextPath}/users?id=${user.id}" class="btn btn-primary" >  Delete</a></li>
                </ul>
            </c:forEach>
    </div>
</div>
</br>
<div class="container-fluid">
    <div class="col-md-3"></div>
    <div class="col-md-2">
        <a href="${pageContext.request.contextPath}/hello" class="btn btn-primary">welcome</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" >logout</a>
    </div>
</div>
</body>
</html>
