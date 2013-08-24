/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.entity;

/**
 *
 * @author Pipilika
 */
public class Employee extends Person {

    private Salary salary;

    public Employee() {
        salary = new Salary();
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
