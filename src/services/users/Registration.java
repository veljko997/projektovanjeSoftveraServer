/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import java.sql.SQLException;
import java.util.HashMap;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author veljko
 */
public class Registration implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        HashMap<String, String> map
                = (HashMap<String, String>) requestObject.getData();
        String username = map.get("username");
        String password = map.get("password");
        ResponseObject responseObject = new ResponseObject();
        if (!new SQLImplementation().CheckUser(username)) {
            try {
                register(username, password);
                responseObject.setStatus(ResponseStatus.SUCESS);
            } catch (SQLException ex) {
                responseObject.setStatus(ResponseStatus.ERROR);
                responseObject.setErrorMessage("Server side error.");
            }
        } else {
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage("Username is busy.");
        }
        return responseObject;
    }

    public void register(String username, String password) throws SQLException {
        new SQLImplementation().register(username, password);
    }
}
