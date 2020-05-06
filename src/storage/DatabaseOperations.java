/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import domain.Match;
import domain.Selection;
import domain.User;
import exceptions.DatabaseException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Veljko
 */
public interface DatabaseOperations {

    public User Login(String username, String password) throws Exception;

    public List<Selection> getAllSelections() throws Exception;

    public List<Selection> getAllSelectionsByPoints() throws Exception;

    public Match getMatch();

    public boolean deleteSelection(Selection selection);

    public boolean deleteMatch(Match match);

    public boolean deleteUser(User user);

    public boolean insertSelection(Selection selection, int id);


    public List<User> getAllUsers();

    public void updateUser(User user);

    public boolean deactivateSelection(Selection selection);

    public void updatePoints(Selection selection);

    public void saveMatch(Match match, Integer id) throws SQLException;

    public List<Match> getAwayMatches(Selection selection);

    public void updateRanking(Selection selection, int ranking);

    public List<Match> getHostMatches(Selection selection);

    public List<Selection> getRangList() throws DatabaseException;

    public boolean CheckUser(String username);

    public void register(String username, String password) throws SQLException;
}
