/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import qbits.configuration.Configuration;
import qbits.entity.User;
import qbits.gui.account.UIAccount;
import qbits.gui.account.UIAccountHead;
import qbits.gui.account.UIAccountTransaction;
import qbits.gui.account.UIGeneralTransaction;
import qbits.gui.purchase.UISupplier;
import qbits.gui.purchase.UISupplierCRUD;
import qbits.gui.purchase.UISupplierInvoice;
import qbits.gui.purchase.UISupplierInvoiceCRUD;
import qbits.gui.purchase.UISupplierTransaction;
import qbits.gui.purchase.product.UIProduct;
import qbits.gui.purchase.product.UIProductDamage;
import qbits.gui.purchase.product.UIProductPurchaseReturn;

/**
 *
 * @author Topu
 */
public class UIParentFrame extends JFrame implements WindowListener {

    private JPanel pnlDynamic;
    private BorderLayout borderLayout;
    public UIStatusBar stausBar;
    public User currentUser;
    private UIBanner banner;

    public UIParentFrame(User user) {

        super(Configuration.APP_TITLE);
        currentUser = user;
        borderLayout = new BorderLayout();
        setLayout(borderLayout);

        pnlDynamic = new JPanel();
        pnlDynamic.setMinimumSize(new Dimension(Configuration.FRAME_WIDTH, Configuration.FRAME_HEIGHT));
        stausBar = new UIStatusBar();
        banner = new UIBanner();

        stausBar.currentUserName.setText(user.getName());

        add(banner, BorderLayout.PAGE_START);
        add(pnlDynamic, BorderLayout.CENTER);
        add(stausBar, BorderLayout.PAGE_END);
        addWindowListener(this);

        setJMenuBar(new UIMenuBar(this));

        showWindow();
    }

    public void showWindow() {

        Toolkit theKit = this.getToolkit();
        Dimension wndSize = theKit.getScreenSize();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds((wndSize.width - Configuration.FRAME_WIDTH) / 2, (wndSize.height - Configuration.FRAME_HEIGHT) / 2, Configuration.FRAME_WIDTH, Configuration.FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void addPanel(JPanel thisPanel, String roleTitle) {

        if (currentUser.canPlay(roleTitle) || currentUser.getUserID() == 1) {
            pnlDynamic.setVisible(false);
            pnlDynamic.removeAll();
            pnlDynamic.add(thisPanel);
            pnlDynamic.setVisible(true);
            pnlDynamic.validate();
        } else {
            showMessage("You do not have sufficient permission to access this feature");
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        close();
    }

    public void close() {
        int state = JOptionPane.showConfirmDialog(this, "Are you realy want to exit?", Configuration.APP_TITLE, JOptionPane.YES_NO_OPTION);

        if (state == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addChangePassword() {
        addPanel(new UIChangePassword(this), "change_password");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void newSupplier() {
        UISupplier supplier = new UISupplier(this);
        addPanel(supplier, "new_supplier");
    }

    public void updateSupplier(int supplierID) {
        UISupplier supplier = new UISupplier(this);
        supplier.update(supplierID);
        addPanel(supplier, "update_supplier");
    }

    void newAccount() {
        addPanel(new UIAccount(this), "new_account");
    }

    void newAccountTransaction() {
        addPanel(new UIAccountTransaction(this), "new_account_transaction");
    }

    void newAccountHead() {
        addPanel(new UIAccountHead(this), "new_account_head");
    }

    void newGeneralTransaction() {
        addPanel(new UIGeneralTransaction(this), "new_general_transaction");
    }

    void supplierCRUD() {
        
        UISupplierCRUD supplierCRUD = new UISupplierCRUD(this);                
        addPanel(supplierCRUD, "supplier_crud");
    }

    public void newEmployee() {
//        addPanel(new UIEmployee(this), "new_employee");
    }

    void newProduct() {
        addPanel(new UIProduct(this), "new_product");
    }

    public void newDamageProduct() {
       addPanel(new UIProductDamage(this), "new_damage_product");
    }

    void newSupplierInvoice() {
        addPanel(new UISupplierInvoice(this), "new_supplier_invoice");
    }

    void newSupplierTransaction() {
        addPanel(new UISupplierTransaction(this), "new_supplier_transaction");
    }

    void newPurchaseReturn() {
        addPanel(new UIProductPurchaseReturn(this), "new_purchase_return");
    }

    public void updateSupplierInvoice(int invoiceID) {
        
        UISupplierInvoice supplierInvoice = new UISupplierInvoice(this);
        supplierInvoice.update(invoiceID);
        addPanel(supplierInvoice, "update_supplier_invoice");        
    }
    
    public void supplierInvoiceCRUD(){
        
        UISupplierInvoiceCRUD supplierInvoiceCRUD = new UISupplierInvoiceCRUD(this);
        addPanel(supplierInvoiceCRUD, "supplier_invoice_crud");
    }
}
