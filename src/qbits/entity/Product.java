/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Topu
 */
public class Product {

    private int id;
    private String name;
    private String brand;
    private String unit;
    private double rpu;
    private String code;
    private int categoryID;
    private double quantity;
    private String category;
    private double notifyQuantity;
    private String genre;

    /**
     * Get the value of genre
     *
     * @return the value of genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Set the value of genre
     *
     * @param genre new value of genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Get the value of notifyQuantity
     *
     * @return the value of notifyQuantity
     */
    public double getNotifyQuantity() {
        return notifyQuantity;
    }

    /**
     * Set the value of notifyQuantity
     *
     * @param notifyQuantity new value of notifyQuantity
     */
    public void setNotifyQuantity(double notifyQuantity) {
        this.notifyQuantity = notifyQuantity;
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(String category) {
        this.category = category;
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

    /**
     * Get the value of categoryID
     *
     * @return the value of categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Set the value of categoryID
     *
     * @param categoryID new value of categoryID
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
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
     * Get the value of unit
     *
     * @return the value of unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the value of unit
     *
     * @param unit new value of unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Get the value of brand
     *
     * @return the value of brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the value of brand
     *
     * @param brand new value of brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
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
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }
}
