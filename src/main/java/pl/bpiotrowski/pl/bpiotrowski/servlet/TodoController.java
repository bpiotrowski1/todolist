package pl.bpiotrowski.pl.bpiotrowski.servlet;

import pl.bpiotrowski.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/todos")
public class TodoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskToDeleteUuid = req.getParameter("taskToDelete");
        List<Todo> todoList = (List<Todo>) req.getSession().getAttribute("todoList");
        if(todoList == null) {
            req.getSession().setAttribute("todoList", new ArrayList<>());
        } else if(taskToDeleteUuid != null) {
            for (Todo td : todoList) {
                if(td.getUuid().equals(taskToDeleteUuid)) {
                    todoList.remove(td);
                    break;
                }
            }
        }

        if(req.getParameter("lang") != null && !req.getParameter("lang").isEmpty()) {
            Cookie cookie = new Cookie("lang", req.getParameter("lang"));
            cookie.setMaxAge(60 * 60 * 24 * 365);
            resp.addCookie(cookie);
        }

        req.getRequestDispatcher("todos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Todo> todoList = (List<Todo>) req.getSession().getAttribute("todoList");
        if(req.getParameter("description") != null && !req.getParameter("description").isEmpty()) {
            Todo todo = new Todo();
            todo.setDescription(req.getParameter("description"));

            String date = req.getParameter("finishDate");
            if(date != null && !date.isEmpty()) {
                LocalDateTime finishDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                todo.setFinishDate(finishDate);
            }
            if(req.getParameter("priority") != null && !req.getParameter("priority").isEmpty()) {
                todo.setPriority(req.getParameter("priority"));
            }
            todoList.add(todo);
        }
        req.getRequestDispatcher("todos.jsp").forward(req, resp);
    }
}
