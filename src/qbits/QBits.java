/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qbits;

import javax.swing.UIManager;
import qbits.gui.common.UILogin;

/**
 *
 * @author Topu
 */
public class QBits {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            UILogin login = new UILogin(null, true);
            login.showWindow();
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}
