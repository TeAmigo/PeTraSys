/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.charts.experiments;

import petrasys.charts.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import petrasys.PeTraSys;
import petrasys.instruments.Instrument;
import petrasys.instruments.PriceBar;

/**
 *
 * @author rickcharon
 */
public class ThirdChart extends JFrame {

  ADXChartPanel chartPanel;
  JPanel topPanel;
  List<PriceBar> priceBars;
  Instrument instrument;
  JFreeChart chart;
  ADXChartControlFrame1 controlFrame;

  public ThirdChart() {
    chart = createChartOrig(createHighLowDataset());
    setupChartWindow();
  }

  public ThirdChart(List<PriceBar> priceBars) {
    this.priceBars = priceBars;
    chart = createChartOrig(createHighLowDataset());
    setupChartWindow();
  }

  public ThirdChart(Instrument instrument) {
    this.instrument = instrument;
    priceBars = instrument.getPriceBars().getDatas();
    //chart = createChartOrig(createHighLowDataset());
    chart = createChart();
    setupChartWindow();
    //controlFrame = new ADXChartControlFrame1();
  }

  public XYDataset createHighLowDataset() {
    TimeSeries highSeries = new TimeSeries("High");
    TimeSeries lowSeries = new TimeSeries("Low");
    for (int idx = 0; idx < priceBars.size(); idx++) {
      Date dt = new Date(priceBars.get(idx).getDate());
      Minute minute = new Minute(dt);
      highSeries.add(minute, priceBars.get(idx).getHigh());
      lowSeries.add(minute, priceBars.get(idx).getLow());
    }
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.addSeries(lowSeries);
    dataset.addSeries(highSeries);
    return dataset;
  }

  public void setupChartWindow() {

    chartPanel = new ADXChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    topPanel = new JPanel(new BorderLayout());
    topPanel.add(chartPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setContentPane(topPanel);
    pack();
  }

  private XYDifferenceRenderer getXYDifferenceRenderer() {
    XYDifferenceRenderer r = new XYDifferenceRenderer(Color.green,
            new Color(229, 255, 247), false);
    //r.setRoundXCoordinates(true);
    r.setSeriesPaint(0, Color.RED);
    r.setSeriesPaint(1, Color.blue);
    r.setBaseShape(new Rectangle(3, 3));
    r.setShapesVisible(true);
    r.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
            "{0} : {1} = {2}", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"),
            new DecimalFormat("0.00")));
    return r;
  }

  private XYLineAndShapeRenderer getLineAndShapeRenderer() {
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    renderer.setBaseShapesVisible(true);
    renderer.setDrawOutlines(true);
    renderer.setUseFillPaint(true);
    renderer.setBaseFillPaint(Color.white);
    renderer.setSeriesStroke(0, new BasicStroke(3.0f));
    renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
    renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
    return renderer;
  }

  private JFreeChart createChart() {
    // create subplot 1...
    XYDataset data1 = createHighLowDataset();
    //XYItemRenderer renderer1 = new StandardXYItemRenderer();
    NumberAxis dAxis = new NumberAxis("High/Low");
    dAxis.setAutoRangeIncludesZero(false);
    XYPlot subplot1 = new XYPlot(data1, null, dAxis, getXYDifferenceRenderer());
    subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    // create subplot 2...
    XYDataset data2 = data1;
    XYItemRenderer renderer2 = new StandardXYItemRenderer();
    NumberAxis dAxis2 = new NumberAxis("2nd High/Low");
    dAxis2.setAutoRangeIncludesZero(false);
    XYPlot subplot2 = new XYPlot(data2, null, dAxis2, getLineAndShapeRenderer());
    subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
    // parent plot...
    DateAxis sharedAxis = new DateAxis("DateTime");
    sharedAxis.setTickMarkInsideLength(5.0f);
    CombinedDomainXYPlot plot = new CombinedDomainXYPlot(sharedAxis);
    plot.setGap(10.0);
    // add the subplots...
    plot.add(subplot1, 1);
    plot.add(subplot2, 1);
    plot.setOrientation(PlotOrientation.VERTICAL);
    setTitle("Third Chart");
//    JFreeChart chart = new JFreeChart("ThirdChart",
//            PeTraSys.DEFAULT_TITLE_FONT, plot, true);
    chart = new JFreeChart(plot);
    //ChartUtilities.applyCurrentTheme(chart);
    //subplot1.setBackgroundPaint(new Color(255, 153, 255));
    plot.setDomainCrosshairLockedOnData(true);
    plot.setRangeCrosshairLockedOnData(true);
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);
    return chart;
  }

  private JFreeChart createChartOrig(XYDataset dataset) {
    JFreeChart chart =
            ChartFactory.createTimeSeriesChart(null, "Time", "Value", dataset, true, true, true);

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

    plot.setBackgroundPaint(new Color(255, 153, 255));
    return chart;
  }

  public static void main(String[] args) {
    JFrame sf = new ThirdChart();
    sf.setTitle("2nd Chart");
    sf.setExtendedState(Frame.MAXIMIZED_BOTH);
    sf.setVisible(true);
  }
}
