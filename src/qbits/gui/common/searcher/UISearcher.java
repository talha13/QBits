/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common.searcher;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
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
import qbits.gui.common.crud.UICRUD;

/**
 *
 * @author Topu
 */
public class UISearcher extends javax.swing.JDialog {

    private QueryBuilder queryBuilder;
    private Vector columnNames;
    private TableRowSorter rowSorter;
    private ActionMenu actionMenu;
    private Point popupSelectedPoint;
    private ArrayList<Integer> primaryKeys;
    private SearcherListener searcherListener;

    /**
     * Creates new form UISearcher
     */
    public UISearcher(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        primaryKeys = new ArrayList<>();
        
    }

    public void showWindow() {

        Toolkit theKit = this.getToolkit();
        Dimension wndSize = theKit.getScreenSize();
        this.setLocation((int) (wndSize.width - this.getWidth()) / 2, (int) (wndSize.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    
    public void addSearcherListener(SearcherListener listener){
        searcherListener = listener;
    }

    public void setTitle(String title) {
        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), title, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

    }

    public void setSubTitle(String subTitle) {
        pnlRecords.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), subTitle));
    }

    public void setColumns(Vector searchTerms) {

        actionMenu = new ActionMenu();
        columnNames = searchTerms;
        int i = 0;
//        cmbSearchBy.setModel(new DefaultComboBoxModel(searchTerms));
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
        tableRecords.setComponentPopupMenu(actionMenu);
        tableRecords.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                popupSelectedPoint = e.getPoint();
            }
        });
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        load();
    }

    private void filter() {

        RowFilter<DefaultTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {

            rf = RowFilter.regexFilter("(?i)" + txfSearch.getText());
            
//            if (cmbSearchBy.getSelectedIndex() == 0) {
//                rf = RowFilter.regexFilter("(?i)" + txfSearch.getText());
//            } else {
//                rf = RowFilter.regexFilter("(?i)" + txfSearch.getText(), cmbSearchBy.getSelectedIndex());
//            }

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
                    System.out.println(resultSet.getInt(1));
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

        lblStatus.setText("Showing records " + count);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txfSearch = new javax.swing.JTextField();
        pnlRecords = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRecords = new javax.swing.JTable();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Searcher", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

        txfSearch.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfSearch)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRecords.setBorder(javax.swing.BorderFactory.createTitledBorder("Searcher"));

        tableRecords.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableRecords);

        lblStatus.setText("Showing records");

        javax.swing.GroupLayout pnlRecordsLayout = new javax.swing.GroupLayout(pnlRecords);
        pnlRecords.setLayout(pnlRecordsLayout);
        pnlRecordsLayout.setHorizontalGroup(
            pnlRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecordsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRecordsLayout.createSequentialGroup()
                        .addGap(0, 389, Short.MAX_VALUE)
                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        pnlRecordsLayout.setVerticalGroup(
            pnlRecordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecordsLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(lblStatus))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRecords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRecords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfSearchKeyReleased
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txfSearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UISearcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UISearcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UISearcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UISearcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UISearcher dialog = new UISearcher(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private class ActionMenu extends JPopupMenu implements ActionListener {

        private JMenuItem itemAdd;

        public ActionMenu() {

            super();
            itemAdd = new JMenuItem("Add");

            itemAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/File-add-icon.png")));

            itemAdd.addActionListener(this);

            add(itemAdd);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int selectedRow = tableRecords.convertRowIndexToModel(tableRecords.rowAtPoint(popupSelectedPoint));

            if(itemAdd == e.getSource()){
                searcherListener.addRecord(primaryKeys.get(selectedRow));
                dispose();
            }            
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRecords;
    private javax.swing.JTable tableRecords;
    private javax.swing.JTextField txfSearch;
    // End of variables declaration//GEN-END:variables
}
