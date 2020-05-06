/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import exceptions.IllegalDateException;
import exceptions.IllegalGoalsException;
import exceptions.IllegalMatchStateException;
import exceptions.IllegalSelectionException;
import java.util.Date;
import junit.framework.TestCase;
import org.junit.Before;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Veljko
 */
public class MatchTest {

    Selection testHost;
    Selection testAway;
    Match match;

    @BeforeTest
    void setUpClasses() throws IllegalSelectionException {
        testHost = Selection.createNewSelection("Serbia", Confederation.EUROPE);
        testAway = Selection.createNewSelection("Italy", Confederation.EUROPE);
    }

    @Test(expectedExceptions = IllegalMatchStateException.class)
    public void emptyHostGoals() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).awayGoals(2).build();
    }

    @Test(expectedExceptions = IllegalMatchStateException.class)
    public void emptyAwayGoals() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).hostGoals(3).build();
    }

    @Test(expectedExceptions = IllegalMatchStateException.class)
    public void emptyAwayAndHostGoals() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).build();
    }

    @Test(expectedExceptions = IllegalGoalsException.class)
    public void testHostGoalsNegative() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).hostGoals(-3).awayGoals(2).build();
    }

    @Test(expectedExceptions = IllegalGoalsException.class)
    public void testAwayGoalsNegative() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).hostGoals(3).awayGoals(-2).build();
    }

    @Test(expectedExceptions = IllegalGoalsException.class)
    public void testAwayAndHostExceptionMessage() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).hostGoals(-3).awayGoals(-2).build();
    }

    @Test(expectedExceptions = IllegalDateException.class)
    public void testDateInFuture() throws IllegalMatchStateException, IllegalGoalsException, IllegalDateException {
        Date date = new Date();
        date.setYear(121);
        match = new Match.Builder(testHost, testAway).hostGoals(1).awayGoals(1).date(date).build();
    }

    @Test(expectedExceptions = IllegalMatchStateException.class)
    public void testDateEmpty() throws IllegalMatchStateException, IllegalGoalsException {
        match = new Match.Builder(testHost, testAway).hostGoals(1).awayGoals(1).build();
    }

    @Test(expectedExceptions = IllegalMatchStateException.class)
    public void emptyMatchType() throws IllegalMatchStateException, IllegalGoalsException, IllegalDateException {
        Date date = new Date();
        date.setYear(119);
        match = new Match.Builder(testHost, testAway).hostGoals(1).awayGoals(1).date(date).build();
    }

    @Test
    public void testMatchesWithParameters() throws Exception {
        Date date = new Date();
        date.setYear(119);
        int awayGoals = 1;
        int hostGoals = 2;

        match = new Match.Builder(testHost, testAway).hostGoals(hostGoals).awayGoals(awayGoals).date(date).matchType(MatchType.WorldCup).build();

        TestCase.assertTrue(match.getAway().equals(testAway) && match.getHost().equals(testHost)
                && match.getMatchType() == MatchType.WorldCup && match.getDate().equals(date) && match.getAwayGoals() == awayGoals
                && match.getHostGoals() == hostGoals);
    }

    @Test
    public void testTwoMatches() throws Exception {
        Date date = new Date();
        date.setYear(119);
        int awayGoals = 1;
        int hostGoals = 2;

        match = new Match.Builder(testHost, testAway).hostGoals(hostGoals).awayGoals(awayGoals).date(date).matchType(MatchType.WorldCup).build();
        Match match2 = new Match.Builder(testHost, testAway).hostGoals(hostGoals).awayGoals(awayGoals).date(date).matchType(MatchType.WorldCup).build();

        TestCase.assertEquals(match2, match);
    }

    @Test
    public void twoMatchesDiffrentDate() throws Exception {
        Date date = new Date();
        date.setYear(119);
        int awayGoals = 1;
        int hostGoals = 2;

        Date date2 = new Date();
        date.setYear(109);

        match = new Match.Builder(testHost, testAway).hostGoals(hostGoals).awayGoals(awayGoals).date(date).matchType(MatchType.WorldCup).build();
        Match match2 = new Match.Builder(testHost, testAway).hostGoals(hostGoals).awayGoals(awayGoals).date(date2).matchType(MatchType.WorldCup).build();

        TestCase.assertTrue(!match.equals(match2));
    }
}
