/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Invoice {

    private int invoiceID;
    private double totalPaid;
    private double netPayable;
    private String invoiceNo;

    /**
     * Get the value of invoiceNo
     *
     * @return the value of invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Set the value of invoiceNo
     *
     * @param invoiceNo new value of invoiceNo
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * Get the value of netPayable
     *
     * @return the value of netPayable
     */
    public double getNetPayable() {
        return netPayable;
    }

    /**
     * Set the value of netPayable
     *
     * @param netPayable new value of netPayable
     */
    public void setNetPayable(double netPayable) {
        this.netPayable = netPayable;
    }

    /**
     * Get the value of totalPaid
     *
     * @return the value of totalPaid
     */
    public double getTotalPaid() {
        return totalPaid;
    }

    /**
     * Set the value of totalPaid
     *
     * @param totalPaid new value of totalPaid
     */
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    /**
     * Get the value of invoiceID
     *
     * @return the value of invoiceID
     */
    public int getInvoiceID() {
        return invoiceID;
    }

    /**
     * Set the value of invoiceID
     *
     * @param invoiceID new value of invoiceID
     */
    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }
}
