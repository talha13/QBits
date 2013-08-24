/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Salary {

    private int salaryID;
    private double basic;
    private double houseRent;
    private double medicalAllowance;
    private double conveyanceAllowance;
    private double incomeTax;
    private double providentFund;

    /**
     * Get the value of providentFund
     *
     * @return the value of providentFund
     */
    public double getProvidentFund() {
        return providentFund;
    }

    /**
     * Set the value of providentFund
     *
     * @param providentFund new value of providentFund
     */
    public void setProvidentFund(double providentFund) {
        this.providentFund = providentFund;
    }

    /**
     * Get the value of incomeTax
     *
     * @return the value of incomeTax
     */
    public double getIncomeTax() {
        return incomeTax;
    }

    /**
     * Set the value of incomeTax
     *
     * @param incomeTax new value of incomeTax
     */
    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    /**
     * Get the value of conveyanceAllowance
     *
     * @return the value of conveyanceAllowance
     */
    public double getConveyanceAllowance() {
        return conveyanceAllowance;
    }

    /**
     * Set the value of conveyanceAllowance
     *
     * @param conveyanceAllowance new value of conveyanceAllowance
     */
    public void setConveyanceAllowance(double conveyanceAllowance) {
        this.conveyanceAllowance = conveyanceAllowance;
    }

    /**
     * Get the value of medicalAllowance
     *
     * @return the value of medicalAllowance
     */
    public double getMedicalAllowance() {
        return medicalAllowance;
    }

    /**
     * Set the value of medicalAllowance
     *
     * @param medicalAllowance new value of medicalAllowance
     */
    public void setMedicalAllowance(double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    /**
     * Get the value of houseRent
     *
     * @return the value of houseRent
     */
    public double getHouseRent() {
        return houseRent;
    }

    /**
     * Set the value of houseRent
     *
     * @param houseRent new value of houseRent
     */
    public void setHouseRent(double houseRent) {
        this.houseRent = houseRent;
    }

    /**
     * Get the value of basic
     *
     * @return the value of basic
     */
    public double getBasic() {
        return basic;
    }

    /**
     * Set the value of basic
     *
     * @param basic new value of basic
     */
    public void setBasic(double basic) {
        this.basic = basic;
    }

    /**
     * Get the value of salaryID
     *
     * @return the value of salaryID
     */
    public int getSalaryID() {
        return salaryID;
    }

    /**
     * Set the value of salaryID
     *
     * @param salaryID new value of salaryID
     */
    public void setSalaryID(int salaryID) {
        this.salaryID = salaryID;
    }
}
