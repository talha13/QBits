/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Topu
 */
public class Supplier {

    private int supplierID;
    private int contactPersonID;
    private int addressID;
    private String phone;
    private String name;
    private String contactFirstName;
    private String contactLastName;

    /**
     * Get the value of contactLastName
     *
     * @return the value of contactLastName
     */
    public String getContactLastName() {
        return contactLastName;
    }

    /**
     * Set the value of contactLastName
     *
     * @param contactLastName new value of contactLastName
     */
    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    /**
     * Get the value of contactFirstName
     *
     * @return the value of contactFirstName
     */
    public String getContactFirstName() {
        return contactFirstName;
    }

    /**
     * Set the value of contactFirstName
     *
     * @param contactFirstName new value of contactFirstName
     */
    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
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
     * Get the value of phone
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the value of phone
     *
     * @param phone new value of phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

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
     * Get the value of contactPersonID
     *
     * @return the value of contactPersonID
     */
    public int getContactPersonID() {
        return contactPersonID;
    }

    /**
     * Set the value of contactPersonID
     *
     * @param contactPersonID new value of contactPersonID
     */
    public void setContactPersonID(int contactPersonID) {
        this.contactPersonID = contactPersonID;
    }

    /**
     * Get the value of supplierID
     *
     * @return the value of supplierID
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * Set the value of supplierID
     *
     * @param supplierID new value of supplierID
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
}
