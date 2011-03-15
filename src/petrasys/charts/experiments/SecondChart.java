/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.charts.experiments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.jdbc.JDBCXYDataset;
import org.jfree.data.xy.XYDataset;
import petrasys.charts.ADXChartPanel;
import petrasys.utils.DBops;

/**
 *
 * @author rickcharon
 */
public class SecondChart extends JFrame {

  ADXChartPanel chartPanel;
  JPanel topPanel;



  public SecondChart() {
    JFreeChart chart = createChart(createDataset());
    chartPanel = new ADXChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    topPanel = new JPanel(new BorderLayout());
    topPanel.add(chartPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setContentPane(topPanel);
    pack();
  }

  private static XYDataset createDataset() {
    JDBCXYDataset data = null;
    try {
      Connection con = DBops.setuptradesConnection();
      data = new JDBCXYDataset(con);
      //'2010-03-16 00:00:00', '2010-03-16 12:25:00'

      String sql = "SELECT `datetime`, `high`, `low` FROM `quotes1min` WHERE "
              + "`symbol`='GBP' and `datetime`>= '2010-03-16 00:00:00'"
              + "and `datetime` <= '2010-03-16 12:25:00'";
      data.executeQuery(sql);
      con.close();
    } catch (SQLException e) {
      System.err.print("SQLException: ");
      System.err.println(e.getMessage());
    } catch (Exception e) {
      System.err.print("Exception: ");
      System.err.println(e.getMessage());
    }
    return data;
  }

  private JFreeChart createChart(XYDataset dataset) {
    JFreeChart chart = 
            ChartFactory.createTimeSeriesChart( null, "Time", "Value", dataset, true, true, true);

    ChartUtilities.applyCurrentTheme(chart);
    //rpc - NOTE:3/27/10 4:09 PM - Do above before changing any stuff, or changes are overwritten
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setDomainPannable(true);
    XYDifferenceRenderer r = new XYDifferenceRenderer(Color.green,
            new Color(229, 255, 247), false);
    r.setRoundXCoordinates(true);
    r.setSeriesPaint(1, Color.RED);
    r.setSeriesPaint(0, Color.blue);
    r.setBaseShape(new Rectangle(5, 5));
    r.setShapesVisible(true);
    plot.setDomainCrosshairLockedOnData(true);
    plot.setRangeCrosshairLockedOnData(true);
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);
    
    Font f = new Font("dialog", Font.PLAIN, 18);
    UIManager.put("ToolTip.font", f);

    r.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
            "{0} : {1} = {2}", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"),
            new DecimalFormat("0.00")));
    plot.setRenderer(r);
    //rpc - WORKING HERE:3/26/10 5:42 PM -
    //ValueAxis domainAxis = new DateAxis("Time");
    DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
    int ic = dataset.getItemCount(0);
    Calendar endCal = new GregorianCalendar();
    Date beginDate = domainAxis.getMinimumDate();
    Date endDate = domainAxis.getMaximumDate();
    //endCal.setTime(beginDate);
    //endCal.add(Calendar.MINUTE, 350);
    //rpc - NOTE:4/2/10 4:53 AM - Where visible range is set
    domainAxis.setRange(beginDate, endDate); //endCal.getTime());
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    plot.setDomainAxis(domainAxis);
    plot.setForegroundAlpha(0.5f);

    plot.setBackgroundPaint(new Color(255, 255, 229));
    return chart;
  }

  public static void main(String[] args) {
    JFrame sf = new SecondChart();
    sf.setTitle("2nd Chart");
    sf.setExtendedState(Frame.MAXIMIZED_BOTH);
    sf.setVisible(true);
  }
}
