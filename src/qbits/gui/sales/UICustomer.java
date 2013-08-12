/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Customer;
import qbits.gui.common.UIParentFrame;
import qbitserp.common.Message;

/**
 *
 * @author Topu
 */
public class UICustomer extends javax.swing.JPanel {

    private UIParentFrame parentFrame;
    private boolean isUpdate;
    private Customer customer;

    /**
     * Creates new form Sample
     */
    public UICustomer(UIParentFrame frame) {
        initComponents();
        parentFrame = frame;
        lblOpeningBalance.setVisible(false);
        spOpeningBalance.setVisible(false);
    }

    public void update(int customerID) {

        isUpdate = true;
        customer = new Customer();
        customer.setCustomerID(customerID);

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("getting customer information");
                return load();
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();

                    if (get() == 1) {
                    } else if (get() == -1) {
                        reset();
                        parentFrame.showMessage("Unable to load customer information");

                    } else if (get() == -2) {
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UICustomer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UICustomer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.execute();
    }

    public int load() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        int status = -1;

        if (database.connect()) {
            try {

                query = "SELECT customer.id,"
                        + " person.person_id, person.first_name, person.last_name, person.gender, person.date_of_birth, person.contact_no,"
                        + " address.address_id, address.address, address.city, address.district"
                        + " FROM customer"
                        + " INNER JOIN person ON person.person_id = customer.person_id"
                        + " INNER JOIN address ON address.address_id = person.address_id"
                        + " WHERE customer.id = " + customer.getCustomerID();

                ResultSet resultSet = database.get(query);

                if (resultSet.next()) {

                    txfCity.setText(resultSet.getString("address.city"));
                    txfDistrict.setText(resultSet.getString("address.district"));
                    txfFirstName.setText(resultSet.getString("person.first_name"));
                    txfLastName.setText(resultSet.getString("person.last_name"));
                    txfPhone.setText(resultSet.getString("person.contact_no"));
                    taAddress.setText(resultSet.getString("address.address"));
                    cmbGender.setSelectedIndex(resultSet.getString("person.gender").compareTo("Male") == 0 ? 1 : 2);
                    dcDOB.setSelectedDate(Utilities.getDateChosserDate(resultSet.getDate("person.date_of_birth")));

                    customer.setAddressID(resultSet.getInt("address.address_id"));
                    customer.setPersonID(resultSet.getInt("person.person_id"));

                    status = 1;
                } else {
                    status = -1;
                }

            } catch (SQLException ex) {
                Logger.getLogger(UICustomer.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taAddress = new javax.swing.JTextArea();
        txfDistrict = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txfCity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txfPhone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txfFirstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txfLastName = new javax.swing.JTextField();
        cmbGender = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        dcDOB = new datechooser.beans.DateChooserCombo();
        spOpeningBalance = new javax.swing.JSpinner();
        lblOpeningBalance = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextField3.setText("jTextField1");

        jLabel2.setText("jLabel2");

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Customer", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Contact Info"));

        taAddress.setColumns(20);
        taAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        taAddress.setRows(5);
        jScrollPane1.setViewportView(taAddress);

        txfDistrict.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("District");

        txfCity.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("City");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Address");

        txfPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Phone*");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txfPhone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfDistrict, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfCity, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true)));

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Save-icon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Refresh-icon.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Basic Info"));

        txfFirstName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 0));
        jLabel6.setText("First Name*");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Last Name*");

        txfLastName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        cmbGender.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Gender", "Male", "Female" }));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Gender");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Date of Birth");

        dcDOB.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        spOpeningBalance.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lblOpeningBalance.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lblOpeningBalance.setText("Opening Balance");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txfLastName)
                                    .addComponent(txfFirstName))
                                .addGap(35, 35, 35))
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblOpeningBalance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spOpeningBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spOpeningBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOpeningBalance))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                parentFrame.stausBar.startLoading("saving customer");
                changeStatus(false);

                if (isUpdate) {
                    return update();
                } else {
                    return save();
                }
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();
                    changeStatus(true);
                    if (get() == 1) {
                        reset();
                        parentFrame.showMessage("Customer information saved");
                    } else if (get() == -1) {
                        parentFrame.showMessage("Unable to save Customer info");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UICustomer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UICustomer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void changeStatus(boolean status) {

        txfCity.setEnabled(status);
        txfDistrict.setEnabled(status);
        txfFirstName.setEnabled(status);
        txfLastName.setEnabled(status);
        txfPhone.setEnabled(status);
        spOpeningBalance.setEnabled(status);
        cmbGender.setEnabled(status);
        dcDOB.setEnabled(status);
        btnReset.setEnabled(status);
        btnSave.setEnabled(status);
        taAddress.setEnabled(status);
    }

    private void reset() {

        taAddress.setText("");
        txfCity.setText("");
        txfDistrict.setText("");
        txfFirstName.setText("");
        txfLastName.setText("");
        txfPhone.setText("");
        spOpeningBalance.setValue(0.0);
        cmbGender.setSelectedIndex(0);
        dcDOB.setSelectedDate(Calendar.getInstance());
        isUpdate = false;
    }

    private int update() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        int status = -1;
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            query = "UPDATE person SET first_name = \"" + txfFirstName.getText() + "\", last_name = \"" + txfLastName.getText() + "\""
                    + ", gender = \"" + cmbGender.getSelectedItem().toString() + "\", date_of_birth = \"" + Utilities.dateForDB(dcDOB.getSelectedDate().getTime()) + "\""
                    + ", contact_no = \"" + txfPhone.getText() + "\""
                    + " WHERE person_id = " + customer.getPersonID();

            long affactedRows = database.update(query);

            if (affactedRows <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            query = "UPDATE address SET address = \"" + taAddress.getText() + "\", city = \"" + txfCity.getText() + "\", district = \"" + txfDistrict.getText() + "\""
                    + " WHERE address.address_id = " + customer.getAddressID();

            affactedRows = database.update(query);

            if (affactedRows <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            } else {
                status = 1;
            }

            database.setAutoCommit(true);
            database.disconnect();

        } else {
            status = -2;
        }

        return status;
    }

    private int save() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            query = "INSERT INTO address VALUES("
                    + "null, \"" + taAddress.getText() + "\", \"" + txfCity.getText() + "\", \"" + txfDistrict.getText() + "\""
                    + ")";

            long addressID = database.insert(query);

            if (addressID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            query = "INSERT INTO person VALUES("
                    + "null, \"" + txfFirstName.getText() + "\", \"" + txfLastName.getText() + "\", \"" + cmbGender.getSelectedItem().toString() + "\", "
                    + "\"" + Utilities.dateForDB(dcDOB.getSelectedDate().getTime()) + "\", \"" + txfPhone.getText() + "\", " + addressID + ""
                    + ")";

            long personID = database.insert(query);

            if (personID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

//            query = "INSERT INTO supplier VALUES("
//                    + "null, \"" + txfSupplierName.getText() + "\", " + personID + ", " + addressID + ", \"" + txfPhone.getText() + "\", "
//                    + "\"" + txfEmail.getText() + "\", " + parentFrame.currentUser.getUserID() + ", NOW()"
//                    + ")";

            queryBuilder.clear();
            queryBuilder.set("person_id", "" + personID);
            queryBuilder.set("last_updated_by", "" + parentFrame.currentUser.getUserID());
            queryBuilder.set("last_updated_time", "NOW()");

            long customerID = database.insert(queryBuilder.insert("customer"));

            if (customerID != -1) {
                database.commit();
            } else {
                database.rollback();
            }

            database.setAutoCommit(true);
            database.disconnect();

            return (customerID == -1) ? -1 : 1;

        } else {
            Message.dbConnectFailed();
            return -1;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbGender;
    private datechooser.beans.DateChooserCombo dcDOB;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblOpeningBalance;
    private javax.swing.JSpinner spOpeningBalance;
    private javax.swing.JTextArea taAddress;
    private javax.swing.JTextField txfCity;
    private javax.swing.JTextField txfDistrict;
    private javax.swing.JTextField txfFirstName;
    private javax.swing.JTextField txfLastName;
    private javax.swing.JTextField txfPhone;
    // End of variables declaration//GEN-END:variables

    private boolean check() {

        if (!Utilities.isValidString(txfPhone.getText())) {
            parentFrame.showMessage("Customer phone no Required");
            return false;
        }
        if (!Utilities.isValidString(txfFirstName.getText())) {
            parentFrame.showMessage("Customer first name Required");
            return false;
        }
        if (!Utilities.isValidString(txfLastName.getText())) {
            parentFrame.showMessage("Customer last name required");
            return false;
        }

        if (cmbGender.getSelectedIndex() == 0) {
            parentFrame.showMessage("Select Customer gender");
            return false;
        }

        if (Double.parseDouble(spOpeningBalance.getValue().toString()) < 0.0) {
            parentFrame.showMessage("Invalid amount for opening balance");
            return false;
        }

        return true;
    }
}