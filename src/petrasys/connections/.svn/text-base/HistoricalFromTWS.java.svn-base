/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.connections;

import petrasys.utils.MsgBox;
import petrasys.utils.IBWrapperAdapter;
import petrasys.instruments.PriceBar;
import petrasys.PeTraSys;

/**
 *
 * @author rickcharon
 */
public class HistoricalFromTWS extends IBWrapperAdapter  {

    HistoricalPriceDataDownloader myMate;

    /**
     *
     * @return
     */
    public HistoricalPriceDataDownloader getMyMate() {
    return myMate;
  }

    public void setMyMate(HistoricalPriceDataDownloader myMate) {
    this.myMate = myMate;
  }

  @Override
  public void error(String str) {
    PeTraSys.writeToReport("Error in HistoricalFromTWS: " + str);
  }




    /**
     *
     */
    public HistoricalFromTWS() {
    
  }

    @Override
    public void historicalData(int reqId, String date, double open, double high,
            double low, double close, int volume, int count, double WAP, boolean hasGaps) {
      //PeTraSys.writeToReport("In historicalData in historicalFromTWS");
      try {
        //QuoteHistory qh = traderAssistant.getStrategy(reqId).getQuoteHistory();
        if (date.startsWith("finished")) {
          myMate.getQh().setIsHistRequestCompleted(true);
          String msg = myMate.getQh().getStrategyName() + ": Historical data request finished. ";
          msg += "Bars returned:  " + myMate.getQh().size();
          PeTraSys.writeToReport(msg);
          //eventReport.report(msg);
          //synchronized (this) {
          //isPendingHistRequest = false;
          //rpc - 3/2/10 5:25 PM - Getting ILLEGAL MONITOR STATE with notifyAll
          myMate.releaseGuard();
          //notifyAll();
          //}
        } else {
          //Strategy strategy = traderAssistant.getStrategy(reqId);
          //long priceBarDate = (Long.parseLong(date) + strategy.getBarSize().toSeconds()) * 1000;
          // rpc - 2/7/10 5:08 PM Above was adding a bar, mistake.
          long priceBarDate = Long.parseLong(date) * 1000;
          PriceBar priceBar = new PriceBar(priceBarDate, open, high, low, close, volume);
          myMate.getQh().addHistoricalPriceBar(priceBar);
//        if (priceBarDate <= System.currentTimeMillis()) { //is the bar completed?
//          strategy.validateIndicators();
//        }
        }
      } catch (Exception t) {
        // Do not allow exceptions come back to the socket -- it will cause disconnects
        MsgBox.err2(t);
        //eventReport.report(t);
      }
    }

//    @Override
//    public void run() {
//      try {
//        while (true) {
//          PeTraSys.writeToReport("In run in historicalFromTWS");
//          //wait();
//          int i = 1;
//        }
//
//      } catch (Exception ex) {
//        MsgBox.err2(ex);
//      }
//    }
  }