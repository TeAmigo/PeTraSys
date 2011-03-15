/*
 * rpc - 3/26/10 10:51 AM - From http://www.jfree.org/phpBB2/viewtopic.php?f=3&t=27401
 */
package petrasys.charts.experiments;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class ScrollingLineChart extends JFrame implements AdjustmentListener {

  private static XYSeries series1;
  private NumberAxis domainAxis;
  private double lowerBound;
  private double upperBound;

  public void adjustmentValueChanged(AdjustmentEvent e) {
    // this method is called when the scrollbar changes
    System.out.println(e.getValue());
    JScrollBar scrollbar = (JScrollBar) e.getAdjustable();
    int x = e.getValue();
    this.domainAxis.setRange(lowerBound + x, upperBound + x);
    System.out.println(x);
  }
  private JFreeChart chart;

  public ScrollingLineChart() {
    super("Scrolling Line Chart Demo");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    XYDataset dataset = createDataset();
    this.chart = createChart(dataset);
    this.domainAxis = (NumberAxis) this.chart.getXYPlot().getDomainAxis();
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    this.add(chartPanel);
    this.add(getScrollBar(), BorderLayout.SOUTH);
  }

  private JScrollBar getScrollBar() {
    // JScrollBar(orientation, value, extent(size of the viewable are or visible amount), min, max)
    // the scrollbar will return values in the range 0 to 100 or max - extent. Extent is the visual width of the slider
    // value - the current value represented by the left top edge of the scrollbar
    JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
    // JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
    scrollBar.addAdjustmentListener(this);
    // System.out.println("Lower Bound: " + domainAxis.getRange().getLowerBound());
    // System.out.println("Upper Bound: " + domainAxis.getRange().getUpperBound());
    lowerBound = this.domainAxis.getLowerBound();
    upperBound = this.domainAxis.getUpperBound();
    // System.out.println(lowerBound);
    // System.out.println(upperBound);
    return scrollBar;
  }

  /**
   * Creates a chart.
   *
   * @param dataset
   *            the data for the chart.
   *
   * @return a chart.
   */
  private static JFreeChart createChart(XYDataset dataset) {
    // create the chart...
    JFreeChart chart = ChartFactory.createXYLineChart("Line Chart Demo 2", // chart
            // title
            null, // x axis label
            null, // y axis label
            dataset, // data
            PlotOrientation.VERTICAL, false, // include legend
            true, // tooltips
            false // urls
            );
    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    chart.setBackgroundPaint(Color.BLACK);
    // get a reference to the plot for further customisation...
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.BLACK);
    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
    plot.setDomainGridlinesVisible(false);
    plot.setRangeGridlinesVisible(false);
    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
    NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
    domainAxis.setAutoRange(true);
    domainAxis.setAutoRangeIncludesZero(false);
    // domainAxis.setRange(50,100);
    // change the auto tick unit selection to integer units only...
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    // OPTIONAL CUSTOMISATION COMPLETED.
    return chart;
  }

  /**
   * Creates a sample dataset.
   *
   * @return a sample dataset.
   */
  private static XYDataset createDataset() {
    series1 = new XYSeries("First");

    int randomX;
    int randomY;
    int min = -200;
    int max = 200;
    Random randomGenerator = new Random();

    for (int i = 0; i < 1000; i++) {
      randomX = (int) (Math.random() * (max - min + 1)) + min;
      randomY = randomGenerator.nextInt(50);
      series1.add(randomX, randomY);
    }



    XYSeriesCollection dataset = new XYSeriesCollection();
    dataset.addSeries(series1);
    return dataset;
  }

  public static void main(String[] args) {
    ScrollingLineChart scrollingChart = new ScrollingLineChart();
    scrollingChart.pack();
    scrollingChart.setVisible(true);
  }
}
