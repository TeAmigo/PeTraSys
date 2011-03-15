/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.indicators;

import javax.swing.JOptionPane;
import petrasys.indicators.support.ATRimpl;
import petrasys.indicators.support.Indicator;
import petrasys.indicators.support.IndicatorValue;
import petrasys.instruments.PriceBar;
import petrasys.instruments.RunState;

/**
 *
 * @author rickcharon
 */
public class ATR extends Indicator {
  int periods;

  public ATR() {
    String str = JOptionPane.showInputDialog("Enter Integer Value For Periods (ATR): ", "10");
    periods = Integer.parseInt(str);
    indicatorValues.put("ATR", new IndicatorValue("ATR"));
  }

  @Override
  public String getName() {
    return "ATR";
  }

  @Override
  public void run() {
    if (instrument.getRunState() == RunState.BackTest) {
      //Run batch on PriceDatas
      double[] inHigh = instrument.getPriceBars().getHighs();
      double[] inLow = instrument.getPriceBars().getLows();
      double[] inClose = instrument.getPriceBars().getCloses();

      int hl = inHigh.length;
      int ll = inLow.length;
      int ic = inClose.length;
      double[] outReal = new double[instrument.getPriceBars().size()];

      ATRimpl.getATR(periods, inHigh, inLow, inClose, outReal);
      for(int i = 0; i< outReal.length; i++) {
        PriceBar pd = instrument.getPriceBars().getDataAt(i);
        indicatorValues.get("ATR").insert(pd.getDate(), outReal[i]);
      }

    }
  }
}
