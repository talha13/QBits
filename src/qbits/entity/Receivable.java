/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Receivable {

    private int customerID;
    private String customerName;
    private String phone;
    private double payable;
    private double paid;

    /**
     * Get the value of paid
     *
     * @return the value of paid
     */
    public double getPaid() {
        return paid;
    }

    /**
     * Set the value of paid
     *
     * @param paid new value of paid
     */
    public void setPaid(double paid) {
        this.paid = paid;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get the value of amount
     *
     * @return the value of amount
     */
    public double getPayable() {
        return payable;
    }

    /**
     * Set the value of amount
     *
     * @param amount new value of amount
     */
    public void setPayable(double amount) {
        this.payable = amount;
    }

    /**
     * Get the value of Phone
     *
     * @return the value of Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the value of Phone
     *
     * @param Phone new value of Phone
     */
    public void setPhone(String Phone) {
        this.phone = Phone;
    }

    /**
     * Get the value of CustomerName
     *
     * @return the value of CustomerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Set the value of CustomerName
     *
     * @param CustomerName new value of CustomerName
     */
    public void setCustomerName(String CustomerName) {
        this.customerName = CustomerName;
    }
}
