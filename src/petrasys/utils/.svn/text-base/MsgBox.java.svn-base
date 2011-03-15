/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.utils;

import javax.swing.JOptionPane;

/**
 *
 * @author rickcharon
 */
public class MsgBox {

  /**
   * //rpc - NOTE:3/1/10 3:08 PM -  Typical Call:
   * String title = ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName() + "() ";
   *MsgBox.err(title, ex.getMessage());
   * @param msg that goes in the box
   * @param title of the box
   */
  public static void err(Object msg, String title) {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * rpc - 3/4/10 4:53 PM - Use this to create a standard info message box
   * @param msg
   * @param title
   */
  public static void info(String msg, String title) {
    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
  }


  /**
   * 
   * typical call
   * catch (Exception ex) {
   *    MsgBox.err2(ex);
   * @param ex Exception
   */
  public static void err2(Exception ex) {
    String body = ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName() + "() ";
    JOptionPane.showMessageDialog(null, body, ex.getMessage(), JOptionPane.ERROR_MESSAGE);
  }
}
