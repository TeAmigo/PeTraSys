/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.runners;

import java.util.SortedSet;
import petrasys.indicators.support.IndicatorResultsAggregator;
import petrasys.instruments.Instrument;

/**
 *
 * @author rickcharon
 */
public class IndicatorScanRunner extends Runner {

  

  public IndicatorScanRunner(Instrument instrument) {
    super(instrument);
    setupIndicators();
    runIndicators();
    IndicatorResultsAggregator indResults = new IndicatorResultsAggregator(instrument);
    SortedSet<String> keys = indResults.getOrderedKeys();
    int j = 4;
    //indResults.toDB();
    //int indNum = instrument.getIndicators().size();
/*
    ADXChartFrame chartFrame = new ADXChartFrame(instrument);
    chartFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
    chartFrame.setVisible(true);
 *
 *
 */
    //chartFrame.test();
//    int indNum = instrument.getIndicators().size();
//    Thread[] threads = new Thread[indNum];
//    int indCtr = 0;
//    for (Indicator ind : instrument.getIndicators()) {
//      PeTraSys.getTopFrame().getBackTestTextArea().append("\nIndicator: " + ind.getName());
//      threads[indCtr] = new Thread(ind);
//      threads[indCtr].start();
//      indCtr++;
//    }
//    for (int i = 0; i < indNum; i++) {
//      try {
//        threads[i].join();
//      } catch (InterruptedException ex) {
//        MsgBox.err2(ex);
//      } finally {
//        
//        i = 1;
//      }
//    }

  }

  
}
