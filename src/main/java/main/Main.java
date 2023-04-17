package main;


import dao.UserDaoJDBCImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.UserServiceImpl;
import servlets.SignInServlet;
import servlets.SignUpServlet;

import static org.eclipse.jetty.io.SelectChannelEndPoint.LOG;

public class Main {
    public static void main(String[] args) throws Exception {
        UserServiceImpl userService = new UserServiceImpl(new UserDaoJDBCImpl());
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(userService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(userService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        LOG.info("Server started");
        server.join();

//        SQL инъекция a';DROP TABLE users; SELECT * FROM userinfo WHERE 't' = 't

    }
}
