/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.server;

import domain.User;
import java.util.List;
import storage.SQLImplementation;

/**
 *
 * @author veljko
 */
public class ServiceHandlingUsers {
    
    private final static ServiceHandlingUsers instance;
    
    static {
     instance = new ServiceHandlingUsers();
    }
    
    private ServiceHandlingUsers(){}
    
    public static ServiceHandlingUsers GetInstance() {
        return instance;
    }
    
    public List<User> getAllUsers() {
        return new SQLImplementation().getAllUsers();
    }
    
    public void updateUsers(List<User> users) {
        for (User user : users) {
            new SQLImplementation().updateUser(user);
        }
    }
}
