/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticFactory;

import domain.MatchType;
import static domain.MatchType.*;

/**
 *
 * @author Veljko
 */
public class MatchTypeFactory {
     public static MatchType createMatchType(String matchType) {
            if(matchType.equals("ConfederationCup")) return ConfederationCup;
            if(matchType.equals("FriendlyGame")) return FriendlyGame;
            if(matchType.equals("WorldCup")) return WorldCup;
            if(matchType.equals("WorldCupQualifier")) return WorldCupQualifier;
            return null;
    }
}
