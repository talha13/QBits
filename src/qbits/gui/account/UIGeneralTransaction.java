/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.gui.common.UIParentFrame;
import qbits.common.Message;

/**
 *
 * @author Topu
 */
public class UIGeneralTransaction extends javax.swing.JPanel {

    private UIParentFrame parentFrame;
    private ButtonGroup clearButtonGroup;
    private HashMap<String, Integer> accounts;
    private HashMap<String, Integer> accountHead;

    /**
     * Creates new form UIAccountTransaction
     */
    public UIGeneralTransaction(UIParentFrame frame) {
        initComponents();
        parentFrame = frame;
        clearButtonGroup = new ButtonGroup();
        clearButtonGroup.add(rbClear);
        clearButtonGroup.add(rbNotClear);
        accounts = new HashMap<>();
        accountHead = new HashMap<>();
        reset();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbPaymentMode = new javax.swing.JComboBox();
        cmbAccount = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spAmount = new javax.swing.JSpinner();
        dcTxnDate = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        dcClearOn = new datechooser.beans.DateChooserCombo();
        jLabel6 = new javax.swing.JLabel();
        rbClear = new javax.swing.JRadioButton();
        rbNotClear = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taNotes = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txfVoucherNo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbAccountHead = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cmbPrinciple = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Account Transaction", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Transaction Infomartion"));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("Payment Mode*");

        cmbPaymentMode.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbPaymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Account Type", "Bank", "Cash", "Card" }));
        cmbPaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaymentModeActionPerformed(evt);
            }
        });

        cmbAccount.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setText("Account*");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setText("Amount*");

        spAmount.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spAmount.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.5d)));

        dcTxnDate.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Date*");

        dcClearOn.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 0));
        jLabel6.setText("Clear On*");

        rbClear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rbClear.setText("Clear");

        rbNotClear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rbNotClear.setText("Not Clear");

        taNotes.setColumns(20);
        taNotes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        taNotes.setRows(5);
        jScrollPane1.setViewportView(taNotes);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Status*");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Notes");

        txfVoucherNo.setEditable(false);
        txfVoucherNo.setBackground(new java.awt.Color(255, 255, 255));
        txfVoucherNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 0));
        jLabel9.setText("Voucher No*");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 0, 0));
        jLabel10.setText("Account Head*");

        cmbAccountHead.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbAccountHead.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Account Type", "Bank", "Cash", "Card" }));
        cmbAccountHead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAccountHeadActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Principle*");

        cmbPrinciple.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbPrinciple.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Principle", "Income", "Expense" }));
        cmbPrinciple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPrincipleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbAccount, 0, 234, Short.MAX_VALUE)
                            .addComponent(cmbPaymentMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbAccountHead, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPrinciple, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(56, 56, 56))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfVoucherNo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(16, 16, 16)
                                .addComponent(rbClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbNotClear))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dcTxnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dcClearOn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txfVoucherNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPrinciple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAccountHead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbPaymentMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcTxnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcClearOn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbClear)
                    .addComponent(rbNotClear)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                parentFrame.stausBar.startLoading("saving account transaction");
                return save();
            }

            protected void done() {
                try {

                    parentFrame.stausBar.stopLoading();
                    if (get() == 1) {
                        parentFrame.showMessage("Account transaction saved");
                        reset();
                    } else if (get() == -1) {
                        parentFrame.showMessage("Unable to save accout transaction");
                    } else if (get() == -2) {
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void changeStatus(boolean status) {

        txfVoucherNo.setEnabled(status);
        taNotes.setEnabled(status);
        cmbAccount.setEnabled(status);
        cmbAccountHead.setEnabled(status);
        cmbPaymentMode.setEnabled(status);
        cmbPrinciple.setEnabled(status);
        dcClearOn.setEnabled(status);
        dcTxnDate.setEnabled(status);
        rbClear.setEnabled(status);
        rbNotClear.setEnabled(status);
        btnReset.setEnabled(status);
        btnSave.setEnabled(status);
        spAmount.setEnabled(status);
    }
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void cmbPaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentModeActionPerformed
        // TODO add your handling code here:

        if (cmbPaymentMode.getSelectedIndex() == 0) {
            return;
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("Loading accounts");
                return loadAccounts(cmbPaymentMode.getSelectedItem().toString());
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();

                    if (get() == 1) {
                    } else if (get() == -1) {
                        cmbPaymentMode.setSelectedIndex(0);
                        cmbAccount.removeAllItems();
                        parentFrame.showMessage("Unable to load accounts");
                    } else if (get() == -2) {
                        cmbPaymentMode.setSelectedIndex(0);
                        cmbAccount.removeAllItems();
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }//GEN-LAST:event_cmbPaymentModeActionPerformed

    private void cmbAccountHeadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAccountHeadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAccountHeadActionPerformed

    private void cmbPrincipleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPrincipleActionPerformed
        // TODO add your handling code here:

        if (cmbPrinciple.getSelectedIndex() == 0) {
            return;
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {
                parentFrame.stausBar.startLoading("Loading account head");
                return loadAccountHeads(cmbPrinciple.getSelectedItem().toString());
            }

            protected void done() {
                try {
                    parentFrame.stausBar.stopLoading();

                    if (get() == 1) {
                    } else if (get() == -1) {
                        cmbPrinciple.setSelectedIndex(0);
                        cmbAccountHead.removeAllItems();
                        parentFrame.showMessage("Unable to load account head");
                    } else if (get() == -2) {
                        cmbPrinciple.setSelectedIndex(0);
                        cmbAccountHead.removeAllItems();
                        Message.dbConnectFailed();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();

    }//GEN-LAST:event_cmbPrincipleActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbAccount;
    private javax.swing.JComboBox cmbAccountHead;
    private javax.swing.JComboBox cmbPaymentMode;
    private javax.swing.JComboBox cmbPrinciple;
    private datechooser.beans.DateChooserCombo dcClearOn;
    private datechooser.beans.DateChooserCombo dcTxnDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbClear;
    private javax.swing.JRadioButton rbNotClear;
    private javax.swing.JSpinner spAmount;
    private javax.swing.JTextArea taNotes;
    private javax.swing.JTextField txfVoucherNo;
    // End of variables declaration//GEN-END:variables

    private boolean check() {


        if (cmbPrinciple.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select principle");
            return false;
        }

        if (cmbAccountHead.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select account head");
            return false;
        }

        if (cmbPaymentMode.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select account type");
            return false;
        }

        if (cmbAccount.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select account");
            return false;
        }

        if ((Double) (spAmount.getValue()) <= 0) {
            parentFrame.showMessage("Please select valid amount");
            return false;
        }

        if (!rbClear.isSelected() && !rbNotClear.isSelected()) {
            parentFrame.showMessage("Please select status");
            return false;
        }

        if (!Utilities.isValidString(txfVoucherNo.getText())) {
            parentFrame.showMessage("Please enter voucher no");
            return false;
        }

        return true;
    }

    private int save() {

        MySQLDatabase database = new MySQLDatabase();
        String query;

        if (database.connect()) {

            database.setAutoCommit(false);

            query = "INSERT INTO general_transaction VALUES(null, " + accountHead.get(cmbAccountHead.getSelectedItem().toString()) + ", \"" + txfVoucherNo.getText() + "\""
                    + ", \"" + spAmount.getValue() + "\", \"" + cmbPaymentMode.getSelectedItem().toString() + "\", " + accounts.get(cmbAccount.getSelectedItem().toString()) + ""
                    + ", \"" + Utilities.dateForDB(dcTxnDate.getSelectedDate().getTime()) + "\", \"" + Utilities.dateForDB(dcClearOn.getSelectedDate().getTime()) + "\""
                    + ", " + rbClear.isSelected() + ", \"" + taNotes.getText() + "\", " + parentFrame.currentUser.getUserID() + ", NOW()"
                    + ")";

            long txnID = database.insert(query);

            if (txnID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            query = "UPDATE voucher_tracker SET count = count + 1 WHERE title =\"general_transaction\"";

            if (database.update(query) <= 0) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            database.commit();
            database.setAutoCommit(true);
            database.disconnect();
            return 1;

        } else {
            return -2;
        }
    }

    private void reset() {

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {

                cmbPrinciple.setSelectedIndex(0);
                cmbPaymentMode.setSelectedIndex(0);
                cmbAccount.removeAllItems();
                spAmount.setValue(0.0);
                cmbAccountHead.removeAllItems();
                dcTxnDate.setSelectedDate(Calendar.getInstance());
                dcClearOn.setSelectedDate(Calendar.getInstance());
                rbClear.setSelected(true);
                taNotes.setText("");

                parentFrame.stausBar.startLoading("generating voucher no");
                String voucherNo = getVoucherNo();

                if (voucherNo.length() == 0) {
                    return -1;
                } else {
                    txfVoucherNo.setText(voucherNo);
                    return 1;
                }
            }

            protected void done() {
                parentFrame.stausBar.stopLoading();
            }
        }.execute();
    }

    private int loadAccounts(String accountType) {

        MySQLDatabase database = new MySQLDatabase();
        Vector<String> accountTitles = new Vector<>();
        String query;
        accounts.clear();
        accountTitles.add("Select Account");
        int status = -1;

        if (database.connect()) {

            query = "SELECT * FROM account WHERE type =\"" + accountType + "\" AND status = 1 ORDER BY id";

            ResultSet resultSet = database.get(query);

            try {
                while (resultSet.next()) {
                    accounts.put(resultSet.getString("title"), resultSet.getInt("id"));
                    accountTitles.add(resultSet.getString("title"));
                }

                cmbAccount.setModel(new DefaultComboBoxModel(accountTitles));
                status = 1;

            } catch (SQLException ex) {
                Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                accounts.clear();
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    private int loadAccountHeads(String principleType) {

        MySQLDatabase database = new MySQLDatabase();
        Vector<String> accountHeadTitles = new Vector<>();
        String query;
        accountHead.clear();
        accountHeadTitles.add("Select Account Head");
        int status = -1;

        if (database.connect()) {

            query = "SELECT * FROM account_head WHERE principle =\"" + principleType + "\" AND status = 1 ORDER BY id";

            ResultSet resultSet = database.get(query);

            try {
                while (resultSet.next()) {
                    accountHead.put(resultSet.getString("title"), resultSet.getInt("id"));
                    accountHeadTitles.add(resultSet.getString("title"));
                }

                cmbAccountHead.setModel(new DefaultComboBoxModel(accountHeadTitles));
                status = 1;

            } catch (SQLException ex) {
                Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                accounts.clear();
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    private String getVoucherNo() {

        String voucherType = "general_transaction";
        String query;

        MySQLDatabase database = new MySQLDatabase();

        if (database.connect()) {

            query = "SELECT count FROM voucher_tracker WHERE title = \"" + voucherType + "\"";
            ResultSet resultSet = database.get(query);

            try {
                if (resultSet.next()) {
                    return "" + (resultSet.getInt("count") + 1);
                } else {
                    parentFrame.showMessage("Unable to generate voucher no");
                    txfVoucherNo.setEditable(true);
                    return "";
                }
            } catch (SQLException ex) {
                Logger.getLogger(UIGeneralTransaction.class.getName()).log(Level.SEVERE, null, ex);
                parentFrame.showMessage("Unable to generate voucher no");
                txfVoucherNo.setEditable(true);
                return "";
            } finally {
                database.disconnect();
            }

        } else {
            parentFrame.showMessage("Unable to connect with database");
            txfVoucherNo.setEditable(true);
            return "";
        }
    }
}
