/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbitserp.common;

import javax.swing.JOptionPane;

/**
 *
 * @author Topu
 */
public class Message {

    public static void dbConnectFailed() {
        JOptionPane.showMessageDialog(null, "Unabe to connect with database");
    }

    public static int confirm(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }

    public static int warning(String message) {
        return confirm(message, "Warning");
    }
}

enum Gender {

    MALE, FEMALE;
}
