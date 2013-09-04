/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class ProductStock {

    private Product product;
    private Measurement purchaseProduct;
    private Measurement saleProduct;
    private Measurement wastageProduct;
    private Measurement returnProduct;
    private double quantityLeft;
    
    public ProductStock(){
        
        product = new Product();
        purchaseProduct = new Measurement();
        saleProduct = new Measurement();
        wastageProduct = new Measurement();
        returnProduct = new Measurement();
    }

    /**
     * Get the value of quantityLeft
     *
     * @return the value of quantityLeft
     */
    public double getQuantityLeft() {
        return quantityLeft;
    }

    /**
     * Set the value of quantityLeft
     *
     * @param quantityLeft new value of quantityLeft
     */
    public void setQuantityLeft(double quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Measurement getPurchaseProduct() {
        return purchaseProduct;
    }

    public void setPurchaseProduct(Measurement purchaseProduct) {
        this.purchaseProduct = purchaseProduct;
    }

    public Measurement getSaleProduct() {
        return saleProduct;
    }

    public void setSaleProduct(Measurement saleProduct) {
        this.saleProduct = saleProduct;
    }

    public Measurement getWastageProduct() {
        return wastageProduct;
    }

    public void setWastageProduct(Measurement wastageProduct) {
        this.wastageProduct = wastageProduct;
    }

    public Measurement getReturnProduct() {
        return returnProduct;
    }

    public void setReturnProduct(Measurement returnProduct) {
        this.returnProduct = returnProduct;
    }
}
