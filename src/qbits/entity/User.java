/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Topu
 */
public class User {

    private String userName;
    private int userID;
    private String name;
    private Set<String> roles;

    public User() {
        roles = new HashSet<>();
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of userID
     *
     * @return the value of userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Set the value of userID
     *
     * @param userID new value of userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoles(Set roles) {
        this.roles = roles;
    }

    public void setRole(String role) {
        this.roles.add(role);
    }

    public boolean canPlay(String thisRole) {
        return roles.contains(thisRole);
    }
}
