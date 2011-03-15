/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.utils;

import java.util.List;
import org.jfree.data.Range;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

/**
 *
 * @author rickcharon
 */
public class TimeSeriesOps {

  TimeSeries timeSeries;

  public TimeSeriesOps(TimeSeries ts) {
    timeSeries = ts;
  }

  public static TimeSeries createSubSeries(TimeSeries seriesIn, RegularTimePeriod start, RegularTimePeriod end) {
    TimeSeries sout = null;
    try {
      sout = seriesIn.createCopy(start, end);
    } catch (CloneNotSupportedException ex) {
      MsgBox.err2(ex);
    } finally {
      return sout;
    }
  }

  public static double maxVal(TimeSeries seriesIn) {
    List<TimeSeriesDataItem> data = (List<TimeSeriesDataItem>) seriesIn.getItems();
    double maxVal = data.get(0).getValue().doubleValue();
    double tmp;
    for (TimeSeriesDataItem tdi : data) {
      tmp = tdi.getValue().doubleValue();
      if (tmp > maxVal) {
        maxVal = tmp;
      }
    }
    return maxVal;
  }

  public static double minVal(TimeSeries seriesIn) {
    List<TimeSeriesDataItem> data = (List<TimeSeriesDataItem>) seriesIn.getItems();
    double minVal = data.get(0).getValue().doubleValue();
    double tmp;
    for (TimeSeriesDataItem tdi : data) {
      tmp = tdi.getValue().doubleValue();
      if (tmp < minVal) {
        minVal = tmp;
      }
    }
    return minVal;
  }



  public static void main(String args[]) {
    try {
      RegularTimePeriod period = new Minute(0, 0, 9, 4, 2010),
              period2 = new Minute(10, 0, 9, 4, 2010);
      TimeSeries test = TimeSeriesOps.createTestSeries(60 * 24, period);
      TimeSeries testSubSeries = TimeSeriesOps.createSubSeries(test, period, period2);
      TimeSeriesCollection tsCol = new TimeSeriesCollection(test);
      double r = tsCol.getDomainUpperBound(true);
      double r2 = tsCol.getDomainLowerBound(true);
      double max = TimeSeriesOps.maxVal(testSubSeries);
      double min = TimeSeriesOps.minVal(testSubSeries);
      System.out.println("max = " + max);
      System.out.println("min = " + min);
      TimeSeriesOps.printVals(testSubSeries);
      // List dd = test.data;
      //TimeSeriesDataItem ti = (TimeSeriesDataItem) test.data.get(0);
      //dd.subList(m, m);
      int j = 2;
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  public static void printVals(TimeSeries tin) {
    List<TimeSeriesDataItem> data = (List<TimeSeriesDataItem>) tin.getItems();
    for (TimeSeriesDataItem tdi : data) {
      System.out.println("[" + tdi.getPeriod() + "] = " + tdi.getValue() + ", ");
    }
  }

  public static TimeSeries createTestSeries(int count, RegularTimePeriod period) {

    double value = 10.0;
    TimeSeries series = new TimeSeries("testSeries");
    for (int i = 0; i < count; i++) {
      series.add(period, value);
      period = period.next();
      value = value * (1 + (Math.random() - 0.495) / 10.0);
    }
    return series;
  }
}
