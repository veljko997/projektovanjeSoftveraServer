/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.server;

import domain.Match;
import domain.Selection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import storage.SQLImplementation;

/**
 *
 * @author Veljko
 */
public class ServiceCalcuteRangList {
    
    private static Logger logger = Logger.getLogger(ServiceCalcuteRangList.class);
    
    public static void calculate() throws Exception {
        List<Selection> selections = getAllSelections();
        for (Selection selection : selections) {
            selection.setMatches(getAllMatchesSelection(selection));
            calcutePoints(selection);
        }
        setRanking();
    }
    
    private static List<Selection> getAllSelections() throws Exception {
        List<Selection> selections = new LinkedList<>();
        try {
            selections = new SQLImplementation().getAllSelections();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        return selections;
    }
    
    public static List<Match> getAllMatchesSelection(Selection selection) {
        List<Match> matches = new LinkedList<>();
        matches.addAll(new SQLImplementation().getAwayMatches(selection));
        matches.addAll(new SQLImplementation().getHostMatches(selection));
        return matches;
    }
    
    private static void calcutePoints(Selection selection) {
        int points = 0;
        SQLImplementation sQLImplementation = new SQLImplementation();
        for (Match m : selection.getMatches()) {
           points += checkWinner(m, selection) * checkImportance(m) * checkDate(m)
                        * checkOpponentStrength(m, selection) * checkConfederationStrength(m, selection);
        }
        selection.setPoints(points);
        sQLImplementation.updatePoints(selection);
    }
    
    private static double checkWinner(Match m, Selection s) {
        if (m.getHost().equals(s)) {
            if (m.getHostGoals() > m.getAwayGoals()) {
                return 3;
            } else if (m.getHostGoals() == m.getAwayGoals()) {
                return 1;
            } else {
                return 0;
            }
        }
        
        if (m.getHostGoals() < m.getAwayGoals()) {
            return 3;
        } else if (m.getHostGoals() == m.getAwayGoals()) {
            return 1;
        }
        return 0;
    }
    
    private static double checkImportance(Match m) {
        return m.getMatchType().STRENGTH_POINTS;
    }
    
    private static double checkDate(Match m) {
        Date date = new Date();
        
        if (m.getDate().getYear() == date.getYear()) {
            return 1;
        } else if (m.getDate().getYear() + 1 == date.getYear()) {
            return 0.5;
        } else if (m.getDate().getYear() + 2 == date.getYear()) {
            return 0.3;
        } else if (m.getDate().getYear() + 3 == date.getYear()) {
            return 0.2;
        }
        return 0;
    }
    
    private static double checkOpponentStrength(Match m, Selection s) {
        return 200 - m.getOpponent(s).getRang();
    }
    
    private static double checkConfederationStrength(Match m, Selection s) {
        return m.getOpponent(s).getConfederation().STRENGTH_POINTS;
    }
    
    private static void setRanking() {
        int ranking = 1;
        try {
            SQLImplementation sQLImplementation = new SQLImplementation();
            List<Selection> selections = sQLImplementation.getAllSelectionsByPoints();
            
            for (Selection selection : selections) {
                sQLImplementation.updateRanking(selection, ranking++);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
