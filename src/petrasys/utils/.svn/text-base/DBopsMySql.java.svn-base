/*********************************************************************
 * File path:     /share/JavaDev/PeTraSys/src/petrasys/utils/DBopsMySql.java
 * Version:       
 * Description:   
 * Author:        Rick Charon <rickcharon@gmail.com>
 * Created at:    Tue Nov 16 10:43:09 2010
 * Modified at:   Tue Nov 16 11:40:24 2010
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 ********************************************************************/

package petrasys.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import petrasys.instruments.PriceBar;
import petrasys.trader.Order;
import petrasys.trader.PaperTrade;

/**
 *
 * @author rickcharon
 */
public class DBopsMySql {

  static private Connection tradesConnection = null;

  public DBopsMySql() {
  }

public static Connection setuptradesConnection() {

    try {
      Class.forName("com.mysql.jdbc.Driver");
      tradesConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Trading", "rickcharon", "1plus7is8");
    } catch (Exception ex) {
      MsgBox.err2(ex);

    } finally {
      return tradesConnection;
    }

  }
  public static CallableStatement distinctSymsProc() {
    CallableStatement ret = null;
    try {
      ret = DBopsMySql.setuptradesConnection().prepareCall("CALL distinctQuoteSymbols()");
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    } finally {
      return ret;
    }
  }

  /**
   * rpc - 3/7/10 9:37 AM - Get a dated range by symbol, only for continuous contract
   * @param sym
   * @param beginDate
   * @param endDate
   * @return
   */
  public static PreparedStatement datedRangeBySymbol(String sym, Timestamp beginDate, Timestamp endDate) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT `datetime`, `open`, `high`,`low`, `close`, "
              + "`volume` FROM `Trading`.`quotes1min`"
              + "where symbol=? and "
              + "`datetime` >= ? and "
              + "`datetime` <= ? order by `datetime`;");
      pstmt.setString(1, sym);
      pstmt.setTimestamp(2, beginDate);
      pstmt.setTimestamp(3, endDate);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  /**
   * rpc - 3/7/10 10:17 AM - Get a dated range for a symbol with a specific expiry
   * @param sym
   * @param expiry
   * @param beginDate
   * @param endDate
   * @return
   */
  public static PreparedStatement datedRangeBySymbolAndExpiry(String sym, int expiry,
          Timestamp beginDate, Timestamp endDate) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT `datetime`, `open`, `high`,`low`, `close`, "
              + "`volume` FROM `Trading`.`quotes1min`"
              + "where symbol=? and "
              + "`datetime` >= ? and "
              + "`datetime` <= ?  and expiry=? order by `datetime`;");
      pstmt.setString(1, sym);
      pstmt.setTimestamp(2, beginDate);
      pstmt.setTimestamp(3, endDate);
      pstmt.setInt(4, expiry);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement minMaxDateBySymbolAndExpiry(String symbol, int expiry) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT min(`datetime`) as minDate, max(`datetime`) as maxDate FROM `quotes1min` "
              + "WHERE `symbol`= ? and `expiry`= ?");
      pstmt.setString(1, symbol);
      pstmt.setInt(2, expiry);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  /**
   * rpc - 3/7/10 10:26 AM - This works because the last symbol in the quotes1min table is
   * assumed to be the current working expiry. If it isn't, this could be a problem,
   * @param symbol the UL
   * @return a PreparedStatement that has 1 row, 1 column, with int max(`expiry`)
   */
  public static int maxExpiryWithDataBySymbol(String symbol) {
    PreparedStatement pstmt = null;
    int expiry = 0;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT max(`expiry`) FROM `quotes1min` WHERE `symbol`= ?");
      pstmt.setString(1, symbol);
      ResultSet res = pstmt.executeQuery();
      if (res.next()) {
        expiry = res.getInt(1);
      } else {
        throw new Exception("No result set returned.");
      }
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return expiry;
    }
  }

  /**
   * rpc - 4/18/10 1:19 PM Get the min and max Date in DB by UL
   * @param sym - UL
   * @return The relevant PreparedStatement.
   */
  public static PreparedStatement minMaxDatesBySym(String sym) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement(
              "SELECT min(`datetime`), max(`datetime`)  FROM `Trading`.`quotes1min` where symbol=?");
      pstmt.setString(1, sym);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }


  public static PreparedStatement distinctSymbolInfos() {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT distinct  symbol, `exchange`, multiplier, "
              + "`priceMagnifier`, `minTick`, `fullName` "
              + "FROM `Trading`.`futuresContractDetails`");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement MultiplierAndMagnifier(String sym) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT distinct `multiplier`, `priceMagnifier` "
              + "FROM `futuresContractDetails` WHERE `symbol` = '" + sym + "'");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement exchangeBySymbolandExpiry(String symbol, int expiry) {
    PreparedStatement pstmt = null;
    try {
      pstmt = DBopsMySql.setuptradesConnection().prepareStatement("SELECT `exchange` FROM `futuresContractDetails` WHERE "
              + "`symbol`= ? and `expiry`= ?");
      pstmt.setString(1, symbol);
      pstmt.setInt(2, expiry);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static List<PriceBar> getPriceDatas(String sym, java.util.Date beginDT, java.util.Date endDT,
          int priceMagnifier, int multiplier) {
    List<PriceBar> priceDatas = new ArrayList<PriceBar>();
    try {
      PreparedStatement datedRangeBySymbol =
              DBopsMySql.datedRangeBySymbol(sym, new Timestamp(beginDT.getTime()),
              new Timestamp(endDT.getTime()));
      ResultSet res = datedRangeBySymbol.executeQuery();
      while (res.next()) {
        PriceBar priceBar = new PriceBar(
                res.getTimestamp("datetime").getTime(),
                res.getDouble("open") / priceMagnifier * multiplier,
                res.getDouble("high") / priceMagnifier * multiplier,
                res.getDouble("low") / priceMagnifier * multiplier,
                res.getDouble("close") / priceMagnifier * multiplier,
                res.getLong("volume"));
        priceDatas.add(priceBar);
      }
      //int i = 1;
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    } catch (Exception ex) {
      MsgBox.err2(ex);
    } finally {
      return priceDatas;
    }
  }

  public static PreparedStatement getExpirysForUpdate(Connection con, String ul, int beginDate, int endDate) {
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement("SELECT * FROM `Trading`.`futuresContractDetails` "
              + "where symbol='" + ul + "'and expiry  >= " + beginDate + " and expiry <= " + endDate
              + " order by expiry");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement getActiveContracts(Connection con) {
    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement("SELECT * FROM `Trading`.`futuresContractDetails` "
              + "where `active` = 1 order by symbol, expiry");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement updateBeginEndDatesForExpiry1(Connection con, String ul, int expiry,
          String beginDate, String endDate) {

    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement("update `Trading`.`futuresContractDetails` "
              + " set active=1, beginDate  =  '" + beginDate + "', endDate = '"
              + endDate + "' where symbol='" + ul + "' and expiry = " + expiry);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static PreparedStatement updateBeginEndDatesForExpiry(Connection con) {

    PreparedStatement pstmt = null;
    try {
      pstmt = con.prepareStatement("update `Trading`.`futuresContractDetails` "
              + " set active=1, beginDate  = ?, endDate = ? where symbol = ? and expiry = ?");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static String createCompressionTable(int compressionFactor) {
    String dbTableName = "quotes" + compressionFactor + "min";
    try {
      PreparedStatement createCompressionTable =
              DBopsMySql.setuptradesConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Trading`.`"
              + dbTableName
              + "` (" + "`symbol` VARCHAR( 15 ) NOT NULL , "
              + "`datetime` DATETIME NOT NULL , "
              + "`open` DOUBLE NOT NULL , "
              + "`high` DOUBLE NOT NULL , "
              + "`low` DOUBLE NOT NULL , "
              + "`close` DOUBLE NOT NULL , "
              + "`volume` BIGINT( 20 ) NOT NULL, "
              + "PRIMARY KEY(`symbol`, `datetime`))");
      createCompressionTable.execute();
      createCompressionTable.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
      dbTableName = null;
    } finally {
      return dbTableName;
    }
  }

  public static PreparedStatement insertIntoCompressionTable(String table) {
    PreparedStatement pstmt = null;
    try {
      pstmt =
              DBopsMySql.setuptradesConnection().
              prepareStatement("REPLACE INTO `" + table + "` VALUES (?, ? , ?, ?, ?, ?, ?)");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return pstmt;
    }
  }

  public static void OLDinsertIntoPaperOrdersTable(Order order) {

    PreparedStatement pstmt = null;
    try {
      pstmt =
              DBopsMySql.setuptradesConnection(). ////rpc - NOTE:8/15/10 11:35 AM - 22 PARAMS
              prepareStatement("REPLACE INTO `PaperOrders` "
              + "(`idx`,`UL`, `Expiry`, `BuySell`, `TotalQuantity`, `FilledQuantity`, "
              + "`RemainingQuantity`, `LimitPrice`, `Auxprice`, `AvgFillPrice`, `OrderType`, "
              + "`TIF`, `TranslatedPrice`, `BarTime`, `LossOrGain`, `OCAGroup`, "
              + "`OcaType`, `OrderID`, `ParentID`, `PermID`, `EntryDateTime`, "
              + "`ExecutedDateTime`, `Status`)"
              + "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ? , ?, ?,"
              + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
    }
  }

  public static void insertIntoPaperOrdersTable(Order order) {
    PreparedStatement pstmt = null;
    try {
      pstmt =
              DBopsMySql.setuptradesConnection(). ////rpc - NOTE:8/15/10 11:35 AM - 22 PARAMS
              prepareStatement("REPLACE INTO `PaperOrders` "
              + "(`idx`,`UL`, `Expiry`, `BuySell`, `TotalQuantity`, `FilledQuantity`, "
              + "`RemainingQuantity`, `LimitPrice`, `Auxprice`, `AvgFillPrice`, `OrderType`, "
              + "`TIF`, `TranslatedPrice`, `BarTime`, `LossOrGain`, `OCAGroup`, "
              + "`OcaType`, `OrderID`, `ParentID`, `PermID`, `EntryDateTime`, "
              + "`ExecutedDateTime`, `Status`)"
              + "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ? , ?, ?,"
              + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      //IDX
      if (order.getIdx() == null) {
        pstmt.setNull(1, Types.INTEGER);
      } else {
        pstmt.setInt(1, order.getIdx());
      }
      //UL
      if (order.getUl() == null) {
        pstmt.setNull(2, Types.VARCHAR);
      } else {
        pstmt.setString(2, order.getUl());
      }
      //Expiry
      if (order.getExpiry() == null) {
        pstmt.setNull(3, Types.INTEGER);
      } else {
        pstmt.setInt(3, order.getExpiry());
      }
      //BuySell
      if (order.getExpiry() == null) {
        pstmt.setNull(4, Types.VARCHAR);
      } else {
        pstmt.setString(4, order.getBuySell());
      }
      //TotalQuantity
      if (order.getTotalQuantity() == null) {
        pstmt.setNull(5, Types.INTEGER);
      } else {
        pstmt.setInt(5, order.getTotalQuantity());
      }
      //FilledQuantity
      if (order.getFilledQuantity() == null) {
        pstmt.setNull(6, Types.INTEGER);
      } else {
        pstmt.setInt(6, order.getFilledQuantity());
      }
      //RemainingQuantity
      if (order.getRemainingQuantity() == null) {
        pstmt.setNull(7, Types.INTEGER);
      } else {
        pstmt.setInt(7, order.getRemainingQuantity());
      }
      //LimitPrice
      if (order.getLimitPrice() == null) {
        pstmt.setNull(8, Types.DOUBLE);
      } else {
        pstmt.setDouble(8, order.getLimitPrice());
      }
      //AuxPrice
      if (order.getAuxprice() == null) {
        pstmt.setNull(9, Types.DOUBLE);
      } else {
        pstmt.setDouble(9, order.getAuxprice());
      }
      //AvgFillPrice
      if (order.getAvgFillPrice() == null) {
        pstmt.setNull(10, Types.DOUBLE);
      } else {
        pstmt.setDouble(10, order.getAvgFillPrice());
      }
      //OrderType
      if (order.getOrderType() == null) {
        pstmt.setNull(11, Types.VARCHAR);
      } else {
        pstmt.setString(11, order.getOrderType());
      }
      //TIF
      if (order.getTif() == null) {
        pstmt.setNull(12, Types.VARCHAR);
      } else {
        pstmt.setString(12, order.getTif());
      }
      //TranslatedPrice
      if (order.getTranslatedPrice() == null) {
        pstmt.setNull(13, Types.DOUBLE);
      } else {
        pstmt.setDouble(13, order.getTranslatedPrice());
      }
      //BarTime
      if (order.getBarTime() == null) {
        pstmt.setNull(14, Types.DATE);
      } else {
        pstmt.setTimestamp(14, (new Timestamp(order.getBarTime().getTime())));
      }
      //Loss or Gain
      if (order.getLossOrGain() == null) {
        pstmt.setNull(15, Types.DOUBLE);
      } else {
        pstmt.setDouble(15, order.getLossOrGain());
      }
      //OCAGroup
      if (order.getOCAGroup() == null) {
        pstmt.setNull(16, Types.VARCHAR);
      } else {
        pstmt.setString(16, order.getOCAGroup());
      }
      //OCAType
      if (order.getOcaType() == null) {
        pstmt.setNull(17, Types.SMALLINT);
      } else {
        pstmt.setShort(17, order.getOcaType());
      }
      // OrderID
      if (order.getOrderID() == null) {
        pstmt.setNull(18, Types.INTEGER);
      } else {
        pstmt.setInt(18, order.getOrderID());
      }
      // ParentID
      if (order.getParentID() == null) {
        pstmt.setNull(19, Types.INTEGER);
      } else {
        pstmt.setInt(19, order.getParentID());
      }
      // PermID
      if (order.getPermID() == null) {
        pstmt.setNull(20, Types.INTEGER);
      } else {
        pstmt.setInt(20, order.getPermID());
      }
      //EntryDateTime
      if (order.getEntryDateTime() == null) {
        pstmt.setNull(21, Types.DATE);
      } else {
        pstmt.setTimestamp(21, (new Timestamp(order.getEntryDateTime().getTime())));
      }
      //ExecutedDateTime
      if (order.getExecutedDateTime() == null) {
        pstmt.setNull(22, Types.DATE);
      } else {
        pstmt.setTimestamp(22, (new Timestamp(order.getExecutedDateTime().getTime())));
      }
      //Status
      if (order.getStatus() == null) {
        pstmt.setNull(23, Types.VARCHAR);
      } else {
        pstmt.setString(23, order.getStatus());
      }
      pstmt.execute();
      pstmt.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    } finally {
    }
  }

  public static void insertIntoPaperTradesTable(PaperTrade paperTrade) {
    PreparedStatement pstmt = null;
    try {
      pstmt =
              DBopsMySql.setuptradesConnection(). 
              prepareStatement("INSERT INTO `PaperTrades` "
              + "(`id`, `EnteredInDB`, `BeginTradeDateTime`, `symbol`, `Position`, "
              + "`entry`, `stop loss`, `stop risk`, `Stop profit`, "
              + "`profitpotential`, `Outcome`, `ExitTradeDateTime`) "
              + "VALUES (NULL, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
      //BeginTradeDateTime
      if (paperTrade.getBeginTradeDateTime() == null) {
        pstmt.setNull(1, Types.DATE);
      } else {
        pstmt.setTimestamp(1, paperTrade.getBeginTradeTimestamp());
      }
      //symbol
      if (paperTrade.getSymbol() == null) {
        pstmt.setNull(2, Types.VARCHAR);
      } else {
        pstmt.setString(2, paperTrade.getSymbol());
      }
      //Position
      if (paperTrade.getPosition() == null) {
        pstmt.setNull(3, Types.VARCHAR);
      } else {
        pstmt.setString(3, paperTrade.getPosition());
      }
      //entry
      pstmt.setDouble(4, paperTrade.getEntry());
      //stop loss
      pstmt.setDouble(5, paperTrade.getStopLoss());
      //stop risk
      pstmt.setDouble(6, paperTrade.getStopRisk());
      //Stop profit
      pstmt.setDouble(7, paperTrade.getProfitStop());
      //profitpotential
      pstmt.setDouble(8, paperTrade.getProfitpotential());
      //Outcome
      pstmt.setDouble(9, paperTrade.getOutcome());
      //ExitTradeDateTime
      pstmt.setTimestamp(10, paperTrade.getExitTradeTimestamp());
      pstmt.execute();
      pstmt.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    } finally {
    }
  }

  public static int getNextPaperOrderID() {
    int id = -1;
    try {
      PreparedStatement pstmt =
              DBopsMySql.setuptradesConnection().
              prepareStatement("SELECT max(`OrderID`) FROM `PaperOrders`");
      ResultSet res = pstmt.executeQuery();
      res.next(); //To get a lastexpiry for loop, so should be one extra early expiry
      id = res.getInt(1);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, ex.getMessage(), "SQLException", JOptionPane.ERROR_MESSAGE);
    } finally {
      return (id + 1);
    }
  }

  public static void main(String[] args) {
    try {
      Connection con = DBopsMySql.setuptradesConnection();
      con.setAutoCommit(false);
      String ul = "AUD";
      PreparedStatement pstmt = DBopsMySql.getExpirysForUpdate(con, ul, 20090300, 20100700);
      PreparedStatement upDateStmt = DBopsMySql.updateBeginEndDatesForExpiry(con);
      ResultSet res = pstmt.executeQuery();
      res.next(); //To get a lastexpiry for loop, so should be one extra early expiry
      int lastexp = res.getInt("expiry");
      while (res.next()) {
        int exp = res.getInt("expiry");
        String bdate = DateOps.dbShortFormatString(lastexp - 5);
        String edate = DateOps.dbShortFormatString(exp - 6);
        upDateStmt.setString(1, bdate);
        if (!res.isLast()) {
          upDateStmt.setString(2, edate);
        }
        upDateStmt.setString(3, ul);
        upDateStmt.setInt(4, exp);
        upDateStmt.addBatch();
        lastexp = exp;
      }
      int[] updateCounts = upDateStmt.executeBatch();
      upDateStmt.close();
      con.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }


  }
}
////rpc - NOTE:3/1/10 3:08 PM -  - Possible additions for DBops
//PreparedStatement stmtForQuotes = quotes1minConnection.prepareStatement(
//                "INSERT INTO `" + tabName + "` VALUES (?, ? , ?, ?, ?, ?, ?)");
//PreparedStatement stmtForResults =
//              conn.prepareStatement("SELECT distinct `symbol` FROM `futuresContractDetails`");
//stmtForResults = conn.prepareStatement(
//                "select * from `futuresContractDetails` where `symbol`='" + curl
//                + "' and `expiry` > " + strYearAgo
//                + " and `expiry` <= (SELECT min(`expiry`) FROM `futuresContractDetails` "
//                + "WHERE `symbol`='GBP' and `expiry` > " + str8out + ") order by `expiry`");
//  CallableStatement datedRangeBySymbolProc;
//  datedRangeBySymbolProc = quotes1minConnection.prepareCall("CALL datedRangeBySymbol(?, ?, ?)");
//  CallableStatement minMaxDatesProc;
//int retval = minMaxDatesProc.executeUpdate();
//Timestamp minD = minMaxDatesProc.getTimestamp(2);
//Timestamp maxD = minMaxDatesProc.getTimestamp(3);
//Timestamp ts = minMaxDatesProc.getTimestamp(1);
//ts = minMaxDatesProc.getTimestamp(2);

