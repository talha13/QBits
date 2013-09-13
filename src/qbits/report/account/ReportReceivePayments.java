/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.report.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import qbits.common.Message;
import qbits.configuration.Utilities;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.entity.Transaction;

/**
 *
 * @author Pipilika
 */
public class ReportReceivePayments {

    public ArrayList<Transaction> getAccountTransaction(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("account.id, account.title, account.type");
                queryBuilder.select("account_transaction.voucher_no, account_transaction.amount, account_transaction.type, account_transaction.txn_date, account_transaction.clear_on, account_transaction.status, account_transaction.notes");
                queryBuilder.leftJoin("account_transaction", "account_transaction.account_id = account.id");
                queryBuilder.where("txn_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("txn_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("account");

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("account_transaction.amount"));
                    transaction.setClear(resultSet.getBoolean("account_transaction.status"));
                    transaction.setClearDate(resultSet.getDate("account_transaction.clear_on"));
                    transaction.setDate(resultSet.getDate("account_transaction.txn_date"));
                    transaction.setDebit(resultSet.getString("account_transaction.type").compareTo("Deposit") == 0 ? true : false);
                    transaction.setNotes(resultSet.getString("account_transaction.notes"));
                    transaction.setParticular(transaction.isDebit()?"Deposit":"Withdraw");
                    transaction.setPaymentMode(resultSet.getString("account.type"));
                    transaction.setVoucherNo(resultSet.getString("account_transaction.voucher_no"));

                    transactions.add(transaction);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ReportReceivePayments.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return transactions;
    }

    private ArrayList<Transaction> sample(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {
            try {
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {
                }

            } catch (SQLException ex) {
                Logger.getLogger(ReportReceivePayments.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }

        return transactions;
    }
}
