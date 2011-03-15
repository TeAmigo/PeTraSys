/* // rpc - 2/12/10 2:39 PM
 * This is a collection of PriceBars with helpful stuff about those datas,
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.instruments;

//import com.jsystemtrader.platform.quote.PriceBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import petrasys.trader.PaperTrade;

/**
 *
 * @author rickcharon
 * //rpc - NOTE:4/4/10 11:24 AM -  - PriceBars, collection
 */
public class PriceBars {

  private List<PriceBar> datas;
  int compressionFactor = 1;

  public int getCompressionFactor() {
    return compressionFactor;
  }

  public void setCompressionFactor(int compressionFactor) {
    this.compressionFactor = compressionFactor;
  }


  public long[] getDates() {
    long[] hs = new long[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getDate();
    }
    return hs;
  }

  public ConcurrentSkipListSet<Long> getDatesAsSet() {
    ConcurrentSkipListSet<Long> hs = new ConcurrentSkipListSet<Long>();
    for (int i = 0; i < datas.size(); i++) {
      hs.add(datas.get(i).getDate());
    }
    return hs;
  }

  public double[] getHighs() {
    double[] hs = new double[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getHigh();
    }
    return hs;
  }

  public double[] getLows() {
    double[] hs = new double[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getLow();
    }
    return hs;
  }

  public double[] getOpens() {
    double[] hs = new double[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getOpen();
    }
    return hs;
  }

  public double[] getCloses() {
    double[] hs = new double[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getClose();
    }
    return hs;
  }

  public long[] getVolumes() {
    long[] hs = new long[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = datas.get(i).getVolume();
    }
    return hs;
  }

  public double[] getVolumesAsDoubles() {
    double[] hs = new double[datas.size()];
    for (int i = 0; i < datas.size(); i++) {
      hs[i] = (double) datas.get(i).getVolume();
    }
    return hs;
  }

  public List<PriceBar> getDatas() {
    return datas;
  }

  public void setDatas(List<PriceBar> datasIn) {
    this.datas = datasIn;
  }

  public Date getBeginDate() {
    ConcurrentSkipListSet<Long> ds = getDatesAsSet();
    //Date dd = new Date(ds.first());
    return new Date(ds.first());
  }

  public Date getEndDate() {
    ConcurrentSkipListSet<Long> ds = getDatesAsSet();
    return new Date(ds.last());
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }
  private String symbol;
  private Date beginDate;
  private Date endDate;

//  public PriceBars() {
//  }
  public PriceBars(List<PriceBar> datas, String symbol, Date beginDate, Date endDate) {
    this.datas = datas;
    this.symbol = symbol;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }

  public PriceBars(String symbol, Date beginDate, Date endDate) {
    this.symbol = symbol;
    this.beginDate = beginDate;
    this.endDate = endDate;
  }

  

  public void PlayItForward(PaperTrade pt) {
    long tradeDate = pt.getBeginTradeDateTime().getTime();
    Double highTarget, lowTarget;
    boolean tradeMade = false;
    if(pt.getPosition().equals("BUY")) {
      highTarget = pt.getProfitStop();
      lowTarget = pt.getStopLoss();
    } else {
      highTarget = pt.getStopLoss();
      lowTarget = pt.getProfitStop();
    }
    int ctr;
    for(ctr = 0; ctr < datas.size(); ctr++) {
      if(datas.get(ctr).getDate() >= tradeDate) break;
    }
    for(; ctr < datas.size(); ctr++) {
      if(datas.get(ctr).getHigh() >= highTarget) {
        tradeMade = true;
        pt.setExitTradeDateTime(new Date(datas.get(ctr).getDate()));
        if(pt.getPosition().equals("BUY")) {
          pt.setOutcome(pt.getProfitpotential());
        } else {
          pt.setOutcome(pt.getStopRisk());
        }
        break;
      } else if(datas.get(ctr).getLow() <= lowTarget) {
        tradeMade = true;
        pt.setExitTradeDateTime(new Date(datas.get(ctr).getDate()));
       if(pt.getPosition().equals("SELL")) {
          pt.setOutcome(pt.getProfitpotential());
        } else {
          pt.setOutcome(pt.getStopRisk());
        }
        break;
      }
    }
    if(!tradeMade) {
      pt.setExitTradeDateTime((new GregorianCalendar(300, 1, 1)).getTime());
      pt.setOutcome(0.0);
    }
   }

  private void compressMyMinuteBars(int compression) {
    List<PriceBar> newPbs = new ArrayList<PriceBar>();
    long beginDate = datas.get(0).getDate();
    Calendar testCal = new GregorianCalendar();
    //Date dd = new Date(2010, 04, 13, 06, 0, 0), ee = new Date(2010, 04, 13, 06, 0, 15);
    //dates are in millisecs, so *1000 * 60 is minute
    long compr = 1000 * 60 * compression;
    //long mod = dd.getTime() % compr;
    //mod = ee.getTime() % compr;
    // so boundry is where date mod compr is 0?
    for (int i = 0; i < datas.size(); i++) {
      PriceBar wpb = new PriceBar(datas.get(i));
      long volumeAcc = 0;
      for (int j = i + 1; j < datas.size(); j++) {
        if ((datas.get(j).getDate() % compr) != 0) { // combine with present bar
          if (datas.get(j).getHigh() > wpb.getHigh()) {
            wpb.setHigh(datas.get(j).getHigh());
          }
          if (datas.get(j).getLow() < wpb.getLow()) {
            wpb.setLow(datas.get(j).getLow());
          }
          wpb.setClose(datas.get(j).getClose());
          volumeAcc += datas.get(j).getVolume();
          i++;
        } else {
          wpb.setVolume(volumeAcc);
          newPbs.add(wpb);
          //i++;
          break;
        }
      }
    }
    datas = newPbs;
    setCompressionFactor(compression);
    int j = 3;
  }

  //rpc - NOTE HERE:4/12/10 1:49 PM - This is where compression gets done.
  // calling it minute bars imagining realtime bars in future,
  public PriceBars compressMinuteBars(int compression) {
    PriceBars compressedBars = new PriceBars(datas, symbol, beginDate, endDate);
    compressedBars.compressMyMinuteBars(compression);
    return compressedBars;
  }

  /**
   * rpc - 3/5/10 7:11 AM - How many PricBars do we have here?
   * @return # of PriceBars
   */
  public int size() {
    return datas.size();
  }

  /**
   *
   * @param idx
   * @return
   */
  public PriceBar getDataAt(int idx) {
    return datas.get(idx);
  }
//  public void createIndicatorArrays(int numArrays) {
//    for(PriceBar pd : datas) {
//      pd.setIndicatorValues(new double[numArrays]);
//    }
//  }
}
