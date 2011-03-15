/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Crosshair;
import org.jfree.ui.RectangleAnchor;
import petrasys.instruments.Instrument;

/**
 *
 * @author rickcharon
 */
public class ADXChartFrame extends JFrame {

  ADXChart chart;
  ADXChartPanel chartPanel;
  JPanel topPanel;
  //private Crosshair crosshair1;
  //private Crosshair crosshair2;
  java.awt.Toolkit toolkit = this.getToolkit();
  java.awt.Image appIcon = toolkit.createImage("/share/icons/MultiLine.png");

  public ADXChartFrame(Instrument instrument) {
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//    addWindowListener(new java.awt.event.WindowAdapter() {
//      public void windowClosing(java.awt.event.WindowEvent evt) {
//        formWindowClosing(evt);
//      }
//    });
    chart = new ADXChart(new CombinedDomainXYPlot(new DateAxis("DateTime")), instrument);
    setupChartWindow(chart);
    setIconImage(appIcon);
    setTitle(instrument.getChartName());
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
    dispose();
    //int i = 3;
  }

  public void setupChartWindow(ADXChart chart) {
    chartPanel = new ADXChartPanel(chart, this);
    chart.setChartPanel(chartPanel);
    chartPanel.setMouseWheelEnabled(true);
    topPanel = new JPanel(new BorderLayout());
    topPanel.add(chartPanel);
    setContentPane(topPanel);
    pack();
    
  }

  



  public static void main(String[] args) {
    Instrument inst = new Instrument("PLAY", WIDTH, WIDTH, SOMEBITS, null, null);
    ADXChartFrame f = new ADXChartFrame(inst);
//    JFrame sf = new ADXChartFrame();
//    sf.setTitle("2nd Chart");
//    sf.setExtendedState(Frame.MAXIMIZED_BOTH);
//    sf.setVisible(true);
  }
}
