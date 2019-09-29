<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${empty cookie.lang ? pageContext.response.locale : cookie.lang.value}"/>
<fmt:setBundle basename="todo"/>

<jsp:include page="header.jsp">
    <jsp:param name="active" value="1"/>
</jsp:include>
<div class="container">
    <h1><fmt:message key="todo.add.task"/></h1>
    <hr/>
    <p>
        <form action="todos" method="post">
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="description"><fmt:message key="todo.description"/>:</label>
                <input name="description" type="text" class="form-control col-sm-8" id="description"/>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="finishDate"><fmt:message key="todo.finish.date"/>:</label>
                <input name="finishDate" type="datetime-local" class="form-control col-sm-8" id="finishDate"/>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="priority"><fmt:message key="todo.priority"/>:</label>
                <select class="form-control col-sm-8" id="priority" name="priority">
                    <option value="HIGH"><fmt:message key="todo.priority.high"/></option>
                    <option value="NORMAL" selected><fmt:message key="todo.priority.normal"/></option>
                    <option value="LOW"><fmt:message key="todo.priority.low"/></option>
                </select>
            </div>
            <button class="btn btn-primary col-sm-3"><fmt:message key="todo.save"/></button>
        </form>
    </p>
    <br/><br/>
    <c:if test="${todoList.size() > 0}">
        <hr/>
        <p>
            <table class="table table-hover">
                <thead class="thead-dark">
                    <th>Description</th>
                    <th>Finish date</th>
                    <th>Priority</th>
                    <th>Delete</th>
                </thead>
                <c:if test="${todoList.size() > 0}">
                    <c:forEach var="task" items="${todoList}">
                        <c:url var="deleteTask" value="/">
                            <c:param name="taskToDelete" value="${task.getUuid()}"/>
                        </c:url>
                        <tr>
                            <td>${task.getDescription()}</td>
                            <td>
                                <c:if test="${task.getFinishDate() != null}">
                                    ${task.getFinishDate()}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${task.getPriority() != null}">
                                    ${task.getPriority()}
                                </c:if>
                            </td>
                            <td><a href="${deleteTask}">Delete task</a></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </p>
    </c:if>
</div>
<%@ include file="footer.jsp"%>
