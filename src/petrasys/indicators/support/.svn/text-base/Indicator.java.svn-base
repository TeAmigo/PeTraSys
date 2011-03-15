/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.indicators.support;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentSkipListMap;
import petrasys.instruments.Instrument;
import petrasys.instruments.PriceBars;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class Indicator implements Runnable, Observer {

  protected PriceBars priceBars;
  protected Instrument instrument;
  //protected int[] myPriceDataIndicatorValuesIdx;
  //int numIndicatorValueArraysNeeded;
  protected String name = "Indicator";
  protected ConcurrentSkipListMap<String, IndicatorValue> indicatorValues;
  protected double[] inHigh;
  protected double[] inLow;
  protected double[] inClose;
  protected long[] inDate;

//   public int[] getMyPriceDataIndicatorValuesIdx() {
//    return myPriceDataIndicatorValuesIdx;
//  }
//  public void setMyPriceDataIndicatorValuesIdx(int[] myPriceDataIndicatorValuesIdx) {
//    this.myPriceDataIndicatorValuesIdx = myPriceDataIndicatorValuesIdx;
//  }
  /**
   * //rpc - WORKING HERE:3/15/10 2:26 PM - New PriceBar has come in, so update the last value of indicator.
   */
  public void updateEndPointValue() {
  }

  public Instrument getInstrument() {
    return instrument;
  }

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  public ConcurrentSkipListMap<String, IndicatorValue> getIndicatorValues() {
    return indicatorValues;
  }

//  public int getNumIndicatorValueArraysNeeded() {
//    return numIndicatorValueArraysNeeded;
//  }
//  public void setNumIndicatorValueArraysNeeded(int numIndicatorValueArraysNeeded) {
//    this.numIndicatorValueArraysNeeded = numIndicatorValueArraysNeeded;
//  }
  public PriceBars getPriceBars() {
    return priceBars;
  }

  public void setPriceBars(PriceBars priceBars) {
    this.priceBars = priceBars;
  }

  public String getName() {
    return name;
  }

  /**
   * rpc - 3/5/10 6:37 AM - not currently used
   * @param pbsIn currently set via init func
   */
  public Indicator(PriceBars pbsIn) {
    this();
    priceBars = pbsIn;
  }

  /**
   * rpc - 3/4/10 3:48 PM These are typically instantiated from
   * PeTraSysTopFrame1.setupForActionsOnInstrument()
   * @param pbsIn PriceBars
   */
  public Indicator() {
    indicatorValues = new ConcurrentSkipListMap<String, IndicatorValue>();
  }

  public void init(PriceBars pbsIn) {
    priceBars = pbsIn;
  }

  protected void initializePrimaryArrays() {
    inHigh = instrument.getPriceBars().getHighs();
    inLow = instrument.getPriceBars().getLows();
    inClose = instrument.getPriceBars().getCloses();
    inDate = instrument.getPriceBars().getDates();
  }

  @Override
  public void run() {
    MsgBox.info("Not supported, should be in child object.", "Indicator.run()");
  }

  public void update(Observable o, Object arg) {
    MsgBox.info("Update in Indicator", "not yet implemented");
  }
}
