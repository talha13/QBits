/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import qbits.configuration.Configuration;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Product;
import qbits.entity.ProductSearch;
import qbits.entity.Supplier;
import qbits.entity.Invoice;
import qbits.gui.account.UIGeneralTransaction;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.searcher.SearcherListener;
import qbits.gui.common.searcher.UISearcher;
import qbits.gui.purchase.product.UIProductDamage;
import qbits.common.Message;

/**
 *
 * @author Topu
 */
public class UISupplierInvoice extends javax.swing.JPanel implements SearcherListener {

    private UIParentFrame parentFrame;
    private ProductSearch productSearch;
    private HashMap<Integer, Supplier> suppliers;
    private HashMap<Integer, Integer> accounts;
    private HashMap<Integer, Integer> categories;
    private HashMap<Integer, Integer> products;
    private ArrayList<Product> selectedProducts;
    private double subtotal;
    private double vat;
    private double netPayable;
    private double paid;
    private boolean shouldPerformActionForcmbCategory;
    private ActionMenu actionMenu;
    private Point popupSelectedPoint;
    private boolean isUpdate;
    private ButtonGroup clearButtonGroup;
    private Invoice supplierInvoice;

    /**
     * Creates new form UISupplierInvoice
     */
    public UISupplierInvoice(UIParentFrame frame) {
        initComponents();
        clearButtonGroup = new ButtonGroup();
        clearButtonGroup.add(rbClear);
        clearButtonGroup.add(rbNotClear);
        this.parentFrame = frame;
        suppliers = new HashMap<>();
        categories = new HashMap<>();
        products = new HashMap<>();
        accounts = new HashMap<>();
        selectedProducts = new ArrayList<>();
        productSearch = new ProductSearch();
        reset();
        tableProducts.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableProducts.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableProducts.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableProducts.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableProducts.getColumnModel().getColumn(3).setPreferredWidth(200);
        tableProducts.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableProducts.getColumnModel().getColumn(5).setPreferredWidth(100);
        popupSelectedPoint = new Point(0, 0);
        actionMenu = new ActionMenu();

        tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableProducts.setComponentPopupMenu(actionMenu);
        tableProducts.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                popupSelectedPoint = e.getPoint();
            }
        });

    }

    public void update(int invoiceID) {
        isUpdate = true;
        supplierInvoice = new Invoice();
        supplierInvoice.setInvoiceID(invoiceID);
        changeStatusPaymentPanel(false);
        load();
    }

    private void changeStatusPaymentPanel(boolean status) {

        txfPaid.setEditable(status);
        cmbPaymentMode.setEnabled(status);
        cmbAccounts.setEnabled(status);
        taNotes.setEditable(status);
        rbClear.setEnabled(status);
        dcClearDate.setEnabled(status);
        rbNotClear.setEnabled(status);
    }

    private int load() {

        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder query = new QueryBuilder();
        int status = 0;
        ResultSet resultSet;

        if (database.connect()) {

            try {
                query.select("supplier_invoice.invoice_id, supplier_invoice.supplier_id, supplier_invoice.supplier_invoice_no, supplier_invoice.invoice_date, supplier_invoice.vat, supplier_invoice.subtotal, supplier_invoice.payable");
                query.from("supplier_invoice");
                query.where("supplier_invoice.invoice_id = ", "" + supplierInvoice.getInvoiceID());

                resultSet = database.get(query.get());

                if (resultSet.next()) {

                    int supplierID = resultSet.getInt("supplier_invoice.supplier_id");

                    status = -1;

                    for (Integer cmbIndex : suppliers.keySet()) {

                        if (supplierID == suppliers.get(cmbIndex).getSupplierID()) {
                            cmbSupplier.setSelectedIndex(cmbIndex);
                            status = 1;
                            break;
                        }
                    }

                    if (status != 1) {
                        return -1;
                    }

                    subtotal = resultSet.getDouble("supplier_invoice.subtotal");
                    vat = resultSet.getDouble("supplier_invoice.vat");
                    netPayable = resultSet.getDouble("supplier_invoice.payable");
                    paid = 0.00;

                    txfInvoiceNo.setText("" + resultSet.getString("supplier_invoice.supplier_invoice_no"));
                    dcDate.setSelectedDate(Utilities.getDateChosserDate(resultSet.getDate("supplier_invoice.invoice_date")));

                    query.clear();

                    query.select("supplier_invoice_transaction.clear_on");
                    query.select("supplier_invoice_transaction.is_clear");
                    query.select("supplier_invoice_transaction.paid_amount");
                    query.select("supplier_invoice_transaction.account_id");
                    query.select("supplier_invoice_transaction.notes");
                    query.select("account.type");
                    query.innerJoin("account", "account.id = supplier_invoice_transaction.account_id");
                    query.from("supplier_invoice_transaction");
                    query.where("supplier_invoice_id = ", "" + supplierInvoice.getInvoiceID());
                    query.orderBy("transaction_date", "asc");

                    resultSet = database.get(query.get());

                    while (resultSet.next()) {

                        dcClearDate.setSelectedDate(Utilities.getDateChosserDate(resultSet.getDate("clear_on")));

                        if (resultSet.getBoolean("is_clear")) {
                            rbClear.setSelected(true);
                        } else {
                            rbNotClear.setSelected(true);
                        }

                        paid += resultSet.getDouble("paid_amount");

                        int accountID = resultSet.getInt("account_id");

                        status = -1;

                        for (int i = 1; i <= 3; i++) {
                            if (cmbPaymentMode.getItemAt(i).toString().compareTo(resultSet.getString("account.type")) == 0) {
                                cmbPaymentMode.setSelectedIndex(i);
                                status = 1;
                                break;
                            }
                        }

                        if (status == -1) {
                            return -1;
                        }

                        status = -1;

                        loadAccounts(resultSet.getString("account.type"));

//                        System.out.println("Accounts: "+ accounts.size());

                        for (Integer cmbAccIndex : accounts.keySet()) {
                            System.out.println(cmbAccIndex);
                            if (accountID == accounts.get(cmbAccIndex)) {
                                cmbAccounts.setSelectedIndex(cmbAccIndex);
                                status = 1;
                                break;
                            }
                        }

                        if (status == -1) {
                            return -1;
                        }

                        taNotes.setText(resultSet.getString("notes"));
                    }

                    selectedProducts.clear();
                    query.clear();

                    query.select("product_stock.stock_id");
                    query.select("product_stock.product_id");
                    query.select("product_stock.supplier_invoice_id");
                    query.select("product_stock.quantity");
                    query.select("product_stock.cost_per_unit");
                    query.select("product_unit.title");
                    query.select("product_brand.title");
                    query.select("product_category.title");
                    query.select("product.title");
                    query.select("product.product_category_id");
                    query.select("product.product_code");
                    query.innerJoin("product", "product.product_id = product_stock.product_id");
                    query.innerJoin("product_category", "product_category.category_id = product.product_category_id");
                    query.leftJoin("product_unit", "product_unit.unit_id = product.product_unit_id");
                    query.leftJoin("product_brand", "product_brand.brand_id = product.product_brand_id");
                    query.where("product_stock.supplier_invoice_id = ", "" + supplierInvoice.getInvoiceID());
                    query.from("product_stock");

                    resultSet = database.get(query.get());
                    Product product;
                    DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();

                    while (resultSet.next()) {

                        product = new Product();
                        product.setBrand(resultSet.getString("product_brand.title"));
                        product.setCategoryID(resultSet.getInt("product.product_category_id"));
                        product.setCode(resultSet.getString("product.product_code"));
                        product.setId(resultSet.getInt("product_stock.product_id"));
                        product.setName(resultSet.getString("product.title"));
                        product.setQuantity(resultSet.getDouble("product_stock.quantity"));
                        product.setRpu(resultSet.getDouble("product_stock.cost_per_unit"));
                        product.setUnit(resultSet.getString("product_unit.title"));
                        product.setCategory(resultSet.getString("product_category.title"));

                        selectedProducts.add(product);
                        model.addRow(new Object[]{selectedProducts.size(), product.getName(), product.getCode(), product.getCategory(), product.getQuantity(), product.getRpu()});
                    }


                    updatePaymentUI();

                } else {
                    status = -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else {
            status = -1;
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

        jPanel1 = new javax.swing.JPanel();
        cmbCategory = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cmbProductName = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        spQuantity = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        spPrice = new javax.swing.JSpinner();
        btnAdd = new javax.swing.JButton();
        txfCode = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();
        txfPhone = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProducts = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txfInvoiceNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dcDate = new datechooser.beans.DateChooserCombo();
        jLabel6 = new javax.swing.JLabel();
        pnlPayment = new javax.swing.JPanel();
        txfSubTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txfVat = new javax.swing.JTextField();
        txfPayable = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txfPaid = new javax.swing.JTextField();
        cmbPaymentMode = new javax.swing.JComboBox();
        cmbAccounts = new javax.swing.JComboBox();
        lblReturn = new javax.swing.JLabel();
        txfDue = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taNotes = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        dcClearDate = new datechooser.beans.DateChooserCombo();
        lblReturn1 = new javax.swing.JLabel();
        rbClear = new javax.swing.JRadioButton();
        rbNotClear = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Supplier Invoice", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Product"));

        cmbCategory.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Category*");

        cmbProductName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 0));
        jLabel8.setText("Name*");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setText("Quantity");

        spQuantity.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spQuantity.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.5d)));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setText("Price");

        spPrice.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        spPrice.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(0.5d)));

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/File-add-icon.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txfCode.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfCodeActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setText("Product Code");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Search-icon.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfCode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txfCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)
                                .addComponent(jLabel7)
                                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cmbProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(spQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(spPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Supplier"));

        jLabel1.setBackground(new java.awt.Color(102, 0, 0));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("Suplier*");

        cmbSupplier.setEditable(true);
        cmbSupplier.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupplierActionPerformed(evt);
            }
        });

        txfPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Phone");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txfPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

        tableProducts.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SL#", "Product Name", "Product Code", "Category", "Quantity", "Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableProducts);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Invoice Info"));

        txfInvoiceNo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(102, 0, 0));
        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Invoice No*");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Invoice Date");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(txfInvoiceNo))
                .addGap(39, 39, 39))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlPayment.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true), "Payment Info"));

        txfSubTotal.setEditable(false);
        txfSubTotal.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("Sub Total");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setText("VAT");

        txfVat.setEditable(false);
        txfVat.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txfPayable.setEditable(false);
        txfPayable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setText("Net Payable");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setText("Paid");

        txfPaid.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txfPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfPaidActionPerformed(evt);
            }
        });

        cmbPaymentMode.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbPaymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Payment Mode", "Cash", "Card", "Bank" }));
        cmbPaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPaymentModeActionPerformed(evt);
            }
        });

        cmbAccounts.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lblReturn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lblReturn.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblReturn.setText("Due");

        txfDue.setEditable(false);
        txfDue.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        taNotes.setColumns(20);
        taNotes.setRows(5);
        jScrollPane2.setViewportView(taNotes);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Notes");

        dcClearDate.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));

        lblReturn1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lblReturn1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblReturn1.setText("Clear On");

        rbClear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rbClear.setText("Clear");

        rbNotClear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        rbNotClear.setText("Not Clear");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 0, 0));
        jLabel16.setText("Status*");

        javax.swing.GroupLayout pnlPaymentLayout = new javax.swing.GroupLayout(pnlPayment);
        pnlPayment.setLayout(pnlPaymentLayout);
        pnlPaymentLayout.setHorizontalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentLayout.createSequentialGroup()
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                        .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPaymentLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txfPayable, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txfVat, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txfPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                                        .addComponent(lblReturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txfDue, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlPaymentLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlPaymentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(cmbAccounts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPaymentMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPaymentLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblReturn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcClearDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pnlPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(16, 16, 16)
                .addComponent(rbClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbNotClear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPaymentLayout.setVerticalGroup(
            pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPaymentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfVat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPayable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReturn))
                .addGap(18, 18, 18)
                .addComponent(cmbPaymentMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAccounts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbClear)
                    .addComponent(rbNotClear)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcClearDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReturn1))
                .addGap(3, 3, 3)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 1, true));

        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Refresh-icon.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Save-icon.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset)
                .addGap(356, 356, 356))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

        if (!checkValidProduct()) {
            return;
        }

        Product product = new Product();

        product.setName(cmbProductName.getSelectedItem().toString());
        product.setCategoryID(categories.get(cmbCategory.getSelectedIndex()));
        product.setCode(txfCode.getText());
        product.setRpu(Double.parseDouble(spPrice.getValue().toString()));
        product.setQuantity(Double.parseDouble(spQuantity.getValue().toString()));

        if (cmbProductName.getSelectedIndex() == -1) {
            product.setId(-1);

        } else {
            product.setId(products.get(cmbProductName.getSelectedIndex()));
        }

        selectedProducts.add(product);

        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();
        model.addRow(new Object[]{selectedProducts.size(), product.getName(), product.getCode(), cmbCategory.getSelectedItem().toString(), product.getQuantity(), product.getRpu()});

        subtotal += product.getQuantity() * product.getRpu();
        vat = subtotal * (Configuration.VAT * .01);
        netPayable = subtotal + vat;
        updatePaymentUI();

        shouldPerformActionForcmbCategory = true;
        spPrice.setValue(0.0);
        spQuantity.setValue(0.0);
        loadCategory();
        cmbCategory.setSelectedIndex(0);
        txfCode.setText("");


    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupplierActionPerformed
        // TODO add your handling code here:

        if (cmbSupplier.getSelectedIndex() <= 0) {
            txfPhone.setText("");
            return;
        }

        txfPhone.setText(suppliers.get(cmbSupplier.getSelectedIndex()).getPhone());

    }//GEN-LAST:event_cmbSupplierActionPerformed

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        // TODO add your handling code here:

        if (!shouldPerformActionForcmbCategory) {
            return;
        }


        if (cmbCategory.getSelectedIndex() == 0) {
            cmbProductName.removeAllItems();
            return;
        }

        loadProducts(categories.get(cmbCategory.getSelectedIndex()));

    }//GEN-LAST:event_cmbCategoryActionPerformed

    private void txfPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfPaidActionPerformed
        // TODO add your handling code here:

        if (Utilities.isOnlyNumber(txfPaid.getText())) {
            paid = Double.parseDouble(txfPaid.getText());
            updatePaymentUI();

        } else {
            txfPaid.setText("0.00");
            parentFrame.showMessage("Please enter valid paid amount");
        }

    }//GEN-LAST:event_txfPaidActionPerformed

    private void changeStatus(boolean status) {

        cmbAccounts.setEnabled(status);
        cmbCategory.setEnabled(status);
        cmbPaymentMode.setEnabled(status);
        cmbProductName.setEnabled(status);
        cmbSupplier.setEnabled(status);

        txfCode.setEnabled(status);
        txfDue.setEnabled(status);
        txfInvoiceNo.setEnabled(status);
        txfPaid.setEnabled(status);
        txfPayable.setEnabled(status);
        txfPhone.setEnabled(status);
        txfSubTotal.setEnabled(status);
        txfVat.setEnabled(status);

        spPrice.setEnabled(status);
        spQuantity.setEnabled(status);

        dcClearDate.setEnabled(status);
        dcDate.setEnabled(status);

        taNotes.setEnabled(status);

        btnAdd.setEnabled(status);
        btnReset.setEnabled(status);
        btnSave.setEnabled(status);

        rbClear.setEnabled(status);
        rbNotClear.setEnabled(status);
    }

    private void cmbPaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentModeActionPerformed
        // TODO add your handling code here:

        if (cmbPaymentMode.getSelectedIndex() == 0) {
            cmbAccounts.removeAllItems();
            accounts.clear();
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

                    if (Integer.parseInt(get().toString()) == 1) {
                    } else if (Integer.parseInt(get().toString()) == -1) {
                        cmbPaymentMode.setSelectedIndex(0);
                        cmbAccounts.removeAllItems();
                        parentFrame.showMessage("Unable to load accounts");
                    } else if (Integer.parseInt(get().toString()) == -2) {
                        cmbPaymentMode.setSelectedIndex(0);
                        cmbAccounts.removeAllItems();
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

    private void txfCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfCodeActionPerformed
        // TODO add your handling code here:


        shouldPerformActionForcmbCategory = false;
        products.clear();
        cmbCategory.removeAllItems();
        cmbProductName.removeAllItems();
        spQuantity.setValue(0);

        int productID = productSearch.getProductByCode(txfCode.getText());

        if (productID <= 0) {
            parentFrame.showMessage("No product found");
            txfCode.setText("");
            loadCategory();
            shouldPerformActionForcmbCategory = true;
        } else {
            loadProduct(productID);
        }
    }//GEN-LAST:event_txfCodeActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:

        if (!check()) {
            return;
        }

        if (paid == 0.00) {

            if (JOptionPane.showConfirmDialog(parentFrame, "Paid amount is 0. Do you want to continue?", "Warning", JOptionPane.YES_NO_OPTION) == 1) {
                return;
            }
        }

        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {

                parentFrame.stausBar.startLoading("saving supplier invoice");
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

                    if (Integer.parseInt(get().toString()) == 1) {

                        if (isUpdate) {
                            changeStatusPaymentPanel(true);
                        }

                        reset();
                        parentFrame.showMessage("Supplier invoice saved");
                    } else if (Integer.parseInt(get().toString()) == -1) {
                        parentFrame.showMessage("Unable to save invoice info");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        UISearcher searcher = new UISearcher(parentFrame, true);
        searcher.addSearcherListener(this);

        Vector terms = new Vector();
        terms.add("All");
        terms.add("Product Code");
        terms.add("Product Desription");
        terms.add("Product Price");
        searcher.setColumns(terms);

        QueryBuilder builder = new QueryBuilder();
        builder.select("product.product_id, product.product_code, product.title, product.rate_per_unit");
        builder.from("product");
        searcher.setQueryBuilder(builder);

        searcher.showWindow();
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbAccounts;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JComboBox cmbPaymentMode;
    private javax.swing.JComboBox cmbProductName;
    private javax.swing.JComboBox cmbSupplier;
    private datechooser.beans.DateChooserCombo dcClearDate;
    private datechooser.beans.DateChooserCombo dcDate;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblReturn;
    private javax.swing.JLabel lblReturn1;
    private javax.swing.JPanel pnlPayment;
    private javax.swing.JRadioButton rbClear;
    private javax.swing.JRadioButton rbNotClear;
    private javax.swing.JSpinner spPrice;
    private javax.swing.JSpinner spQuantity;
    private javax.swing.JTextArea taNotes;
    private javax.swing.JTable tableProducts;
    private javax.swing.JTextField txfCode;
    private javax.swing.JTextField txfDue;
    private javax.swing.JTextField txfInvoiceNo;
    private javax.swing.JTextField txfPaid;
    private javax.swing.JTextField txfPayable;
    private javax.swing.JTextField txfPhone;
    private javax.swing.JTextField txfSubTotal;
    private javax.swing.JTextField txfVat;
    // End of variables declaration//GEN-END:variables

    private int loadAccounts(String accountType) {

        MySQLDatabase database = new MySQLDatabase();
        Vector<String> accountTitles = new Vector<>();
        String query;
        accounts.clear();
        accountTitles.add("Select Account");
        int status = -1;
        int countAccount = 0;

        if (database.connect()) {

            query = "SELECT * FROM account WHERE type =\"" + accountType + "\" AND status = 1 ORDER BY id";

            ResultSet resultSet = database.get(query);

            try {
                while (resultSet.next()) {
                    countAccount++;
                    accounts.put(countAccount, resultSet.getInt("id"));
                    accountTitles.add(resultSet.getString("title"));
//                    System.out.println(resultSet.getString("title"));
                }

                cmbAccounts.setModel(new DefaultComboBoxModel(accountTitles));
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

    private void loadSuppliers() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> supplierNames = new Vector<>();
        suppliers.clear();
        int status = 0;
        int supplierCount = 0;

        supplierNames.add("Select Supplier");

        if (database.connect()) {
            try {
                query = "SELECT supplier_id, name, phone FROM supplier";

                ResultSet resultSet = database.get(query);

                while (resultSet.next()) {

                    Supplier supplier = new Supplier();
                    supplier.setSupplierID(resultSet.getInt("supplier_id"));
                    supplier.setName(resultSet.getString("name"));
                    supplier.setPhone(resultSet.getString("phone"));
                    supplierNames.add(supplier.getName());

                    supplierCount++;
                    suppliers.put(supplierCount, supplier);
                }

                cmbSupplier.setModel(new DefaultComboBoxModel(supplierNames));
                status = 1;

            } catch (SQLException ex) {
                Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

    }

    private void loadCategory() {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> categoryNames = new Vector<>();
        categories.clear();
        int status = 0;
        int categoryCount = 0;

        categoryNames.add("Select Category");

        if (database.connect()) {
            try {
                query = "SELECT category_id, title FROM product_category";

                ResultSet resultSet = database.get(query);

                while (resultSet.next()) {
                    categoryNames.add(resultSet.getString("title"));
                    categoryCount++;
                    categories.put(categoryCount, resultSet.getInt("category_id"));
                }

                cmbCategory.setModel(new DefaultComboBoxModel(categoryNames));
                status = 1;

            } catch (SQLException ex) {
                Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

    }

    private int loadProducts(int catID) {

        MySQLDatabase database = new MySQLDatabase();
        String query;
        Vector<String> productTitle = new Vector<>();
        products.clear();
        int status = 0;
        int countProduct = 0;

        productTitle.add("Select Product");

        if (database.connect()) {
            try {
                query = "SELECT product.product_id, product.title, product_brand.title"
                        + " FROM product"
                        + " INNER JOIN product_brand ON product_brand.brand_id = product.product_brand_id"
                        + " WHERE product.product_category_id = " + catID;

                ResultSet resultSet = database.get(query);

                while (resultSet.next()) {

                    String productName = resultSet.getString("product.title") + "-" + resultSet.getString("product_brand.title");
                    productTitle.add(productName);
                    countProduct++;
                    products.put(countProduct, resultSet.getInt("product.product_id"));
                }

                cmbProductName.setModel(new DefaultComboBoxModel(productTitle));
                status = 1;

            } catch (Exception ex) {
                Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                status = -1;
            } finally {
                database.disconnect();
            }

        } else {
            status = -2;
        }

        return status;
    }

    private void updatePaymentUI() {

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        txfDue.setText("" + decimalFormat.format(Math.abs(paid - netPayable)));
        txfPaid.setText("" + paid);
        txfPayable.setText("" + netPayable);
        txfSubTotal.setText("" + subtotal);
        txfVat.setText("" + decimalFormat.format(vat));

        if (paid > netPayable) {
            lblReturn.setText("Return");
            lblReturn.setForeground(Color.red);
        } else {
            lblReturn.setText("Due");
            lblReturn.setForeground(Color.BLUE);
        }

    }

    private void reset() {

        isUpdate = false;
        subtotal = 0.0;
        paid = 0.0;
        netPayable = 0.0;
        vat = 0.0;

        updatePaymentUI();

        loadSuppliers();
        loadCategory();
        selectedProducts.clear();
        cmbAccounts.removeAllItems();
        accounts.clear();
        shouldPerformActionForcmbCategory = true;
        loadSuppliers();
        txfInvoiceNo.setText("");
        txfPhone.setText("");
        txfCode.setText("");
        dcDate.setSelectedDate(Calendar.getInstance());
        cmbPaymentMode.setSelectedIndex(0);
        isUpdate = false;

        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }


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
                query = "SELECT product.product_id, product.title, product.product_category_id, product.product_code, product_brand.title, product_unit.title, product.rate_per_unit, "
                        + " product_category.title"
                        + " FROM product"
                        + " INNER JOIN product_brand ON product_brand.brand_id = product.product_brand_id"
                        + " INNER JOIN product_unit ON product_unit.unit_id = product.product_unit_id"
                        + " INNER JOIN product_category ON product_category.category_id = product.product_category_id"
                        + " WHERE product.product_id = " + productID + " LIMIT 1";

                ResultSet resultSet = database.get(query);

                if (resultSet.next()) {

//                    Product product = new Product();
//                    product.setBrand(resultSet.getString("product_brand.title"));
//                    product.setId(resultSet.getInt("product.product_id"));
//                    product.setName(resultSet.getString("product.title"));
//                    product.setRpu(resultSet.getDouble("product.rate_per_unit"));
//                    product.setUnit(resultSet.getString("product_unit.title"));

                    String productName = resultSet.getString("product.title") + "-" + resultSet.getString("product_brand.title");
                    productTitle.add(productName);
                    products.put(1, productID);

                    cmbProductName.setModel(new DefaultComboBoxModel(productTitle));
                    cmbCategory.setModel(new DefaultComboBoxModel(new String[]{"Select Category", resultSet.getString("product_category.title")}));
                    categories.put(1, resultSet.getInt("product.product_category_id"));

                    cmbProductName.setSelectedIndex(1);
                    shouldPerformActionForcmbCategory = false;
                    cmbCategory.setSelectedIndex(1);
                    txfCode.setText(resultSet.getString("product.product_code"));
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

    private boolean checkValidProduct() {

        if (cmbCategory.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select product category");
            return false;
        }

        if (cmbProductName.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select product name");
            return false;
        }

        if (Double.parseDouble(spQuantity.getValue().toString()) == 0.00 || Double.parseDouble(spQuantity.getValue().toString()) <= -1.00) {
            parentFrame.showMessage("Please enter valid quantity");
            return false;
        }

        if (Double.parseDouble(spPrice.getValue().toString()) == 0.00 || Double.parseDouble(spPrice.getValue().toString()) <= -1.00) {
            parentFrame.showMessage("Please enter valid price");
            return false;
        }

        return true;
    }

    private boolean check() {

        if (cmbSupplier.getSelectedIndex() == 0) {
            parentFrame.showMessage("Please select supplier");
            return false;
        }

        if (!Utilities.isValidString(txfInvoiceNo.getText())) {
            parentFrame.showMessage("Please enter invoice no");
            return false;
        }

        if (selectedProducts.size() == 0) {
            parentFrame.showMessage("Please add some product");
            return false;
        }

        if (!isUpdate) {
            if (paid > 0.00 && cmbPaymentMode.getSelectedIndex() == 0) {
                parentFrame.showMessage("Please select payment mode");
                return false;
            }

            if (paid > 0.00 && cmbAccounts.getSelectedIndex() == 0) {
                parentFrame.showMessage("Please select an account");
                return false;
            }

            if (paid > 0.00 && cmbPaymentMode.getSelectedItem().toString().compareTo("Bank") == 0 && !rbClear.isSelected() && !rbNotClear.isSelected()) {
                parentFrame.showMessage("Please select status");
                return false;
            }
        }
        return true;

    }

    private void removeProduct(int selectedRow) {

        DefaultTableModel model = (DefaultTableModel) tableProducts.getModel();

        subtotal -= selectedProducts.get(selectedRow).getRpu() * selectedProducts.get(selectedRow).getQuantity();
        vat = subtotal * (Configuration.VAT * .01);
        netPayable = subtotal + vat;
        updatePaymentUI();

        selectedProducts.remove(selectedRow);
        model.removeRow(selectedRow);

        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }

    }

    private int update() {

        int status = 0;
        MySQLDatabase database = new MySQLDatabase();
        String query;
        long supplierID;
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            if (cmbSupplier.getSelectedIndex() == -1) {

                query = "INSERT INTO person VALUES("
                        + "null, null, null, null, "
                        + "null, null, -1"
                        + ")";

                long personID = database.insert(query);

                if (personID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

                query = "INSERT INTO address VALUES("
                        + "null, null, null, null"
                        + ")";

                long addressID = database.insert(query);

                if (addressID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

                query = "INSERT INTO supplier VALUES("
                        + "null, \"" + cmbSupplier.getSelectedItem().toString() + "\", " + personID + ", " + addressID + ", \"" + txfPhone.getText() + "\", "
                        + "null, " + parentFrame.currentUser.getUserID() + ", NOW()"
                        + ")";

                supplierID = database.insert(query);

                if (supplierID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

            } else {
                supplierID = suppliers.get(cmbSupplier.getSelectedIndex()).getSupplierID();
            }

//            query = "INSERT INTO supplier_invoice VALUES(null, "
//                    + "" + supplierID + ", \"" + txfInvoiceNo.getText() + "\", \"" + Utilities.dateForDB(dcDate.getSelectedDate().getTime()) + "\", " + vat + ""
//                    + ", " + subtotal + ", " + netPayable + ", " + parentFrame.currentUser.getUserID() + ", NOW())";

            queryBuilder.clear();
            queryBuilder.set("supplier_id", "" + supplierID);
            queryBuilder.setString("supplier_invoice_no", txfInvoiceNo.getText());
            queryBuilder.setString("invoice_date", "" + Utilities.dateForDB(dcDate.getSelectedDate().getTime()));
            queryBuilder.set("vat", "" + vat);
            queryBuilder.set("subtotal", "" + subtotal);
            queryBuilder.set("payable", "" + netPayable);
            queryBuilder.set("last_updated_by", "" + parentFrame.currentUser.getUserID());
            queryBuilder.set("last_updated_time", "NOW()");
            queryBuilder.where("invoice_id = ", "" + supplierInvoice.getInvoiceID());

            long affectedRow = database.update(queryBuilder.update("supplier_invoice"));

            if (affectedRow == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            queryBuilder.clear();

            queryBuilder.where("supplier_invoice_id = ", "" + supplierInvoice.getInvoiceID());
            affectedRow = database.delete(queryBuilder.delete("product_stock"));

            if (affectedRow == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            long stockID = 0;

            for (Product product : selectedProducts) {

                try {
                    query = "INSERT INTO product_stock VALUES(null, " + product.getId() + ", " + supplierInvoice.getInvoiceID() + ", " + product.getQuantity() + ""
                            + ", " + product.getRpu() + ", CURDATE(), " + parentFrame.currentUser.getUserID() + ", NOW())";

                    stockID = database.insert(query);

                    if (stockID == -1) {
                        database.rollback();
                        database.setAutoCommit(true);
                        database.disconnect();
                        return -1;
                    }

                    // calculating average cost for product
//                    query = "SELECT use_average_cost FROM product WHERE product_id = " + product.getId();
//
//                    ResultSet resultSet = database.get(query);
//
//                    if (resultSet.next()) {
//
//                        if (resultSet.getBoolean("use_average_cost")) {
//
//                            query = "SELECT SUM(cost_per_unit*quantity)/SUM(quantity) AS avg_cost FROM product_stock WHERE product_id = " + product.getId();
//                            ResultSet stockInfo = database.get(query);
//                            double avgCost = 0;
//
//                            if (stockInfo.next()) {
//                                avgCost = stockInfo.getDouble("avg_cost");
//                            } else {
//                                avgCost = product.getRpu();
//                            }
//
//                            query = "UPDATE product SET rate_per_unit = " + Utilities.round(avgCost) + " WHERE product_id = " + product.getId();
//
//                            if (database.update(query) == -1) {
//                                database.rollback();
//                                database.setAutoCommit(true);
//                                database.disconnectFromDatabase();
//                                status = -1;
//                                return status;
//                            }
//                        }
//                    }

                } catch (Exception ex) {
                    Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                    status = -1;
                    break;
                }

                if (status == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return status;
                }
            }

//            if (paid > 0) {
//
//                query = "INSERT INTO supplier_invoice_transaction VALUES(null,"
//                        + "" + invoiceID + ", \"" + Utilities.dateForDB(dcDate.getSelectedDate().getTime()) + "\",\"" + Utilities.dateForDB(dcClearDate.getSelectedDate().getTime()) + "\", " + rbClear.isSelected() + ", " + paid + ", " + accounts.get(cmbAccounts.getSelectedIndex()) + ""
//                        + ", \"Withdraw\", \"" + taNotes.getText() + "\", " + parentFrame.currentUser.getUserID() + ", NOW()"
//                        + ")";
//
//                long txnID = database.insert(query);
//
//                if (txnID == -1) {
//                    database.rollback();
//                    database.setAutoCommit(true);
//                    database.disconnectFromDatabase();
//                    return -1;
//                }
//            }

            database.setAutoCommit(true);
            database.disconnect();
            status = 1;
        } else {
            status = -2;
        }

        return status;

    }

    private int save() {

        int status = 0;
        MySQLDatabase database = new MySQLDatabase();
        String query;
        long supplierID;

        if (database.connect()) {

            database.setAutoCommit(false);

            if (cmbSupplier.getSelectedIndex() == -1) {

                query = "INSERT INTO person VALUES("
                        + "null, null, null, null, "
                        + "null, null, -1"
                        + ")";

                long personID = database.insert(query);

                if (personID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

                query = "INSERT INTO address VALUES("
                        + "null, null, null, null"
                        + ")";

                long addressID = database.insert(query);

                if (addressID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

                query = "INSERT INTO supplier VALUES("
                        + "null, \"" + cmbSupplier.getSelectedItem().toString() + "\", " + personID + ", " + addressID + ", \"" + txfPhone.getText() + "\", "
                        + "null, " + parentFrame.currentUser.getUserID() + ", NOW()"
                        + ")";

                supplierID = database.insert(query);

                if (supplierID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

                query = "INSERT INTO supplier_invoice VALUES(null, "
                        + "" + supplierID + ", \"open\", CURDATE(), " + 0.00 + ""
                        + ", " + 0.00 + ", " + 0.00 + ", " + parentFrame.currentUser.getUserID() + ", NOW())";

                long invoiceID = database.insert(query);

                if (invoiceID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }

            } else {
                supplierID = suppliers.get(cmbSupplier.getSelectedIndex()).getSupplierID();
            }

            query = "INSERT INTO supplier_invoice VALUES(null, "
                    + "" + supplierID + ", \"" + txfInvoiceNo.getText() + "\", \"" + Utilities.dateForDB(dcDate.getSelectedDate().getTime()) + "\", " + vat + ""
                    + ", " + subtotal + ", " + netPayable + ", " + parentFrame.currentUser.getUserID() + ", NOW())";

            long invoiceID = database.insert(query);

            if (invoiceID == -1) {
                database.rollback();
                database.setAutoCommit(true);
                database.disconnect();
                return -1;
            }

            long stockID = 0;

            for (Product product : selectedProducts) {

                try {
                    query = "INSERT INTO product_stock VALUES(null, " + product.getId() + ", " + invoiceID + ", " + product.getQuantity() + ""
                            + ", " + product.getRpu() + ", CURDATE(), " + parentFrame.currentUser.getUserID() + ", NOW())";

                    stockID = database.insert(query);

                    if (stockID == -1) {
                        database.rollback();
                        database.setAutoCommit(true);
                        database.disconnect();
                        return -1;
                    }

                    // calculating average cost for product
//                    query = "SELECT use_average_cost FROM product WHERE product_id = " + product.getId();
//
//                    ResultSet resultSet = database.get(query);
//
//                    if (resultSet.next()) {
//
//                        if (resultSet.getBoolean("use_average_cost")) {
//
//                            query = "SELECT SUM(cost_per_unit*quantity)/SUM(quantity) AS avg_cost FROM product_stock WHERE product_id = " + product.getId();
//                            ResultSet stockInfo = database.get(query);
//                            double avgCost = 0;
//
//                            if (stockInfo.next()) {
//                                avgCost = stockInfo.getDouble("avg_cost");
//                            } else {
//                                avgCost = product.getRpu();
//                            }
//
//                            query = "UPDATE product SET rate_per_unit = " + Utilities.round(avgCost) + " WHERE product_id = " + product.getId();
//
//                            if (database.update(query) == -1) {
//                                database.rollback();
//                                database.setAutoCommit(true);
//                                database.disconnectFromDatabase();
//                                status = -1;
//                                return status;
//                            }
//                        }
//                    }

                } catch (Exception ex) {
                    Logger.getLogger(UISupplierInvoice.class.getName()).log(Level.SEVERE, null, ex);
                    status = -1;
                    break;
                }

                if (status == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return status;
                }
            }

            if (paid > 0) {

                query = "INSERT INTO supplier_invoice_transaction VALUES(null,"
                        + "" + invoiceID + ", \"" + Utilities.dateForDB(dcDate.getSelectedDate().getTime()) + "\",\"" + Utilities.dateForDB(dcClearDate.getSelectedDate().getTime()) + "\", " + rbClear.isSelected() + ", " + paid + ", " + accounts.get(cmbAccounts.getSelectedIndex()) + ""
                        + ", \"Withdraw\", \"" + taNotes.getText() + "\", " + parentFrame.currentUser.getUserID() + ", NOW()"
                        + ")";

                long txnID = database.insert(query);

                if (txnID == -1) {
                    database.rollback();
                    database.setAutoCommit(true);
                    database.disconnect();
                    return -1;
                }
            }

            database.setAutoCommit(true);
            database.disconnect();
            status = 1;
        } else {
            status = -2;
        }

        return status;

    }

    @Override
    public void addRecord(int recordID) {
        loadProduct(recordID);
    }

    private class ActionMenu extends JPopupMenu implements ActionListener {

        private JMenuItem itemDelete;

        public ActionMenu() {

            super();
            itemDelete = new JMenuItem("Remove");
            itemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qbits/resources/image/Delete-icon.png")));

            itemDelete.addActionListener(this);

            add(itemDelete);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (itemDelete == e.getSource()) {

                if (JOptionPane.showConfirmDialog(parentFrame, "Do you realy want to remove this product?", "Warning", JOptionPane.YES_NO_OPTION) == 0) {
                    int selectedRow = tableProducts.rowAtPoint(popupSelectedPoint);
                    removeProduct(selectedRow);
                }


            }
        }
    }
}
