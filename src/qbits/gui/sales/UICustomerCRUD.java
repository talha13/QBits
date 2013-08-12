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
public class UICustomerCRUD extends UICRUD implements CRUDListener {

    private UIParentFrame parentFrame;

    public UICustomerCRUD(UIParentFrame frame) {
        super();
        this.parentFrame = frame;

        setTitle("Customer List");

        Vector terms = new Vector();
        terms.add("All");
        terms.add("Customer Name");
//        terms.add("Address");
        terms.add("Contact No");

        QueryBuilder builder = new QueryBuilder();
        builder.select("id, CONCAT(person.first_name, ' ', person.last_name), person.contact_no");
        builder.innerJoin("person", "person.person_id = customer.person_id");
        builder.from("customer");

        setColumns(terms);
        setQueryBuilder(builder);

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
        parentFrame.updateCustomer(recordID);
    }

    @Override
    public void addRecord() {
        parentFrame.newCustomer();
    }
}
