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
    <link rel="stylesheet" href="../../home.css">
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
<div class="container-fluid">
    <div id="men-left" class="rad col-md-2"></div>
    <div class="col-md-2"></div>
    <div class=" col-md-4">
        <div>
            <h1 id="h1r" class=" text-center">Your posts</h1>
            <c:if test="${postNull!=null}">
                <h3 class="text-center">${postNull}</h3>
            </c:if>
            <c:if test="${postNull==null}">
                <h2 class="text-center">All Posts</h2>
            </c:if>
                <c:forEach var="post" items="${getPostList}">
                    <ul>
                        <li>Post name : ${post.name}
                        <li>Post text : ${post.text}</li>
                        <a href="${pageContext.request.contextPath}/updatePost?idTopic=${idTopic}&idPost=${post.id}"
                           class="btn btn-primary"> Update</a>
                        <a href="${pageContext.request.contextPath}/post?deletePost=${post.id}&idTopic=${idTopic}"
                           class="btn btn-primary"> Delete</a>
                        </li>
                    </ul>
                </c:forEach>
            <a href="${pageContext.request.contextPath}/addPost?idTopic=${idTopic}" class="btn btn-primary">add post</a>
            <a href="${pageContext.request.contextPath}/welcome" class="btn btn-primary">welcome</a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">logout</a>
        </div>
    </div>
    <div class="col-md-2"></div>
    <div class="col-md-2"></div>
</div>
</body>
</html>
