/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import domain.Selection;
import java.util.HashMap;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author veljko
 */
public class SaveSelection implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        SQLImplementation sQLImplementation = new SQLImplementation();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) requestObject.getData();
        Selection selection = (Selection) hashMap.get("Selection");
        int id = (Integer) hashMap.get("Id");
        ResponseObject responseObject = new ResponseObject();
        if (sQLImplementation.insertSelection(selection, id)) {
            responseObject.setStatus(ResponseStatus.SUCESS);
        } else {
            responseObject.setStatus(ResponseStatus.ERROR);
        }
        return responseObject;
    }

}
