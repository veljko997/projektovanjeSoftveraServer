/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import exceptions.DatabaseException;
import org.apache.log4j.Logger;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;

/**
 *
 * @author veljko
 */
public class RangList implements Service {
    
    private final Logger logger = Logger.getLogger(Login.class);
    
    @Override
    public ResponseObject execute(RequestObject requestObject) {
        ResponseObject responseObject = new ResponseObject();
        try {
            responseObject.setData(new SQLImplementation().getRangList());
        } catch (DatabaseException ex) {
            logger.error(ex.getMessage());
        }
        return responseObject;
    }
    
}
