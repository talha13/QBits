/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.employee;

import java.util.Vector;
import qbits.db.QueryBuilder;
import qbits.gui.common.UIParentFrame;
import qbits.gui.common.crud.CRUDListener;
import qbits.gui.common.crud.UICRUD;

/**
 *
 * @author Pipilika
 */
public class UIEmployeeCRUD extends UICRUD implements CRUDListener{
    
    private UIParentFrame parentFrame;
    
    public UIEmployeeCRUD(UIParentFrame frame){
        super();
        parentFrame = frame;
        
        setTitle("Employee List");
        
        Vector<String> columns = new Vector<>();
        QueryBuilder queryBuilder = new QueryBuilder();
        
        columns.add("All");
        columns.add("Name");
        columns.add("Contact No");
        columns.add("Department");
        columns.add("Designation");
        
        queryBuilder.select("employee.employee_id, CONCAT(person.first_name, ' ', person.last_name), person.contact_no");
        queryBuilder.select("employee_department.title, employee_designation.title");
        queryBuilder.innerJoin("person", "person.person_id = employee.person_id");
        queryBuilder.innerJoin("employee_department", "employee_department.department_id = employee.department_id");
        queryBuilder.innerJoin("employee_designation", "employee_designation.designation_id = employee.designation_id");
        queryBuilder.from("employee");
        
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
       parentFrame.updateEmployee(recordID);
    }

    @Override
    public void addRecord() {
        parentFrame.newEmployee();
    }
    
}
