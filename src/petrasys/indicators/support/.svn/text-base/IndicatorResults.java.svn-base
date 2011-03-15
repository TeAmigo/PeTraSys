/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.indicators.support;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import petrasys.instruments.Instrument;
import petrasys.utils.DBops;
import petrasys.utils.DateOps;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class IndicatorResults {

  private Instrument instrument;
  private String ul;
  private int expiry;
  private PreparedStatement createIndicatorTable;
  private PreparedStatement stmtForResults;
  private IndicatorResultsAggregator indicatorResultsAggregate;

  public IndicatorResults(Instrument instrument) {
    this.instrument = instrument;
    indicatorResultsAggregate = new IndicatorResultsAggregator(instrument);
  }

  private void createTableAndInsertStmnt() {
//   ArrayList<String> names = new ArrayList<String>();
//    for (Indicator ind : instrument.getIndicators()) {
//      for (IndicatorValue indVal : ind.getIndicatorValues().values()) {
//        names.add(indVal.getName());
//      }
//    }
    SortedSet<String> names = indicatorResultsAggregate.getOrderedKeys();
    String dbTableName = instrument.getUl();
    String columnDefs = "";
    String valuesStmnt = "` VALUES (?, ?";
    for (String name : names) {
      dbTableName += "-" + name;
      columnDefs += "`" + name + "`" + " DOUBLE NOT NULL, ";
      valuesStmnt += ", ?";
    }
    valuesStmnt += ")";
    if(dbTableName.length() > 42) {
      dbTableName = dbTableName.substring(0, 41);
    }
    dbTableName += "-" + DateOps.fileFormatString(new Date());
    try {
      createIndicatorTable =
              DBops.setuptradesConnection().prepareStatement(
              "CREATE TABLE IF NOT EXISTS `Trading`.`" + dbTableName + "` ("
              + "`symbol` VARCHAR( 15 ) NOT NULL , "
              + "`dt` DATETIME NOT NULL , "
              + columnDefs
              + "PRIMARY KEY(`symbol`, `dt`))");
      createIndicatorTable.execute();
      createIndicatorTable.close();
      stmtForResults = DBops.setuptradesConnection().prepareStatement(
              "REPLACE INTO `" + dbTableName + valuesStmnt);
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  public void toDB() {
    try {
      createTableAndInsertStmnt();
      String instName = instrument.getFullName();
      String instUl = instrument.getUl();

      indicatorResultsAggregate.getEntriesForDate(expiry);
      Iterator iter = indicatorResultsAggregate.getIteratorForIndicatorValues();
      while (iter.hasNext()) {

        Map.Entry pairs = (Map.Entry) iter.next();
        Long dateUp = (Long) pairs.getKey();
        ArrayList<Double> vals = indicatorResultsAggregate.getEntriesForDate(dateUp);
        stmtForResults.setString(1, instUl);
        stmtForResults.setTimestamp(2, new java.sql.Timestamp(dateUp));
        int nextVal = 3;
        for (Double val : vals) {
          stmtForResults.setDouble(nextVal, val);
          nextVal++;
        }
        stmtForResults.addBatch();
      }
      int[] updateCounts = stmtForResults.executeBatch();
      stmtForResults.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }

    for (Indicator ind : instrument.getIndicators()) {
      for (IndicatorValue indVal : ind.getIndicatorValues().values()) {
        String nm = indVal.getName();
        Iterator iter2 = indVal.getIvMap().entrySet().iterator();


        while (iter2.hasNext()) {
          Map.Entry pairs = (Map.Entry) iter2.next();
          System.out.println(pairs.getKey() + " = " + pairs.getValue());

        }
      }
    }
  }
}
