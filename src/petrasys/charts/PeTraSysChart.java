/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.charts;

import java.awt.Font;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

/**
 *  // rpc - TODO:4/5/10 7:16 AM Meant to be the base class for PeTraSys charts,
 * @author rickcharon
 */
public abstract class PeTraSysChart extends JFreeChart {

  public PeTraSysChart(String title, Font titleFont, Plot plot, boolean createLegend) {
    super(title, titleFont, plot, createLegend);
  }

  public PeTraSysChart(String title, Plot plot) {
    super(title, plot);
  }

  public PeTraSysChart(Plot plot) {
    super(plot);
  }


  public abstract void openControlFrame();

}
