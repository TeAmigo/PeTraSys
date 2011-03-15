/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class CCI {

  /**
   * rpc - 3/9/10 7:32 PM - Commodity Channel Index (CCI)
   * @see <a href="http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:commodity_channel_in">CCI</a>
   * @param startIdx
   * @param endIdx
   * @param inHigh
   * @param inLow
   * @param inClose
   * @param optInTimePeriod
   * @param outBegIdx
   * @param outNBElement
   * @param outReal
   * @return
   */
  public RetCode cci(int startIdx,
          int endIdx,
          double inHigh[],
          double inLow[],
          double inClose[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double tempReal, tempReal2, theAverage, lastValue;
    int i, j, outIdx, lookbackTotal;
    int circBuffer_Idx = 0;
    double[] circBuffer;
    int maxIdx_circBuffer = (30 - 1);
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    lookbackTotal = (optInTimePeriod - 1);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    {
      if (optInTimePeriod <= 0) {
        return RetCode.AllocErr;
      }
      circBuffer = new double[optInTimePeriod];
      maxIdx_circBuffer = (optInTimePeriod - 1);
    }
    ;
    i = startIdx - lookbackTotal;
    if (optInTimePeriod > 1) {
      while (i < startIdx) {
        circBuffer[circBuffer_Idx] = (inHigh[i] + inLow[i] + inClose[i]) / 3;
        i++;
        {
          circBuffer_Idx++;
          if (circBuffer_Idx > maxIdx_circBuffer) {
            circBuffer_Idx = 0;
          }
        }
        ;
      }
    }
    outIdx = 0;
    do {
      lastValue = (inHigh[i] + inLow[i] + inClose[i]) / 3;
      circBuffer[circBuffer_Idx] = lastValue;
      theAverage = 0;
      for (j = 0; j < optInTimePeriod; j++) {
        theAverage += circBuffer[j];
      }
      theAverage /= optInTimePeriod;
      tempReal2 = 0;
      for (j = 0; j < optInTimePeriod; j++) {
        tempReal2 += Math.abs(circBuffer[j] - theAverage);
      }
      tempReal = lastValue - theAverage;
      if ((tempReal != 0.0) && (tempReal2 != 0.0)) {
        outReal[outIdx++] = tempReal / (0.015 * (tempReal2 / optInTimePeriod));
      } else {
        outReal[outIdx++] = 0.0;
      }
      {
        circBuffer_Idx++;
        if (circBuffer_Idx > maxIdx_circBuffer) {
          circBuffer_Idx = 0;
        }
      }
      ;
      i++;
    } while (i <= endIdx);
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }


}
