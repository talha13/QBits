/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import java.util.Vector;
import qbits.db.QueryBuilder;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;

/**
 *
 * @author Pipilika
 */
public class UISupplierInvoiceCRUD extends UICRUD implements CRUDListener {

    private UIParentFrame parentFrame;

    public UISupplierInvoiceCRUD(UIParentFrame frame) {

        parentFrame = frame;

        QueryBuilder query = new QueryBuilder();
        query.select("supplier_invoice.invoice_id, supplier.name, supplier_invoice.supplier_invoice_no, supplier_invoice.invoice_date, supplier_invoice.payable");
        query.from("supplier_invoice");
        query.innerJoin("supplier", "supplier.supplier_id = supplier_invoice.supplier_id");

        Vector columns = new Vector();
        columns.add("All");
        columns.add("Supplier");
        columns.add("Invoice No");
        columns.add("Invoice Date");
        columns.add("Payable");
        

        setColumns(columns);
        setQueryBuilder(query);


        addCRUDListener(this);
    }

    @Override
    public void removeRecord(int recordID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRecord(int recordID) {
        parentFrame.updateSupplierInvoice(recordID);
    }

    @Override
    public void addRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
