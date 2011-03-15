/*
 * //rpc - NOTE:9/14/10 1:54 PM -
   Moved the traderFrame.form to /share/PetrasyBackup, nice for design but gets in the way.

 * TraderFrame.java
 *
 * Created on Jul 11, 2010, 12:05:13 PM
 */
package petrasys.trader;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import petrasys.charts.ADXChart;
import petrasys.charts.ADXChartPanel;
import petrasys.connections.AccountUpdates;
import petrasys.connections.MySocket;
import petrasys.instruments.Instrument;
import petrasys.instruments.PriceBar;
import petrasys.utils.DBops;

/**
 *
 *
 * @author rickcharon
 * //rpc - WORKING HERE:3/5/10 2:33 PM - Trader - Responsible for executing the trades,
 * either to a local protfolio, or to an actual portfolio
 * at IB.
 */
public class TraderFrame extends javax.swing.JFrame {

  Instrument instrument;
  boolean portfolioUpdate = false;
  boolean marketData = false;
  final String portfolioUpdateActive = "PortfolioUdpate (is on)";
  final String portfolioUpdateInactive = "PortfolioUdpate (is off)";
  AccountUpdates accountUpdates = new AccountUpdates();
  DefaultTableCellRenderer dtc;
  MySocket socket = null;
  boolean paperTradeActive = false;
  //DefaultTableModel dtml;
  Double stopLossAmount = 200.00;
  Double profitStopAmount = 500.00;

  public TraderFrame() {
    //dtml = initDtm1();
    initComponents();
  }

  /** Creates new form TraderFrame */
  public TraderFrame(Instrument instrumentIn) {
    instrument = instrumentIn;
    //dtml = initDtm1();
    initComponents();
    setTitle("Trading - " + instrument.getFullName() + "  expiry: " + instrument.getExpiry());
    setLocationToBottom();
  }

  public Double getProfitStopAmount() {
    return profitStopAmount;
  }

  public void setProfitStopAmount(Double profitStopAmount) {
    this.profitStopAmount = profitStopAmount;
  }

  public Double getStopLossAmount() {
    return stopLossAmount;
  }

  public void setStopLossAmount(Double stopLossAmount) {
    this.stopLossAmount = stopLossAmount;
  }



  public void setLocationToBottom() {
    Dimension screen = getToolkit().getScreenSize();
    int height = getSize().height;
    setLocation(100, (screen.getSize().height - height + 50));
  }

  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    buttonPanel = new javax.swing.JPanel();
    ExecuteTradeButton = new javax.swing.JButton();
    paperTradeButton = new javax.swing.JButton();
    openOrdersButton = new javax.swing.JButton();
    executedOrdersButton = new javax.swing.JButton();
    currentPortfolioButton = new javax.swing.JButton();
    linesToChartButton = new javax.swing.JButton();
    parentOrderScrollPane1 = new javax.swing.JScrollPane();
    ParentTradeTable = new OrderTable1Row();
    stopLossOrderScrollPane2 = new javax.swing.JScrollPane();
    StopLossTable = new OrderTable1Row();
    profitStopOrderScrollPane3 = new javax.swing.JScrollPane();
    ProfitStopTable = new OrderTable1Row();

    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    getContentPane().setLayout(new java.awt.GridBagLayout());

    buttonPanel.setMinimumSize(new java.awt.Dimension(1042, 32));
    buttonPanel.setPreferredSize(new java.awt.Dimension(1042, 32));
    buttonPanel.setRequestFocusEnabled(false);
    buttonPanel.setLayout(new java.awt.GridLayout(1, 0));

    ExecuteTradeButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    ExecuteTradeButton.setText("Execute - Trade");
    ExecuteTradeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ExecuteTradeButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(ExecuteTradeButton);

    paperTradeButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    paperTradeButton.setText("Execute Paper Trade");
    paperTradeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        paperTradeButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(paperTradeButton);

    openOrdersButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    openOrdersButton.setText("Open Orders");
    buttonPanel.add(openOrdersButton);

    executedOrdersButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    executedOrdersButton.setText("Executed Orders");
    buttonPanel.add(executedOrdersButton);

    currentPortfolioButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    currentPortfolioButton.setText("Current Portfolio");
    currentPortfolioButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        currentPortfolioButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(currentPortfolioButton);

    linesToChartButton.setText("Send Lines to Chart");
    linesToChartButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        linesToChartButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(linesToChartButton);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 3174;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    getContentPane().add(buttonPanel, gridBagConstraints);

    parentOrderScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parent Order", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N
    parentOrderScrollPane1.setMinimumSize(new java.awt.Dimension(1042, 48));
    parentOrderScrollPane1.setPreferredSize(new java.awt.Dimension(1042, 48));
    parentOrderScrollPane1.setViewportView(ParentTradeTable);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 4188;
    gridBagConstraints.ipady = 32;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    getContentPane().add(parentOrderScrollPane1, gridBagConstraints);

    stopLossOrderScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stop Loss Order", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N
    stopLossOrderScrollPane2.setMinimumSize(new java.awt.Dimension(1042, 48));
    stopLossOrderScrollPane2.setPreferredSize(new java.awt.Dimension(1042, 48));

    stopLossOrderScrollPane2.setViewportView(StopLossTable);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 4200;
    gridBagConstraints.ipady = 32;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    getContentPane().add(stopLossOrderScrollPane2, gridBagConstraints);

    profitStopOrderScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profit Stop Order", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 18))); // NOI18N
    profitStopOrderScrollPane3.setMinimumSize(new java.awt.Dimension(1042, 48));
    profitStopOrderScrollPane3.setPreferredSize(new java.awt.Dimension(1042, 48));
    profitStopOrderScrollPane3.setVerifyInputWhenFocusTarget(false);
    profitStopOrderScrollPane3.setViewportView(ProfitStopTable);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 4200;
    gridBagConstraints.ipady = 32;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    getContentPane().add(profitStopOrderScrollPane3, gridBagConstraints);

    pack();
  }

  
  String[] buySellStrings = {"BUY", "SELL"};
  JComboBox buySellCombo = new JComboBox(buySellStrings);
  //DefaultCellEditor dce = new DefaultCellEditor(parentBuySellEditor);
  String[] orderTypeString = {"LMT", "STP", "MKT", "TRAIL"};
  JComboBox orderTypeCombo = new JComboBox(orderTypeString);
  String[] TIFString = {"DAY", "GTC"};
  JComboBox TIFCombo = new JComboBox(orderTypeString);

  private void currentPortfolioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentPortfolioButtonActionPerformed
    if (!portfolioUpdate) {
      currentPortfolioButton.setText(portfolioUpdateActive);
    }
    AccountUpdates au = new AccountUpdates();
    Thread auThread = new Thread(au);
    auThread.run();
  }//GEN-LAST:event_currentPortfolioButtonActionPerformed

  private void ExecuteTradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecuteTradeButtonActionPerformed
//    String buyOrSell = (String) ParentTradeTable.getValueAt(0, 0);
//    Integer quantity = (Integer) ParentTradeTable.getValueAt(0, 1);
//    Date dd = new Date();
//    //initTradeTable(ParentTradeTable);
//    ParentTradeTable.setValueAt(dd, 0, 6);
//    TableCellEditor tce = ParentTradeTable.getDefaultEditor(Integer.class);
    setLocationToBottom();
    int j = 3;
  }//GEN-LAST:event_ExecuteTradeButtonActionPerformed

//  private void paperTradeActionHelper(JTable tradeTable, int tradeID, int parentID,
//          Timestamp timeStamp, boolean executed) {
//    try {
//      //rpc - NOTE:7/20/10 11:34 AM - The order of params in JTable:
//      //    int BuySell 0, int Quantity 1, double Price 2, String OrderType 3, String TIF 4,
//      //    double TranslatedPrice 5,Timestamp BarTime 6, Double LossOrGain 7, String OCA Group 8,
//      //    Integer OrderID 9, Integer ParentID 10,
//
//
//      /** next as of 8/15/10
//       *  UL = 1, Expiry = 2, BuySell = 3, TotalQuantity = 4, FilledQuantity = 5,
//      RemainingQuantity = 6, LimitPrice = 7, Auxprice = 8, AvgFillPrice = 9, OrderType = 10,
//      TIF = 11, TranslatedPrice = 12, BarTime = 13, LossOrGain = 14, OCAGroup = 15,
//      OcaType = 16, OrderID = 17, ParentID = 18, PermID = 19, EntryDateTime = 20,
//      ExecutedDateTime = 21, Status = 22
//       */
//      PreparedStatement stmtForResults = DBops.insertIntoPaperOrdersTable();
//      stmtForResults.setString(1, instrument.getUl()); //UL
//      stmtForResults.setInt(2, instrument.getExpiry()); //Expiry
//      stmtForResults.setString(3, (tradeTable.getValueAt(0, 0)).toString()); //BuySell
//      stmtForResults.setInt(4, (Integer) (tradeTable.getValueAt(0, 1))); //Quantity
//      stmtForResults.setDouble(5, (Double) (tradeTable.getValueAt(0, 2))); //Price
//      stmtForResults.setString(6, (tradeTable.getValueAt(0, 3)).toString()); //OrderType
//      stmtForResults.setString(7, (tradeTable.getValueAt(0, 4)).toString()); //TIF
//      stmtForResults.setDouble(8, (Double) (tradeTable.getValueAt(0, 5))); //TranslatedPrice
//      Timestamp barTimeStamp = new Timestamp(((Date) (tradeTable.getValueAt(0, 6))).getTime());
//      stmtForResults.setTimestamp(9, barTimeStamp);  //BarTime
//      stmtForResults.setDouble(10, (Double) (tradeTable.getValueAt(0, 7))); //Loss or Gain
//      if (tradeTable.getValueAt(0, 8) == null) {
//        stmtForResults.setNull(11, Types.VARCHAR);
//      } else {
//        stmtForResults.setString(11, (tradeTable.getValueAt(0, 7)).toString()); // OCAGroup
//      }
//
//      stmtForResults.setInt(12, (Integer) tradeID); // OrderID
//      stmtForResults.setInt(13, (Integer) parentID); // ParentID
//
//      stmtForResults.setTimestamp(14, timeStamp); //EntryDateTime
//      stmtForResults.setBoolean(15, executed); //Executed
//      stmtForResults.execute();
//      stmtForResults.close();
//    } catch (SQLException ex) {
//      MsgBox.err2(ex);
//    }
//  }

  private void playItForward() {
    PaperTrade pt = new PaperTrade();
    pt.setSymbol(instrument.getShortName());
    pt.setBeginTradeDateTime(((OrderTable1Row) ParentTradeTable).getOrder().getBarTime());
    pt.setEntry(((OrderTable1Row) ParentTradeTable).getOrder().getTranslatedPrice());
    pt.setStopLoss(((OrderTable1Row) StopLossTable).getOrder().getTranslatedPrice());
    pt.setProfitStop(((OrderTable1Row) ProfitStopTable).getOrder().getTranslatedPrice());
    pt.setPosition(((OrderTable1Row) ParentTradeTable).getOrder().getBuySell());
    pt.setStopRisk((((OrderTable1Row) StopLossTable).getOrder().getLossOrGain()));
    pt.setProfitpotential(((OrderTable1Row) ProfitStopTable).getOrder().getLossOrGain());
//    Date tradeDate = ((OrderTable1Row) ParentTradeTable).getOrder().getBarTime();
//    Double tradePrice = ((OrderTable1Row) ParentTradeTable).getOrder().getTranslatedPrice();
//    Double stopPrice = ((OrderTable1Row) StopLossTable).getOrder().getTranslatedPrice();
//    Double profitPrice = ((OrderTable1Row) ProfitStopTable).getOrder().getTranslatedPrice();
//    Double result;
//    if(((OrderTable1Row) ParentTradeTable).getOrder().getBuySell().equals("BUY")) {
//      result = instrument.getMinutePriceBars().PlayItForward(tradeDate, profitPrice, stopPrice);
//    }

    instrument.getMinutePriceBars().PlayItForward(pt);
    Date beginDate = pt.getBeginTradeDateTime();
    Date endDate = pt.getExitTradeDateTime();
    paperTradeActive = false;
    paperTradeButton.setText("Execute Paper Trade");
    DBops.insertIntoPaperTradesTable(pt);
    int j = 3;
  }


  private void paperTradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paperTradeButtonActionPerformed
    if(paperTradeActive) {
      playItForward();
      return;
    }
    int orderID = getNextPaperTradeID();
    int parentID = orderID;
    Timestamp timeStamp = new Timestamp((new Date()).getTime());
    ((OrderTable1Row) ParentTradeTable).getOrder().setOrderID(orderID);
    ((OrderTable1Row) ParentTradeTable).getOrder().setParentID(parentID);
    ((OrderTable1Row) ParentTradeTable).getOrder().setExecutedDateTime(timeStamp);
    ((OrderTable1Row) ParentTradeTable).getOrder().setEntryDateTime(timeStamp);

    orderID += 1;
    ((OrderTable1Row) StopLossTable).getOrder().setOrderID(orderID);
    ((OrderTable1Row) StopLossTable).getOrder().setParentID(parentID);
    ((OrderTable1Row) StopLossTable).getOrder().setExecutedDateTime(timeStamp);
    ((OrderTable1Row) StopLossTable).getOrder().setEntryDateTime(timeStamp);

    orderID += 1;
    ((OrderTable1Row) ProfitStopTable).getOrder().setOrderID(orderID);
    ((OrderTable1Row) ProfitStopTable).getOrder().setParentID(parentID);
    ((OrderTable1Row) ProfitStopTable).getOrder().setExecutedDateTime(timeStamp);
    ((OrderTable1Row) ProfitStopTable).getOrder().setEntryDateTime(timeStamp);

    DBops.insertIntoPaperOrdersTable(((OrderTable1Row) ParentTradeTable).getOrder());
    DBops.insertIntoPaperOrdersTable(((OrderTable1Row) StopLossTable).getOrder());
    DBops.insertIntoPaperOrdersTable(((OrderTable1Row) ProfitStopTable).getOrder());
    paperTradeButton.setText("Play It Forward...");
    paperTradeActive = true;
  }//GEN-LAST:event_paperTradeButtonActionPerformed
  //test line

  //rpc - WORKING HERE:7/26/10 1:25 PM - linesToChartButtonActionPerformed
  private void linesToChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linesToChartButtonActionPerformed
    for (ADXChart chart : instrument.getCharts()) {
      ADXChartPanel panel = chart.getChartPanel();
      chart.getChartPanel().setupHorizontalPrefixedCrosshair((Double) (ParentTradeTable.getValueAt(0, 9)),
              ParentTradeTable.getValueAt(0, 0).toString());
      chart.getChartPanel().setupHorizontalPrefixedCrosshair((Double) (StopLossTable.getValueAt(0, 9)),
              StopLossTable.getValueAt(0, 0).toString() + "-STOPLOSS");
      chart.getChartPanel().setupHorizontalPrefixedCrosshair((Double) (ProfitStopTable.getValueAt(0, 9)),
              ProfitStopTable.getValueAt(0, 0).toString() + "-PROFIT");

    }
  }//GEN-LAST:event_linesToChartButtonActionPerformed

  //rpc - WORKING HERE:7/22/10 2:47 PM - Return TWS nextvalidID, or get next available from DB
  public int getNextTradeID() {
    int id = -1;
    if (!(socket == null)) {
      // Get id for a live trade
    }
    return id;
  }

  public int getNextPaperTradeID() {
    return DBops.getNextPaperOrderID();
  }

  public void instigateBuyTrade(double tradeDateTime, double expandedPrice) {
    Date tradeBarDateTime = new Date((long) tradeDateTime);
    Order parentOrder = new Order();
    parentOrder.setUl(instrument.getUl());
    parentOrder.setExpiry(instrument.getExpiry());
    parentOrder.setBuySell("BUY");
    parentOrder.setTotalQuantity(1);
    parentOrder.setLimitPrice(instrument.getActualPriceFromExpandedPrice(expandedPrice));
    parentOrder.setOrderType("LMT");
    parentOrder.setTif("DAY");
    parentOrder.setTranslatedPrice(expandedPrice);
    parentOrder.setBarTime(tradeBarDateTime);
    parentOrder.setStatus("Filled");
    ((OrderTable1Row) ParentTradeTable).setOrder(parentOrder);

    Order stopLossOrder = new Order();
    stopLossOrder.setUl(instrument.getUl());
    stopLossOrder.setExpiry(instrument.getExpiry());
    stopLossOrder.setBuySell("SELL");
    double expandedStopLossPrice = expandedPrice - stopLossAmount;
    stopLossOrder.setAuxprice(instrument.getActualPriceFromExpandedPrice(expandedStopLossPrice));
    stopLossOrder.setOrderType("STP");
    stopLossOrder.setTif("GTC");
    stopLossOrder.setTranslatedPrice(expandedStopLossPrice);
    stopLossOrder.setBarTime(tradeBarDateTime);
    stopLossOrder.setLossOrGain(expandedStopLossPrice - expandedPrice);
    stopLossOrder.setStatus("Submitted");
    ((OrderTable1Row) StopLossTable).setOrder(stopLossOrder);

    Order profitStopOrder = new Order();
    profitStopOrder.setUl(instrument.getUl());
    profitStopOrder.setExpiry(instrument.getExpiry());
    profitStopOrder.setBuySell("SELL");
    double expandedProfitStopPrice = expandedPrice + profitStopAmount;
    profitStopOrder.setLimitPrice(instrument.getActualPriceFromExpandedPrice(expandedProfitStopPrice));
    profitStopOrder.setOrderType("LMT");
    profitStopOrder.setTif("GTC");
    profitStopOrder.setTranslatedPrice(expandedProfitStopPrice);
    profitStopOrder.setBarTime(tradeBarDateTime);
    profitStopOrder.setLossOrGain(expandedProfitStopPrice - expandedPrice);
    profitStopOrder.setStatus("Submitted");
    ((OrderTable1Row) ProfitStopTable).setOrder(profitStopOrder);
    linesToChartButtonActionPerformed(null);
  }

  public void instigateSellTrade(double tradeDateTime, double expandedPrice) {
    Date tradeBarDateTime = new Date((long) tradeDateTime);
    Order parentOrder = new Order();
    parentOrder.setUl(instrument.getUl());
    parentOrder.setExpiry(instrument.getExpiry());
    parentOrder.setBuySell("SELL");
    parentOrder.setTotalQuantity(1);
    parentOrder.setLimitPrice(instrument.getActualPriceFromExpandedPrice(expandedPrice));
    parentOrder.setOrderType("LMT");
    parentOrder.setTif("DAY");
    parentOrder.setTranslatedPrice(expandedPrice);
    parentOrder.setBarTime(tradeBarDateTime);
    parentOrder.setStatus("Filled");
    ((OrderTable1Row) ParentTradeTable).setOrder(parentOrder);

    Order stopLossOrder = new Order();
    stopLossOrder.setUl(instrument.getUl());
    stopLossOrder.setExpiry(instrument.getExpiry());
    stopLossOrder.setBuySell("BUY");
    double expandedStopLossPrice = expandedPrice + stopLossAmount;
    stopLossOrder.setAuxprice(instrument.getActualPriceFromExpandedPrice(expandedStopLossPrice));
    stopLossOrder.setOrderType("STP");
    stopLossOrder.setTif("GTC");
    stopLossOrder.setTranslatedPrice(expandedStopLossPrice);
    stopLossOrder.setBarTime(tradeBarDateTime);
    stopLossOrder.setLossOrGain(expandedPrice - expandedStopLossPrice);
    stopLossOrder.setStatus("Submitted");
    ((OrderTable1Row) StopLossTable).setOrder(stopLossOrder);

    Order profitStopOrder = new Order();
    profitStopOrder.setUl(instrument.getUl());
    profitStopOrder.setExpiry(instrument.getExpiry());
    profitStopOrder.setBuySell("BUY");
    double expandedProfitStopPrice = expandedPrice - profitStopAmount;
    profitStopOrder.setLimitPrice(instrument.getActualPriceFromExpandedPrice(expandedProfitStopPrice));
    profitStopOrder.setOrderType("LMT");
    profitStopOrder.setTif("GTC");
    profitStopOrder.setTranslatedPrice(expandedProfitStopPrice);
    profitStopOrder.setBarTime(tradeBarDateTime);
    profitStopOrder.setLossOrGain(expandedPrice - expandedProfitStopPrice);
    profitStopOrder.setStatus("Submitted");
    ((OrderTable1Row) ProfitStopTable).setOrder(profitStopOrder);
    linesToChartButtonActionPerformed(null);
   }


  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new TraderFrame().setVisible(true);
      }
    });
  }

  //rpc - WORKING HERE:7/22/10 3:28 PM - Need a listener for openOrders 
  private void ConnectToTWS() {
    //socket = IBConnectionManager.connect(histFromTWS);
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton ExecuteTradeButton;
  private javax.swing.JTable ParentTradeTable;
  private javax.swing.JTable ProfitStopTable;
  private javax.swing.JTable StopLossTable;
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton currentPortfolioButton;
  private javax.swing.JButton executedOrdersButton;
  private javax.swing.JButton linesToChartButton;
  private javax.swing.JButton openOrdersButton;
  private javax.swing.JButton paperTradeButton;
  private javax.swing.JScrollPane parentOrderScrollPane1;
  private javax.swing.JScrollPane profitStopOrderScrollPane3;
  private javax.swing.JScrollPane stopLossOrderScrollPane2;
  // End of variables declaration//GEN-END:variables
}
