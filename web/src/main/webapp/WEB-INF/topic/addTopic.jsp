<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddTopic</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="reg.css">
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
    <c:if test="${error!=null}">
        <h4 style=" color: red">${error}</h4>
    </c:if>
    <c:if test="${role == 'admin'}">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form method="post" class="form-horizontal" action="addTopic">
                    <small>
                        <span class="heading ">Add Topic</span>
                    </small>
                    <div class="form-group">
                        <input type="text" class="form-control" name="addTopic" placeholder="Name Topic">
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default"
                                onclick="${pageContext.request.contextPath}/welcome'">Add Topic
                        </button>
                    </div>
                </form>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-primary">Your users</a>
                <a href="${pageContext.request.contextPath}/welcome" class="btn btn-primary">welcome</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
            </div>
        </div>
    </c:if>
    <c:if test="${role == 'user'}">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <c:if test="${error!=null}">
                    <h4 style=" color: red">${error}</h4>
                </c:if>
                <c:if test="${topicNull!=null}">
                    <h3 class="text-center">${topicNull}</h3>
                </c:if>
                <c:if test="${topicNull==null}">
                    <form action="addTopic" method="post">
                        <select name="idUserTopic">
                            <c:forEach var="topic" items="${topicList}">
                                <option value="<c:out value="${topic.id}"/>">
                                    <c:out value="${topic.nameTopic}"/>
                                </option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-default" onclick="${pageContext.request.contextPath}/welcome'">add</button>
                    </form>
                </c:if>
                <a href="${pageContext.request.contextPath}/welcome" class="btn btn-primary">welcome</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>