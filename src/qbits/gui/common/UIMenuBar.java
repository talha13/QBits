/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Topu
 */
public class UIMenuBar extends JMenuBar implements ActionListener {

    private UIParentFrame parentFrame;
    private JMenu menuFile, menuPurchase, menuEmployee, menuSale, menuAccount, menuProduct, menuReport;
    private JMenuItem itemExit, itemChangePass;
    private JMenuItem itemNewSupplier, itemSupplierList, itemNewSupplierInvoice, itemNewSupplierTransaction, itemPurchaseReturn, itemsupplierInvoiceCRUD;
    private JMenuItem itemNewAccount, itemNewAccountTxn, itemNewAccountHead, itemNewGeneralTransaction;
    private JMenuItem itemNewEmployee, itemEmployeeCRUD;
    private JMenuItem itemNewProduct, itemNewDamageProduct, itemGenerateBarcode;
    private JMenuItem itemReportProductStock;
    private JMenuItem itemNewCustomer, itemCustomerList, itemSalesInvoice, itemSalesInvoiceCRUD, itemCustomerTransaction, itemCustomerReceivable;

    public UIMenuBar(UIParentFrame pf) {

        super();
        this.parentFrame = pf;

        menuFile = new JMenu("File");
        menuSale = new JMenu("Sale");
        menuPurchase = new JMenu("Purchase");
        menuAccount = new JMenu("Account");
        menuEmployee = new JMenu("Employee");
        menuProduct = new JMenu("Product");
        menuReport = new JMenu("Report");

        itemChangePass = new JMenuItem("Change Password");
        itemExit = new JMenuItem("Exit");

        itemNewSupplier = new JMenuItem("New Supplier");
        itemSupplierList = new JMenuItem("Supplier List");
        itemNewSupplierInvoice = new JMenuItem("New Supplier Invoice");
        itemNewSupplierTransaction = new JMenuItem("New Supplier Transaction");
        itemPurchaseReturn = new JMenuItem("Purchase Return");
        itemsupplierInvoiceCRUD = new JMenuItem("Supplier Invoice List");

        itemNewCustomer = new JMenuItem("New Customer");
        itemCustomerList = new JMenuItem("Customer List");
        itemSalesInvoice = new JMenuItem("Sales Invoice");
        itemSalesInvoiceCRUD = new JMenuItem("Sales Invoice List");
        itemCustomerReceivable = new JMenuItem("Receivable");
        itemCustomerTransaction = new JMenuItem("Customer Transaction");

        itemNewAccount = new JMenuItem("New Account");
        itemNewAccountTxn = new JMenuItem("New Account Transaction");
        itemNewAccountHead = new JMenuItem("New Account Head");
        itemNewGeneralTransaction = new JMenuItem("New General Transaction");

        itemNewEmployee = new JMenuItem("New Employee");
        itemEmployeeCRUD = new JMenuItem("Employee List");

        itemNewProduct = new JMenuItem("New Product");
        itemNewDamageProduct = new JMenuItem("New Damage Product");
        itemGenerateBarcode = new JMenuItem("Generate Barcode");

        itemReportProductStock = new JMenuItem("Product Stock");

        itemChangePass.addActionListener(this);
        itemExit.addActionListener(this);

        itemNewSupplier.addActionListener(this);
        itemSupplierList.addActionListener(this);
        itemNewSupplierInvoice.addActionListener(this);
        itemNewSupplierTransaction.addActionListener(this);
        itemPurchaseReturn.addActionListener(this);
        itemsupplierInvoiceCRUD.addActionListener(this);

        itemNewCustomer.addActionListener(this);
        itemCustomerList.addActionListener(this);
        itemSalesInvoice.addActionListener(this);
        itemSalesInvoiceCRUD.addActionListener(this);
        itemCustomerReceivable.addActionListener(this);
        itemCustomerTransaction.addActionListener(this);

        itemNewAccount.addActionListener(this);
        itemNewAccountTxn.addActionListener(this);
        itemNewAccountHead.addActionListener(this);
        itemNewGeneralTransaction.addActionListener(this);

        itemNewEmployee.addActionListener(this);
        itemEmployeeCRUD.addActionListener(this);

        itemNewProduct.addActionListener(this);
        itemNewDamageProduct.addActionListener(this);
        itemGenerateBarcode.addActionListener(this);

        itemReportProductStock.addActionListener(this);

        menuFile.add(itemChangePass);
        menuFile.addSeparator();
        menuFile.add(itemExit);

        menuPurchase.add(itemNewSupplier);
        menuPurchase.add(itemNewSupplierInvoice);
        menuPurchase.addSeparator();
        menuPurchase.add(itemNewSupplierTransaction);
        menuPurchase.addSeparator();
        menuPurchase.add(itemSupplierList);
        menuPurchase.add(itemsupplierInvoiceCRUD);
        menuPurchase.addSeparator();
        menuPurchase.add(itemPurchaseReturn);

        menuSale.add(itemNewCustomer);
        menuSale.add(itemCustomerReceivable);
        menuSale.add(itemCustomerTransaction);
        menuSale.addSeparator();
        menuSale.add(itemSalesInvoice);
        menuSale.add(itemSalesInvoiceCRUD);
        menuSale.addSeparator();
        menuSale.add(itemCustomerList);

        menuAccount.add(itemNewAccount);
        menuAccount.add(itemNewAccountHead);
        menuAccount.addSeparator();
        menuAccount.add(itemNewAccountTxn);
        menuAccount.add(itemNewGeneralTransaction);

        menuEmployee.add(itemNewEmployee);
        menuEmployee.addSeparator();
        menuEmployee.add(itemEmployeeCRUD);

        menuProduct.add(itemNewProduct);
        menuProduct.add(itemNewDamageProduct);
        menuProduct.addSeparator();
        menuProduct.add(itemGenerateBarcode);

        menuReport.add(itemReportProductStock);

        add(menuFile);
        add(menuPurchase);
        add(menuSale);
        add(menuAccount);
        add(menuEmployee);
        add(menuProduct);
        add(menuReport);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (itemChangePass == e.getSource()) {
            parentFrame.addChangePassword();
        } else if (itemExit == e.getSource()) {
            parentFrame.close();
        } else if (itemNewSupplier == e.getSource()) {
            parentFrame.newSupplier();
        } else if (itemNewAccount == e.getSource()) {
            parentFrame.newAccount();
        } else if (itemNewAccountTxn == e.getSource()) {
            parentFrame.newAccountTransaction();
        } else if (itemNewAccountHead == e.getSource()) {
            parentFrame.newAccountHead();
        } else if (itemNewGeneralTransaction == e.getSource()) {
            parentFrame.newGeneralTransaction();
        } else if (itemSupplierList == e.getSource()) {
            parentFrame.supplierCRUD();
        } else if (itemNewEmployee == e.getSource()) {
            parentFrame.newEmployee();
        } else if (itemNewProduct == e.getSource()) {
            parentFrame.newProduct();
        } else if (itemNewDamageProduct == e.getSource()) {
            parentFrame.newDamageProduct();
        } else if (itemNewSupplierInvoice == e.getSource()) {
            parentFrame.newSupplierInvoice();
        } else if (itemNewSupplierTransaction == e.getSource()) {
            parentFrame.newSupplierTransaction();
        } else if (itemPurchaseReturn == e.getSource()) {
            parentFrame.newPurchaseReturn();
        } else if (itemsupplierInvoiceCRUD == e.getSource()) {
            parentFrame.supplierInvoiceCRUD();
        } else if (itemReportProductStock == e.getSource()) {
            parentFrame.reportProductStock();
        } else if (itemNewCustomer == e.getSource()) {
            parentFrame.newCustomer();
        } else if (itemCustomerList == e.getSource()) {
            parentFrame.customerList();
        } else if (itemSalesInvoice == e.getSource()) {
            parentFrame.newSalesInvoice();
        } else if (itemSalesInvoiceCRUD == e.getSource()) {
            parentFrame.salesInvoiceCRUD();
        } else if (itemGenerateBarcode == e.getSource()) {
            parentFrame.generateBarcode();
        } else if (itemCustomerReceivable == e.getSource()) {
            parentFrame.customerReceivable();
        } else if (itemCustomerTransaction == e.getSource()) {
            parentFrame.customerTransaction();        
        } else if (itemEmployeeCRUD == e.getSource()) {
            parentFrame.employeeCRUD();
        }

    }
}
