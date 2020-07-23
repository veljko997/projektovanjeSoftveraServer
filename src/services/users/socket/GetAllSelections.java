/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users.socket;

import org.apache.log4j.Logger;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author veljko
 */
public class GetAllSelections implements Service {

    private final Logger logger = Logger.getLogger(GetAllSelections.class);

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        ResponseObject responseObject = new ResponseObject();
        try {
            responseObject.setData(new SQLImplementation().getAllSelections());
            responseObject.setStatus(ResponseStatus.SUCESS);
        } catch (Exception ex) {
            responseObject.setStatus(ResponseStatus.ERROR);
            logger.info(ex.getMessage());
        }
        return responseObject;
    }

}
