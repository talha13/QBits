/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.gui.common.crud;

import javax.swing.JTable;

/**
 *
 * @author Topu
 */
public interface CRUDListener {

    public void removeRecord(int recordID);

    public void printRecords();

    public void updateRecord(int recordID);

    public void addRecord();
}
