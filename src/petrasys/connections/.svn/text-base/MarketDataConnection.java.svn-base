
package petrasys.connections;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import java.util.Observable;
import java.util.Observer;
import petrasys.PeTraSys;
import petrasys.instruments.Instrument;
import petrasys.utils.IBWrapperAdapter;

/**
 *
 * @author rickcharon - to establish a Market Data connection, snapshot is just one take,
 * rather than continuous stream of info,
 */
// rpc - TODO:2/23/10 9:50 AM -  2 way communication all here
public class MarketDataConnection extends IBWrapperAdapter implements Runnable, Observer {

  private MySocket socket;
  private int serverVersion;
  private int tickerId;
  private Contract contract;
  private String genericTicklist;
  private boolean snapshot;
  private Instrument instrument;

  /**
   *
   * @param instrument
   * @param genericTicklist
   * @param snapshot
   */
  public MarketDataConnection(Instrument instrument, String genericTicklist, boolean snapshot) {
    this.tickerId = instrument.getTickerId();
    this.contract = instrument.getContract();
    this.genericTicklist = genericTicklist;
    this.snapshot = snapshot;
    this.instrument = instrument;
  }




  /**
   *
   * @param tickerId
   * @param contract
   * @param genericTicklist
   * @param snapshot
   */
  public MarketDataConnection(int tickerId, Contract contract, String genericTicklist, boolean snapshot) {
    this.tickerId = tickerId;
    this.contract = contract;
    this.genericTicklist = genericTicklist;
    this.snapshot = snapshot;
  }

  /**
   *
   */
  public void connect() {

//    socket = new EClientSocket(this);
//
//    if (!socket.isConnected()) {
//      socket.eConnect(IBConnectionManager.getHost(), IBConnectionManager.getPort(), IBConnectionManager.getClientId());
//    }
//    if (!socket.isConnected()) {
//      PeTraSys.writeToReport("MarketDataConnection - Could not connect to TWS.");
//    }
//
//    // IB Log levels: 1=SYSTEM 2=ERROR 3=WARNING 4=INFORMATION 5=DETAIL
//    socket.setServerLogLevel(2);
//    socket.reqNewsBulletins(true);
    socket = IBConnectionManager.connect(this);
    serverVersion = socket.serverVersion();

    //eventReport.report("Connected to TWS");

  }

  /**
   *
   */
  public void reqMktData() {
    // rpc - 2/26/10 4:03 PM connect() must be done first!
    socket.reqMktData(tickerId, contract, genericTicklist, snapshot);
  }

  /**
   *
   * @param tickerId
   * @param tickType
   * @param basisPoints
   * @param formattedBasisPoints
   * @param impliedFuture
   * @param holdDays
   * @param futureExpiry
   * @param dividendImpact
   * @param dividendsToExpiry
   */
  @Override
  public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints,
          double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
    super.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture, holdDays, futureExpiry,
            dividendImpact, dividendsToExpiry);
  }

  /**
   *
   * @param tickerId
   * @param tickType
   * @param value
   */
  @Override
  public void tickGeneric(int tickerId, int tickType, double value) {
    super.tickGeneric(tickerId, tickType, value);
  }

  /**
   *
   * @param tickerId
   * @param field
   * @param impliedVol
   * @param delta
   * @param modelPrice
   * @param pvDividend
   */
  @Override
  public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double modelPrice,
          double pvDividend) {
    super.tickOptionComputation(tickerId, field, impliedVol, delta, modelPrice, pvDividend);
  }

  /**
   * rpc - 2/26/10 3:43 PM
   * @param tickerId - The ticker Id that was specified previously in the call to reqMktData()
   * @param field -
   * Specifies the type of price. Pass the field value into TickType.getField(int tickType) to retrieve
   * the field description.  For example, a field value of 1 will map to bidPrice, a field value of 2
   * will map to askPrice, etc. 1 = bid, 2 = ask, 4 = last, 6 = high, 7 = low, 9 = close
   * @param price - Specifies the price for the specified field
   * @param canAutoExecute
   */
  @Override
  public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
    //super.tickPrice(tickerId, field, price, canAutoExecute);
    if(field == 4) { //last
      // rpc - TODO:2/27/10 1:12 PM Implement
      int i = 4;
    }
  }

  /**
   *
   * @param tickerId
   * @param field
   * @param size
   */
  @Override
  public void tickSize(int tickerId, int field, int size) {
    super.tickSize(tickerId, field, size);
  }

  /**
   *
   * @param reqId
   */
  @Override
  public void tickSnapshotEnd(int reqId) {
    super.tickSnapshotEnd(reqId);
  }

  /**
   *
   * @param tickerId
   * @param tickType
   * @param value
   */
  @Override
  public void tickString(int tickerId, int tickType, String value) {
    super.tickString(tickerId, tickType, value);
  }



  @Override
  public void run() {
    connect();
    reqMktData();
    // rpc - TODO:2/26/10 4:15 PM LEFT OFF HERE - need to do this during mkt hrs.
  }

  @Override
  public void update(Observable o, Object arg) {
    throw new UnsupportedOperationException("Not supported yet.");
  }


}
