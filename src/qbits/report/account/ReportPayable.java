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
public class ReportPayable {

    private ArrayList<Receivable> payables;

    public ReportPayable() {
        payables = new ArrayList<>();
    }

    public ArrayList<Receivable> get() {

        QueryBuilder queryBuilder = new QueryBuilder();
        MySQLDatabase database = new MySQLDatabase();

        payables.clear();

        if (database.connect()) {
            try {

                queryBuilder.select("supplier.supplier_id, supplier.name, supplier.phone, SUM(supplier_invoice.payable) AS total");
                queryBuilder.leftJoin("supplier_invoice", "supplier_invoice.supplier_id = supplier.supplier_id");
                queryBuilder.from("supplier");

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    Receivable receivable = new Receivable();
                    receivable.setCustomerID(resultSet.getInt("supplier.supplier_id"));
                    receivable.setCustomerName(resultSet.getString("supplier.name"));
                    receivable.setPhone(resultSet.getString("supplier.phone"));
                    receivable.setPayable(resultSet.getDouble("total"));
                    receivable.setPaid(getTotalPayment(receivable.getCustomerID()));
                    payables.add(receivable);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportPayable.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Message.dbConnectFailed();
        }

        return payables;
    }

    public double getTotalPayment(int supplierID) {

        QueryBuilder queryBuilder = new QueryBuilder();
        MySQLDatabase database = new MySQLDatabase();
        double totalPaid = 0.0;

        if (database.connect()) {
            try {

                queryBuilder.select("SUM(supplier_invoice_transaction.paid_amount) AS total");
                queryBuilder.from("supplier_invoice");
                queryBuilder.leftJoin("supplier_invoice_transaction", "supplier_invoice_transaction.supplier_invoice_id = supplier_invoice.invoice_id");
                queryBuilder.where("supplier_invoice.supplier_id = " + supplierID);
                ResultSet resultSet = database.get(queryBuilder.get());

                if (resultSet.next()) {
                    totalPaid += resultSet.getDouble("total");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ReportReceivable.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
            return 0.00;
        }

        return totalPaid;
    }
}
