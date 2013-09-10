/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Measurement;
import qbits.entity.Product;
import qbits.entity.ProductStock;
import qbits.common.Message;

/**
 *
 * @author Pipilika
 */
public class ProductReport {

    public HashMap<Integer, ProductStock> getCurrentStocks() {
        return getStocks(null, null);
    }

    public HashMap<Integer, ProductStock> getStocks(Date startDate, Date endDate) {

        ArrayList<Measurement> wastageProducts = getWastagedProduct(startDate, endDate);
        ArrayList<Measurement> returnedProducts = getReturnedProduct(startDate, endDate);
        ArrayList<Measurement> purchasedProducts = getPurchasedProduct(startDate, endDate);
        ArrayList<Measurement> soldProducts = getSoldProduct(startDate, endDate);

        return mergeMeasurments(wastageProducts, returnedProducts, purchasedProducts, soldProducts);
    }

    public HashMap<Integer, ProductStock> mergeMeasurments(ArrayList<Measurement> wastageProducts, ArrayList<Measurement> returnedProducts,
            ArrayList<Measurement> purchasedProducts, ArrayList<Measurement> soldProducts) {

        HashMap<Integer, ProductStock> productStocks = getProducts();

        // populate wastaged products
        for (Measurement m : wastageProducts) {

            if (productStocks.containsKey(m.getProductID())) {

                ProductStock productStock = productStocks.get(m.getProductID());
                productStock.getWastageProduct().setQuantity(m.getQuantity());
                productStock.getWastageProduct().setRpu(m.getRpu());
                productStock.getWastageProduct().setTotal(m.getTotal());
                productStock.setQuantityLeft(productStock.getQuantityLeft() - m.getQuantity());

            } else {
                System.err.println("Wastage Product Not Found " + m.getProductID());
            }
        }

        // populate returned products
        for (Measurement m : returnedProducts) {

            if (productStocks.containsKey(m.getProductID())) {

                ProductStock productStock = productStocks.get(m.getProductID());
                productStock.getReturnProduct().setQuantity(m.getQuantity());
                productStock.getReturnProduct().setRpu(m.getRpu());
                productStock.getReturnProduct().setTotal(m.getTotal());
                productStock.setQuantityLeft(productStock.getQuantityLeft() - m.getQuantity());

            } else {
                System.err.println("Returned Product Not Found " + m.getProductID());
            }
        }

        // populate purchased products
        for (Measurement m : purchasedProducts) {

            if (productStocks.containsKey(m.getProductID())) {

                ProductStock productStock = productStocks.get(m.getProductID());
                productStock.getPurchaseProduct().setQuantity(m.getQuantity());
                productStock.getPurchaseProduct().setRpu(m.getRpu());
                productStock.getPurchaseProduct().setTotal(m.getTotal());
                productStock.setQuantityLeft(productStock.getQuantityLeft() + m.getQuantity());

            } else {
                System.err.println("Purchased Product Not Found " + m.getProductID());
            }
        }

        // populate sold products
        for (Measurement m : soldProducts) {

            if (productStocks.containsKey(m.getProductID())) {

                ProductStock productStock = productStocks.get(m.getProductID());
                productStock.getSaleProduct().setQuantity(m.getQuantity());
                productStock.getSaleProduct().setRpu(m.getRpu());
                productStock.getSaleProduct().setTotal(m.getTotal());
                productStock.setQuantityLeft(productStock.getQuantityLeft() - m.getQuantity());

            } else {
                System.err.println("Sold Product Not Found " + m.getProductID());
            }
        }

        return productStocks;
    }

    public void populateOpeningProduct(Date startDate) {
    }

    public ArrayList<Measurement> getWastagedProduct(Date startDate, Date endDate) {

        ArrayList<Measurement> wastageProducts = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Measurement measurement;

        queryBuilder.clear();

        queryBuilder.select("product_id, SUM(rate_per_unit) AS total_rate, SUM(quantity) AS total_qty");
        queryBuilder.from("product_damage");

        if (startDate != null && endDate != null) { // between tow dates
            queryBuilder.where("entry_date >= " + Utilities.dateForDB(startDate));
            queryBuilder.where("entry_date <= " + Utilities.dateForDB(endDate));
        } else if (startDate != null && endDate == null) { // defore start date
            queryBuilder.where("entry_date < " + Utilities.dateForDB(startDate));
        } else if (startDate == null && endDate != null) { // after end date
            queryBuilder.where("entry_date > " + Utilities.dateForDB(endDate));
        }

        queryBuilder.groupBy("product_id");

        if (database.connect()) {

            try {

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {
                    measurement = new Measurement();
                    measurement.setProductID(resultSet.getInt("product_id"));
                    measurement.setQuantity(resultSet.getDouble("total_qty"));
                    measurement.setTotal(resultSet.getDouble("total_rate"));
                    measurement.setRpu(measurement.getTotal() / measurement.getQuantity());
                    wastageProducts.add(measurement);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductReport.class.getName()).log(Level.SEVERE, null, ex);
                Message.warning("Unable populate wastage product");
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return wastageProducts;
    }

    public ArrayList<Measurement> getReturnedProduct(Date startDate, Date endDate) {

        ArrayList<Measurement> returnProducts = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Measurement measurement;

        queryBuilder.clear();

        queryBuilder.select("product_id, SUM(rate_per_unit) AS total_rate, SUM(quantity) AS total_qty");
        queryBuilder.from("product_return_to_supplier");

        if (startDate != null && endDate != null) { // between tow dates
            queryBuilder.where("entry_date >= " + Utilities.dateForDB(startDate));
            queryBuilder.where("entry_date <= " + Utilities.dateForDB(endDate));
        } else if (startDate != null && endDate == null) { // defore start date
            queryBuilder.where("entry_date < " + Utilities.dateForDB(startDate));
        } else if (startDate == null && endDate != null) { // after end date
            queryBuilder.where("entry_date > " + Utilities.dateForDB(endDate));
        }

        queryBuilder.groupBy("product_id");

        if (database.connect()) {

            try {

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {
                    measurement = new Measurement();
                    measurement.setProductID(resultSet.getInt("product_id"));
                    measurement.setQuantity(resultSet.getDouble("total_qty"));
                    measurement.setTotal(resultSet.getDouble("total_rate"));
                    measurement.setRpu(measurement.getTotal() / measurement.getQuantity());
                    returnProducts.add(measurement);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductReport.class.getName()).log(Level.SEVERE, null, ex);
                Message.warning("Unable populate return product");
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return returnProducts;
    }

    public ArrayList<Measurement> getPurchasedProduct(Date startDate, Date endDate) {

        ArrayList<Measurement> purchaseProduct = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Measurement measurement;

        queryBuilder.clear();

        queryBuilder.select("product_id, SUM(cost_per_unit) AS total_rate, SUM(quantity) AS total_qty");
        queryBuilder.from("product_stock");

        if (startDate != null && endDate != null) { // between tow dates
            queryBuilder.where("entry_date >= " + Utilities.dateForDB(startDate));
            queryBuilder.where("entry_date <= " + Utilities.dateForDB(endDate));
        } else if (startDate != null && endDate == null) { // defore start date
            queryBuilder.where("entry_date < " + Utilities.dateForDB(startDate));
        } else if (startDate == null && endDate != null) { // after end date
            queryBuilder.where("entry_date > " + Utilities.dateForDB(endDate));
        }

        queryBuilder.groupBy("product_id");

        if (database.connect()) {

            try {

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {
                    measurement = new Measurement();
                    measurement.setProductID(resultSet.getInt("product_id"));
                    measurement.setQuantity(resultSet.getDouble("total_qty"));
                    measurement.setTotal(resultSet.getDouble("total_rate"));
                    measurement.setRpu(measurement.getTotal() / measurement.getQuantity());
                    purchaseProduct.add(measurement);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductReport.class.getName()).log(Level.SEVERE, null, ex);
                Message.warning("Unable populate return product");
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return purchaseProduct;
    }

    public ArrayList<Measurement> getSoldProduct(Date startDate, Date endDate) {

        ArrayList<Measurement> salesProduct = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Measurement measurement;

        queryBuilder.clear();

        queryBuilder.select("product_id, SUM(cost_per_unit) AS total_rate, SUM(quantity) AS total_qty");
        queryBuilder.from("product_stock");

        if (startDate != null && endDate != null) { // between tow dates
            queryBuilder.where("entry_date >= " + Utilities.dateForDB(startDate));
            queryBuilder.where("entry_date <= " + Utilities.dateForDB(endDate));
        } else if (startDate != null && endDate == null) { // defore start date
            queryBuilder.where("entry_date < " + Utilities.dateForDB(startDate));
        } else if (startDate == null && endDate != null) { // after end date
            queryBuilder.where("entry_date > " + Utilities.dateForDB(endDate));
        }

        queryBuilder.groupBy("product_id");

        if (database.connect()) {

            try {

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {
                    measurement = new Measurement();
                    measurement.setProductID(resultSet.getInt("product_id"));
                    measurement.setQuantity(resultSet.getDouble("total_qty"));
                    measurement.setTotal(resultSet.getDouble("total_rate"));
                    measurement.setRpu(measurement.getTotal() / measurement.getQuantity());
                    salesProduct.add(measurement);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductReport.class.getName()).log(Level.SEVERE, null, ex);
                Message.warning("Unable populate return product");
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return salesProduct;
    }

    public HashMap<Integer, ProductStock> getProducts() {

        HashMap<Integer, ProductStock> productStocks = new HashMap<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();

        productStocks.clear();

        queryBuilder.select("product.product_id, product.title, product_category.title, product_brand.title, product_unit.title, product.rate_per_unit, product.notify_quantity");
        queryBuilder.innerJoin("product_category", "product_category.category_id = product.product_category_id");
        queryBuilder.leftJoin("product_brand", "product_brand.brand_id = product.product_brand_id");
        queryBuilder.leftJoin("product_unit", "product_unit.unit_id = product.product_unit_id");
        queryBuilder.from("product");

        if (database.connect()) {

            try {
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    Product product = new Product();
                    product.setBrand(resultSet.getString("product_brand.title"));
                    product.setCategory(resultSet.getString("product_category.title"));
                    product.setId(resultSet.getInt("product.product_id"));
                    product.setName(resultSet.getString("product.title"));
                    product.setUnit(resultSet.getString("product_unit.title"));
                    product.setRpu(resultSet.getDouble("product.rate_per_unit"));
                    product.setNotifyQuantity(resultSet.getDouble("product.notify_quantity"));

                    ProductStock productStock = new ProductStock();
                    productStock.setProduct(product);

                    productStocks.put(product.getId(), productStock);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductReport.class.getName()).log(Level.SEVERE, null, ex);
                Message.warning("Unable populate product");
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return productStocks;
    }
}
