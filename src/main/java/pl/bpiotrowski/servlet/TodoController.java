package pl.bpiotrowski.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.bpiotrowski.entity.Priority;
import pl.bpiotrowski.entity.Todo;
import pl.bpiotrowski.repository.TodoRepository;

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
    private static TodoRepository todoRepository;

    @Override
    public void init() throws ServletException {
        todoRepository = new TodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String lang = req.getParameter("lang");
        if (lang != null) {
            Cookie cookie = new Cookie("lang", lang);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            resp.addCookie(cookie);
            resp.sendRedirect("todos");
            return;
        }

        List<Todo> todos = todoRepository.findAll();
        req.setAttribute("todos", todos);

        if (req.getParameter("taskToDelete") != null) {
            Long idToDelete = Long.parseLong(req.getParameter("taskToDelete"));
            todoRepository.deleteById(idToDelete);
            resp.sendRedirect("todos");
            return;
        }

//        if(session.getAttribute("task") != null) {
//            session.removeAttribute("task");
//        }

        req.getRequestDispatcher("todos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("description") != null && !req.getParameter("description").isEmpty()) {
            log.debug("Creating todo task " + req.getParameter("description"));
            Todo todo = new Todo();
            todo.setDescription(req.getParameter("description"));

            String date = req.getParameter("finishDate");
            if (date != null && !date.isEmpty()) {
                LocalDateTime finishDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                todo.setFinishDate(finishDate);
            }
            if(req.getParameter("priority") != null && !req.getParameter("priority").isEmpty()) {
                Priority priority = Priority.valueOf(req.getParameter("priority").toUpperCase());
                todo.setPriority(priority);
            }

//            if(todoList.contains(req.getSession().getAttribute("task"))) {
//                todoList.remove(req.getSession().getAttribute("task"));
//                req.getSession().removeAttribute("task");
//            }

            todoRepository.save(todo);
        }
        resp.sendRedirect("todos");
    }
}
