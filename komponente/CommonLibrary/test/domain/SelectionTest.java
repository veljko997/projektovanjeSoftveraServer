/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import exceptions.IllegalPointsRanking;
import exceptions.IllegalRankingException;
import exceptions.IllegalSelectionException;
import junit.framework.TestCase;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Veljko
 */
public class SelectionTest {

    Selection newSelection;
    Selection existingSelection;

    @BeforeTest
    public void setClass() throws IllegalSelectionException, IllegalRankingException, IllegalPointsRanking {
        newSelection = Selection.createNewSelection("Serbia", Confederation.EUROPE);
        existingSelection = Selection.createExistingSelection("Italy", Confederation.EUROPE, 1, 2);
    }

    @Test(expectedExceptions = IllegalSelectionException.class)
    public void createSelectionWithoutName() throws IllegalSelectionException {
        newSelection = Selection.createNewSelection("", Confederation.EUROPE);
    }

    @Test(expectedExceptions = IllegalSelectionException.class)
    public void createSelectionWithoutConfederation() throws IllegalSelectionException {
        newSelection = Selection.createNewSelection("Serbia", null);
    }

    @Test
    public void TestNewSelectionWithParametars() {
        TestCase.assertTrue(newSelection.getName().equals("Serbia") && newSelection.getConfederation() == Confederation.EUROPE);
    }

    @Test
    public void TestTwoNewSelections() throws IllegalSelectionException {
        Selection selection2 = Selection.createNewSelection("Serbia", Confederation.EUROPE);
        TestCase.assertEquals(newSelection, selection2);
    }

    @Test
    public void TestTwoExistingSelections() throws Exception {
        Selection existingSelection2 = Selection.createExistingSelection("Italy", Confederation.EUROPE, 1, 2);
        TestCase.assertTrue(existingSelection.equals(existingSelection2));
    }

    @Test
    public void TestTwoDifferentSelections() throws Exception {
        Selection existingSelection2 = Selection.createExistingSelection("Brazil", Confederation.SOUTHAMERICA, 10, 20);
        TestCase.assertTrue(!existingSelection.equals(existingSelection2));
    }

    @Test(expectedExceptions = IllegalRankingException.class)
    public void TestWrongRanking() throws IllegalSelectionException, IllegalRankingException, IllegalPointsRanking {
        Selection selection = Selection.createExistingSelection("Belgium", Confederation.EUROPE, -1, 10);
    }

    @Test(expectedExceptions = IllegalPointsRanking.class)
    public void TestWrongPoints() throws IllegalSelectionException, IllegalRankingException, IllegalPointsRanking {
        Selection selection = Selection.createExistingSelection("Belgium", Confederation.EUROPE, 100, -10);
    }

}
