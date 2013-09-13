/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common.daterangepicker;

import java.util.Calendar;

/**
 *
 * @author Pipilika
 */
public class UIDateRangePicker extends javax.swing.JPanel {

    private DateRangeListener dateRangeListener;
    private Calendar calendarFrom = Calendar.getInstance();
    private Calendar calendarTo = Calendar.getInstance();

    /**
     * Creates new form UIDateRangePicker
     */
    public UIDateRangePicker() {
        initComponents();
    }

    public void addDateRangeListener(DateRangeListener listener) {
        this.dateRangeListener = listener;
    }

    public void setTitle(String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), title, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cmbDateRangeType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        dcFrom = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        dcTo = new datechooser.beans.DateChooserCombo();
        jPanel2 = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Date Range Picker", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

        cmbDateRangeType.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbDateRangeType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Today", "Yesterday", "This Week", "Last Week", "This Month", "Last Month", "Custom" }));
        cmbDateRangeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDateRangeTypeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("From Date");

        dcFrom.setEnabled(false);
        dcFrom.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("To Date");

        dcTo.setEnabled(false);
        dcTo.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbDateRangeType, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbDateRangeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcFrom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcTo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

        btnSubmit.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
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
                .addComponent(btnSubmit)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDateRangeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDateRangeTypeActionPerformed
        // TODO add your handling code here:

        int offsetFrom = 0;
        int offsetTo = 0;

        calendarFrom = Calendar.getInstance();
        calendarTo = Calendar.getInstance();

        dcFrom.setEnabled(true);
        dcTo.setEnabled(true);


        switch (cmbDateRangeType.getSelectedIndex()) {
            case 1: // yesterday
                calendarFrom.add(Calendar.DAY_OF_MONTH, -1);
                calendarTo.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case 2: // this week
                calendarFrom.add(Calendar.DAY_OF_MONTH, -1 * calendarFrom.get(Calendar.DAY_OF_WEEK));
                break;
            case 3: // last week
                calendarFrom.add(Calendar.DAY_OF_MONTH, (-1 * calendarFrom.get(Calendar.DAY_OF_WEEK)) - 7);
                calendarTo.add(Calendar.DAY_OF_MONTH, (-1 * calendarTo.get(Calendar.DAY_OF_WEEK)) - 1);
                break;
            case 4: // this month
                calendarFrom.add(Calendar.DAY_OF_MONTH, (-1 * calendarFrom.get(Calendar.DAY_OF_MONTH)) + 1);
                break;
            case 5: // last month
                calendarTo.add(Calendar.DAY_OF_MONTH, (-1 * calendarTo.get(Calendar.DAY_OF_MONTH)));
                calendarFrom.add(Calendar.DAY_OF_MONTH, (-1 * calendarFrom.get(Calendar.DAY_OF_MONTH)) - (calendarTo.get(Calendar.DAY_OF_MONTH)) + 1);
                break;                
        }

        dcFrom.setSelectedDate(calendarFrom);
        dcTo.setSelectedDate(calendarTo);

        if (cmbDateRangeType.getSelectedIndex() != 6) {
            dcFrom.setEnabled(false);
            dcTo.setEnabled(false);
        }

    }//GEN-LAST:event_cmbDateRangeTypeActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        
        if(cmbDateRangeType.getSelectedIndex() == 6){
            dateRangeListener.processDateRange(dcFrom.getSelectedDate(), dcTo.getSelectedDate());
        }else{
            dateRangeListener.processDateRange(calendarFrom, calendarTo);
        }        
        
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        cmbDateRangeType.setSelectedIndex(0);
        dcFrom.setSelectedDate(Calendar.getInstance());
        dcTo.setSelectedDate(Calendar.getInstance());
    }//GEN-LAST:event_btnResetActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cmbDateRangeType;
    private datechooser.beans.DateChooserCombo dcFrom;
    private datechooser.beans.DateChooserCombo dcTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {

        Calendar calendarFrom = Calendar.getInstance();
//        calendarFrom.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(calendarFrom.getTime());
//        System.out.println(calendarFrom.get(Calendar.MONTH));
//        calendarFrom.add(Calendar.DAY_OF_MONTH, (-1 * calendarFrom.get(Calendar.DAY_OF_WEEK)) - 7);
//        System.out.println(calendarFrom.getTime());

        System.out.println(calendarFrom.getActualMaximum(Calendar.DAY_OF_MONTH));

    }
}
