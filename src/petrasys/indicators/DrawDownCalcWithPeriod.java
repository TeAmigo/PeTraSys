/*
 * // rpc - 2/12/10 4:00 PM
 * This is to calculate the drawdown, using a close as the beginning, running forward in time,
 * and finding the nearest, if any, drawdown of the amount specified,
 * This is a forward looking indicator
 */
package petrasys.indicators;

//import com.jsystemtrader.platform.quote.PriceBar;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import petrasys.PeTraSys;
import petrasys.indicators.support.Indicator;
import petrasys.indicators.support.DrawDownCalcDlg;
import petrasys.instruments.PriceBar;
import petrasys.instruments.PriceBars;
import petrasys.utils.DBops;

/**
 *
 * @author rickcharon
 */
public class DrawDownCalcWithPeriod extends Indicator {

  private double allowableDrawDown;
  private boolean goLong; //short is false;
  private String dbTableName;
  private int periodsLookBack;

  private class resultRow {

    public Date dateTime;
    public double largestDrawDown;
    public Date dateOfLargestDrawdown;
    public double highestPriceBeforeDrawdown;
    public double largestGain;
    public Date dateOfHighestPrice;
  }
  private ArrayList<resultRow> resultRows;

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

  public DrawDownCalcWithPeriod() {
    resultRows = new ArrayList<resultRow>(100);
  }

  /**
   * rpc - 3/4/10 3:46 PM These are typically instantiated from
   * PeTraSysTopFrame1.runExplorationButtonActionPerformed()
   * @param pbs
   */
  public DrawDownCalcWithPeriod(PriceBars pbs) {
    super(pbs);
    resultRows = new ArrayList<resultRow>(pbs.size());
  }

  private void findDrawDownForLong(int idx) {
    PriceBar activeBar = priceBars.getDataAt(idx);
    PriceBar beginBar = priceBars.getDataAt(idx - periodsLookBack);
    resultRow result = this.new resultRow();

    result.dateTime = new Date(activeBar.getDate());
    result.largestDrawDown = 0.0;
    result.dateOfLargestDrawdown = new Date(0L);
    Double beginPrice = beginBar.getClose();
    result.highestPriceBeforeDrawdown = beginPrice;
    result.largestGain = 0;
    result.dateOfHighestPrice = new Date(beginBar.getDate());

    //rpc - NOTE:3/15/10 2:12 PM - Need a +1, because the periodsLookBack.close is
    //the buy-in, so all calculations have to be after the close on periodsLookBack
    for (int idx2 = idx - periodsLookBack + 1; idx2 < idx; idx2++) {

      double drawDownCandidate = result.highestPriceBeforeDrawdown - priceBars.getDataAt(idx2).getLow();

      //Calculate largestDrawDown and dateOfLargestDrawdown RETURN if this drawdown
      // meets or exceeds allowableDrawDown if trading, this is EXIT from trade.
      if (priceBars.getDataAt(idx2).getLow() <= (result.highestPriceBeforeDrawdown - allowableDrawDown)) {
        result.largestDrawDown = drawDownCandidate;
        result.dateOfLargestDrawdown.setTime(priceBars.getDataAt(idx2).getDate());
        resultRows.add(idx, result);
        return;
      }

      //A problem with using time segment bars is we don't know whether the low or high
      //came first, so calculating drawdown over a period itself is impossible.
      if ((result.highestPriceBeforeDrawdown - priceBars.getDataAt(idx2).getLow()) > result.largestDrawDown) {
        result.largestDrawDown = drawDownCandidate;
        result.dateOfLargestDrawdown.setTime(priceBars.getDataAt(idx2).getDate());
      }

      //Calculate highestPriceBeforeDrawdown and dateOfHighestPrice
      if (priceBars.getDataAt(idx2).getHigh() > result.highestPriceBeforeDrawdown) {
        result.highestPriceBeforeDrawdown = priceBars.getDataAt(idx2).getHigh();
        result.dateOfHighestPrice.setTime(priceBars.getDataAt(idx2).getDate());
      }

      //Calculate the largestGain so far.
      if (priceBars.getDataAt(idx2).getHigh() - beginPrice > result.largestGain) {
        result.largestGain = priceBars.getDataAt(idx2).getHigh() - beginPrice;
      }

    }
    // Never hit the specified DrawDown,
    resultRows.add(idx, result);
  }

  private resultRow nullRow() {
    resultRow result = new resultRow();
    result.dateTime = new Date(0L);
    result.largestDrawDown = 0.0;
    result.dateOfLargestDrawdown = new Date(0L);
    Double beginPrice = 0.0;
    result.highestPriceBeforeDrawdown = 0.0;
    result.largestGain = 0;
    result.dateOfHighestPrice = new Date(0L);
    return result;
  }

  private void findDrawDownForShort(int idx) {
    PriceBar activeBar = priceBars.getDataAt(idx);
    double maxPosExcursion = activeBar.getClose();
    //for(int idx2 = idx + 1)
  }


  private PreparedStatement createDrawDownCalcTable;
  private PreparedStatement insertIntoDrawDownCalcTable;
  private PreparedStatement stmtForResults;

  public void setuptradesConnection() {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy-hhmmss");
      dbTableName = "DrawDownCalc-" + priceBars.getSymbol() + formatter.format(priceBars.getBeginDate()) + "-"
              + formatter.format(priceBars.getEndDate());
      createDrawDownCalcTable =
              DBops.setuptradesConnection().prepareStatement(
              "CREATE TABLE IF NOT EXISTS `Trading`.`" + dbTableName + "` ("
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
      stmtForResults = DBops.setuptradesConnection().prepareStatement(
              "REPLACE INTO `" + dbTableName + "` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    } catch (SQLException sqlex) {
      System.err.println("SQLException: " + sqlex.getMessage());
    }

  }

  private void resultsToDB() {
    PeTraSys.writeToReport("creating table for DB");
    setuptradesConnection();
    PeTraSys.writeToReport(dbTableName + "created on DB");

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
      PeTraSys.writeToReport(dbTableName + " " + size + " rows written");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    }

  }

  @Override
  public void run() {
    DrawDownCalcDlg dd = new DrawDownCalcDlg(null, true, priceBars.getSymbol(),
            priceBars.getBeginDate(), priceBars.getEndDate());
    dd.setVisible(true);
    allowableDrawDown = dd.allowableDrawdown();
    goLong = dd.goLong();
    periodsLookBack = dd.periodsLookBack();
    dd.dispose();

    resultRows.ensureCapacity(priceBars.size());
    for(int idx = 0; idx < periodsLookBack; idx++) {
      resultRows.add(idx, nullRow());
    }
    //rpc - WORKING HERE:3/8/10 5:03 PM - LEFT OFF - need to do time interval rather than periods,
    for (int idx = periodsLookBack; idx < priceBars.size(); idx++) {
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
