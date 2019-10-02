package pl.bpiotrowski.servlet;

import pl.bpiotrowski.entity.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/update")
public class TodoUpdateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String taskToDeleteUuid = req.getParameter("taskToUpdate");
        if(taskToDeleteUuid != null) {
            List<Todo> todoList = (List<Todo>) req.getSession().getAttribute("todoList");
            if(todoList != null) {
                for (Todo td : todoList) {
                    if (td.getUuid().equals(taskToDeleteUuid)) {
                        session.setAttribute("task", td);
                        resp.sendRedirect("update");
                        return;
                    }
                }
            }
        }

        req.getRequestDispatcher("todoUpdate.jsp").forward(req, resp);
    }
}
