/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class BBands {

  MovingAverages ma;

  public BBands() {
    this.ma = new MovingAverages();
  }

  public RetCode bbands(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          double optInNbDevUp,
          double optInNbDevDn,
          MAType optInMAType,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outRealUpperBand[],
          double outRealMiddleBand[],
          double outRealLowerBand[]) {
    RetCode retCode;
    int i;
    double tempReal, tempReal2;
    double[] tempBuffer1;
    double[] tempBuffer2;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 5;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    if (optInNbDevUp == (-4e+37)) {
      optInNbDevUp = 2.000000e+0;
    } else if ((optInNbDevUp < -3.000000e+37) || (optInNbDevUp > 3.000000e+37)) {
      return RetCode.BadParam;
    }
    if (optInNbDevDn == (-4e+37)) {
      optInNbDevDn = 2.000000e+0;
    } else if ((optInNbDevDn < -3.000000e+37) || (optInNbDevDn > 3.000000e+37)) {
      return RetCode.BadParam;
    }
    if (inReal == outRealUpperBand) {
      tempBuffer1 = outRealMiddleBand;
      tempBuffer2 = outRealLowerBand;
    } else if (inReal == outRealLowerBand) {
      tempBuffer1 = outRealMiddleBand;
      tempBuffer2 = outRealUpperBand;
    } else if (inReal == outRealMiddleBand) {
      tempBuffer1 = outRealLowerBand;
      tempBuffer2 = outRealUpperBand;
    } else {
      tempBuffer1 = outRealMiddleBand;
      tempBuffer2 = outRealUpperBand;
    }
    if ((tempBuffer1 == inReal) || (tempBuffer2 == inReal)) {
      return RetCode.BadParam;
    }
    retCode = ma.movingAverage(startIdx, endIdx, inReal,
            optInTimePeriod, optInMAType,
            outBegIdx, outNBElement, tempBuffer1);
    if ((retCode != RetCode.Success) || ((int) outNBElement.value == 0)) {
      outNBElement.value = 0;
      return retCode;
    }
    if (optInMAType == MAType.Sma) {
      TA_INT_stddev_using_precalc_ma(inReal, tempBuffer1,
              (int) outBegIdx.value, (int) outNBElement.value,
              optInTimePeriod, tempBuffer2);
    } else {
      retCode = stdDev((int) outBegIdx.value, endIdx, inReal,
              optInTimePeriod, 1.0,
              outBegIdx, outNBElement, tempBuffer2);
      if (retCode != RetCode.Success) {
        outNBElement.value = 0;
        return retCode;
      }
    }
    if (tempBuffer1 != outRealMiddleBand) {
      System.arraycopy(tempBuffer1, 0, outRealMiddleBand, 0, outNBElement.value);
    }
    if (optInNbDevUp == optInNbDevDn) {
      if (optInNbDevUp == 1.0) {
        for (i = 0; i < (int) outNBElement.value; i++) {
          tempReal = tempBuffer2[i];
          tempReal2 = outRealMiddleBand[i];
          outRealUpperBand[i] = tempReal2 + tempReal;
          outRealLowerBand[i] = tempReal2 - tempReal;
        }
      } else {
        for (i = 0; i < (int) outNBElement.value; i++) {
          tempReal = tempBuffer2[i] * optInNbDevUp;
          tempReal2 = outRealMiddleBand[i];
          outRealUpperBand[i] = tempReal2 + tempReal;
          outRealLowerBand[i] = tempReal2 - tempReal;
        }
      }
    } else if (optInNbDevUp == 1.0) {
      for (i = 0; i < (int) outNBElement.value; i++) {
        tempReal = tempBuffer2[i];
        tempReal2 = outRealMiddleBand[i];
        outRealUpperBand[i] = tempReal2 + tempReal;
        outRealLowerBand[i] = tempReal2 - (tempReal * optInNbDevDn);
      }
    } else if (optInNbDevDn == 1.0) {
      for (i = 0; i < (int) outNBElement.value; i++) {
        tempReal = tempBuffer2[i];
        tempReal2 = outRealMiddleBand[i];
        outRealLowerBand[i] = tempReal2 - tempReal;
        outRealUpperBand[i] = tempReal2 + (tempReal * optInNbDevUp);
      }
    } else {
      for (i = 0; i < (int) outNBElement.value; i++) {
        tempReal = tempBuffer2[i];
        tempReal2 = outRealMiddleBand[i];
        outRealUpperBand[i] = tempReal2 + (tempReal * optInNbDevUp);
        outRealLowerBand[i] = tempReal2 - (tempReal * optInNbDevDn);
      }
    }
    return RetCode.Success;
  }

  static void TA_INT_stddev_using_precalc_ma(double inReal[],
          double inMovAvg[],
          int inMovAvgBegIdx,
          int inMovAvgNbElement,
          int timePeriod,
          double output[]) {
    double tempReal, periodTotal2, meanValue2;
    int outIdx;
    int startSum, endSum;
    startSum = 1 + inMovAvgBegIdx - timePeriod;
    endSum = inMovAvgBegIdx;
    periodTotal2 = 0;
    for (outIdx = startSum; outIdx < endSum; outIdx++) {
      tempReal = inReal[outIdx];
      tempReal *= tempReal;
      periodTotal2 += tempReal;
    }
    for (outIdx = 0; outIdx < inMovAvgNbElement; outIdx++, startSum++, endSum++) {
      tempReal = inReal[endSum];
      tempReal *= tempReal;
      periodTotal2 += tempReal;
      meanValue2 = periodTotal2 / timePeriod;
      tempReal = inReal[startSum];
      tempReal *= tempReal;
      periodTotal2 -= tempReal;
      tempReal = inMovAvg[outIdx];
      tempReal *= tempReal;
      meanValue2 -= tempReal;
      if (!(meanValue2 < (0.00000000000001))) {
        output[outIdx] = Math.sqrt(meanValue2);
      } else {
        output[outIdx] = (double) 0.0;
      }
    }
  }

  public  RetCode stdDev(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          double optInNbDev,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int i;
    RetCode retCode;
    double tempReal;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 5;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    if (optInNbDev == (-4e+37)) {
      optInNbDev = 1.000000e+0;
    } else if ((optInNbDev < -3.000000e+37) || (optInNbDev > 3.000000e+37)) {
      return RetCode.BadParam;
    }
    retCode = TA_INT_VAR(startIdx, endIdx,
            inReal, optInTimePeriod,
            outBegIdx, outNBElement, outReal);
    if (retCode != RetCode.Success) {
      return retCode;
    }
    if (optInNbDev != 1.0) {
      for (i = 0; i < (int) outNBElement.value; i++) {
        tempReal = outReal[i];
        if (!(tempReal < (0.00000000000001))) {
          outReal[i] = Math.sqrt(tempReal) * optInNbDev;
        } else {
          outReal[i] = (double) 0.0;
        }
      }
    } else {
      for (i = 0; i < (int) outNBElement.value; i++) {
        tempReal = outReal[i];
        if (!(tempReal < (0.00000000000001))) {
          outReal[i] = Math.sqrt(tempReal);
        } else {
          outReal[i] = (double) 0.0;
        }
      }
    }
    return RetCode.Success;
  }

  public RetCode TA_INT_VAR(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double tempReal, periodTotal1, periodTotal2, meanValue1, meanValue2;
    int i, outIdx, trailingIdx, nbInitialElementNeeded;
    nbInitialElementNeeded = (optInTimePeriod - 1);
    if (startIdx < nbInitialElementNeeded) {
      startIdx = nbInitialElementNeeded;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    periodTotal1 = 0;
    periodTotal2 = 0;
    trailingIdx = startIdx - nbInitialElementNeeded;
    i = trailingIdx;
    if (optInTimePeriod > 1) {
      while (i < startIdx) {
        tempReal = inReal[i++];
        periodTotal1 += tempReal;
        tempReal *= tempReal;
        periodTotal2 += tempReal;
      }
    }
    outIdx = 0;
    do {
      tempReal = inReal[i++];
      periodTotal1 += tempReal;
      tempReal *= tempReal;
      periodTotal2 += tempReal;
      meanValue1 = periodTotal1 / optInTimePeriod;
      meanValue2 = periodTotal2 / optInTimePeriod;
      tempReal = inReal[trailingIdx++];
      periodTotal1 -= tempReal;
      tempReal *= tempReal;
      periodTotal2 -= tempReal;
      outReal[outIdx++] = meanValue2 - meanValue1 * meanValue1;
    } while (i <= endIdx);
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }
}
