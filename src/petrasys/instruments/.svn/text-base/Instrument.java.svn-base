package petrasys.instruments;

import com.ib.client.Contract;
import java.util.ArrayList;


import petrasys.connections.IBConnectionManager;
import petrasys.connections.MarketDataConnection;
import petrasys.indicators.support.Indicator;
import petrasys.PeTraSys;
import petrasys.charts.ADXChart;
import petrasys.runners.BacktestRunner;
import petrasys.runners.ChartRunner;
import petrasys.runners.IndicatorScanRunner;
import petrasys.trader.TraderFrame;
import petrasys.utils.DateOps;

/**
 * This is the main thing to be traded, i.e., a futures contract.
 * @author rickcharon
 */
public class Instrument implements Runnable {

  private String ul;
  private int expiry;
  private double multiplier;
  private double priceMagnifier;
  private String exchange;
  private double minTick;
  private String fullName;
  private RunState runState;
  private int tickerId;
  private Contract contract;
  MarketDataConnection mdc;
  ArrayList<Indicator> indicators;
  PriceBars minutePriceBars;
  PriceBars priceBars;
  TraderFrame traderFrame;
  ArrayList<ADXChart> charts;

  public ArrayList<ADXChart> getCharts() {
    return charts;
  }

  public void setCharts(ArrayList<ADXChart> charts) {
    this.charts = charts;
  }

  
  
  public void addChart(ADXChart chart) {
    charts.add(chart);
  }
  
  public void removeChart(ADXChart chart) {
    charts.remove(chart);
  }

  //rpc - NOTE:7/26/10 12:59 PM -  Helper for TraderFrame, should maybe go somewhere else?
  public double getActualPriceFromExpandedPrice(double expandedPrice) {
    //res.getDouble("open") / workingSI.priceMagnifier * workingSI.multiplier,
    return (expandedPrice / getMultiplier()) * getPriceMagnifier();
  }

  public ArrayList<Indicator> getIndicators() {
    return indicators;
  }

  public void setIndicators(ArrayList<Indicator> indicators) {
    this.indicators = indicators;
  }

  public PriceBars getPriceBars() {
    return priceBars;
  }

  public PriceBars getMinutePriceBars() {
    return minutePriceBars;
  }

  public void setMinutePriceBars(PriceBars priceBars) {
    this.priceBars = priceBars;
    this.minutePriceBars = priceBars;
  }

  public void setPriceBars(PriceBars priceBars) {
    this.priceBars = priceBars;
  }



  public void compressPriceBars(int compressionFactor) {
    priceBars = minutePriceBars.compressMinuteBars(compressionFactor);
    int j = 3;
  }

  public void addIndicator(Indicator ind) {
    indicators.add(ind);
  }

  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public int getExpiry() {
    return expiry;
  }

  public void setExpiry(int expiry) {
    this.expiry = expiry;
  }

  public String getFullName() {
    return fullName;
  }

  public String getChartName() {
    return fullName + "-" + priceBars.getCompressionFactor() + "min "
            + DateOps.prettyString(priceBars.getBeginDate())
            + " - " + DateOps.prettyString(priceBars.getEndDate());
  }

  public String getChartNameSansDates() {
    return fullName + "-" + priceBars.getCompressionFactor() + "min";
            
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public MarketDataConnection getMdc() {
    return mdc;
  }

  public void setMdc(MarketDataConnection mdc) {
    this.mdc = mdc;
  }

  public double getMinTick() {
    return minTick;
  }

  public void setMinTick(double minTick) {
    this.minTick = minTick;
  }

  public double getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(double multiplier) {
    this.multiplier = multiplier;
  }

  public double getPriceMagnifier() {
    return priceMagnifier;
  }

  public void setPriceMagnifier(double priceMagnifier) {
    this.priceMagnifier = priceMagnifier;
  }

  public int getTickerId() {
    return tickerId;
  }

  public void setTickerId(int tickerId) {
    this.tickerId = tickerId;
  }

  public String getUl() {
    return ul;
  }

  public void setUl(String ul) {
    this.ul = ul;
  }

  public TraderFrame getTraderFrame() {
    return traderFrame;
  }




  public Instrument(String ul, int expiry, double multiplier, double priceMagnifier, String exchange,
          String fullName) {
    charts = new ArrayList<ADXChart>();
    this.ul = ul;
    this.expiry = expiry;
    this.multiplier = multiplier;
    this.priceMagnifier = priceMagnifier;
    this.exchange = exchange;
    this.fullName = fullName;
    tickerId = IBConnectionManager.getTickerId();
    contract = new Contract(tickerId, ul, "FUT", new Integer(expiry).toString(),
            0, "", "", exchange, "USD", "", null, "", true);
    initVars();
    PeTraSys.addInstrument(this);
  }

//  public Instrument(String ul, int expiry) {
//    this(ul, expiry, 0.0, 0.0, "", ul + "-" + Integer.toString(expiry));
////    this.ul = ul;
////    this.expiry = expiry;
////    fullName = ul + "-" + Integer.toString(expiry);
////    tickerId = IBConnectionManager.getTickerId();
////    initVars();
//  }

  private void initVars() {
    indicators = new ArrayList<Indicator>();
    traderFrame = new TraderFrame(this);
  }

  public RunState getRunState() {
    return runState;
  }

  public void setRunState(RunState runState) {
    this.runState = runState;
  }

  public String getShortName() {
    return ul + "-" + new Integer(expiry).toString();
  }


  

  //rpc - WORKING HERE:3/4/10 5:30 PM - Instrument.run()
  @Override
  public void run() {
    //rpc - WORKING HERE:3/6/10 3:36 PM - Put this Instrument in a collection
    //somewhere so it stays arround.

    if (runState == RunState.ForwardTest || runState == RunState.Trade) {
      mdc = new MarketDataConnection(this, "", false);
      // rpc - TODO:2/26/10 6:31 PM LEFT OFF - put backdata in here
      mdc = new MarketDataConnection(tickerId, contract, "", false);
      Thread thread = new Thread(mdc);
      thread.start();
    }
    else if (runState == RunState.BackTest) {
      PeTraSys.writeToReport("Starting Backtest for " + getShortName());
      BacktestRunner btr = new BacktestRunner(this);
    }
    else if (runState == RunState.Exploration) {
      //rpc - NOTE:3/12/10 9:16 AM - This will be important for charting,
      PeTraSys.writeToReport("Starting Exploration for " + getShortName());
    }
    else if(runState == RunState.Chart) {
      PeTraSys.writeToReport("Starting ChartRunner for " + getShortName());
      ChartRunner cr = new ChartRunner(this);
    } else if(runState == RunState.IndicatorScan) {
      IndicatorScanRunner isr = new IndicatorScanRunner(this);
    }
  }



}


