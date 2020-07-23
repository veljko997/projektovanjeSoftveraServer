/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import domain.Match;
import domain.Selection;
import domain.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Veljko
 */
public interface RMI extends Remote {

    public boolean deactiveSelection(Selection selection) throws Exception;

    public boolean deleteMatch(Match match) throws Exception;

    public boolean DeleteSelection(Selection selection) throws Exception;

    public List<Selection> getAllSelection() throws Exception;

    public List<Match> getMatches(Selection selection) throws Exception;

    public User login(String username,String password) throws Exception;

    public List<Selection> getRangList() throws Exception;

    public boolean register(String username,String password) throws Exception;

    public boolean saveMatch(Match match,int idUser) throws Exception;

    public boolean saveSelection(Selection selection,int idUser) throws Exception;
}
