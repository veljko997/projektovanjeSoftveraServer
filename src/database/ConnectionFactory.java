/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Veljko
 */
public class ConnectionFactory {

    private Connection connection;
    public static ConnectionFactory instance;
    private final Logger logger = Logger.getLogger(ConnectionFactory.class);

    private ConnectionFactory() throws SQLException {
        String url = Controller.getInstance().readUrl().trim();
        String username = Controller.getInstance().readUsername().trim();
        String password = Controller.getInstance().readPassword().trim();
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            logger.fatal(ex.getMessage());
            throw new SQLException("Connection is not created!");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConnectionFactory getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new ConnectionFactory();
        }
        return instance;
    }
}
