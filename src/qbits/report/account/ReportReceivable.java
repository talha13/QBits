/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import qbits.common.Message;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Receivable;

/**
 *
 * @author Pipilika
 */
public class ReportReceivable {
    
    private ArrayList<Receivable> receivables;

    public ReportReceivable() {        
        receivables = new ArrayList<>();
    }
    
    public ArrayList<Receivable> get(){
        
        QueryBuilder queryBuilder = new QueryBuilder();
        MySQLDatabase database = new MySQLDatabase();
        
        receivables.clear();
        
        if(database.connect()){
            try {
                queryBuilder.select("customer.id, CONCAT(person.first_name, ' ', person.last_name) AS name, person.contact_no");
                queryBuilder.select("SUM(sales_invoice.payable) AS total_payable");
                queryBuilder.from("customer");
                queryBuilder.innerJoin("person", "person.person_id = customer.person_id");
                queryBuilder.innerJoin("sales_invoice", "sales_invoice.customer_id = customer.id");
                queryBuilder.groupBy("customer.id");
                
                ResultSet resultSet = database.get(queryBuilder.get());
                
                while(resultSet.next()){
                    
                    Receivable receivable = new Receivable();
                    receivable.setCustomerID(resultSet.getInt("customer.id"));
                    receivable.setCustomerName(resultSet.getString("name"));
                    receivable.setPhone(resultSet.getString("person.contact_no"));
                    receivable.setPayable(resultSet.getDouble("total_payable"));
                    receivable.setPaid(getTotalPayment(receivable.getCustomerID()));
                    receivables.add(receivable);                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportReceivable.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            Message.dbConnectFailed();
        }
        
        return receivables;
    }
    
    public double getTotalPayment(int customerID){
        
        return 0.00;
        
    }
    
    
}
