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
                queryBuilder.innerJoin("account_transaction", "account_transaction.account_id = account.id");
                queryBuilder.where("account_transaction.txn_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("account_transaction.txn_date <= '" + Utilities.dateForDB(toDate) + "'");
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
                    transaction.setParticular(transaction.isDebit() ? "Deposit" : "Withdraw");
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

    public ArrayList<Transaction> getGeneralTransaction(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("account_head.id, account_head.title, account_head.principle");
                queryBuilder.select("general_transaction.voucher_no, general_transaction.amount, general_transaction.payment_mode, general_transaction.txn_date, general_transaction.clear_on, general_transaction.is_clear, general_transaction.notes");
                queryBuilder.select("account.title, account.id");
                queryBuilder.innerJoin("general_transaction", "general_transaction.account_head_id = account_head.id");
                queryBuilder.innerJoin("account", "account.id = general_transaction.account_id");
                queryBuilder.where("general_transaction.txn_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("general_transaction.txn_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("account_head");
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("general_transaction.amount"));
                    transaction.setClear(resultSet.getBoolean("general_transaction.is_clear"));
                    transaction.setDate(resultSet.getDate("general_transaction.txn_date"));
                    transaction.setDebit(resultSet.getString("account_head.principle").compareTo("Income") == 0 ? true : false);
                    transaction.setNotes(resultSet.getString("general_transaction.notes"));
                    transaction.setParticular(resultSet.getString("account_head.title"));
                    transaction.setPaymentMode(resultSet.getString("general_transaction.payment_mode"));
                    transaction.setVoucherNo(resultSet.getString("general_transaction.voucher_no"));

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

    public ArrayList<Transaction> getEmployeeSalaries(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("CONCAT(person.first_name, ' ', person.last_name) AS name");
                queryBuilder.select("employee_salary_transaction.voucher_no, employee_salary_transaction.transaction_date, employee_salary_transaction.clear_on, employee_salary_transaction.is_clear");
                queryBuilder.select("employee_salary_transaction.month, employee_salary_transaction.year, employee_salary_transaction.gross_salary, employee_salary_transaction.income_tax, employee_salary_transaction.provident_fund, employee_salary_transaction.bonus, employee_salary_transaction.deduction, employee_salary_transaction.notes");
                queryBuilder.select("account.title, account.id, account.type");
                queryBuilder.innerJoin("person", "person.person_id = employee.person_id");
                queryBuilder.innerJoin("employee_salary_transaction", "employee_salary_transaction.employee_id = employee.employee_id");
                queryBuilder.innerJoin("account", "account.id = employee_salary_transaction.account_id");
                queryBuilder.where("employee_salary_transaction.transaction_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("employee_salary_transaction.transaction_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("employee");
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("employee_salary_transaction.gross_salary"));
                    transaction.setClear(resultSet.getBoolean("employee_salary_transaction.is_clear"));
                    transaction.setClearDate(resultSet.getDate("employee_salary_transaction.clear_on"));
                    transaction.setDate(resultSet.getDate("employee_salary_transaction.transaction_date"));
                    transaction.setDebit(false);
                    transaction.setNotes(resultSet.getString("employee_salary_transaction.notes"));
                    transaction.setParticular("EmployeeSalary: " + resultSet.getString("name") + " (" + resultSet.getString("employee_salary_transaction.month") + "-" + resultSet.getInt("employee_salary_transaction.year") + ")");
                    transaction.setPaymentMode(resultSet.getString("account.type"));
                    transaction.setVoucherNo(resultSet.getString("employee_salary_transaction.voucher_no"));

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

    public ArrayList<Transaction> getEmployeeGeneralTransaction(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("CONCAT(person.first_name, ' ', person.last_name) AS name");
                queryBuilder.select("employee_general_transaction.voucher_no, employee_general_transaction.txn_date, employee_general_transaction.clear_on, employee_general_transaction.is_clear");
                queryBuilder.select("employee_general_transaction.notes, employee_general_transaction.amount");
                queryBuilder.select("account.title, account.id, account.type");
                queryBuilder.innerJoin("person", "person.person_id = employee.person_id");
                queryBuilder.innerJoin("employee_general_transaction", "employee_general_transaction.employee_id = employee.employee_id");
                queryBuilder.innerJoin("account", "account.id = employee_general_transaction.account_id");
                queryBuilder.where("employee_general_transaction.txn_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("employee_general_transaction.txn_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("employee");
                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("employee_general_transaction.amount"));
                    transaction.setClear(resultSet.getBoolean("employee_general_transaction.is_clear"));
                    transaction.setClearDate(resultSet.getDate("employee_general_transaction.clear_on"));
                    transaction.setDate(resultSet.getDate("employee_general_transaction.txn_date"));
                    transaction.setDebit(false);
                    transaction.setNotes(resultSet.getString("employee_general_transaction.notes"));
                    transaction.setParticular("EmployeePayment: " + resultSet.getString("name"));
                    transaction.setPaymentMode(resultSet.getString("account.type"));
                    transaction.setVoucherNo(resultSet.getString("employee_general_transaction.voucher_no"));

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

    public ArrayList<Transaction> getSalesTransaction(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("sale_invoice_transaction.transaction_date, sale_invoice_transaction.clear_on, sale_invoice_transaction.is_clear, sale_invoice_transaction.paid_amount, sale_invoice_transaction.account_id, sale_invoice_transaction.notes");
                queryBuilder.select("sales_invoice.invoice_no, sales_invoice.invoice_date");
                queryBuilder.select("account.title, account.id, account.type");
                queryBuilder.innerJoin("sales_invoice", "sales_invoice.id = sale_invoice_transaction.sale_invoice_id");
                queryBuilder.innerJoin("account", "account.id = sale_invoice_transaction.account_id");
                queryBuilder.where("sale_invoice_transaction.transaction_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("sale_invoice_transaction.transaction_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("sale_invoice_transaction");

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("sale_invoice_transaction.paid_amount"));
                    transaction.setClear(resultSet.getBoolean("sale_invoice_transaction.is_clear"));
                    transaction.setDate(resultSet.getDate("sale_invoice_transaction.transaction_date"));
                    transaction.setDebit(true);
                    transaction.setNotes(resultSet.getString("sale_invoice_transaction.notes"));
                    transaction.setParticular("Sale: " + resultSet.getString("sales_invoice.invoice_no"));
                    transaction.setPaymentMode(resultSet.getString("account.type"));
                    transaction.setVoucherNo(resultSet.getString("sales_invoice.invoice_no"));

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

    public ArrayList<Transaction> getSupplierTransaction(Date fromDate, Date toDate) {

        ArrayList<Transaction> transactions = new ArrayList<>();
        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        Transaction transaction;

        if (database.connect()) {
            try {

                queryBuilder.select("supplier.name");
                queryBuilder.select("supplier_invoice_transaction.transaction_date, supplier_invoice_transaction.clear_on, supplier_invoice_transaction.is_clear, supplier_invoice_transaction.paid_amount, supplier_invoice_transaction.account_id, supplier_invoice_transaction.notes");
                queryBuilder.select("supplier_invoice.supplier_invoice_no, supplier_invoice.invoice_date");
                queryBuilder.select("account.title, account.id, account.type");                
                queryBuilder.innerJoin("supplier_invoice", "supplier_invoice.invoice_id = supplier_invoice_transaction.supplier_invoice_id");
                queryBuilder.innerJoin("account", "account.id = supplier_invoice_transaction.account_id");
                queryBuilder.innerJoin("supplier", "supplier.supplier_id = supplier_invoice.supplier_id");
                queryBuilder.where("supplier_invoice_transaction.transaction_date >= '" + Utilities.dateForDB(fromDate) + "'");
                queryBuilder.where("supplier_invoice_transaction.transaction_date <= '" + Utilities.dateForDB(toDate) + "'");
                queryBuilder.from("supplier_invoice_transaction");

                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("supplier_invoice_transaction.paid_amount"));
                    transaction.setClear(resultSet.getBoolean("supplier_invoice_transaction.is_clear"));
                    transaction.setDate(resultSet.getDate("supplier_invoice_transaction.transaction_date"));
                    transaction.setDebit(false);
                    transaction.setNotes(resultSet.getString("supplier_invoice_transaction.notes"));
                    transaction.setParticular("SupplierPayment: " + resultSet.getString("supplier.name"));
                    transaction.setPaymentMode(resultSet.getString("account.type"));
                    transaction.setVoucherNo(resultSet.getString("supplier_invoice.supplier_invoice_no"));

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
        Transaction transaction;

        if (database.connect()) {
            try {



                ResultSet resultSet = database.get(queryBuilder.get());

                while (resultSet.next()) {

                    transaction = new Transaction();
                    transaction.setAccountID(resultSet.getInt("account.id"));
                    transaction.setAccountTitle(resultSet.getString("account.title"));
                    transaction.setAmount(resultSet.getDouble("sale_invoice_transaction.paid_amount"));
                    transaction.setClear(resultSet.getBoolean("sale_invoice_transaction.is_clear"));
                    transaction.setDate(resultSet.getDate("sale_invoice_transaction.transaction_date"));
                    transaction.setDebit(false);
                    transaction.setNotes(resultSet.getString("general_transaction.notes"));
                    transaction.setParticular(resultSet.getString("account_head.title"));
                    transaction.setPaymentMode(resultSet.getString("general_transaction.payment_mode"));
                    transaction.setVoucherNo(resultSet.getString("general_transaction.voucher_no"));

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
}
