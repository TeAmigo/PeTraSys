/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.utils;

import java.util.Date;
import java.util.List;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.stat.regression.SimpleRegression;
import petrasys.instruments.PriceBar;
import petrasys.instruments.PriceBars;
import petrasys.talib.MInteger;
import petrasys.talib.OBV;
import petrasys.talib.RetCode;

/**
 *
 * @author rickcharon
 */
public class TaLibTest {
  Date beginDate, endDate;
  String symbol;
  List<PriceBar> pds;
  PriceBars priceDatas;
  SummaryStatistics ss;
  SimpleRegression sr;

  public TaLibTest() {
    beginDate = DateOps.dateFromDbFormatString("2010-03-04 00:00:00");
    endDate = new Date();
    symbol = "GBP";
    pds = DBops.getPriceDatas(symbol, beginDate, endDate, 1, 62500);
    priceDatas = new PriceBars(pds, symbol, beginDate, endDate);
  }

  public void doObv() {
    MInteger outBegIdx = new MInteger(), outNBElement = new MInteger();
    double[] outReal;
    outReal = new double[pds.size()];
    OBV o = new OBV();
    RetCode ret = o.obv(0, priceDatas.size() -1, priceDatas.getCloses(), priceDatas.getVolumesAsDoubles(),
            outBegIdx, outNBElement, outReal);
    ss = new SummaryStatistics();
    for(int i = 0; i< pds.size(); i++) {
      ss.addValue(outReal[i]);
    }
    double gm = ss.getGeometricMean();
    double max = ss.getMax();
    double mean = ss.getMean();
    double min = ss.getMin();
    long len = ss.getN();
    double sd = ss.getStandardDeviation();
    double sum = ss.getSum();
    String summary = ss.toString();
    int i = 1;
  }
  
  public static void main(String[] args) {
    TaLibTest main = new TaLibTest();
    main.doObv();
    //main.setupData();
  }

  public RetCode obv(int startIdx,
          int endIdx,
          //List<PriceBar> pds,
          double inReal[],
          double inVolume[],
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int i;
    int outIdx;
    double prevReal, tempReal, prevOBV;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    //prevOBV = pds.get(startIdx).getVolume();
    prevOBV = inVolume[startIdx];
    //prevReal = pds.get(startIdx).getClose();
    prevReal = inReal[startIdx];
    outIdx = 0;
    for (i = startIdx; i <= endIdx; i++) {
      //tempReal = pds.get(i).getClose();
      tempReal = inReal[i];
      if (tempReal > prevReal) {
        //prevOBV += pds.get(i).getVolume();
        prevOBV += inVolume[i];
      } else if (tempReal < prevReal) {
        //prevOBV -= pds.get(i).getVolume();
        prevOBV -= inVolume[i];
      }
      outReal[outIdx++] = prevOBV;
      prevReal = tempReal;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

}
