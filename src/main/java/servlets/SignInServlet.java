package servlets;

import dbService.UsersDataSet;
import service.UserServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final UserServiceImpl userService;

    public SignInServlet(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.getWriter().println(request.getParameter("key"));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");

        if (login == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (login.equals("") || password.equals("")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UsersDataSet usersDataSet;
        try {
            usersDataSet = userService.getUser(login);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (usersDataSet == null || !usersDataSet.getPassword().equals(password)) {

            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        response.getWriter().println("Authorized: " + login);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
