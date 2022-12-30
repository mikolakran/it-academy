<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Topic</title>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/> "/>
    <link rel="stylesheet"
          href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/> "/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reg.css'/>"/>
    <link rel="stylesheet" href="<c:url value='/resources/css/user.css'/>"/>
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="col-md-8">
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
            <div class="userinfo">
                <div class="user">
                    <ul>
                        <li>
                            <c:if test="${userForm.userName!=null}">
                                <c:if test="${userForm.photo!=null}">
                                    <img src="${pageContext.request.contextPath}/image" title="user-name"/>
                                </c:if>
                                <c:if test="${userForm.photo==null}">
                                    <img src="<c:url value='/resources/image/smail.jfif'/>" title="user-name"/>
                                </c:if>
                                <span>Hello ${userForm.userName}</span>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
            </div>
        </div>
    </nav>
</header>
<div class="container-fluid">
    <c:if test="${error!=null}">
        <h4 style=" color: red">${error}</h4>
    </c:if>
    <c:if test="${userForm.role == 'admin'}">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form:form method="post" class="form-horizontal" action="addTopic" modelAttribute="topicForm">
                    <small>
                        <span class="heading ">Add Topic</span>
                    </small>
                    <div class="form-group">
                        <form:input type="text" class="form-control" name="addTopic" placeholder="Name Topic"
                                    path="nameTopic"/>
                        <i class="fa fa-user"></i>
                    </div>
                    <div class="form-group">
                        <form:button type="submit" class="btn btn-default">
                            Add Topic
                        </form:button>
                    </div>
                </form:form>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-primary">Your users</a>
                <a href="${pageContext.request.contextPath}/welcome" class="btn btn-primary">welcome</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
            </div>
        </div>
    </c:if>
    <c:if test="${userForm.role == 'user'}">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <c:if test="${error!=null}">
                    <h4 style=" color: red">${error}</h4>
                </c:if>
                <c:if test="${topicNull!=null}">
                    <h3 class="text-center">${topicNull}</h3>
                </c:if>
                <c:if test="${topicNull==null}">
                    <form:form action="addTopic" method="post" modelAttribute="topicForm">
                        <form:select name="idAddTopic" path="idAddTopic">
                            <c:forEach var="topic" items="${topics}">
                                <option value="<c:out value="${topic.id}"/>">
                                    <c:out value="${topic.nameTopic}"/>
                                </option>
                            </c:forEach>
                        </form:select>
                        <button type="submit" class="btn btn-default">add</button>
                    </form:form>
                </c:if>
                <a href="${pageContext.request.contextPath}/welcome" class="btn btn-primary">welcome</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>