/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits.configuration;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Topu
 */
public class Utilities {
    
    public static String round(double thisValue) {

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(thisValue);
    }

    public static String now() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        return sdf.format(cal.getTime());
    }
    
    public static String nowDate() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    public static String nowTime() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
        return sdf.format(cal.getTime());
    }

    public static String getFormattedNumber(String pattern, double value) {

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    public static String getFormattedNumber(double value) {
        return Utilities.getFormattedNumber(Configuration.CURRENCY_FORMAT_DEFAULT, value);
    }

    public static boolean isValidString(String value) {


        if (value == null) {
            return false;
        }

        if (value.trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOnlyNumber(String value) {

        if (value == null) {
            return false;
        }

        value = value.trim();

        if (value.length() == 0) {
            return false;
        }

        StringTokenizer stringTokenizer = new StringTokenizer(value, "0123456789.");
        return !stringTokenizer.hasMoreTokens();
    }

    public static Calendar getDateChosserDate(Date cDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cDate);
        return calendar;
    }

    public static String dateForDB(Date cDate) {
        return Utilities.getFormattedDate(cDate, Configuration.DATE_FORMAT_DB);
    }

    public static String getFormattedDate(Date cDate) {
        return Utilities.getFormattedDate(cDate, Configuration.DATE_FORMAT_DEFAULT);
    }

    public static String getFormattedDate(Date cDate, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(cDate);
    }

    public static String getMD5(String plainText) {

        String hashtext = "";

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plainText.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hashtext;
    }
}
