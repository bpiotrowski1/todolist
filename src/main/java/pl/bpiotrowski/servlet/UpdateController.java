package pl.bpiotrowski.servlet;

import lombok.extern.slf4j.Slf4j;
import pl.bpiotrowski.entity.Todo;
import pl.bpiotrowski.repository.TodoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/update")
public class UpdateController extends HttpServlet {
    private static TodoRepository todoRepository;

    @Override
    public void init() throws ServletException {
        todoRepository = new TodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Test if taskToUpdate is not null: " + req.getParameter("taskToUpdate"));
        if (req.getParameter("taskToUpdate") != null) {
            log.info("True");
            Long taskToUpdateId = Long.parseLong(req.getParameter("taskToUpdate"));
            log.info("Task to update id: " + taskToUpdateId);
            Todo todo = todoRepository.findById(taskToUpdateId);
            log.info("Set request attribute task: " + todo);
            req.setAttribute("task", todo);
            resp.sendRedirect("update");
            return;
        }
        log.info("Attribute task: " + req.getAttribute("task"));

        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }
}
