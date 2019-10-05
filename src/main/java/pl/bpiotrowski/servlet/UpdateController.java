package pl.bpiotrowski.servlet;

import pl.bpiotrowski.entity.Todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/update")
public class UpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tasktoUpdateUuid = req.getParameter("taskToUpdate");
        if(tasktoUpdateUuid != null) {
            List<Todo> todoList = (List<Todo>) req.getSession().getAttribute("todoList");
            if(todoList != null) {
                for (Todo td : todoList) {
                    if (td.getUuid().equals(tasktoUpdateUuid)) {
                        req.getSession().setAttribute("task", td);
                        resp.sendRedirect("update");
                        return;
                    }
                }
            }
        }

        req.getRequestDispatcher("update.jsp").forward(req, resp);
    }
}
