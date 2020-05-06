/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author Veljko
 */
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private boolean active;
    private boolean administator;
    private static final long serialVersionUID = 11223381L;

    public boolean isAdministator() {
        return administator;
    }

    public void setAdministator(boolean administator) {
        this.administator = administator;
    }

    public int getId() {
        return id;
    }

    public User(int id, String username, String password, boolean active, boolean administator) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.administator = administator;
    }
    
    

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", active=" + active + '}';
    }

}
