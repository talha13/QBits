/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Customer {

    private String firstName;
    private String lastName;
    private int customerID;
    private int personID;
    private String contactNo;
    private int addressID;

    /**
     * Get the value of addressID
     *
     * @return the value of addressID
     */
    public int getAddressID() {
        return addressID;
    }

    /**
     * Set the value of addressID
     *
     * @param addressID new value of addressID
     */
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    /**
     * Get the value of contactNo
     *
     * @return the value of contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Set the value of contactNo
     *
     *
     * @param contactNo new value of contactNo
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * Get the value of personID
     *
     * @return the value of personID
     */
    public int getPersonID() {
        return personID;
    }

    /**
     * Set the value of personID
     *
     * @param personID new value of personID
     */
    public void setPersonID(int personID) {
        this.personID = personID;
    }

    /**
     * Get the value of customerID
     *
     * @return the value of customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set the value of customerID
     *
     * @param customerID new value of customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
