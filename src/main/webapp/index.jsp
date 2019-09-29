<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="pl.bpiotrowski.Todo" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<jsp:include page="header.jsp">
    <jsp:param name="active" value="1"/>
</jsp:include>
<%
    List<Todo> todoList = (List<Todo>) session.getAttribute("todoList");
    if(todoList == null) {
        session.setAttribute("todoList", new ArrayList<>());
        response.setHeader("REFRESH", "0");
    }

    if(request.getParameter("description") != null) {
        Todo todo = new Todo();
        todo.setDescription(request.getParameter("description"));

        String date = request.getParameter("finishDate");
        if(date != null && !date.isEmpty()) {
            LocalDateTime finishDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
            todo.setFinishDate(finishDate);
        }
        if(request.getParameter("priority") != null && !request.getParameter("priority").isEmpty()) {
            todo.setPriority(request.getParameter("priority"));
        }
        todoList.add(todo);
        response.sendRedirect("/");
    }
%>
--%>
<div class="container">
    <h1>Main page</h1>
    <hr/>
    <p>
        <form action="?" method="post">
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="description">Description:</label>
                <input name="description" type="text" class="form-control col-sm-8" id="description"/>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="finishDate">Finish date:</label>
                <input name="finishDate" type="datetime-local" class="form-control col-sm-8" id="finishDate"/>
            </div>
            <div class="form-group row">
                <label class="col-sm-4 col-form-label" for="priority">Priority:</label>
                <input name="priority" type="number" class="form-control col-sm-8" id="priority"/>
            </div>
            <button class="btn btn-primary col-sm-3">Send</button>
        </form>
        <br/><br/>
        <table class="table table-dark">
            <tr>
                <th>Description</th>
                <th>Finish date</th>
                <th>Priority</th>
            </tr>
            <%
                for (Todo td: todoList) {
                    out.print("<tr>");
                    out.print("<td>" + td.getDescription() + "</td><td>" +
                            (td.getFinishDate() == null ? "" : td.getFinishDate() ) + "</td><td>" +
                            (td.getPriority() == null ? "" : td.getPriority()) + "</td><td>" +
                            "<a href=\"/\">Delete task</a></td>");
                    out.print("</td>");
                }
            %>
        </table>
    </p>
</div>
<%@ include file="footer.jsp"%>
