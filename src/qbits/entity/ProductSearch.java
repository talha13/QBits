/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import qbits.db.MySQLDatabase;

/**
 *
 * @author Topu
 */
public class ProductSearch {

    public int getProductByCode(String productCode) {

        MySQLDatabase database = new MySQLDatabase();
        String query;

        if (database.connect()) {
            try {
                query = "SELECT product_id FROM product WHERE product_code =\"" + productCode + "\"";

                ResultSet resultSet = database.get(query);

                if (resultSet.next()) {
                    return resultSet.getInt("product_id");
                } else {
                    return -1;
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductSearch.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }

            return -1;

        } else {
            return -1;
        }
    }          
    
}
