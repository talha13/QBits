/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.purchase;

import qbits.gui.common.crud.UICRUD;
import qbits.gui.common.crud.CRUDListener;
import java.util.Vector;
import qbits.db.QueryBuilder;
import qbits.gui.common.UIParentFrame;
import qbitserp.common.Message;

/**
 *
 * @author Topu
 */
public class UISupplierCRUD extends UICRUD implements CRUDListener {

    private UIParentFrame parentFrame;

    public UISupplierCRUD(UIParentFrame frame) {

        super();

        parentFrame = frame;
        setTitle("Supplier");
        setSubTitle("Suplier Information");

        Vector<String> terms = new Vector<>();
        terms.add("All");
        terms.add("Supplier Name");
        terms.add("Phone");
        terms.add("Email");
        terms.add("Contact person");

        QueryBuilder builder = new QueryBuilder();
        builder.select("supplier.supplier_id, supplier.name, supplier.phone, supplier.email, CONCAT(person.first_name,\" \" ,person.last_name)");
        builder.from("supplier");
        builder.innerJoin("person", "person.person_id = supplier.contact_person_id");

        setColumns(terms);
        setQueryBuilder(builder);
        addCRUDListener(this);
    }

    public void removeRecord(int recordID) {
       
        if(Message.confirm("Realy want to remove Supplier?", "")==0){
            
            QueryBuilder builder = new QueryBuilder();
            builder.where("supplier_id ="+ recordID);
            
            System.out.println(builder.delete("supplier"));
            
        }
        
    }

    @Override
    public void printRecords() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRecord(int recordID) {
        parentFrame.updateSupplier(recordID);
    }

    @Override
    public void addRecord() {
        parentFrame.newSupplier();
    }
}
