/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import domain.User;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.log4j.Logger;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author veljko
 */
public class Login implements Service {

    private final Logger logger = Logger.getLogger(Login.class);

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        ResponseObject responseObject = new ResponseObject();
        try {
            HashMap<String, String> map
                    = (HashMap<String, String>) requestObject.getData();
            String username = map.get("username");
            String password = map.get("password");
            User user = new SQLImplementation().Login(username, password);

            responseObject = new ResponseObject();
            responseObject.setStatus(ResponseStatus.SUCESS);
            responseObject.setData(user);
        } catch (SQLException ex) {
            responseObject.setStatus(ResponseStatus.ERROR);
            logger.fatal(ex.getMessage());
        }
        return responseObject;
    }

}
