/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users.socket;

import domain.Match;
import domain.Selection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import storage.SQLImplementation;
import transfer.RequestObject;
import transfer.ResponseObject;

/**
 *
 * @author Veljko
 */
public class GetMatches implements Service {

    @Override
    public ResponseObject execute(RequestObject requestObject) {
        Selection selection = (Selection) requestObject.getData();
        List<Match> matches = new SQLImplementation().getHostMatches(selection);
        matches.addAll(new SQLImplementation().getAwayMatches(selection));
        
        matches = matches.stream()
                .sorted(Comparator.comparing(Match::getDate).reversed())
                .collect(Collectors.toList());
        
        ResponseObject responseObject = new ResponseObject();
        responseObject.setData(matches);
        return responseObject;
    }
}
