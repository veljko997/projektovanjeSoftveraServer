/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users.socket;

import domain.Match;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.ResponseStatus;

/**
 *
 * @author Veljko
 */
public class DeleteMatch implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        Match match = (Match) requestObject.getData();
        ResponseObject responseObject = new ResponseObject();
        if (new SQLImplementation().deleteMatch(match)) {
            responseObject.setStatus(ResponseStatus.SUCESS);
        } else {
            responseObject.setStatus(ResponseStatus.ERROR);
        }
        return responseObject;
    }

}
