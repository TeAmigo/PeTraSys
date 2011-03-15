/*
 * //rpc - NOTE:7/12/10 7:33 AM - This is for getting account info,
 */
package petrasys.connections;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import petrasys.ContractInfoDialog;
import petrasys.PeTraSys;
import petrasys.utils.DBops;
import petrasys.utils.IBWrapperAdapter;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 * rpc - 7/12/10 11:32 AM - Copined the ContractInfos class, use it as a template to create
 * the AccountUpdates class.
 */
public class AccountUpdates extends IBWrapperAdapter implements Runnable {

  private MySocket socket;
  private ContractInfoDialog contractInfoDlg;
  private Statement mySqlStmtForContractDetails;
  private Connection mySqlConnectionForAccountUpdates;
  private Contract contract;
  int orderID;
  int contractCount;

  public AccountUpdates() {
    contractCount = 0;
  }

  public void setParams(ContractInfoDialog cidIn, Contract contractIn) {
    contractInfoDlg = cidIn;
    contract = contractIn;
    orderID = IBConnectionManager.getTickerId();
  }

  public void requestAccountUpdates(){
    socket = IBConnectionManager.connect(this);
    socket.reqAccountUpdates(true, "DU49172"); //DU49172 is paper trading, u490561 is real money
  }

  public void setUpdateAccountConnection() {
    try {
      mySqlConnectionForAccountUpdates = DBops.setuptradesConnection();
      mySqlStmtForContractDetails = mySqlConnectionForAccountUpdates.createStatement();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }

  }

  @Override
  public void updateAccountTime(String timeStamp) {
    PeTraSys.writeToReport("AccountUpdate.updateAccountTime timeStamp: "  + timeStamp);
  }

  @Override
  public void updateAccountValue(String key, String value, String currency, String accountName) {
    PeTraSys.writeToReport("AccountUpdate.updateAccountValue key: " + key + ", value: " +
            value + ", currency: " + currency + ", accountName: " + accountName);
  }

  @Override
  public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue,
          double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
    PeTraSys.writeToReport("AccountUpdate.updatePortfolio contract: " + printContract(contract) + ", position: " +
            position + ", marketPrice: " + marketPrice + ", marketValue: " + marketValue +
            ", averageCost: " + averageCost + ", unrealizedPNL: " + unrealizedPNL + ",realizedPNL: " +
            realizedPNL + ", accountName: " + accountName);
  }

public String printContract(Contract cin) {
  String retval = "Symbol: " + cin.m_symbol + "\n" + "Expiry: " + cin.m_expiry;
  return retval;
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
              + "(`symbol`, `expiry`,`multiplier`,`priceMagnifier`, `exchange`,`minTick`,`fullName`)"
              + " values( '" + ct.m_symbol + "', " + Integer.parseInt(ct.m_expiry.trim())
              + ", " + ct.m_multiplier + ", " + priceMag + ", '" + ct.m_exchange + "', " + cds.m_minTick + ", '"
              + cds.m_longName + "')";
      mySqlStmtForContractDetails.executeUpdate(insStr);
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
      mySqlStmtForContractDetails.close();
      mySqlConnectionForAccountUpdates.close();
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    }
  }

  public void run() {
    try {
      setUpdateAccountConnection();
      requestAccountUpdates();
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }
  //MsgBox.info("ContractInfos", "Not Implemented");
}


