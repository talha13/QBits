/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.sales;

import java.util.Vector;
import qbits.db.QueryBuilder;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;

/**
 *
 * @author Pipilika
 */
public class UISalesInvoiceCRUD extends UICRUD implements CRUDListener {

    private UIParentFrame parentFrame;

    public UISalesInvoiceCRUD(UIParentFrame frame) {
        super();
        parentFrame = frame;
        
        setTitle("Sales Invoice");
        
        Vector columns = new Vector();
        columns.add("All");
        columns.add("Invoice No");
        columns.add("Date");
        columns.add("Receivable");
        
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select("id, invoice_no, invoice_date, payable");
        queryBuilder.from("sales_invoice");
        
        setColumns(columns);
        setQueryBuilder(queryBuilder);
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
        parentFrame.updateSalesInvoice(recordID);
    }

    @Override
    public void addRecord() {
        parentFrame.newSalesInvoice();
    }
}
