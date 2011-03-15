/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rickcharon 
 * Basic Date and Calendar Operations
 */
public class DateOps {

  public static final SimpleDateFormat backwardDateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yy zzz");
  public static final SimpleDateFormat expiryFormat = new SimpleDateFormat("yyyyMMdd");
  public static final SimpleDateFormat strFormat = new SimpleDateFormat("MM/dd/yy hh:mm");
  public static final SimpleDateFormat fileFormat = new SimpleDateFormat("MM-dd-yyyy-HHmmss");
  public static final SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final SimpleDateFormat dbShortFormat = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat stdShortFormat = new SimpleDateFormat("MM/dd/yy");

  /**
   * rpc - 2/24/10 1:07 PM
   * @return String representation of now in "MM/dd/yy hh:mm" format
   */
  public static String nowPrettyString() {
    return DateOps.strFormat.format(new Date());
  }

  public static String prettyString(Date dateIn) {
    return DateOps.strFormat.format(dateIn);
  }

  /**
   * rpc - 2/24/10 1:06 PM
   * @return String representation of now in format suitable for file naming, MM-dd-yyyy-HHmmss
   */
  public static String nowFileNameString() {
    return DateOps.fileFormat.format(new Date());
  }

  public static String fileFormatString(Date dateIn) {
    return DateOps.fileFormat.format(dateIn);
  }

  public static String dbFormatString(Date dateIn) {
    return DateOps.dbFormat.format(dateIn);
  }

  public static Date dateFromDbFormatString(String strIn) {
    Date date = null;
    try {
      date = dbFormat.parse(strIn);
    } catch (ParseException ex) {
      MsgBox.err2(ex);
    } finally {
      return date;
    }
  }

  public static Date dateFromStdShortFormatString(String strIn) {
    Date date = null;
    try {
      date = stdShortFormat.parse(strIn);
    } catch (ParseException ex) {
      MsgBox.err2(ex);
    } finally {
      return date;
    }
  }

  /**
   *
   * @param strIn
   * @return
   */
  public static Date dateFromExpiryFormatString(String strIn) {
    Date date = null;
    try {
      date = expiryFormat.parse(strIn);
    } catch (ParseException ex) {
      MsgBox.err2(ex);
    } finally {
      return date;
    }
  }

  /**
   * rpc - 2/24/10 1:04 PM
   * @param expiry - a java.util.Date that is the expiry date
   * @return a String representation of the date in IB-TWS expiry format, yyyyMMdd.
   */
  public static String expiryFormatString(Date expiry) {
    return DateOps.expiryFormat.format(expiry);
  }

  public static String dbShortFormatString(int intDate) {
    String dOut = null;
    try {
      Date d1 = DateOps.expiryFormat.parse(Integer.toString(intDate));
      dOut = DateOps.dbShortFormat.format(d1);
    } catch (ParseException ex) {
      MsgBox.err2(ex);
    } finally {
      return dOut;
    }
  }

  /**
   *
   * @param expiry Date of expiry
   * @return int representation of expiry date in IB-TWS format yyyyMMdd.
   */
  public static int expiryFormatInt(Date expiry) {
    return Integer.parseInt(DateOps.expiryFormat.format(expiry));
  }

  /**
   * rpc - 2/24/10 1:05 PM
   * @return The date Now in IB-TWS expiry format, yyyyMMdd.
   */
  public static String expiryNowFormatString() {
    return DateOps.expiryFormat.format(new Date());
  }

  /**
   * rpc - 2/24/10 12:53 PM 
   * @param dateIn - the java.util.date that will have days added or subtracted
   * @param howManyDays - Number of days to add or subract, negative int subracts
   * @return a Date set to the desired date
   * @see Date
   * @see Calendar
   */
  public static Date addOrSubractDaysFromDate(Date dateIn, int howManyDays) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(dateIn);
    cal.add(Calendar.DATE, howManyDays);
    return (Date) cal.getTime();
  }

  public static void main(String[] args) {
    Calendar c1 = Calendar.getInstance(), c2 = Calendar.getInstance();
    c1.setTime(new Date());
    c2.setTime(new Date());
    c2.add(Calendar.MONTH, 1);
    if (c1.after(c2)) {
      int i = 1;
    }
    int j = 2;
  }
}
