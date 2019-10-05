package pl.bpiotrowski.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.bpiotrowski.entity.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/todos")
public class TodoController extends HttpServlet {
    private static Logger log = LoggerFactory.getLogger(TodoController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("todoList") == null) {
            List<Todo> todoList = new ArrayList<>();
            session.setAttribute("todoList", todoList);
        }

        String lang = req.getParameter("lang");
        if(lang != null) {
            Cookie cookie = new Cookie("lang", lang);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            resp.addCookie(cookie);
            resp.sendRedirect("todos");
            return;
        }

        String taskToDeleteUuid = req.getParameter("taskToDelete");
        if(taskToDeleteUuid != null) {
            List<Todo> todoList = (List<Todo>) req.getSession().getAttribute("todoList");
            if(todoList != null) {
                for (Todo td : todoList) {
                    if (td.getUuid().equals(taskToDeleteUuid)) {
                        todoList.remove(td);
                        resp.sendRedirect("todos");
                        return;
                    }
                }
            }
        }

        if(session.getAttribute("task") != null) {
            session.removeAttribute("task");
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

            if(todoList.contains(req.getSession().getAttribute("task"))) {
                todoList.remove(req.getSession().getAttribute("task"));
                req.getSession().removeAttribute("task");
            }

            todoList.add(todo);
        }
        resp.sendRedirect("todos");
    }
}
