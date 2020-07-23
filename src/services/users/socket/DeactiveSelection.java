/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users.socket;

import domain.Selection;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author Veljko
 */
public class DeactiveSelection implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        Selection selection = (Selection) requestObject.getData();
        ResponseObject responseObject = new ResponseObject();
        if (new SQLImplementation().deactivateSelection(selection)) {
            responseObject.setStatus(ResponseStatus.SUCESS);
        } else {
            responseObject.setStatus(ResponseStatus.ERROR);
        }
        return responseObject;
    }

}
