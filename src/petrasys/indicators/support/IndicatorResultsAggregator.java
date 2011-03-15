/**
 * rpc - 3/6/10 3:15 PM This is the Aggregator for the arrays the Indicators
 * might need, as well as the price information, the
 */
package petrasys.indicators.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListMap;
import petrasys.instruments.Instrument;

/**
 *
 * @author rickcharon
 * //rpc - WORKING HERE:3/7/10 5:45 AM - IndicatorResultsAggregator put together the results from all indicators,
 * to make a buy/sell determination, could be part of Determinator
 */
public class IndicatorResultsAggregator {

  ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, Double>> indMaps;
  Instrument instrument;
  SortedSet<String> indMapNames;

  public IndicatorResultsAggregator(Instrument instrument) {
    this.instrument = instrument;
    indMaps = new ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, Double>>();
    for (Indicator ind : instrument.getIndicators()) {
      for (IndicatorValue indVal : ind.getIndicatorValues().values()) {
        indMaps.put(indVal.getName(), indVal.getIvMap());
      }
    }
    indMapNames = getOrderedKeys();
  }

  public ConcurrentSkipListMap<String, ConcurrentSkipListMap<Long, Double>> getIndMaps() {
    return indMaps;
  }

  public ConcurrentSkipListMap<Long, Double> getIndicatorValues(String indicatorName) {
    return indMaps.get(indicatorName);
  }

  public Iterator getIteratorForIndicator(String indicatorName) {
    ConcurrentSkipListMap<Long, Double> map = indMaps.get(indicatorName);
    return map.entrySet().iterator();
  }

  public SortedSet<String> getOrderedKeys() {
    return indMaps.keySet();
  }

  /**
   * rpc - 3/17/10 4:25 PM - It is expected that all IndicatorValues are the same length,
   * and cover the same periods.
   * @return the length of the results map(long date, double value), in the 1st IndicatorValue.
   */
  public int getValuesSize() {
    return indMaps.firstEntry().getValue().size();
  }

  public ArrayList<Double> getEntriesForDate(long date) {
    ArrayList<Double> vals = new ArrayList<Double>();
    for (String name : indMapNames) {
      vals.add(indMaps.get(name).get(date));
    }
    return vals;
  }

  //rpc - WORKING HERE:4/10/10 11:39 AM - BROKEN - doesn't work
  public Iterator getIteratorForIndicatorValues() {
    return indMaps.firstEntry().getValue().entrySet().iterator();
  }

  public static void main(String args[]) {
    ConcurrentSkipListMap<String, Double> tm = new ConcurrentSkipListMap<String, Double>();
    tm.put("a", 1.0);
    tm.put("b", 1.0);
    tm.put("d", 1.0);
    tm.put("f", 1.0);
    tm.put("e", 1.0);
    tm.put("c", 1.0);
    tm.put("g", 1.0);
    SortedSet<String> ks = tm.keySet();
    int j = 3;
  }

}
