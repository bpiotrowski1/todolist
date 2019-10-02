<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${empty cookie.lang ? pageContext.response.locale : cookie.lang.value}"/>
<fmt:setBundle basename="todo"/>

<jsp:include page="header.jsp">
    <jsp:param name="active" value="2"/>
</jsp:include>
<div class="container">
    <h1><fmt:message key="todo.add.task"/></h1>
    <hr/>
    <p>
    <form action="todos" method="post">
        <div class="form-group row">
            <label class="col-sm-4 col-form-label" for="description"><fmt:message key="todo.description"/>:</label>
            <input name="description" type="text" class="form-control col-sm-8" id="description" value="${sessionScope.get("task").getDescription()}"/>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label" for="finishDate"><fmt:message key="todo.finish.date"/>:</label>
            <input name="finishDate" type="datetime-local" class="form-control col-sm-8" id="finishDate" value="${sessionScope.get("task").getFinishDate()}"/>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label" for="priority"><fmt:message key="todo.priority"/>:</label>
            <select class="form-control col-sm-8" id="priority" name="priority">
                <option value="HIGH" ${sessionScope.get('task').getPriority() == "HIGH" ? "selected" : ""}><fmt:message key="todo.priority.high"/></option>
                <option value="NORMAL" ${sessionScope.get('task').getPriority() == "NORMAL" ? "selected" : ""}><fmt:message key="todo.priority.normal"/></option>
                <option value="LOW" ${sessionScope.get('task').getPriority() == "LOW" ? "selected" : ""}><fmt:message key="todo.priority.low"/></option>
            </select>
        </div>
        <button class="btn btn-primary col-sm-3"><fmt:message key="todo.save"/></button>
    </form>
    </p>
</div>
<%@ include file="footer.jsp"%>
