/*
 *  Copyright (C) 2010 Rick Charon
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package petrasys.trader;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * OrderTable1Row.java Created Aug 28, 2010 3:33:17 PM in Project PeTraSys
 *  
 * @author Rick Charon
 * 
 */
public class OrderTable1Row extends JTable {

  Order order = null;
  DefaultTableModel dtml;
  // From http://forums.netbeans.org/post-62966.html
  // idea is to put a combobox in a jTable
  String[] buySellStrings = {"BUY", "SELL"};
  JComboBox buySellCombo = new JComboBox(buySellStrings);
  //DefaultCellEditor dce = new DefaultCellEditor(parentBuySellEditor);
  String[] orderTypeString = {"LMT", "STP", "MKT", "TRAIL"};
  JComboBox orderTypeCombo = new JComboBox(orderTypeString);
  String[] TIFString = {"DAY", "GTC"};
  JComboBox TIFCombo = new JComboBox(TIFString);



  Class[] columnTypes = new Class[]{
    java.lang.Object.class, //0 "Buy/Sell" Combo
    java.lang.Integer.class, //1 "Quantity"
    java.lang.Integer.class, //2 FilledQuantity
    java.lang.Integer.class, //3 RemainingQuantity
    java.lang.Double.class, //4 LimitPrice
    java.lang.Double.class, //5 Auxprice
    java.lang.Double.class, //6 AvgFillPrice
    java.lang.Object.class, //7 OrderType, Combo
    java.lang.Object.class, //8 TIF, Combo
    java.lang.Double.class, //9 TranslatedPrice
    java.lang.Object.class, //10 BarTime
    java.lang.Double.class, //11 LossOrGain
    java.lang.String.class, //12 OCA Group
    java.lang.String.class, //13 OcaType
    java.lang.Integer.class, //14 OrderID
    java.lang.Integer.class, //15 ParentID
    java.lang.Integer.class, //16 PermID
    java.lang.Object.class, //17 EntryDateTime
    java.lang.Object.class, //18 ExecutedDateTime
    java.lang.Object.class //19 Status
  };
  boolean[] canEdit = new boolean[]{
    true, //0 "Buy/Sell" Combo
    true, //1 "Quantity"
    false, //2 FilledQuantity
    false, //3 RemainingQuantity
    true, //4 LimitPrice
    true, //5 Auxprice
    false, //6 AvgFillPrice
    true, //7 OrderType, Combo
    true, //8 TIF, Combo
    true, //9 TranslatedPrice
    true, //10 BarTime
    false, //11 LossOrGain
    true, //12 OCA Group
    false, //13 OcaType
    false, //14 OrderID
    false, //15 ParentID
    false, //16 PermID
    false, //17 EntryDateTime
    false, //18 ExecutedDateTime
    false //19 Status
  };

  public OrderTable1Row() {
    dtml = initTableModel();
    setModel(dtml);
    initTable();
    initLookAndFeel();
    getModel().addTableModelListener(this);
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    super.tableChanged(e);
    int column = e.getColumn();
    int type = e.getType();
    Object d1;
    if (type == TableModelEvent.UPDATE && column > -1) {
      d1 = dtml.getValueAt(0, column);
      int k = 3;
    }
    int delEv = TableModelEvent.DELETE;
    int insertEv = TableModelEvent.INSERT;
    int updateEv = TableModelEvent.UPDATE;
    int j = 3; //82800.4954773736
  }

//  public void tableChanged(TableModelEvent e) {
//    int row = e.getFirstRow();
//    int column = e.getColumn();
//    TableModel model = (TableModel) e.getSource();
//    String columnName = model.getColumnName(column);
//    Object data = model.getValueAt(row, column);
//    int j = 3;
//  }
  private void initLookAndFeel() {
    this.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    this.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
    this.setRowHeight(24);
    this.setRowSelectionAllowed(false);
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
    setValueAt(order.getBuySell(), 0, 0);               //0 "Buy/Sell" Combo
    setValueAt(order.getTotalQuantity(), 0, 1);         //1 "Quantity"
    setValueAt(order.getFilledQuantity(), 0, 2);        //2 FilledQuantity
    setValueAt(order.getRemainingQuantity(), 0, 3);     //3 RemainingQuantity
    setValueAt(order.getLimitPrice(), 0, 4);            //4 LimitPrice
    setValueAt(order.getAuxprice(), 0, 5);              //5 Auxprice
    setValueAt(order.getAvgFillPrice(), 0, 6);          //6 AvgFillPrice
    setValueAt(order.getOrderType(), 0, 7);             //7 OrderType, Combo
    setValueAt(order.getTif(), 0, 8);                   //8 TIF, Combo
    setValueAt(order.getTranslatedPrice(), 0, 9);       //9 TranslatedPrice
    setValueAt(order.getBarTime(), 0, 10);              //10 BarTime
    setValueAt(order.getLossOrGain(), 0, 11);           //11 LossOrGain
    setValueAt(order.getOCAGroup(), 0, 12);             //12 OCA Group
    setValueAt(order.getOcaType(), 0, 13);              //13 OcaType
    setValueAt(order.getOrderID(), 0, 14);              //14 OrderID
    setValueAt(order.getParentID(), 0, 15);             //15 ParentID
    setValueAt(order.getPermID(), 0, 16);               //16 PermID
    setValueAt(order.getEntryDateTime(), 0, 17);        //17 EntryDateTime
    setValueAt(order.getExecutedDateTime(), 0, 18);     //18 ExecutedDateTime
    setValueAt(order.getStatus(), 0, 19);               //19 Status
  }

//    ParentTradeTable.setValueAt(instrument.getActualPriceFromExpandedPrice(expandedPrice), 0, 2);
//    ParentTradeTable.setValueAt("LMT", 0, 3);
//    ParentTradeTable.setValueAt("DAY", 0, 4);
//    ParentTradeTable.setValueAt(expandedPrice, 0, 9);
//    ParentTradeTable.setValueAt(tradeBarDateTime, 0, 6);
//    ParentTradeTable.setValueAt(getNextTradeID(), 0, 7);
  private DefaultTableModel initTableModel() {
    return new javax.swing.table.DefaultTableModel(
            new Object[][]{{null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null}},
            new String[]{"Buy/Sell", "Quantity", "FilledQuantity", "RemainingQuantity", "LimitPrice", "Auxprice",
              "AvgFillPrice", "OrderType", "TIF", "TranslatedPrice", "BarTime", "LossOrGain", "OCA Group",
              "OcaType", "OrderID", "ParentID", "PermID", "EntryDateTime", "ExecutedDateTime", "Status"}) {

      @Override
      public Class getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
      }

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    };

  }





  private void initTable() {
    /** next as of 8/15/10
     *  UL = 1, Expiry = 2, BuySell = 3, TotalQuantity = 4, FilledQuantity = 5,
    RemainingQuantity = 6, LimitPrice = 7, Auxprice = 8, AvgFillPrice = 9, OrderType = 10,
    TIF = 11, TranslatedPrice = 12, BarTime = 13, LossOrGain = 14, OCAGroup = 15,
    OcaType = 16, OrderID = 17, ParentID = 18, PermID = 19, EntryDateTime = 20,
    ExecutedDateTime = 21, Status = 22
     */
    this.setRowHeight(24);
    this.setRowSelectionAllowed(false);
    //stopLossOrderScrollPane2.setViewportView(this);
    this.getColumnModel().getColumn(0).setPreferredWidth(45); //0 "Buy/Sell" Combo
    this.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(buySellCombo));
    this.getColumnModel().getColumn(1).setPreferredWidth(45); //1 "Quantity"
    this.getColumnModel().getColumn(2).setPreferredWidth(45); //2 FilledQuantity
    this.getColumnModel().getColumn(3).setPreferredWidth(45); //3 RemainingQuantity
    this.getColumnModel().getColumn(4).setPreferredWidth(75); //4 LimitPrice
    this.getColumnModel().getColumn(5).setPreferredWidth(75); //5 Auxprice
    this.getColumnModel().getColumn(6).setPreferredWidth(75); //6 AvgFillPrice
    this.getColumnModel().getColumn(7).setPreferredWidth(50); //7 OrderType, Combo
    this.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(orderTypeCombo));
    this.getColumnModel().getColumn(8).setPreferredWidth(75); //8 TIF, Combo
    this.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(TIFCombo));
    this.getColumnModel().getColumn(9).setPreferredWidth(100); //9 TranslatedPrice
    this.getColumnModel().getColumn(10).setPreferredWidth(100); //10 BarTime
    this.getColumnModel().getColumn(10).setCellRenderer(new OrderTableDateRenderer());
    this.getColumnModel().getColumn(11).setPreferredWidth(70); //11 LossOrGain
    this.getColumnModel().getColumn(12).setPreferredWidth(20); //12 OCA Group
    this.getColumnModel().getColumn(13).setPreferredWidth(0); //13 OcaType
    this.getColumnModel().getColumn(13).setMaxWidth(0);
    this.getColumnModel().getColumn(13).setMinWidth(0);
    this.getColumnModel().getColumn(14).setPreferredWidth(10); //14 OrderID
    this.getColumnModel().getColumn(15).setPreferredWidth(10); //15 ParentID
    this.getColumnModel().getColumn(16).setPreferredWidth(10); //16 PermID
    this.getColumnModel().getColumn(17).setPreferredWidth(100); //17 EntryDateTime
    this.getColumnModel().getColumn(17).setCellRenderer(new OrderTableDateRenderer());
    this.getColumnModel().getColumn(18).setPreferredWidth(0); //18 ExecutedDateTime
    this.getColumnModel().getColumn(18).setMaxWidth(0);
    this.getColumnModel().getColumn(18).setMinWidth(0);
    this.getColumnModel().getColumn(19).setPreferredWidth(40); //19 Status
    this.getTableHeader().setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    DefaultCellEditor editor = (DefaultCellEditor) this.getDefaultEditor(Integer.class);
    DefaultCellEditor editor2 = (DefaultCellEditor) this.getDefaultEditor(String.class);
    JTextField textField = (JTextField) editor.getComponent();
    textField.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    JTextField textField2 = (JTextField) editor2.getComponent();
    textField2.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
  }
}
