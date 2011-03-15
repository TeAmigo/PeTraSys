/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.runners;

import java.util.ArrayList;
import petrasys.PeTraSys;
import petrasys.indicators.support.Indicator;
import petrasys.instruments.Instrument;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class BacktestRunner extends Runner {

  



  public BacktestRunner(Instrument instrument) {
    super(instrument);
    PeTraSys.getTopFrame().setBacktestInstrumentLabelWithID(instrument.getFullName(), instrument.getTickerId());
    PeTraSys.getTopFrame().getBackTestTextArea().append("Beginning Backtest of " + instrument.getFullName());
    setupIndicators();
    int indNum = instrument.getIndicators().size();
    Thread[] threads = new Thread[indNum];
    int indCtr = 0;
    for (Indicator ind : instrument.getIndicators()) {
      PeTraSys.getTopFrame().getBackTestTextArea().append("\nIndicator: " + ind.getName());
      threads[indCtr] = new Thread(ind);
      threads[indCtr].start();
      indCtr++;
    }
    for (int i = 0; i < indNum; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException ex) {
        MsgBox.err2(ex);
      } finally {
        ////rpc - WORKING HERE:4/4/10 11:23 AM - Do the backtest, 
        i = 1;
      }
    }

  }

  /**
   * rpc - 3/12/10 12:28 PM - Indicator gets the indices into the PriceData.indicatorValues
   * array, and access to all the price bars, for backtest, they can be run in batch
   */
//  private void setupIndicators() {
//    for (Indicator ind : instrument.getIndicators()) {
//      ind.setInstrument(instrument);
//      ind.setMinutePriceBars(instrument.getPriceBars());
//    }
//  }
}
