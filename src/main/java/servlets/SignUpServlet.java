package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UsersDataSet usersDataSet;

        try {
            usersDataSet = dbService.getUser(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        if (usersDataSet == null) {

            try {
                dbService.addUser(login, password);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }

            response.getWriter().println("successful registration");
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.getWriter().println("the user exists");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
