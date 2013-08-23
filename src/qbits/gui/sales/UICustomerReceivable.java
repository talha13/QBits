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
public class UICustomerReceivable extends UICRUD implements CRUDListener{
    
    private UIParentFrame parentFrame;
    
    public UICustomerReceivable(UIParentFrame frame){
        
        super();
        parentFrame = frame;
        
        
        Vector columns = new Vector();
        columns.add("All");
        columns.add("Customer Name");
        columns.add("Contact No");
        columns.add("Total Receivable");
        
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select("customer.id, CONCAT(person.first_name, '' , person.last_name), person.contact_no");
        queryBuilder.select("SUM(sales.)");
        
        setShowPopup(false);
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
