/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import domain.Match;
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
public class SaveMatch implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        ResponseObject responseObject = new ResponseObject();
        try {
            SQLImplementation sQLImplementation = new SQLImplementation();
            HashMap<String, Object> hashMap = (HashMap<String, Object>) requestObject.getData();
            Match match = (Match) hashMap.get("Match");
            Integer id = (Integer) hashMap.get("Id");
            sQLImplementation.saveMatch(match, id);
            responseObject.setStatus(ResponseStatus.SUCESS);
        } catch (SQLException ex) {
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

}
