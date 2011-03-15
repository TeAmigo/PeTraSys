
package petrasys.indicators.support;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

/**
 * rpc - 3/21/10 3:11 PM - All of the basic indicators needed for more complex ones,
 * @author rickcharon
 */
public class BasicIndicators {

  /**
   * Calculates a single Simple Moving Average for the last value in the inArray,
   * assumes the inArray is the size of the desired MA.
   * @param periods
   * @param inArray
   * @return
   */
  public static double SMAsingle(double[] inArray) {
    double val = 0;
    for (int idx = 0; idx < inArray.length; idx++) {
      val += inArray[idx];
    }
    val = val / inArray.length;
    return val;
  }

  public static double EMAsingle(double exponent, double thisInput, double lastEMA) {
    //Today's Exponential Moving Average=(current day's closing price x Exponent) + 
    //(previous day's EMA x (1-Exponent)) 
    return (thisInput * exponent) + (lastEMA * (1 - exponent));
  }

  /**
   * rpc - 3/21/10 3:21 PM - Given last period close, this period high and low, cal this period ATR.
   * @param pastClose
   * @param high
   * @param low
   * @return
   */
  public static double TRsingle(double pastClose, double high, double low) {
    //double trueHigh = 0, trueLow = 0;
    double thisRange = high - low;
    double ctoh = Math.abs(high - pastClose);
    double ctol = Math.abs(low - pastClose);
    thisRange = Math.max(thisRange, ctoh);
    return Math.max(thisRange, ctol);
  }

  public static double PlusDMsingle(double pastHigh, double thisHigh, double pastLow, double thisLow) {
    double upmove = thisHigh - pastHigh;
    double downmove = pastLow - thisLow;
    return ((upmove > downmove) && (upmove > 0)) ? upmove : 0;
  }

  public static double MinusDMsingle(double pastHigh, double thisHigh, double pastLow, double thisLow) {
    double upmove = thisHigh - pastHigh;
    double downmove = pastLow - thisLow;
    return ((downmove > upmove) && (downmove > 0)) ? downmove : 0;
  }

  /**
   * rpc - 3/24/10 7:21 AM - Welles Wilder - DM(period) = last DM(period) - last DM(period)/period + +DM
   * NOTE: this should work for both +DM(period) and -DM(period).
   * @param period
   * @param lastDM
   * @param thisPeriodDM
   * @return
   */
  public static double SmoothPeriodAverager(int period, double lastDM, double thisPeriodDM) {
    return lastDM - (lastDM / period) + thisPeriodDM;
  }

  /**
   * rpc - 3/24/10 6:27 AM - Welles Wilder - +DM/TR = +DI
   * @param plusDm
   * @param tr
   * @return
   */
  public static double PlusDIsingle(double plusDm, double tr) {
    return plusDm / tr;
  }

  /**
   * rpc - 3/24/10 6:30 AM - Welles Wilder -DM/TR = -DI
   * @param minusDM
   * @param tr
   * @return
   */
  public static double MinusDIsingle(double minusDM, double tr) {
    return minusDM / tr;
  }

  //ADX = 100 times the exponential moving average of the Absolute value of (+DI − -DI) divided by (+DI + -DI)
  public static double ADXsingle(int period, double prevADX, double DXnow) {
    return  ((prevADX * (period - 1)) + DXnow) / period;
  }

  /**
   * rpc - 3/24/10 12:00 PM - gets a DescriptiveStatistics obj inited to inArray
   * @see <a href="http://127.0.0.1:8082/resource/jar%3Afile%3A/share/JavaDev/PeTraSys/lib/LibsLocal/commons-math-2.0-javadoc.jar!/org/apache/commons/math/stat/descriptive/DescriptiveStatistics.html#getSum%28%29"</a>
   * @param inArray
   * @return
   */
  public static DescriptiveStatistics getStats(double[] inArray) {
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (int i = 0; i < inArray.length; i++) {
      stats.addValue(inArray[i]);
    }
    return stats;
  }

  /**
   * rpc - 3/24/10 8:14 AM - Testing...
   * @param args
   */
  public static void main(String[] args) {
    double[] inHigh = {93956.25, 93993.75, 94043.75, 94025, 93962.5, 93906.25, 93750, 93762.5, 93818.75, 93843.75,
      93862.5, 93862.5, 93868.75, 93893.75, 93931.25, 93943.75, 93937.5, 93943.75, 93956.25, 93993.75,
      94031.25, 94031.25, 94037.5, 94012.5, 94018.75, 94037.5, 94050, 94018.75, 93987.5, 93987.5, 93987.5, 93975,
      93975, 93943.75, 93943.75, 93918.75, 93931.25, 93906.25, 93906.25, 93918.75, 93906.25, 93862.5, 93856.25,
      93850, 93868.75, 93900, 93906.25, 93937.5, 93962.5, 93943.75, 93931.25, 93925, 93912.5, 93918.75, 93925,
      93962.5, 94018.75, 94018.75, 93962.5};

    double[] inLow = {93831.25, 93937.5, 93981.25, 93893.75, 93900, 93700, 93700, 93725, 93731.25, 93743.75,
      93787.5, 93806.25, 93806.25, 93831.25, 93868.75, 93900, 93906.25, 93900, 93912.5, 93937.5, 93956.25,
      93956.25, 93975, 93962.5, 93968.75, 93956.25, 94012.5, 93962.5, 93943.75, 93950, 93956.25, 93968.75,
      93925, 93931.25, 93868.75, 93843.75, 93881.25, 93887.5, 93881.25, 93893.75, 93862.5, 93825, 93831.25,
      93818.75, 93843.75, 93862.5, 93875, 93887.5, 93918.75, 93900, 93893.75, 93900, 93893.75, 93900, 93906.25,
      93931.25, 93950, 93937.5, 93943.75};

    double[] inClose = {93956.25, 93962.5, 94000, 93925, 93912.5, 93700, 93731.25, 93743.75, 93762.5, 93806.25,
      93856.25, 93806.25, 93856.25, 93893.75, 93900, 93925, 93912.5, 93912.5, 93937.5, 93987.5, 93956.25, 94012.5,
      93981.25, 94006.25, 93975, 94037.5, 94012.5, 93993.75, 93962.5, 93956.25, 93968.75, 93968.75, 93937.5,
      93937.5, 93875, 93887.5, 93906.25, 93893.75, 93893.75, 93906.25, 93862.5, 93843.75, 93843.75, 93850,
      93868.75, 93862.5, 93881.25, 93918.75, 93925, 93925, 93906.25, 93912.5, 93906.25, 93912.5, 93925, 93956.25,
      94012.5, 93943.75, 93950};

    int period = 10;

    double[] plusDM = new double[inHigh.length];
    double[] minusDM = new double[inHigh.length];
    double[] plusDmSumForPeriod = new double[inHigh.length];
    double[] minusDmSumForPeriod = new double[inHigh.length];
    double[] TR = new double[inHigh.length];
    double[] TRSumForPeriod = new double[inHigh.length];
    double[] plusDI = new double[inHigh.length];
    double[] minusDI = new double[inHigh.length];
    double[] DIDiff = new double[inHigh.length];
    double[] DISum = new double[inHigh.length];
    double[] DX = new double[inHigh.length];
    double[] ADX = new double[inHigh.length];

    // Setup +DM and -DM
    for (int i = 1; i < inHigh.length; i++) {
      plusDM[i] = BasicIndicators.PlusDMsingle(inHigh[i - 1], inHigh[i], inLow[i - 1], inLow[i]);
      minusDM[i] = BasicIndicators.MinusDMsingle(inHigh[i - 1], inHigh[i], inLow[i - 1], inLow[i]);
    }

    // Setup +DM(period) and -DM(period)
    for (int i = 0; i < period; i++) {
      plusDmSumForPeriod[i] = 0.0;
      minusDmSumForPeriod[i] = 0.0;
      ADX[i] = 0.0;
    }


    double[] plusDMslice = new double[period];
    double[] minusDMslice = new double[period];
    System.arraycopy(plusDM, 0, plusDMslice, 0, period);
    System.arraycopy(minusDM, 0, minusDMslice, 0, period);

    plusDmSumForPeriod[period - 1] = BasicIndicators.getStats(plusDMslice).getSum();
    minusDmSumForPeriod[period - 1] = BasicIndicators.getStats(minusDMslice).getSum();

    for (int i = period; i < inHigh.length; i++) {
      plusDmSumForPeriod[i] = BasicIndicators.SmoothPeriodAverager(period, plusDmSumForPeriod[i - 1], plusDM[i]);
      minusDmSumForPeriod[i] = BasicIndicators.SmoothPeriodAverager(period, minusDmSumForPeriod[i - 1], minusDM[i]);
    }

    // Setup the TR and TR(period)
    TR[0] = inHigh[0] - inLow[0];
    for (int i = 1; i < inHigh.length; i++) {
      TR[i] = BasicIndicators.TRsingle(inClose[i - 1], inHigh[i], inLow[i]);
    }

    double[] TRSlice = new double[period];
    System.arraycopy(TR, 0, TRSlice, 0, period);
    TRSumForPeriod[period - 1] = BasicIndicators.getStats(TRSlice).getSum();
    for (int i = period; i < inHigh.length; i++) {
      TRSumForPeriod[i] = BasicIndicators.SmoothPeriodAverager(period, TRSumForPeriod[i - 1], TR[i]);

    }
    // Setup DI's, and DX
    for (int i = period - 1; i < inHigh.length; i++) {
      plusDI[i] = plusDmSumForPeriod[i] / TRSumForPeriod[i] * 100;
      minusDI[i] = minusDmSumForPeriod[i] / TRSumForPeriod[i] * 100;
      DIDiff[i] = Math.abs(plusDI[i] - minusDI[i]);
      DISum[i] = plusDI[i] + minusDI[i];
      DX[i] = DIDiff[i] / DISum[i] * 100;
    }
    //Setup ADX
    double[] DXSlice = new double[period];
    System.arraycopy(DX, period-1, DXSlice, 0, period);
    ADX[(period - 1) * 2 ] = BasicIndicators.getStats(DXSlice).getSum() / period;
    for(int i = (((period - 1) * 2) + 1); i < inHigh.length; i++) {
      ADX[i] = BasicIndicators.ADXsingle(period, ADX[i-1], DX[i]);
    }


      System.out.println("plusDmSumForPeriod:");
    for (int i = 1; i < inHigh.length; i++) {
      System.out.println("[" + i + "]" + plusDmSumForPeriod[i]);
    }

    System.out.println("\nminusDmSumForPeriod:");
    for (int i = 1; i < inHigh.length; i++) {
      System.out.println("[" + i + "]" + minusDmSumForPeriod[i]);
    }

  }
}
