/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Measurement {

    private int productID;
    private double quantity;
    private double rpu;
    private double total;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Get the value of total
     *
     * @return the value of total
     */
    public double getTotal() {
        return total;
    }

    /**
     * Set the value of total
     *
     * @param total new value of total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Get the value of rpu
     *
     * @return the value of rpu
     */
    public double getRpu() {
        return rpu;
    }

    /**
     * Set the value of rpu
     *
     * @param rpu new value of rpu
     */
    public void setRpu(double rpu) {
        this.rpu = rpu;
    }

    /**
     * Get the value of quantity
     *
     * @return the value of quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set the value of quantity
     *
     * @param quantity new value of quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
