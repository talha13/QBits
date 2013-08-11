/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import qbits.gui.purchase.product.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;
import qbits.db.MySQLDatabase;
import qbits.entity.Product;
import qbits.entity.ProductSearch;
import qbits.gui.common.UIParentFrame;
import qbitserp.common.Message;

/**
 *
 * @author Topu
 */
public class UIProductDamageb extends javax.swing.JPanel {

    private UIParentFrame parentFrame;
    private boolean isUpdate;
    private HashMap<String, Integer> categories;
    private HashMap<String, Product> products;
    private ProductSearch productSearch;
    private boolean shouldPerformActionForcmbCategory;

    /**
     * Creates new form UIProductDamage
     */
    public UIProductDamageb(UIParentFrame frame) {
        initComponents();
        parentFrame = frame;
        isUpdate = false;
        categories = new HashMap<>();
        products = new HashMap<>();
        productSearch = new ProductSearch();
        shouldPerformActionForcmbCategory = false;
        reset();
//        loadCategory();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cmbName = new javax.swing.JComboBox();
        txfCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        spQuantity = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        spRPU = new javax.swing.JSpinner();
        cmbStatus = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taNotes = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Damage Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Product Information"));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("Product Category*");

        cmbCategory.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setText("Product Name*");

        cmbName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNameActionPerformed(evt);
            }
        });

        txfCode.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfCodeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Product Code");

        spQuantity.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spQuantity.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.5d)));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 0));
        jLabel4.setText("Quantity*");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Rate Per Unit*");

        spRPU.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spRPU.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.5d)));

        cmbStatus.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Status", "Damaged", "Expired" }));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 0));
        jLabel6.setText("Status*");

        taNotes.setColumns(20);
        taNotes.setRows(5);
        jScrollPane1.setViewportView(taNotes);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Notes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txfCode)
                                .addComponent(cmbCategory, 0, 189, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 81, Short.MAX_VALUE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spRPU, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(81, 81, 81)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spRPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (!check()) {
            return;
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("saving product");

                if (isUpdate) {
                    return update();
                } else {
                    return save();
                }
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();

                    if (get() == 1) {
                        reset();
                        parentFrame.showMessage("Product information saved");
                    } else if (get() == -1) {
                        parentFrame.showMessage("Unable to save Product information");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        // TODO add your handling code here:

        if (!shouldPerformActionForcmbCategory) {
            return;
        }

        if (cmbCategory.getSelectedIndex() == 0) {
            return;
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {

                parentFrame.stausBar.startLoading("loading product");
                return loadProducts(categories.get(cmbCategory.getSelectedItem().toString()));
            }

            protected void done() {
                parentFrame.stausBar.stopLoading();
                try {
                    if (get() == 1) {
                    } else if (get() == -1) {
                        parentFrame.showMessage("Unable to load products");
                    } else if (get() == -2) {
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();

    }//GEN-LAST:event_cmbCategoryActionPerformed

    private void cmbNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNameActionPerformed
        // TODO add your handling code here:

        if (cmbName.getSelectedIndex() == 0) {
            return;
        }

        if (products.size() != 0) {
            spRPU.setValue(products.get(cmbName.getSelectedItem().toString()).getRpu());
        }

    }//GEN-LAST:event_cmbNameActionPerformed

    private void txfCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfCodeActionPerformed
        // TODO add your handling code here:

        shouldPerformActionForcmbCategory = false;
        products.clear();
        cmbCategory.removeAllItems();
        cmbName.removeAllItems();
        cmbStatus.setSelectedIndex(0);
        spQuantity.setValue(0);
        spRPU.setValue(0);
        taNotes.setText("");

        int productID = productSearch.getProductByCode(txfCode.getText());

        if (productID <= 0) {
            parentFrame.showMessage("No product found");
        } else {
            loadProduct(productID);
        }
    }//GEN-LAST:event_txfCodeActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JComboBox cmbName;
    private javax.swing.JComboBox cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spQuantity;
    private javax.swing.JSpinner spRPU;
    private javax.swing.JTextArea taNotes;
    private javax.swing.JTextField txfCode;
    // End of variables declaration//GEN-END:variables

    private boolean check() {

        if (cmbCategory.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select category");
            return false;
        }

        if (cmbName.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select product");
            return false;
        }

        if (Double.parseDouble(spQuantity.getValue().toString()) < -1) {
            parentFrame.showMessage("Please select valid quantity");
            return false;
        }

        if (Double.parseDouble(spRPU.getValue().toString()) < -1) {
            parentFrame.showMessage("Please select valid price");
            return false;
        }

        if (cmbStatus.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select product status");
            return false;
        }

        return true;
    }

    private int save() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        int status;

        if (database.connect()) {

            query = "INSERT INTO product_damage VALUES(null, "
                    + "" + products.get(cmbName.getSelectedItem().toString()).getId() + ", " + spRPU.getValue() + ", " + spQuantity.getValue() + ","
                    + "\"" + cmbStatus.getSelectedItem().toString() + "\", \"" + taNotes.getText() + "\", " + parentFrame.currentUser.getUserID() + ", NOW()"
                    + ")";

            long damageProductID = database.insert(query);

            status = damageProductID == -1 ? -1 : 1;
            database.disconnect();

        } else {
            status = -2;
        }

        return status;
    }

    private void reset() {

        loadCategory();
        products.clear();
        txfCode.setText("");
        cmbName.removeAllItems();
        cmbStatus.setSelectedIndex(0);
        spQuantity.setValue(0);
        spRPU.setValue(0);
        taNotes.setText("");
        shouldPerformActionForcmbCategory = true;
    }

    private boolean update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int loadProducts(int catID) {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> productTitle = new Vector<>();
        products.clear();
        int status = 0;

        productTitle.add("Select Product");

        if (database.connect()) {
            try {
                query = "SELECT product.product_id, product.title, product.product_code, product_brand.title, product_unit.title, product.rate_per_unit "
                        + " FROM product"
                        + " INNER JOIN product_brand ON product_brand.brand_id = product.product_brand_id"
                        + " INNER JOIN product_unit ON product_unit.unit_id = product.product_unit_id"
                        + " WHERE product.product_category_id = " + catID;

                ResultSet resultSet = database.get(query);

                while (resultSet.next()) {

                    Product product = new Product();
                    product.setBrand(resultSet.getString("product_brand.title"));
                    product.setId(resultSet.getInt("product.product_id"));
                    product.setName(resultSet.getString("product.title"));
                    product.setRpu(resultSet.getDouble("product.rate_per_unit"));
                    product.setUnit(resultSet.getString("product_unit.title"));

                    String productName = resultSet.getString("product.title") + "-" + resultSet.getString("product_brand.title");
                    productTitle.add(productName);
                    products.put(productName, product);
                }

                cmbName.setModel(new DefaultComboBoxModel(productTitle));
                status = 1;

            } catch (Exception ex) {
                Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    private int loadProduct(int productID) {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> productTitle = new Vector<>();
        products.clear();
        categories.clear();
        int status = 0;

        productTitle.add("Select Product");

        if (database.connect()) {
            try {
                query = "SELECT product.product_id, product.title, product.product_code, product_brand.title, product_unit.title, product.rate_per_unit, "
                        + " product_category.title"
                        + " FROM product"
                        + " INNER JOIN product_brand ON product_brand.brand_id = product.product_brand_id"
                        + " INNER JOIN product_unit ON product_unit.unit_id = product.product_unit_id"
                        + " INNER JOIN product_category ON product_category.category_id = product.product_category_id"
                        + " WHERE product.product_id = " + productID + " LIMIT 1";

                ResultSet resultSet = database.get(query);

                if (resultSet.next()) {

                    Product product = new Product();
                    product.setBrand(resultSet.getString("product_brand.title"));
                    product.setId(resultSet.getInt("product.product_id"));
                    product.setName(resultSet.getString("product.title"));
                    product.setRpu(resultSet.getDouble("product.rate_per_unit"));
                    product.setUnit(resultSet.getString("product_unit.title"));

                    String productName = resultSet.getString("product.title") + "-" + resultSet.getString("product_brand.title");
                    productTitle.add(productName);
                    products.put(productName, product);

                    cmbName.setModel(new DefaultComboBoxModel(productTitle));
                    cmbCategory.setModel(new DefaultComboBoxModel(new String[]{"Select Category", resultSet.getString("product_category.title")}));

                    cmbName.setSelectedIndex(1);
                    shouldPerformActionForcmbCategory = false;
                    cmbCategory.setSelectedIndex(1);
                }

                status = 1;

            } catch (Exception ex) {
                Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    private void loadCategory() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> categoryTitles = new Vector<>();
        categories.clear();
        categoryTitles.add("Select Category");

        if (database.connect()) {
            try {
                query = "SELECT * FROM product_category WHERE status = 1";
                ResultSet resultSet = database.get(query);

                while (resultSet.next()) {
                    categories.put(resultSet.getString("title"), resultSet.getInt("category_id"));
                    categoryTitles.add(resultSet.getString("title"));
                }

                cmbCategory.setModel(new DefaultComboBoxModel(categoryTitles));

            } catch (SQLException ex) {
                Logger.getLogger(UIProductDamage.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }
        }
    }
}
