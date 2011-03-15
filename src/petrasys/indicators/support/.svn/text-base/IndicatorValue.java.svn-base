/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.indicators.support;


import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * @author rickcharon
 */
public class IndicatorValue  {
  ConcurrentSkipListMap<Long, Double> ivMap;
  String name;



  public IndicatorValue(String name) {
    ivMap = new ConcurrentSkipListMap<Long, Double>();
    this.name = name;
  }

  public void insert(Long date, Double val) {
    ivMap.put(date, val);
  }

  public ConcurrentSkipListMap<Long, Double> getIvMap() {
    return ivMap;
  }

  public String getName() {
    return name;
  }




  public int size() {
    return ivMap.size();
  }

  public boolean isEmpty() {
    return ivMap.isEmpty();
  }

  public boolean containsKey(Object key) {
    return ivMap.containsKey(key);
  }


}
