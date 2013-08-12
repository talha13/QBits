/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.configuration;

import java.util.Date;

/**
 *
 * @author Topu
 */
public class Configuration {

    public final static String DATABASE_NAME = "qbits";
    public final static String DATABASE_USER_NAME = "root";
    public final static String DATABASE_PASSWORD = "";
    public final static String WITHDRAW = "Withdraw";
    public final static String DEPOSITE = "Deposit";
    public final static String PRINCIPAL_INCOME = "Income";
    public final static String PRINCIPAL_EXPENSE = "Expense";
    public final static String CURRENCY_FORMAT_DEFAULT = "###,##,##,###.##";
    public final static String DATE_FORMAT_DEFAULT = "dd-MM-yyyy";
    public final static String DATE_FORMAT_DB = "yyyy-MM-dd";
    public final static String APP_TITLE = "QBits";
    public final static int FRAME_WIDTH = 970;
    public final static int FRAME_HEIGHT = 670;
    public final static Date DATE_START = new Date(24L * 60L * 60L * 1000);
    public final static Date DATE_END = new Date(24L * 60L * 60L * 1000 * 1000000);
    public final static String MYSQL_BIN_LOCATION = "E:/xampp/mysql/bin/"; // must ends with '/'
    public final static String COPYRIGHT_TEXT = "QBits";
    public final static double VAT = 5.0;
}
