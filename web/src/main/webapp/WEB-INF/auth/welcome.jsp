<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="home.css">
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="col-md-10">
                <div class="navbar-header">
                    <a href="#" class="navbar-brand">It-Academy</a>
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
            <h1 id="h1r" class=" text-center">Welcome</h1>
            <c:if test="${role == 'admin'}">
                <c:if test="${topicNull!=null}">
                    <h3 class="text-center">${topicNull}</h3>
                </c:if>
                <c:if test="${topicNull==null}">
                    <h2 class="text-center">All Topic</h2>
                </c:if>
                <c:if test="${name!=null}">
                    <h3 style="color: deepskyblue">Hello ${name}</h3>
                </c:if>
                <c:forEach var="topic" items="${topicList}">
                    <ul>
                        <li>${topic.nameTopic}
                           <%-- <a href="${pageContext.request.contextPath}/welcome?deleteTopic=${topic.id}"
                               class="btn btn-primary"> Delete</a></li>--%>
                    </ul>
                </c:forEach>
                <a href="${pageContext.request.contextPath}/addTopic" class="btn btn-primary">Add Topic</a>
                <a href="${pageContext.request.contextPath}/upDate?idUser=${user.id}" class="btn btn-primary" >Update user</a>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-primary">Your users</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">logout</a>
            </c:if>

            <c:if test="${role == 'user'}">
                <c:if test="${topicNull!=null}">
                    <h3 class="text-center">${topicNull}</h3>
                </c:if>
                <c:if test="${topicNull==null}">
                    <h2 class="text-center">All Topic</h2>
                </c:if>
                <c:if test="${name!=null}">
                    <h3 style="color: deepskyblue">Hello ${name}</h3>
                </c:if>
                <c:forEach var="topic" items="${topicList}">
                    <ul>
                        <li>${topic.nameTopic}
                            <a href="${pageContext.request.contextPath}/post?idTopic=${topic.id}"
                               class="btn btn-primary"> Display Topic</a>
                            <a href="${pageContext.request.contextPath}/welcome?idUserTopic=${topic.id}"
                               class="btn btn-primary"> Delete Topic</a>
                        </li>
                    </ul>
                </c:forEach>
                <a href="${pageContext.request.contextPath}/addTopic" class="btn btn-primary">Add Topic</a>
                <a href="${pageContext.request.contextPath}/upDate?idUser=${user.id}" class="btn btn-primary" >Update user</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">logout</a>
            </c:if>
        </div>
    </div>
    <div class="col-md-2"></div>
    <div class="col-md-2"></div>
</div>

</body>
</html>
