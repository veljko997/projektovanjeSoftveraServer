/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.User;
import java.util.List;
import org.apache.log4j.Logger;
import services.server.ServiceReadDatabaseProperties;
import services.users.socket.Service;
import transfer.RequestObject;
import transfer.ResponseObject;

/**
 *
 * @author veljko
 */
public class Controller {

    private final Logger logger = Logger.getLogger(Controller.class);
    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public ResponseObject executeRequest(RequestObject requestObject) {
        String operation = (String) requestObject.getOperation();

        try {
            Service service
                    = (Service) Class.forName("services.users.socket." + operation).getConstructor().newInstance();
            return service.execute(requestObject);
        } catch (Exception ex) {
            logger.fatal(ex.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        return services.server.ServiceHandlingUsers.GetInstance().getAllUsers();
    }

    public void updateUser(List<User> users) {
        services.server.ServiceHandlingUsers.GetInstance().updateUsers(users);
    }

    public String readUrl() {
        return new ServiceReadDatabaseProperties().readUrl();
    }

    public String readUsername() {
        return new ServiceReadDatabaseProperties().readUsername();
    }

    public String readPassword() {
        return new ServiceReadDatabaseProperties().readPassword();
    }

    public void writeUrl(String url) {
        new ServiceReadDatabaseProperties().writeUrl(url);
    }

    public void writeUsername(String username) {
        new ServiceReadDatabaseProperties().writeUsername(username);
    }

    public void writePassword(String password) {
        new ServiceReadDatabaseProperties().writePassword(password);
    }

}
