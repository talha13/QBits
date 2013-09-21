/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import qbits.common.Message;
import qbits.db.MySQLDatabase;
import qbits.db.QueryBuilder;
import qbits.gui.common.UIParentFrame;

/**
 *
 * @author Pipilika
 */
public class AutoEmployeeSalary {

    private UIParentFrame parentFrame;

    public AutoEmployeeSalary(UIParentFrame frame) {
        parentFrame = frame;
    }

    public void salaryAsReceivable() {
    }

    public void salaryAsReceivable(int year, int month) {

        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();

        queryBuilder.select("employee.employee_id, ");
        queryBuilder.from("employee");
        queryBuilder.where("DATEDIFF(employee.joining_date, CURDATE()) > 30");

        if (database.connect()) {

            ResultSet resultSet = database.get(queryBuilder.get());

            try {
                while (resultSet.next()) {
                }
            } catch (SQLException ex) {
                Logger.getLogger(AutoEmployeeSalary.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
        }
    }

    private void assingSalary(int employeeID, int month, int year) {

        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();

        if (database.connect()) {

            database.setAutoCommit(false);

            if (isSalaryAssigned(employeeID, month, year) == 0) {

                queryBuilder.set("employee_id", "" + employeeID);
                queryBuilder.set("year", "" + year);
                queryBuilder.set("month", "" + month);
                queryBuilder.set("", null);

            }

            database.disconnect();

        } else {
            Message.dbConnectFailed();
        }

    }

    private int isSalaryAssigned(int employeeID, int month, int year) {

        MySQLDatabase database = new MySQLDatabase();
        QueryBuilder queryBuilder = new QueryBuilder();
        int isAssigned = -1;

        queryBuilder.select("id");
        queryBuilder.from("employee_salary_receivable");
        queryBuilder.where("employee_id = " + employeeID);
        queryBuilder.where("month = " + month);
        queryBuilder.where("year = " + year);

        if (database.connect()) {

            ResultSet resultSet = database.get(queryBuilder.get());

            try {
                if (resultSet.next()) {
                    isAssigned = 1;
                } else {
                    isAssigned = 0;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AutoEmployeeSalary.class.getName()).log(Level.SEVERE, null, ex);
                isAssigned = -1;
            } finally {
                database.disconnect();
            }

        } else {
            Message.dbConnectFailed();
            isAssigned = -1;
        }

        return isAssigned;
    }
}
