/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import petrasys.indicators.support.IndicatorResultsAggregator;
import petrasys.instruments.Instrument;
import petrasys.instruments.PriceBar;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class ADXChart extends PeTraSysChart {

  private ADXChartPanel chartPanel;
  private JPanel topPanel;
  private List<PriceBar> priceBars;
  private Instrument instrument;
  private JFreeChart chart;
  private ADXChartControlFrame controlFrame;
  private CombinedDomainXYPlot comboPlot;
  private OHLCSeriesCollection priceSeriesCollection;
  OHLCSeries priceSeries;
  private TimeSeries volumeSeries;
  private XYPlot plotPrices;
  private XYPlot plotADX;
  private XYPlot plotVolume;
  private Date beginDate, endDate;
  private IndicatorResultsAggregator indicatorResultsAggregate;
  

  public Instrument getInstrument() {
    return instrument;
  }

  public ADXChartPanel getChartPanel() {
    return chartPanel;
  }

  public void setChartPanel(ADXChartPanel chartPanel) {
    this.chartPanel = chartPanel;
  }

  


  public ADXChart(CombinedDomainXYPlot plot, Instrument instrument) {
    super(plot);
    comboPlot = plot;
    this.instrument = instrument;
    priceBars = instrument.getPriceBars().getDatas();
    beginDate = instrument.getPriceBars().getBeginDate();
    endDate = instrument.getPriceBars().getEndDate();
    controlFrame = new ADXChartControlFrame(this);
    indicatorResultsAggregate = new IndicatorResultsAggregator(instrument);
    setupChart();
    instrument.addChart(this);
  }

  public CombinedDomainXYPlot getComboPlot() {
    return comboPlot;
  }

  public XYPlot getPricePlot() {
    return plotPrices;
  }

  public XYPlot getADXPlot() {
    return plotADX;
  }

  public XYPlot getVolumePlot() {
    return plotVolume;
  }



  public Date getBeginDate() {
    return beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public TimeSeriesCollection createHighLowDataset() {
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    try {
      TimeSeries highSeries = new TimeSeries("High");
      TimeSeries lowSeries = new TimeSeries("Low");
      TimeSeries volumeSeries = new TimeSeries("Volume");
      for (int idx = 0; idx < priceBars.size(); idx++) {
        Date dt = new Date(priceBars.get(idx).getDate());
        Minute minute = new Minute(dt);
        highSeries.add(minute, priceBars.get(idx).getHigh());
        lowSeries.add(minute, priceBars.get(idx).getLow());
        volumeSeries.add(minute, priceBars.get(idx).getVolume());
      }
      dataset.addSeries(lowSeries);
      dataset.addSeries(highSeries);
    } catch (Exception ex) {
      MsgBox.err2(ex);
    } finally {
      return dataset;
    }
  }

  //rpc - NOTE:5/6/10 4:43 PM - I am adding the Volume series init in here,
  // there doesn't seem to be a neater way to do it,
  public OHLCSeriesCollection createOHLCDataset() {
    OHLCSeriesCollection dataset = new OHLCSeriesCollection();
    volumeSeries  = new TimeSeries("Volume");
    priceSeries = new OHLCSeries("PriceBar");
    try {
       for (int idx = 0; idx < priceBars.size(); idx++) {
        Date dt = new Date(priceBars.get(idx).getDate());
        Minute minute = new Minute(dt);
        priceSeries.add(minute, priceBars.get(idx).getOpen(), priceBars.get(idx).getHigh(),
                priceBars.get(idx).getLow(), priceBars.get(idx).getClose());
        volumeSeries.add(minute, priceBars.get(idx).getVolume());
      }
      dataset.addSeries(priceSeries);
    } catch (Exception ex) {
      MsgBox.err2(ex);
    } finally {
      return dataset;
    }
  }



  // Called from createADXDataset
  private TimeSeries createADXSeries(String indicatorName) {
    TimeSeries timeSeries = new TimeSeries(indicatorName);
    Iterator iter = indicatorResultsAggregate.getIteratorForIndicator(indicatorName);
    while (iter.hasNext()) {
      Map.Entry pairs = (Map.Entry) iter.next();
      Date dateUp = new Date((Long) pairs.getKey());
      Minute minute = new Minute(dateUp);
      double val = (Double) pairs.getValue();
      timeSeries.add(minute, val);
    }
    return timeSeries;
  }

  public TimeSeriesCollection createADXDataset() {
    SortedSet<String> keys = indicatorResultsAggregate.getOrderedKeys();
    TimeSeries ADXSeries = createADXSeries("ADX");
    TimeSeries diPlusSeries = createADXSeries("PlusDI");
    TimeSeries diMinusSeries = createADXSeries("MinusDI");
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.addSeries(ADXSeries);
    dataset.addSeries(diPlusSeries);
    dataset.addSeries(diMinusSeries);
    return dataset;
  }

  private XYDifferenceRenderer getXYDifferenceRenderer() {
    XYDifferenceRenderer r = new XYDifferenceRenderer(Color.green,
            new Color(229, 255, 247), false);
    r.setSeriesPaint(0, Color.RED);
    r.setSeriesPaint(1, Color.blue);
    r.setBaseShape(new Rectangle(1, 1));
    r.setShapesVisible(true);
    r.setBaseToolTipGenerator(getToolTipGenerator());
    return r;
  }

  private ADXOHLCRenderer getHighLowRenderer(OHLCSeriesCollection seriesCol) {
    ADXOHLCRenderer r = new ADXOHLCRenderer(seriesCol);
    r.setBaseToolTipGenerator(new HighLowItemLabelGenerator());
    r.setSeriesPaint(0, Color.BLACK);
    r.setSeriesStroke(0, new BasicStroke(50.0f));
    return r;
  }

  private CandlestickRenderer getCandlestickRenderer() {
    CandlestickRenderer r = new CandlestickRenderer(5);
    r.setBaseToolTipGenerator(new HighLowItemLabelGenerator());
    r.setSeriesPaint(0, Color.BLACK);
    r.setSeriesStroke(0, new BasicStroke(2.0f));
    return r;
  }

  private XYBarRenderer getVolumeRenderer() {
    XYBarRenderer volumeRenderer = new XYBarRenderer();
    volumeRenderer.setShadowVisible(false);
    volumeRenderer.setSeriesPaint(0, Color.BLACK);
    volumeRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
    volumeRenderer.setBaseToolTipGenerator(getToolTipGenerator());
    return volumeRenderer;
  }

  private XYLineAndShapeRenderer getLineAndShapeRenderer(TimeSeriesCollection dataset) {
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
    renderer.setSeriesPaint(dataset.indexOf(dataset.getSeries("ADX")), Color.BLACK);
    renderer.setSeriesPaint(dataset.indexOf(dataset.getSeries("PlusDI")), Color.BLUE);
    renderer.setSeriesPaint(dataset.indexOf(dataset.getSeries("MinusDI")), Color.RED);
    renderer.setSeriesStroke(0, new BasicStroke(1.5f));
    renderer.setSeriesStroke(1, new BasicStroke(1.5f));
    renderer.setSeriesStroke(2, new BasicStroke(1.5f));
//    renderer.setBaseShape(new Rectangle(1, 1));
    //renderer.setShapesVisible(true);
    renderer.setBaseToolTipGenerator(getToolTipGenerator());
//    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//    renderer.setBaseShapesVisible(true);
//    renderer.setDrawOutlines(true);
//    renderer.setUseFillPaint(true);
//    renderer.setBaseFillPaint(Color.white);
//    renderer.setSeriesStroke(0, new BasicStroke(1.0f));
//    renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0f));
//    renderer.setSeriesShape(0, new Ellipse2D.Double(-1.0, -1.0, 1.0, 1.0));
//    renderer.setBaseToolTipGenerator(getToolTipGenerator());
    return renderer;
  }

  private void setupChart() {
    // create subplot 1...
    //TimeSeriesCollection highsAndLows = createHighLowDataset();
    priceSeriesCollection = createOHLCDataset();
    NumberAxis dAxis = new NumberAxis("High/Low");
    dAxis.setAutoRangeIncludesZero(false);
    dAxis.setUpperMargin(0.01);
    dAxis.setLowerMargin(0.01);
    //plotPrices = new XYPlot(priceSeries, null, dAxis, getHighLowRenderer(priceSeries));
    plotPrices = new PricePlot(priceSeriesCollection, null, dAxis, getCandlestickRenderer());
    plotPrices.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
    plotPrices.setDomainPannable(true);
    plotPrices.setRangePannable(true);
    // create subplot 2, adx subplot...
    TimeSeriesCollection diPlusDiMinusADX = createADXDataset();
    NumberAxis dAxis2 = new NumberAxis("ADX");
    dAxis2.setAutoRangeIncludesZero(false);
    plotADX = new XYPlot(diPlusDiMinusADX, null, dAxis2, getLineAndShapeRenderer(diPlusDiMinusADX));
    plotADX.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
    plotADX.setDomainPannable(true);
    plotADX.setRangePannable(true);
   // create subplot 3, volume subplot...

    NumberAxis vAxis = new NumberAxis("Volume");
    vAxis.setAutoRangeIncludesZero(false);
    TimeSeriesCollection volCol = new TimeSeriesCollection(volumeSeries);
    plotVolume = new XYPlot(volCol, null, vAxis, getVolumeRenderer());
    plotVolume.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
    plotVolume.setDomainPannable(true);
    plotVolume.setRangePannable(true);
    // add the subplots to comboPlot...
    comboPlot.setGap(2.0);
    comboPlot.add(plotPrices, 50);
    comboPlot.add(plotADX, 10);
    comboPlot.add(plotVolume, 5);
    comboPlot.setOrientation(PlotOrientation.VERTICAL);
    comboPlot.setDomainPannable(true);
    comboPlot.setRangePannable(true);
    comboPlot.getDomainAxis().setTickMarkInsideLength(5.0f);
  }

  private StandardXYToolTipGenerator getToolTipGenerator() {
    return new StandardXYToolTipGenerator(
            "{0} : {1} = {2}", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
            new DecimalFormat("0.00"));
  }


  @Override
  public void openControlFrame() {
    controlFrame.setVisible(true);
  }
}
