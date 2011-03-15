/*
 * // rpc - 2/12/10 4:00 PM
 * This is to calculate the drawdown, using a close as the beginning, running forward in time,
 * and finding the nearest, if any, drawdown of the amount specified,
 */
package petrasys.indicators.support;

//import com.jsystemtrader.platform.quote.PriceBar;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import petrasys.indicators.support.Indicator;
import petrasys.indicators.support.DrawDownCalcDlg;
import petrasys.instruments.PriceBar;
import petrasys.instruments.PriceBars;

/**
 *
 * @author rickcharon
 * rpc - 3/7/10 5:33 AM - This idea of storing data in a database directly from here is ok for an
 * "exporation", but doesn't make sense for a forward/backtest, or live trading environment
 * I can imagine running an exploration of this, then devising a trading strategy based on an
 * analysis of the results, and using that to put values in the PriceBar.
 */
public class DrawDownCalc1 extends Indicator {

  private double allowableDrawDown;
  private boolean goLong; //short is false;

  private class resultRow {

    public Date dateTime;
    public double largestDrawDown;
    public Date dateOfLargestDrawdown;
    public double highestPriceBeforeDrawdown;
    public double largestGain;
    public Date dateOfHighestPrice;
  }
  private List<resultRow> resultRows;

  public boolean isGoLong() {
    return goLong;
  }

  public void setGoLong(boolean goLong) {
    this.goLong = goLong;
  }

  public double getAllowableDrawDown() {
    return allowableDrawDown;
  }

  public void setAllowableDrawDown(double allowableDrawDown) {
    this.allowableDrawDown = allowableDrawDown;
  }

  public DrawDownCalc1(PriceBars pbs) {
    super(pbs);
    resultRows = new ArrayList<resultRow>(pbs.size());
  }

  private void findDrawDownForLong(int idx) {
    PriceBar activeBar = priceBars.getDataAt(idx);
    resultRow result = this.new resultRow();

    result.dateTime = new Date(activeBar.getDate());
    result.largestDrawDown = 0.0;
    result.dateOfLargestDrawdown = new Date(0L);
    Double beginPrice = activeBar.getClose();
    result.highestPriceBeforeDrawdown = beginPrice;
    result.largestGain = 0;
    result.dateOfHighestPrice = new Date(activeBar.getDate());


    for (int idx2 = idx + 1; idx2 < priceBars.size(); idx2++) {
      if (priceBars.getDataAt(idx2).getLow() <= result.highestPriceBeforeDrawdown - allowableDrawDown) {
        result.largestDrawDown = result.highestPriceBeforeDrawdown - priceBars.getDataAt(idx2).getLow();
        result.dateOfLargestDrawdown.setTime(priceBars.getDataAt(idx2).getDate());
        resultRows.add(idx, result);
        return;
      }
      if (priceBars.getDataAt(idx2).getHigh() > result.highestPriceBeforeDrawdown) {
        result.highestPriceBeforeDrawdown = priceBars.getDataAt(idx2).getHigh();
        result.dateOfHighestPrice.setTime(priceBars.getDataAt(idx2).getDate());
      }
      if ((result.highestPriceBeforeDrawdown - priceBars.getDataAt(idx2).getLow()) > result.largestDrawDown) {
        result.largestDrawDown = result.highestPriceBeforeDrawdown - priceBars.getDataAt(idx2).getLow();
        result.dateOfLargestDrawdown.setTime(priceBars.getDataAt(idx2).getDate());
      }
      if (priceBars.getDataAt(idx2).getHigh() - beginPrice > result.largestGain) {
        result.largestGain = priceBars.getDataAt(idx2).getHigh() - beginPrice;
      }

    }
    // Never hit the specified DrawDown,
    resultRows.add(idx, result);
  }

  private void findDrawDownForShort(int idx) {
    PriceBar activeBar = priceBars.getDataAt(idx);
    double maxPosExcursion = activeBar.getClose();
    //for(int idx2 = idx + 1)
  }
  private Connection tradesConnection;
  private PreparedStatement createDrawDownCalcTable;
  private PreparedStatement insertIntoDrawDownCalcTable;
  private PreparedStatement stmtForResults;

  public void setuptradesConnection() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (java.lang.ClassNotFoundException e) {
      System.err.print("ClassNotFoundException: ");
      System.err.println(e.getMessage());
    }
    try {
      tradesConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Trading",
              "rickcharon", "1plus7is8");
      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy-hhmmss");
      String tabName = "DrawDownCalc-" + priceBars.getSymbol() + formatter.format(priceBars.getBeginDate()) + "-"
              + formatter.format(priceBars.getEndDate());
      createDrawDownCalcTable =
              tradesConnection.prepareStatement(
              "CREATE TABLE IF NOT EXISTS `Trading`.`" + tabName + "` ("
              + "`symbol` VARCHAR( 15 ) NOT NULL , "
              + "`dt` DATETIME NOT NULL , "
              + "`largestDrawDown` DOUBLE NOT NULL , "
              + "`dateOfLargestDrawdown` DATETIME NOT NULL , "
              + "`highestPriceBeforeDrawdown` DOUBLE NOT NULL , "
              + "`largestGain` DOUBLE NOT NULL , "
              + "`dateOfHighestPrice` DATETIME NOT NULL , "
              + "`open` DOUBLE NOT NULL , "
              + "`high` DOUBLE NOT NULL , "
              + "`low` DOUBLE NOT NULL , "
              + "`close` DOUBLE NOT NULL , "
              + "`volume` BIGINT( 20 ) NOT NULL, "
              + "PRIMARY KEY(`symbol`, `dt`))");
      createDrawDownCalcTable.execute();
      createDrawDownCalcTable.close();
      stmtForResults = tradesConnection.prepareStatement(
              "REPLACE INTO `" + tabName + "` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    } catch (SQLException sqlex) {
      System.err.println("SQLException: " + sqlex.getMessage());
    }

  }


  private void resultsToDB() {
    setuptradesConnection();
    int size = priceBars.size();
    PriceBar priceBar;
    try {
      for (int idx = 0; idx < size; idx++) {

        priceBar = priceBars.getDataAt(idx);
        java.sql.Timestamp dateIn = new java.sql.Timestamp(priceBar.getDate());
        //java.sql.Date dateIn = new java.sql.Date(udatein);
        stmtForResults.setString(1, priceBars.getSymbol());
        stmtForResults.setTimestamp(2, dateIn);
        stmtForResults.setDouble(3, resultRows.get(idx).largestDrawDown);
        stmtForResults.setTimestamp(4, new java.sql.Timestamp(resultRows.get(idx).dateOfLargestDrawdown.getTime()));
        stmtForResults.setDouble(5, resultRows.get(idx).highestPriceBeforeDrawdown);
        stmtForResults.setDouble(6, resultRows.get(idx).largestGain);
        stmtForResults.setTimestamp(7, new java.sql.Timestamp(resultRows.get(idx).dateOfHighestPrice.getTime()));
        stmtForResults.setDouble(8, priceBar.getOpen());
        stmtForResults.setDouble(9, priceBar.getHigh());
        stmtForResults.setDouble(10, priceBar.getLow());
        stmtForResults.setDouble(11, priceBar.getClose());
        stmtForResults.setLong(12, priceBar.getVolume());
        stmtForResults.addBatch();
      }
      int[] updateCounts = stmtForResults.executeBatch();
      stmtForResults.close();
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    }

  }

  @Override
  public void run() {
//    DrawDownCalcDlg dd = new DrawDownCalcDlg(null, true, this, priceBars.getSymbol(),
//            priceBars.getBeginDate(), priceBars.getEndDate());
//    dd.setVisible(true);


    for (int idx = 0; idx
            < priceBars.size(); idx++) {
      if (goLong) {
        findDrawDownForLong(idx);


      } else {
        findDrawDownForShort(idx);


      }
      int i = 1;


    }
    resultsToDB();

  }
}
