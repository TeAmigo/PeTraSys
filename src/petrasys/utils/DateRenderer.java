/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * // rpc - 2/18/10 10:42 AM This is from
 * http://java.sun.com/docs/books/tutorial/uiswing/components/table.html#editrender
 * with quite a few modifications...
 */


public class DateRenderer extends DefaultTableCellRenderer {


    SimpleDateFormat formatter;
    public DateRenderer() { super(); }

  public DateRenderer(SimpleDateFormat formatter) {
    this.formatter = formatter;
  }



  @Override
    public void setValue(Object value) {
        if (formatter==null) {
            formatter = new SimpleDateFormat();
        }
        setText((value == null) ? "" : formatter.format(value));
    }
}
