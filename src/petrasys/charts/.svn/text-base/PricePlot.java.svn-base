/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.charts;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.DefaultOHLCDataset;

/**
 *
 * @author rickcharon
 */
public class PricePlot extends XYPlot {

  OHLCSeries ohlc;

  public PricePlot(OHLCSeriesCollection dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
    super(dataset, domainAxis, rangeAxis, renderer);
    ohlc = dataset.getSeries(0);
    //int j = 3;
  }

  public PricePlot(DefaultOHLCDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, XYItemRenderer renderer) {
    super(dataset, domainAxis, rangeAxis, renderer);
    //ohlc = dataset.getSeries(0);
    //int j = 3;
  }
}
