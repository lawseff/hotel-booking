package com.epam.booking;

import com.epam.booking.command.Command;
import com.epam.booking.command.factory.CommandFactory;
import com.epam.booking.command.factory.CommandFactoryImpl;
import com.epam.booking.command.CommandResult;
import com.epam.booking.connection.ConnectionPool;
import com.epam.booking.dao.DaoFactory;
import com.epam.booking.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static final String COMMAND_PARAMETER = "command";
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private ConnectionPool connectionPool;

    @Override
    public void init() {
        connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    public void destroy() {
        try {
            connectionPool.close();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try (Connection connection = connectionPool.getConnection()) {
            DaoFactory daoFactory = new DaoFactory(connection);
            CommandFactory commandFactory = new CommandFactoryImpl(daoFactory);
            String commandName = request.getParameter(COMMAND_PARAMETER);
            Command command = commandFactory.createCommand(commandName);
            CommandResult commandResult = command.execute(request, response);
            dispatch(request, response, commandResult);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServletException(e.getMessage(), e);
        }
    }



    private void dispatch(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (commandResult.isRedirect()) {
            String contextPath = getServletContext().getContextPath();
            response.sendRedirect(contextPath + page);
        } else {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
        }
    }

}
