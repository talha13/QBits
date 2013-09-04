/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common.crud;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;

/**
 *
 * @author Topu
 */
public class UICRUD extends javax.swing.JPanel {

    private CRUDListener cRUDListener;
    private CRUDDataLoaderListener cRUDDataLoaderListener;
    private QueryBuilder queryBuilder;
    private Vector columnNames;
    private TableRowSorter rowSorter;
    private ActionMenu actionMenu;
    private Point popupSelectedPoint;
    private ArrayList<Integer> primaryKeys;
    private boolean showPopup;

    /**
     * Creates new form UICRUD
     */
    public UICRUD() {
        initComponents();
        primaryKeys = new ArrayList<>();
        showPopup = true;
    }

    public boolean isShowPopup() {
        return showPopup;
    }

    public void setShowPopup(boolean showPopup) {
        this.showPopup = showPopup;
    }

    public void addCRUDListener(CRUDListener listener) {
        cRUDListener = listener;
    }

    public void addCRUDDataLoaderListener(CRUDDataLoaderListener dataLoaderListener) {
        cRUDDataLoaderListener = dataLoaderListener;
    }

    public void setTitle(String title) {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), title, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

    }

    public void setSubTitle(String subTitle) {
        pnlSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), subTitle));
    }

    public void setColumns(Vector searchTerms) {

        actionMenu = new ActionMenu();
        columnNames = searchTerms;
        int i = 0;
        cmbSearchBy.setModel(new DefaultComboBoxModel(searchTerms));
        DefaultTableModel tableModel = (DefaultTableModel) tableRecords.getModel();
        tableModel.setColumnIdentifiers(searchTerms);
        tableRecords.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableRecords.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableRecords.getColumnModel().getColumn(0).setHeaderValue("SL#");

        for (i = 1; i < searchTerms.size(); i++) {
            tableRecords.getColumnModel().getColumn(i).setPreferredWidth(200);
        }

        rowSorter = new TableRowSorter(tableModel);
        tableRecords.setRowSorter(rowSorter);

        tableRecords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (isShowPopup()) {
            tableRecords.setComponentPopupMenu(actionMenu);
            tableRecords.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    popupSelectedPoint = e.getPoint();
                }
            });
        }
    }

    public void populateRecords() {
        cRUDDataLoaderListener.load(tableRecords);
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        load();
    }

    private void filter() {

        RowFilter<DefaultTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {

            if (cmbSearchBy.getSelectedIndex() == 0) {
                rf = RowFilter.regexFilter("(?i)" + txfSearch.getText());
            } else {
                rf = RowFilter.regexFilter("(?i)" + txfSearch.getText(), cmbSearchBy.getSelectedIndex());
            }

        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }

        rowSorter.setRowFilter(rf);
        lblStatus.setText("Showing records " + rowSorter.getViewRowCount());
    }

    private void load() {

        MySQLDatabase database = new MySQLDatabase();
        DefaultTableModel tableModel = (DefaultTableModel) tableRecords.getModel();
        Vector rowData;
        int count = 0;
        int i = 0;
        primaryKeys.clear();

        if (database.connect()) {
            try {
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    primaryKeys.add(resultSet.getInt(1));
                    rowData = new Vector();

                    rowData.add(count + 1);

                    for (i = 1; i < columnNames.size(); i++) {
                        rowData.add(resultSet.getObject(i + 1));
                        tableModel.isCellEditable(count + 1, i);
                    }

                    tableModel.addRow(rowData);
                    count++;
                }

            } catch (SQLException ex) {
                Logger.getLogger(UICRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        lblStatus.setText("Showing records " + count);
        changeStatusMessage(count);
    }

    public void changeStatusMessage(int count) {
        lblStatus.setText("Showing records " + count);
    }

    private void addRecord() {
        cRUDListener.addRecord();
    }

    private void printRecords() {
        cRUDListener.printRecords();
    }

    private class ActionMenu extends JPopupMenu implements ActionListener {

        private JMenuItem itemRemove, itemUpdate;

        public ActionMenu() {

            super();
            itemRemove = new JMenuItem("Remove");
            itemUpdate = new JMenuItem("Update");

            itemRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Close-icon.png")));
            itemUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Edit-icon.png")));

            itemRemove.addActionListener(this);
            itemUpdate.addActionListener(this);

            add(itemUpdate);
            addSeparator();
            add(itemRemove);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int selectedRow = tableRecords.convertRowIndexToModel(tableRecords.rowAtPoint(popupSelectedPoint));

            if (itemRemove == e.getSource()) {
                removeRecord(selectedRow);
            } else if (itemUpdate == e.getSource()) {
                updateRecord(selectedRow);
            }
        }
    }

    private void removeRecord(int selectedRow) {
        cRUDListener.removeRecord(primaryKeys.get(selectedRow));
    }

    private void updateRecord(int selectedRow) {
        cRUDListener.updateRecord(primaryKeys.get(selectedRow));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSubPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRecords = new javax.swing.JTable();
        lblStatus = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txfSearch = new javax.swing.JTextField();
        cmbSearchBy = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Title", 2, 0));

        pnlSubPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Records"));

        tableRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableRecords);

        lblStatus.setForeground(new java.awt.Color(102, 0, 0));
        lblStatus.setText("Showing Records 500");

        btnPrint.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/hp_printer (2).png"))); // NOI18N
        btnPrint.setText("Print");
        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout pnlSubPanelLayout = new javax.swing.GroupLayout(pnlSubPanel);
        pnlSubPanel.setLayout(pnlSubPanelLayout);
        pnlSubPanelLayout.setHorizontalGroup(
            pnlSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addGroup(pnlSubPanelLayout.createSequentialGroup()
                        .addComponent(btnPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStatus)))
                .addContainerGap())
        );
        pnlSubPanelLayout.setVerticalGroup(
            pnlSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(btnPrint))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Search"));

        txfSearch.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfSearchKeyReleased(evt);
            }
        });

        cmbSearchBy.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbSearchBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSearchByActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/File-add-icon.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Search-icon (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSubPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSubPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        cRUDListener.addRecord();
    }//GEN-LAST:event_btnAddActionPerformed

    private void txfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfSearchKeyReleased
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txfSearchKeyReleased

    private void cmbSearchByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSearchByActionPerformed
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_cmbSearchByActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox cmbSearchBy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlSubPanel;
    private javax.swing.JTable tableRecords;
    private javax.swing.JTextField txfSearch;
    // End of variables declaration//GEN-END:variables
}
