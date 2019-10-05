package pl.bpiotrowski.servlet;

import pl.bpiotrowski.entity.Todo;
import pl.bpiotrowski.repository.TodoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateController extends HttpServlet {
    private static TodoRepository todoRepository;

    @Override
    public void init() throws ServletException {
        todoRepository = new TodoRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("taskToUpdate") != null) {
            Long taskToUpdateId = Long.parseLong(req.getParameter("taskToUpdate"));
            Todo todo = todoRepository.findById(taskToUpdateId);
            req.setAttribute("todo", todo);
            resp.sendRedirect("update");
            return;
        }

        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }
}
