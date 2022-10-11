<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="reg.css">
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid" >
            <div class="col-md-8">
                <div class="navbar-header">
                    <a href="${pageContext.request.contextPath}/home" class="navbar-brand">It-Academy</a>
                </div>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/home">Главная</a> </li>
                    <li ><a href="#">О нас</a> </li>
                    <li><a href="#">Обратная связь</a> </li>
                    <li><a href="#">Registration</a> </li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <c:if test="${error!=null}">
                <h4 style=" color: red" >${error}</h4>
            </c:if>
            <form method="post" class="form-horizontal" action="upDate">
                <small>
                    <span class="heading ">Input</span>
                </small>
                <div class="form-group">
                    <input type="text" class="form-control" name="userName"
                           value="${pageContext.request.getAttribute("name")}" required placeholder="Login">
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group help">
                    <input type="password" class="form-control" name="password"
                           value="${pageContext.request.getAttribute("pass")}" required placeholder="Password">
                    <i class="fa fa-lock"></i>
                    <a href="#" class="fa fa-question-circle"></a>
                </div>
                <div class="form-group help">
                    <input type="password" class="form-control" name="password2"  placeholder="Password">
                    <i class="fa fa-lock"></i>
                    <a href="#" class="fa fa-question-circle"></a>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="email"
                           value="${pageContext.request.getAttribute("emailName")}" required placeholder="E-mail">
                    <i class="fa fa-user"></i>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default" >UpDate</button>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
                <c:if test="${role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/users" class="btn btn-primary" >Your users</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/hello" class="btn btn-primary">welcome</a>
            </form>
        </div>

    </div><!-- /.row -->
</div><!-- /.container -->
</body>
</html>
