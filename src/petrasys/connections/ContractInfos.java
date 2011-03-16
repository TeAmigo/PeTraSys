/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.connections;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import petrasys.ContractInfoDialog;
import petrasys.utils.DBops;
import petrasys.utils.IBWrapperAdapter;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class ContractInfos extends IBWrapperAdapter implements Runnable {

  private MySocket socket;
  private ContractInfoDialog contractInfoDlg;
  private Statement pgStmtForContractDetails;
  private Connection pgConnectionForContractDetails;
  private Contract contract;
  int orderID;
  int contractCount;

  public ContractInfos() {
    contractCount = 0;
  }

  public void setParams(ContractInfoDialog cidIn, Contract contractIn) {
    contractInfoDlg = cidIn;
    contract = contractIn;
    orderID = IBConnectionManager.getTickerId();
  }

  public void requestContractDetails() {
    socket = IBConnectionManager.connect(this);
    socket.reqContractDetails(orderID, contract);
  }

  public void setContractDetailsConnection() {
    try {
      pgConnectionForContractDetails = DBops.setuptradesConnection();
      pgStmtForContractDetails = pgConnectionForContractDetails.createStatement();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }

  }

  /**
   * @param reqId
   * @param cds
   */
  @Override
  public void contractDetails(int reqId, ContractDetails cds) {
    try {
      Contract ct = cds.m_summary;
      int priceMag = cds.m_priceMagnifier;
      int expir = Integer.parseInt(ct.m_expiry.trim());
      String insStr = "insert into futuresContractDetails "
              + "(symbol, expiry,multiplier,priceMagnifier, exchange,minTick,fullName)"
              + " values( '" + ct.m_symbol + "', " + Integer.parseInt(ct.m_expiry.trim())
              + ", " + ct.m_multiplier + ", " + priceMag + ", '" + ct.m_exchange + "', " + cds.m_minTick + ", '"
              + cds.m_longName + "')";
      pgStmtForContractDetails.executeUpdate(insStr);
      contractCount++;
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }
  }

  /**
   *
   * @param reqId
   */
  @Override
  public void contractDetailsEnd(int reqId) {
    contractInfoDlg.setVisible(false);
    JOptionPane.showMessageDialog(null, contractCount + " Contracts Processed.");
    contractInfoDlg.dispose();
    socket.disConnect();
    try {
      pgStmtForContractDetails.close();
      pgConnectionForContractDetails.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }
  }

  public void run() {
    try {
      setContractDetailsConnection();
      requestContractDetails();
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }
  //MsgBox.info("ContractInfos", "Not Implemented");
}


