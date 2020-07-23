/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users.rmi;

import domain.Match;
import domain.Selection;
import domain.User;
import exceptions.BusyUsernameException;
import exceptions.DatabaseException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import rmi.RMI;
import services.users.socket.GetAllSelections;
import storage.SQLImplementation;

/**
 *
 * @author Veljko
 */
public class RMIServer extends UnicastRemoteObject implements RMI {

    private final Logger logger = org.apache.log4j.Logger.getLogger(GetAllSelections.class);
    
    public RMIServer() throws RemoteException {
        super();
    }

    @Override
    public boolean deactiveSelection(Selection selection) {
        return new SQLImplementation().deactivateSelection(selection);
    }

    @Override
    public boolean deleteMatch(Match match) {
        return new SQLImplementation().deleteMatch(match);
    }

    @Override
    public boolean DeleteSelection(Selection selection) {
        return new SQLImplementation().deleteSelection(selection);
    }

    @Override
    public List<Selection> getAllSelection() throws Exception {
        try {
            return new SQLImplementation().getAllSelections();
        } catch (Exception ex) {
            logger.info(ex);
            throw ex;
        }
    }

    @Override
    public List<Match> getMatches(Selection selection) throws Exception {
        List<Match> matches = new SQLImplementation().getAwayMatches(selection);
        matches.addAll(new SQLImplementation().getHostMatches(selection));
        matches = matches.stream()
                .sorted(Comparator.comparing(Match::getDate).reversed())
                .collect(Collectors.toList());

        return matches;
    }

    @Override
    public List<Selection> getRangList() throws Exception {
        try {
            return new SQLImplementation().getRangList();
        } catch (DatabaseException ex) {
            logger.error(ex);
            throw ex;
        }
    }

    @Override
    public User login(String username, String password) throws DatabaseException {
        try {
            return new SQLImplementation().Login(username, password);
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DatabaseException();
        }
    }

    @Override
    public boolean register(String username, String password) throws BusyUsernameException {
        if (new SQLImplementation().CheckUser(username)) throw new BusyUsernameException();
        try {
            new SQLImplementation().register(username, password);
            return true;
        } catch (SQLException ex) {
            logger.error(ex);
            return false;
        }
    }

    @Override
    public boolean saveSelection(Selection selection, int idUser) throws Exception {
        return new SQLImplementation().insertSelection(selection, idUser);
    }

    @Override
    public boolean saveMatch(Match match, int idUser) throws Exception {
        return new SQLImplementation().saveMatch(match, idUser);
    }

}
