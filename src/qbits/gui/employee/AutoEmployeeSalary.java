/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.employee;

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

    public void salaryAsReceivable(int year, String month) {

        String query;

        query = "SELECT employee.employee_id,"
                + " person.person_id, person.first_name, person.last_name, person.gender, person.date_of_birth, person.contact_no,"
                + " address.address_id, address.address, address.city, address.district,"
                + " employee.salary_id, employee_salary.basic, employee_salary.house_rent_allowance, employee_salary.medical_allowance, employee_salary.conveyance_allowance, employee_salary.income_tax, employee_salary.provident_fund"
                + " FROM employee"
                + " INNER JOIN person ON person.person_id = employee.person_id"
                + " INNER JOIN address ON address.address_id = person.address_id"
                + " INNER JOIN employee_salary ON employee_salary.employee_salary_id = employee.salary_id"
                + " WHERE DATEDIFF(employee.joining_date, CURDATE()) > 30";

    }
}
