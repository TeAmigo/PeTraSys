/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class MovingAverages {
     private int[] unstablePeriod;
     private Compatibility compatibility;

  public MovingAverages() {
  }

  public int movingAverageLookback(int optInTimePeriod,
          MAType optInMAType) {
    int retValue;
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 1) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    if (optInTimePeriod <= 1) {
      return 0;
    }
    switch (optInMAType) {
      case Sma:
        retValue = smaLookback(optInTimePeriod);
        break;
      case Ema:
        retValue = emaLookback(optInTimePeriod);
        break;
      case Wma:
        retValue = wmaLookback(optInTimePeriod);
        break;
      case Dema:
        retValue = demaLookback(optInTimePeriod);
        break;
      case Tema:
        retValue = temaLookback(optInTimePeriod);
        break;
      case Trima:
        retValue = trimaLookback(optInTimePeriod);
        break;
      case Kama:
        retValue = kamaLookback(optInTimePeriod);
        break;
      case Mama:
        retValue = mamaLookback(0.5, 0.05);
        break;
      case T3:
        retValue = t3Lookback(optInTimePeriod, 0.7);
        break;
      default:
        retValue = 0;
    }
    return retValue;
  }

  public RetCode movingAverage(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MAType optInMAType,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] dummyBuffer;
    RetCode retCode;
    int nbElement;
    int outIdx, todayIdx;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 1) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    if (optInTimePeriod == 1) {
      nbElement = endIdx - startIdx + 1;
      outNBElement.value = nbElement;
      for (todayIdx = startIdx, outIdx = 0; outIdx < nbElement; outIdx++, todayIdx++) {
        outReal[outIdx] = inReal[todayIdx];
      }
      outBegIdx.value = startIdx;
      return RetCode.Success;
    }
    switch (optInMAType) {
      case Sma:
        retCode = sma(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Ema:
        retCode = ema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Wma:
        retCode = wma(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Dema:
        retCode = dema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Tema:
        retCode = tema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Trima:
        retCode = trima(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Kama:
        retCode = kama(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Mama:
        dummyBuffer = new double[(endIdx - startIdx + 1)];
        retCode = mama(startIdx, endIdx, inReal, 0.5, 0.05,
                outBegIdx, outNBElement,
                outReal, dummyBuffer);
        break;
      case T3:
        retCode = t3(startIdx, endIdx, inReal,
                optInTimePeriod, 0.7,
                outBegIdx, outNBElement, outReal);
        break;
      default:
        retCode = RetCode.BadParam;
        break;
    }
    return retCode;
  }

  public RetCode movingAverage(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MAType optInMAType,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] dummyBuffer;
    RetCode retCode;
    int nbElement;
    int outIdx, todayIdx;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 1) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    if (optInTimePeriod == 1) {
      nbElement = endIdx - startIdx + 1;
      outNBElement.value = nbElement;
      for (todayIdx = startIdx, outIdx = 0; outIdx < nbElement; outIdx++, todayIdx++) {
        outReal[outIdx] = inReal[todayIdx];
      }
      outBegIdx.value = startIdx;
      return RetCode.Success;
    }
    switch (optInMAType) {
      case Sma:
        retCode = sma(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Ema:
        retCode = ema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Wma:
        retCode = wma(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Dema:
        retCode = dema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Tema:
        retCode = tema(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Trima:
        retCode = trima(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Kama:
        retCode = kama(startIdx, endIdx, inReal, optInTimePeriod,
                outBegIdx, outNBElement, outReal);
        break;
      case Mama:
        dummyBuffer = new double[(endIdx - startIdx + 1)];
        retCode = mama(startIdx, endIdx, inReal, 0.5, 0.05,
                outBegIdx, outNBElement,
                outReal, dummyBuffer);
        break;
      case T3:
        retCode = t3(startIdx, endIdx, inReal,
                optInTimePeriod, 0.7,
                outBegIdx, outNBElement, outReal);
        break;
      default:
        retCode = RetCode.BadParam;
        break;
    }
    return retCode;
  }

  public int emaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1 + (this.unstablePeriod[FuncUnstId.Ema.ordinal()]);
  }

  public RetCode ema(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    return TA_INT_EMA(startIdx, endIdx, inReal,
            optInTimePeriod,
            ((double) 2.0 / ((double) (optInTimePeriod + 1))),
            outBegIdx, outNBElement, outReal);
  }

  public RetCode TA_INT_EMA(int startIdx,
          int endIdx,
          double[] inReal,
          int optInTimePeriod,
          double optInK_1,
          MInteger outBegIdx,
          MInteger outNBElement,
          double[] outReal) {
    double tempReal, prevMA;
    int i, today, outIdx, lookbackTotal;
    lookbackTotal = emaLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    if ((this.compatibility) == Compatibility.Default) {
      today = startIdx - lookbackTotal;
      i = optInTimePeriod;
      tempReal = 0.0;
      while (i-- > 0) {
        tempReal += inReal[today++];
      }
      prevMA = tempReal / optInTimePeriod;
    } else {
      prevMA = inReal[0];
      today = 1;
    }
    while (today <= startIdx) {
      prevMA = ((inReal[today++] - prevMA) * optInK_1) + prevMA;
    }
    outReal[0] = prevMA;
    outIdx = 1;
    while (today <= endIdx) {
      prevMA = ((inReal[today++] - prevMA) * optInK_1) + prevMA;
      outReal[outIdx++] = prevMA;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode ema(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    return TA_INT_EMA(startIdx, endIdx, inReal,
            optInTimePeriod,
            ((double) 2.0 / ((double) (optInTimePeriod + 1))),
            outBegIdx, outNBElement, outReal);
  }

  public RetCode TA_INT_EMA(int startIdx,
          int endIdx,
          float[] inReal,
          int optInTimePeriod,
          double optInK_1,
          MInteger outBegIdx,
          MInteger outNBElement,
          double[] outReal) {
    double tempReal, prevMA;
    int i, today, outIdx, lookbackTotal;
    lookbackTotal = emaLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    if ((this.compatibility) == Compatibility.Default) {
      today = startIdx - lookbackTotal;
      i = optInTimePeriod;
      tempReal = 0.0;
      while (i-- > 0) {
        tempReal += inReal[today++];
      }
      prevMA = tempReal / optInTimePeriod;
    } else {
      prevMA = inReal[0];
      today = 1;
    }
    while (today <= startIdx) {
      prevMA = ((inReal[today++] - prevMA) * optInK_1) + prevMA;
    }
    outReal[0] = prevMA;
    outIdx = 1;
    while (today <= endIdx) {
      prevMA = ((inReal[today++] - prevMA) * optInK_1) + prevMA;
      outReal[outIdx++] = prevMA;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public int smaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode sma(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    return TA_INT_SMA(startIdx, endIdx,
            inReal, optInTimePeriod,
            outBegIdx, outNBElement, outReal);
  }

  RetCode TA_INT_SMA(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double periodTotal, tempReal;
    int i, outIdx, trailingIdx, lookbackTotal;
    lookbackTotal = (optInTimePeriod - 1);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    periodTotal = 0;
    trailingIdx = startIdx - lookbackTotal;
    i = trailingIdx;
    if (optInTimePeriod > 1) {
      while (i < startIdx) {
        periodTotal += inReal[i++];
      }
    }
    outIdx = 0;
    do {
      periodTotal += inReal[i++];
      tempReal = periodTotal;
      periodTotal -= inReal[trailingIdx++];
      outReal[outIdx++] = tempReal / optInTimePeriod;
    } while (i <= endIdx);
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public RetCode sma(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    return TA_INT_SMA(startIdx, endIdx,
            inReal, optInTimePeriod,
            outBegIdx, outNBElement, outReal);
  }

  RetCode TA_INT_SMA(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double periodTotal, tempReal;
    int i, outIdx, trailingIdx, lookbackTotal;
    lookbackTotal = (optInTimePeriod - 1);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    periodTotal = 0;
    trailingIdx = startIdx - lookbackTotal;
    i = trailingIdx;
    if (optInTimePeriod > 1) {
      while (i < startIdx) {
        periodTotal += inReal[i++];
      }
    }
    outIdx = 0;
    do {
      periodTotal += inReal[i++];
      tempReal = periodTotal;
      periodTotal -= inReal[trailingIdx++];
      outReal[outIdx++] = tempReal / optInTimePeriod;
    } while (i <= endIdx);
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public int wmaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode wma(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int inIdx, outIdx, i, trailingIdx, divider;
    double periodSum, periodSub, tempReal, trailingValue;
    int lookbackTotal;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    lookbackTotal = optInTimePeriod - 1;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    if (optInTimePeriod == 1) {
      outBegIdx.value = startIdx;
      outNBElement.value = endIdx - startIdx + 1;
      System.arraycopy(inReal, startIdx, outReal, 0, (int) outNBElement.value);
      return RetCode.Success;
    }
    divider = (optInTimePeriod * (optInTimePeriod + 1)) >> 1;
    outIdx = 0;
    trailingIdx = startIdx - lookbackTotal;
    periodSum = periodSub = (double) 0.0;
    inIdx = trailingIdx;
    i = 1;
    while (inIdx < startIdx) {
      tempReal = inReal[inIdx++];
      periodSub += tempReal;
      periodSum += tempReal * i;
      i++;
    }
    trailingValue = 0.0;
    while (inIdx <= endIdx) {
      tempReal = inReal[inIdx++];
      periodSub += tempReal;
      periodSub -= trailingValue;
      periodSum += tempReal * optInTimePeriod;
      trailingValue = inReal[trailingIdx++];
      outReal[outIdx++] = periodSum / divider;
      periodSum -= periodSub;
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public RetCode wma(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int inIdx, outIdx, i, trailingIdx, divider;
    double periodSum, periodSub, tempReal, trailingValue;
    int lookbackTotal;
    int mmmixi, mmmixdestIdx, mmmixsrcIdx;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    lookbackTotal = optInTimePeriod - 1;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    if (optInTimePeriod == 1) {
      outBegIdx.value = startIdx;
      outNBElement.value = endIdx - startIdx + 1;
      {
        for (mmmixi = 0, mmmixdestIdx = 0, mmmixsrcIdx = startIdx;
                mmmixi < (int) outNBElement.value; mmmixi++, mmmixdestIdx++, mmmixsrcIdx++) {
          outReal[mmmixdestIdx] = inReal[mmmixsrcIdx];
        }
      }
      ;
      return RetCode.Success;
    }
    divider = (optInTimePeriod * (optInTimePeriod + 1)) >> 1;
    outIdx = 0;
    trailingIdx = startIdx - lookbackTotal;
    periodSum = periodSub = (double) 0.0;
    inIdx = trailingIdx;
    i = 1;
    while (inIdx < startIdx) {
      tempReal = inReal[inIdx++];
      periodSub += tempReal;
      periodSum += tempReal * i;
      i++;
    }
    trailingValue = 0.0;
    while (inIdx <= endIdx) {
      tempReal = inReal[inIdx++];
      periodSub += tempReal;
      periodSub -= trailingValue;
      periodSum += tempReal * optInTimePeriod;
      trailingValue = inReal[trailingIdx++];
      outReal[outIdx++] = periodSum / divider;
      periodSum -= periodSub;
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public int demaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return emaLookback(optInTimePeriod) * 2;
  }

  public RetCode dema(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] firstEMA;
    double[] secondEMA;
    double k;
    MInteger firstEMABegIdx = new MInteger();
    MInteger firstEMANbElement = new MInteger();
    MInteger secondEMABegIdx = new MInteger();
    MInteger secondEMANbElement = new MInteger();
    int tempInt, outIdx, firstEMAIdx, lookbackTotal, lookbackEMA;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outNBElement.value = 0;
    outBegIdx.value = 0;
    lookbackEMA = emaLookback(optInTimePeriod);
    lookbackTotal = lookbackEMA * 2;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      return RetCode.Success;
    }
    if (inReal == outReal) {
      firstEMA = outReal;
    } else {
      tempInt = lookbackTotal + (endIdx - startIdx) + 1;
      firstEMA = new double[tempInt];
    }
    k = ((double) 2.0 / ((double) (optInTimePeriod + 1)));
    retCode = TA_INT_EMA(startIdx - lookbackEMA, endIdx, inReal,
            optInTimePeriod, k,
            firstEMABegIdx, firstEMANbElement,
            firstEMA);
    if ((retCode != RetCode.Success) || (firstEMANbElement.value == 0)) {
      return retCode;
    }
    secondEMA = new double[firstEMANbElement.value];
    retCode = TA_INT_EMA(0, firstEMANbElement.value - 1, firstEMA,
            optInTimePeriod, k,
            secondEMABegIdx, secondEMANbElement,
            secondEMA);
    if ((retCode != RetCode.Success) || (secondEMANbElement.value == 0)) {
      return retCode;
    }
    firstEMAIdx = secondEMABegIdx.value;
    outIdx = 0;
    while (outIdx < secondEMANbElement.value) {
      outReal[outIdx] = (2.0 * firstEMA[firstEMAIdx++]) - secondEMA[outIdx];
      outIdx++;
    }
    outBegIdx.value = firstEMABegIdx.value + secondEMABegIdx.value;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode dema(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] firstEMA;
    double[] secondEMA;
    double k;
    MInteger firstEMABegIdx = new MInteger();
    MInteger firstEMANbElement = new MInteger();
    MInteger secondEMABegIdx = new MInteger();
    MInteger secondEMANbElement = new MInteger();
    int tempInt, outIdx, firstEMAIdx, lookbackTotal, lookbackEMA;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outNBElement.value = 0;
    outBegIdx.value = 0;
    lookbackEMA = emaLookback(optInTimePeriod);
    lookbackTotal = lookbackEMA * 2;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      return RetCode.Success;
    }
    tempInt = lookbackTotal + (endIdx - startIdx) + 1;
    firstEMA = new double[tempInt];
    k = ((double) 2.0 / ((double) (optInTimePeriod + 1)));
    retCode = TA_INT_EMA(startIdx - lookbackEMA, endIdx, inReal,
            optInTimePeriod, k,
            firstEMABegIdx, firstEMANbElement,
            firstEMA);
    if ((retCode != RetCode.Success) || (firstEMANbElement.value == 0)) {
      return retCode;
    }
    secondEMA = new double[firstEMANbElement.value];
    retCode = TA_INT_EMA(0, firstEMANbElement.value - 1, firstEMA,
            optInTimePeriod, k,
            secondEMABegIdx, secondEMANbElement,
            secondEMA);
    if ((retCode != RetCode.Success) || (secondEMANbElement.value == 0)) {
      return retCode;
    }
    firstEMAIdx = secondEMABegIdx.value;
    outIdx = 0;
    while (outIdx < secondEMANbElement.value) {
      outReal[outIdx] = (2.0 * firstEMA[firstEMAIdx++]) - secondEMA[outIdx];
      outIdx++;
    }
    outBegIdx.value = firstEMABegIdx.value + secondEMABegIdx.value;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
  /* Generated */

  public int divLookback() {
    return 0;
  }

  public RetCode div(int startIdx,
          int endIdx,
          double inReal0[],
          double inReal1[],
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int i;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    for (i = startIdx, outIdx = 0; i <= endIdx; i++, outIdx++) {
      outReal[outIdx] = inReal0[i] / inReal1[i];
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public int temaLookback(int optInTimePeriod) {
    int retValue;
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    retValue = emaLookback(optInTimePeriod);
    return retValue * 3;
  }

  public RetCode tema(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] firstEMA;
    double[] secondEMA;
    double k;
    MInteger firstEMABegIdx = new MInteger();
    MInteger firstEMANbElement = new MInteger();
    MInteger secondEMABegIdx = new MInteger();
    MInteger secondEMANbElement = new MInteger();
    MInteger thirdEMABegIdx = new MInteger();
    MInteger thirdEMANbElement = new MInteger();
    int tempInt, outIdx, lookbackTotal, lookbackEMA;
    int firstEMAIdx, secondEMAIdx;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outNBElement.value = 0;
    outBegIdx.value = 0;
    lookbackEMA = emaLookback(optInTimePeriod);
    lookbackTotal = lookbackEMA * 3;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      return RetCode.Success;
    }
    tempInt = lookbackTotal + (endIdx - startIdx) + 1;
    firstEMA = new double[tempInt];
    k = ((double) 2.0 / ((double) (optInTimePeriod + 1)));
    retCode = TA_INT_EMA(startIdx - (lookbackEMA * 2), endIdx, inReal,
            optInTimePeriod, k,
            firstEMABegIdx, firstEMANbElement,
            firstEMA);
    if ((retCode != RetCode.Success) || (firstEMANbElement.value == 0)) {
      return retCode;
    }
    secondEMA = new double[firstEMANbElement.value];
    retCode = TA_INT_EMA(0, firstEMANbElement.value - 1, firstEMA,
            optInTimePeriod, k,
            secondEMABegIdx, secondEMANbElement,
            secondEMA);
    if ((retCode != RetCode.Success) || (secondEMANbElement.value == 0)) {
      return retCode;
    }
    retCode = TA_INT_EMA(0, secondEMANbElement.value - 1, secondEMA,
            optInTimePeriod, k,
            thirdEMABegIdx, thirdEMANbElement,
            outReal);
    if ((retCode != RetCode.Success) || (thirdEMANbElement.value == 0)) {
      return retCode;
    }
    firstEMAIdx = thirdEMABegIdx.value + secondEMABegIdx.value;
    secondEMAIdx = thirdEMABegIdx.value;
    outBegIdx.value = firstEMAIdx + firstEMABegIdx.value;
    outIdx = 0;
    while (outIdx < thirdEMANbElement.value) {
      outReal[outIdx] += (3.0 * firstEMA[firstEMAIdx++]) - (3.0 * secondEMA[secondEMAIdx++]);
      outIdx++;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode tema(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] firstEMA;
    double[] secondEMA;
    double k;
    MInteger firstEMABegIdx = new MInteger();
    MInteger firstEMANbElement = new MInteger();
    MInteger secondEMABegIdx = new MInteger();
    MInteger secondEMANbElement = new MInteger();
    MInteger thirdEMABegIdx = new MInteger();
    MInteger thirdEMANbElement = new MInteger();
    int tempInt, outIdx, lookbackTotal, lookbackEMA;
    int firstEMAIdx, secondEMAIdx;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outNBElement.value = 0;
    outBegIdx.value = 0;
    lookbackEMA = emaLookback(optInTimePeriod);
    lookbackTotal = lookbackEMA * 3;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      return RetCode.Success;
    }
    tempInt = lookbackTotal + (endIdx - startIdx) + 1;
    firstEMA = new double[tempInt];
    k = ((double) 2.0 / ((double) (optInTimePeriod + 1)));
    retCode = TA_INT_EMA(startIdx - (lookbackEMA * 2), endIdx, inReal,
            optInTimePeriod, k,
            firstEMABegIdx, firstEMANbElement,
            firstEMA);
    if ((retCode != RetCode.Success) || (firstEMANbElement.value == 0)) {
      return retCode;
    }
    secondEMA = new double[firstEMANbElement.value];
    retCode = TA_INT_EMA(0, firstEMANbElement.value - 1, firstEMA,
            optInTimePeriod, k,
            secondEMABegIdx, secondEMANbElement,
            secondEMA);
    if ((retCode != RetCode.Success) || (secondEMANbElement.value == 0)) {
      return retCode;
    }
    retCode = TA_INT_EMA(0, secondEMANbElement.value - 1, secondEMA,
            optInTimePeriod, k,
            thirdEMABegIdx, thirdEMANbElement,
            outReal);
    if ((retCode != RetCode.Success) || (thirdEMANbElement.value == 0)) {
      return retCode;
    }
    firstEMAIdx = thirdEMABegIdx.value + secondEMABegIdx.value;
    secondEMAIdx = thirdEMABegIdx.value;
    outBegIdx.value = firstEMAIdx + firstEMABegIdx.value;
    outIdx = 0;
    while (outIdx < thirdEMANbElement.value) {
      outReal[outIdx] += (3.0 * firstEMA[firstEMAIdx++]) - (3.0 * secondEMA[secondEMAIdx++]);
      outIdx++;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public int trimaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode trima(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int lookbackTotal;
    double numerator;
    double numeratorSub;
    double numeratorAdd;
    int i, outIdx, todayIdx, trailingIdx, middleIdx;
    double factor, tempReal;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
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
    outIdx = 0;
    if ((optInTimePeriod % 2) == 1) {
      i = (optInTimePeriod >> 1);
      factor = (i + 1) * (i + 1);
      factor = 1.0 / factor;
      trailingIdx = startIdx - lookbackTotal;
      middleIdx = trailingIdx + i;
      todayIdx = middleIdx + i;
      numerator = 0.0;
      numeratorSub = 0.0;
      for (i = middleIdx; i >= trailingIdx; i--) {
        tempReal = inReal[i];
        numeratorSub += tempReal;
        numerator += numeratorSub;
      }
      numeratorAdd = 0.0;
      middleIdx++;
      for (i = middleIdx; i <= todayIdx; i++) {
        tempReal = inReal[i];
        numeratorAdd += tempReal;
        numerator += numeratorAdd;
      }
      outIdx = 0;
      tempReal = inReal[trailingIdx++];
      outReal[outIdx++] = numerator * factor;
      todayIdx++;
      while (todayIdx <= endIdx) {
        numerator -= numeratorSub;
        numeratorSub -= tempReal;
        tempReal = inReal[middleIdx++];
        numeratorSub += tempReal;
        numerator += numeratorAdd;
        numeratorAdd -= tempReal;
        tempReal = inReal[todayIdx++];
        numeratorAdd += tempReal;
        numerator += tempReal;
        tempReal = inReal[trailingIdx++];
        outReal[outIdx++] = numerator * factor;
      }
    } else {
      i = (optInTimePeriod >> 1);
      factor = i * (i + 1);
      factor = 1.0 / factor;
      trailingIdx = startIdx - lookbackTotal;
      middleIdx = trailingIdx + i - 1;
      todayIdx = middleIdx + i;
      numerator = 0.0;
      numeratorSub = 0.0;
      for (i = middleIdx; i >= trailingIdx; i--) {
        tempReal = inReal[i];
        numeratorSub += tempReal;
        numerator += numeratorSub;
      }
      numeratorAdd = 0.0;
      middleIdx++;
      for (i = middleIdx; i <= todayIdx; i++) {
        tempReal = inReal[i];
        numeratorAdd += tempReal;
        numerator += numeratorAdd;
      }
      outIdx = 0;
      tempReal = inReal[trailingIdx++];
      outReal[outIdx++] = numerator * factor;
      todayIdx++;
      while (todayIdx <= endIdx) {
        numerator -= numeratorSub;
        numeratorSub -= tempReal;
        tempReal = inReal[middleIdx++];
        numeratorSub += tempReal;
        numeratorAdd -= tempReal;
        numerator += numeratorAdd;
        tempReal = inReal[todayIdx++];
        numeratorAdd += tempReal;
        numerator += tempReal;
        tempReal = inReal[trailingIdx++];
        outReal[outIdx++] = numerator * factor;
      }
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public RetCode trima(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int lookbackTotal;
    double numerator;
    double numeratorSub;
    double numeratorAdd;
    int i, outIdx, todayIdx, trailingIdx, middleIdx;
    double factor, tempReal;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
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
    outIdx = 0;
    if ((optInTimePeriod % 2) == 1) {
      i = (optInTimePeriod >> 1);
      factor = (i + 1) * (i + 1);
      factor = 1.0 / factor;
      trailingIdx = startIdx - lookbackTotal;
      middleIdx = trailingIdx + i;
      todayIdx = middleIdx + i;
      numerator = 0.0;
      numeratorSub = 0.0;
      for (i = middleIdx; i >= trailingIdx; i--) {
        tempReal = inReal[i];
        numeratorSub += tempReal;
        numerator += numeratorSub;
      }
      numeratorAdd = 0.0;
      middleIdx++;
      for (i = middleIdx; i <= todayIdx; i++) {
        tempReal = inReal[i];
        numeratorAdd += tempReal;
        numerator += numeratorAdd;
      }
      outIdx = 0;
      tempReal = inReal[trailingIdx++];
      outReal[outIdx++] = numerator * factor;
      todayIdx++;
      while (todayIdx <= endIdx) {
        numerator -= numeratorSub;
        numeratorSub -= tempReal;
        tempReal = inReal[middleIdx++];
        numeratorSub += tempReal;
        numerator += numeratorAdd;
        numeratorAdd -= tempReal;
        tempReal = inReal[todayIdx++];
        numeratorAdd += tempReal;
        numerator += tempReal;
        tempReal = inReal[trailingIdx++];
        outReal[outIdx++] = numerator * factor;
      }
    } else {
      i = (optInTimePeriod >> 1);
      factor = i * (i + 1);
      factor = 1.0 / factor;
      trailingIdx = startIdx - lookbackTotal;
      middleIdx = trailingIdx + i - 1;
      todayIdx = middleIdx + i;
      numerator = 0.0;
      numeratorSub = 0.0;
      for (i = middleIdx; i >= trailingIdx; i--) {
        tempReal = inReal[i];
        numeratorSub += tempReal;
        numerator += numeratorSub;
      }
      numeratorAdd = 0.0;
      middleIdx++;
      for (i = middleIdx; i <= todayIdx; i++) {
        tempReal = inReal[i];
        numeratorAdd += tempReal;
        numerator += numeratorAdd;
      }
      outIdx = 0;
      tempReal = inReal[trailingIdx++];
      outReal[outIdx++] = numerator * factor;
      todayIdx++;
      while (todayIdx <= endIdx) {
        numerator -= numeratorSub;
        numeratorSub -= tempReal;
        tempReal = inReal[middleIdx++];
        numeratorSub += tempReal;
        numeratorAdd -= tempReal;
        numerator += numeratorAdd;
        tempReal = inReal[todayIdx++];
        numeratorAdd += tempReal;
        numerator += tempReal;
        tempReal = inReal[trailingIdx++];
        outReal[outIdx++] = numerator * factor;
      }
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

  public int kamaLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod + (this.unstablePeriod[FuncUnstId.Kama.ordinal()]);
  }

  public RetCode kama(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    final double constMax = 2.0 / (30.0 + 1.0);
    final double constDiff = 2.0 / (2.0 + 1.0) - constMax;
    double tempReal, tempReal2;
    double sumROC1, periodROC, prevKAMA;
    int i, today, outIdx, lookbackTotal;
    int trailingIdx;
    double trailingValue;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outBegIdx.value = 0;
    outNBElement.value = 0;
    lookbackTotal = optInTimePeriod + (this.unstablePeriod[FuncUnstId.Kama.ordinal()]);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    sumROC1 = 0.0;
    today = startIdx - lookbackTotal;
    trailingIdx = today;
    i = optInTimePeriod;
    while (i-- > 0) {
      tempReal = inReal[today++];
      tempReal -= inReal[today];
      sumROC1 += Math.abs(tempReal);
    }
    prevKAMA = inReal[today - 1];
    tempReal = inReal[today];
    tempReal2 = inReal[trailingIdx++];
    periodROC = tempReal - tempReal2;
    trailingValue = tempReal2;
    if ((sumROC1 <= periodROC) || (((-(0.00000000000001)) < sumROC1)
            && (sumROC1 < (0.00000000000001)))) {
      tempReal = 1.0;
    } else {
      tempReal = Math.abs(periodROC / sumROC1);
    }
    tempReal = (tempReal * constDiff) + constMax;
    tempReal *= tempReal;
    prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
    while (today <= startIdx) {
      tempReal = inReal[today];
      tempReal2 = inReal[trailingIdx++];
      periodROC = tempReal - tempReal2;
      sumROC1 -= Math.abs(trailingValue - tempReal2);
      sumROC1 += Math.abs(tempReal - inReal[today - 1]);
      trailingValue = tempReal2;
      if ((sumROC1 <= periodROC) || (((-(0.00000000000001)) < sumROC1)
              && (sumROC1 < (0.00000000000001)))) {
        tempReal = 1.0;
      } else {
        tempReal = Math.abs(periodROC / sumROC1);
      }
      tempReal = (tempReal * constDiff) + constMax;
      tempReal *= tempReal;
      prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
    }
    outReal[0] = prevKAMA;
    outIdx = 1;
    outBegIdx.value = today - 1;
    while (today <= endIdx) {
      tempReal = inReal[today];
      tempReal2 = inReal[trailingIdx++];
      periodROC = tempReal - tempReal2;
      sumROC1 -= Math.abs(trailingValue - tempReal2);
      sumROC1 += Math.abs(tempReal - inReal[today - 1]);
      trailingValue = tempReal2;
      if ((sumROC1 <= periodROC) || (((-(0.00000000000001)) < sumROC1)
              && (sumROC1 < (0.00000000000001)))) {
        tempReal = 1.0;
      } else {
        tempReal = Math.abs(periodROC / sumROC1);
      }
      tempReal = (tempReal * constDiff) + constMax;
      tempReal *= tempReal;
      prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
      outReal[outIdx++] = prevKAMA;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode kama(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    final double constMax = 2.0 / (30.0 + 1.0);
    final double constDiff = 2.0 / (2.0 + 1.0) - constMax;
    double tempReal, tempReal2;
    double sumROC1, periodROC, prevKAMA;
    int i, today, outIdx, lookbackTotal;
    int trailingIdx;
    double trailingValue;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 30;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    outBegIdx.value = 0;
    outNBElement.value = 0;
    lookbackTotal = optInTimePeriod + (this.unstablePeriod[FuncUnstId.Kama.ordinal()]);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    sumROC1 = 0.0;
    today = startIdx - lookbackTotal;
    trailingIdx = today;
    i = optInTimePeriod;
    while (i-- > 0) {
      tempReal = inReal[today++];
      tempReal -= inReal[today];
      sumROC1 += Math.abs(tempReal);
    }
    prevKAMA = inReal[today - 1];
    tempReal = inReal[today];
    tempReal2 = inReal[trailingIdx++];
    periodROC = tempReal - tempReal2;
    trailingValue = tempReal2;
    if ((sumROC1 <= periodROC) || (((-(0.00000000000001)) < sumROC1) && (sumROC1 < (0.00000000000001)))) {
      tempReal = 1.0;
    } else {
      tempReal = Math.abs(periodROC / sumROC1);
    }
    tempReal = (tempReal * constDiff) + constMax;
    tempReal *= tempReal;
    prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
    while (today <= startIdx) {
      tempReal = inReal[today];
      tempReal2 = inReal[trailingIdx++];
      periodROC = tempReal - tempReal2;
      sumROC1 -= Math.abs(trailingValue - tempReal2);
      sumROC1 += Math.abs(tempReal - inReal[today - 1]);
      trailingValue = tempReal2;
      if ((sumROC1 <= periodROC)
              || (((-(0.00000000000001)) < sumROC1) && (sumROC1 < (0.00000000000001)))) {
        tempReal = 1.0;
      } else {
        tempReal = Math.abs(periodROC / sumROC1);
      }
      tempReal = (tempReal * constDiff) + constMax;
      tempReal *= tempReal;
      prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
    }
    outReal[0] = prevKAMA;
    outIdx = 1;
    outBegIdx.value = today - 1;
    while (today <= endIdx) {
      tempReal = inReal[today];
      tempReal2 = inReal[trailingIdx++];
      periodROC = tempReal - tempReal2;
      sumROC1 -= Math.abs(trailingValue - tempReal2);
      sumROC1 += Math.abs(tempReal - inReal[today - 1]);
      trailingValue = tempReal2;
      if ((sumROC1 <= periodROC) || (((-(0.00000000000001)) < sumROC1) && (sumROC1 < (0.00000000000001)))) {
        tempReal = 1.0;
      } else {
        tempReal = Math.abs(periodROC / sumROC1);
      }
      tempReal = (tempReal * constDiff) + constMax;
      tempReal *= tempReal;
      prevKAMA = ((inReal[today++] - prevKAMA) * tempReal) + prevKAMA;
      outReal[outIdx++] = prevKAMA;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public int mamaLookback(double optInFastLimit,
          double optInSlowLimit) {
    if (optInFastLimit == (-4e+37)) {
      optInFastLimit = 5.000000e-1;
    } else if ((optInFastLimit < 1.000000e-2) || (optInFastLimit > 9.900000e-1)) {
      return -1;
    }
    if (optInSlowLimit == (-4e+37)) {
      optInSlowLimit = 5.000000e-2;
    } else if ((optInSlowLimit < 1.000000e-2) || (optInSlowLimit > 9.900000e-1)) {
      return -1;
    }
    return 32 + (this.unstablePeriod[FuncUnstId.Mama.ordinal()]);
  }

  public RetCode mama(int startIdx,
          int endIdx,
          double inReal[],
          double optInFastLimit,
          double optInSlowLimit,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outMAMA[],
          double outFAMA[]) {
    int outIdx, i;
    int lookbackTotal, today;
    double tempReal, tempReal2;
    double adjustedPrevPeriod, period;
    int trailingWMAIdx;
    double periodWMASum, periodWMASub, trailingWMAValue;
    double smoothedValue;
    final double a = 0.0962;
    final double b = 0.5769;
    double hilbertTempReal;
    int hilbertIdx;
    double[] detrender_Odd = new double[3];
    double[] detrender_Even = new double[3];
    double detrender;
    double prev_detrender_Odd;
    double prev_detrender_Even;
    double prev_detrender_input_Odd;
    double prev_detrender_input_Even;
    double[] Q1_Odd = new double[3];
    double[] Q1_Even = new double[3];
    double Q1;
    double prev_Q1_Odd;
    double prev_Q1_Even;
    double prev_Q1_input_Odd;
    double prev_Q1_input_Even;
    double[] jI_Odd = new double[3];
    double[] jI_Even = new double[3];
    double jI;
    double prev_jI_Odd;
    double prev_jI_Even;
    double prev_jI_input_Odd;
    double prev_jI_input_Even;
    double[] jQ_Odd = new double[3];
    double[] jQ_Even = new double[3];
    double jQ;
    double prev_jQ_Odd;
    double prev_jQ_Even;
    double prev_jQ_input_Odd;
    double prev_jQ_input_Even;
    double Q2, I2, prevQ2, prevI2, Re, Im;
    double I1ForOddPrev2, I1ForOddPrev3;
    double I1ForEvenPrev2, I1ForEvenPrev3;
    double rad2Deg;
    double mama, fama, todayValue, prevPhase;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if (optInFastLimit == (-4e+37)) {
      optInFastLimit = 5.000000e-1;
    } else if ((optInFastLimit < 1.000000e-2) || (optInFastLimit > 9.900000e-1)) {
      return RetCode.BadParam;
    }
    if (optInSlowLimit == (-4e+37)) {
      optInSlowLimit = 5.000000e-2;
    } else if ((optInSlowLimit < 1.000000e-2) || (optInSlowLimit > 9.900000e-1)) {
      return RetCode.BadParam;
    }
    rad2Deg = 180.0 / (4.0 * Math.atan(1));
    lookbackTotal = 32 + (this.unstablePeriod[FuncUnstId.Mama.ordinal()]);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    trailingWMAIdx = startIdx - lookbackTotal;
    today = trailingWMAIdx;
    tempReal = inReal[today++];
    periodWMASub = tempReal;
    periodWMASum = tempReal;
    tempReal = inReal[today++];
    periodWMASub += tempReal;
    periodWMASum += tempReal * 2.0;
    tempReal = inReal[today++];
    periodWMASub += tempReal;
    periodWMASum += tempReal * 3.0;
    trailingWMAValue = 0.0;
    i = 9;
    do {
      tempReal = inReal[today++];
      {
        periodWMASub += tempReal;
        periodWMASub -= trailingWMAValue;
        periodWMASum += tempReal * 4.0;
        trailingWMAValue = inReal[trailingWMAIdx++];
        smoothedValue = periodWMASum * 0.1;
        periodWMASum -= periodWMASub;
      }
      ;
    } while (--i != 0);
    hilbertIdx = 0;
    {
      detrender_Odd[0] = 0.0;
      detrender_Odd[1] = 0.0;
      detrender_Odd[2] = 0.0;
      detrender_Even[0] = 0.0;
      detrender_Even[1] = 0.0;
      detrender_Even[2] = 0.0;
      detrender = 0.0;
      prev_detrender_Odd = 0.0;
      prev_detrender_Even = 0.0;
      prev_detrender_input_Odd = 0.0;
      prev_detrender_input_Even = 0.0;
    }
    ;
    {
      Q1_Odd[0] = 0.0;
      Q1_Odd[1] = 0.0;
      Q1_Odd[2] = 0.0;
      Q1_Even[0] = 0.0;
      Q1_Even[1] = 0.0;
      Q1_Even[2] = 0.0;
      Q1 = 0.0;
      prev_Q1_Odd = 0.0;
      prev_Q1_Even = 0.0;
      prev_Q1_input_Odd = 0.0;
      prev_Q1_input_Even = 0.0;
    }
    ;
    {
      jI_Odd[0] = 0.0;
      jI_Odd[1] = 0.0;
      jI_Odd[2] = 0.0;
      jI_Even[0] = 0.0;
      jI_Even[1] = 0.0;
      jI_Even[2] = 0.0;
      jI = 0.0;
      prev_jI_Odd = 0.0;
      prev_jI_Even = 0.0;
      prev_jI_input_Odd = 0.0;
      prev_jI_input_Even = 0.0;
    }
    ;
    {
      jQ_Odd[0] = 0.0;
      jQ_Odd[1] = 0.0;
      jQ_Odd[2] = 0.0;
      jQ_Even[0] = 0.0;
      jQ_Even[1] = 0.0;
      jQ_Even[2] = 0.0;
      jQ = 0.0;
      prev_jQ_Odd = 0.0;
      prev_jQ_Even = 0.0;
      prev_jQ_input_Odd = 0.0;
      prev_jQ_input_Even = 0.0;
    }
    ;
    period = 0.0;
    outIdx = 0;
    prevI2 = prevQ2 = 0.0;
    Re = Im = 0.0;
    mama = fama = 0.0;
    I1ForOddPrev3 = I1ForEvenPrev3 = 0.0;
    I1ForOddPrev2 = I1ForEvenPrev2 = 0.0;
    prevPhase = 0.0;
    while (today <= endIdx) {
      adjustedPrevPeriod = (0.075 * period) + 0.54;
      todayValue = inReal[today];
      {
        periodWMASub += todayValue;
        periodWMASub -= trailingWMAValue;
        periodWMASum += todayValue * 4.0;
        trailingWMAValue = inReal[trailingWMAIdx++];
        smoothedValue = periodWMASum * 0.1;
        periodWMASum -= periodWMASub;
      }
      ;
      if ((today % 2) == 0) {
        {
          hilbertTempReal = a * smoothedValue;
          detrender = -detrender_Even[hilbertIdx];
          detrender_Even[hilbertIdx] = hilbertTempReal;
          detrender += hilbertTempReal;
          detrender -= prev_detrender_Even;
          prev_detrender_Even = b * prev_detrender_input_Even;
          detrender += prev_detrender_Even;
          prev_detrender_input_Even = smoothedValue;
          detrender *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * detrender;
          Q1 = -Q1_Even[hilbertIdx];
          Q1_Even[hilbertIdx] = hilbertTempReal;
          Q1 += hilbertTempReal;
          Q1 -= prev_Q1_Even;
          prev_Q1_Even = b * prev_Q1_input_Even;
          Q1 += prev_Q1_Even;
          prev_Q1_input_Even = detrender;
          Q1 *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * I1ForEvenPrev3;
          jI = -jI_Even[hilbertIdx];
          jI_Even[hilbertIdx] = hilbertTempReal;
          jI += hilbertTempReal;
          jI -= prev_jI_Even;
          prev_jI_Even = b * prev_jI_input_Even;
          jI += prev_jI_Even;
          prev_jI_input_Even = I1ForEvenPrev3;
          jI *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * Q1;
          jQ = -jQ_Even[hilbertIdx];
          jQ_Even[hilbertIdx] = hilbertTempReal;
          jQ += hilbertTempReal;
          jQ -= prev_jQ_Even;
          prev_jQ_Even = b * prev_jQ_input_Even;
          jQ += prev_jQ_Even;
          prev_jQ_input_Even = Q1;
          jQ *= adjustedPrevPeriod;
        }
        ;
        if (++hilbertIdx == 3) {
          hilbertIdx = 0;
        }
        Q2 = (0.2 * (Q1 + jI)) + (0.8 * prevQ2);
        I2 = (0.2 * (I1ForEvenPrev3 - jQ)) + (0.8 * prevI2);
        I1ForOddPrev3 = I1ForOddPrev2;
        I1ForOddPrev2 = detrender;
        if (I1ForEvenPrev3 != 0.0) {
          tempReal2 = (Math.atan(Q1 / I1ForEvenPrev3) * rad2Deg);
        } else {
          tempReal2 = 0.0;
        }
      } else {
        {
          hilbertTempReal = a * smoothedValue;
          detrender = -detrender_Odd[hilbertIdx];
          detrender_Odd[hilbertIdx] = hilbertTempReal;
          detrender += hilbertTempReal;
          detrender -= prev_detrender_Odd;
          prev_detrender_Odd = b * prev_detrender_input_Odd;
          detrender += prev_detrender_Odd;
          prev_detrender_input_Odd = smoothedValue;
          detrender *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * detrender;
          Q1 = -Q1_Odd[hilbertIdx];
          Q1_Odd[hilbertIdx] = hilbertTempReal;
          Q1 += hilbertTempReal;
          Q1 -= prev_Q1_Odd;
          prev_Q1_Odd = b * prev_Q1_input_Odd;
          Q1 += prev_Q1_Odd;
          prev_Q1_input_Odd = detrender;
          Q1 *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * I1ForOddPrev3;
          jI = -jI_Odd[hilbertIdx];
          jI_Odd[hilbertIdx] = hilbertTempReal;
          jI += hilbertTempReal;
          jI -= prev_jI_Odd;
          prev_jI_Odd = b * prev_jI_input_Odd;
          jI += prev_jI_Odd;
          prev_jI_input_Odd = I1ForOddPrev3;
          jI *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * Q1;
          jQ = -jQ_Odd[hilbertIdx];
          jQ_Odd[hilbertIdx] = hilbertTempReal;
          jQ += hilbertTempReal;
          jQ -= prev_jQ_Odd;
          prev_jQ_Odd = b * prev_jQ_input_Odd;
          jQ += prev_jQ_Odd;
          prev_jQ_input_Odd = Q1;
          jQ *= adjustedPrevPeriod;
        }
        ;
        Q2 = (0.2 * (Q1 + jI)) + (0.8 * prevQ2);
        I2 = (0.2 * (I1ForOddPrev3 - jQ)) + (0.8 * prevI2);
        I1ForEvenPrev3 = I1ForEvenPrev2;
        I1ForEvenPrev2 = detrender;
        if (I1ForOddPrev3 != 0.0) {
          tempReal2 = (Math.atan(Q1 / I1ForOddPrev3) * rad2Deg);
        } else {
          tempReal2 = 0.0;
        }
      }
      tempReal = prevPhase - tempReal2;
      prevPhase = tempReal2;
      if (tempReal < 1.0) {
        tempReal = 1.0;
      }
      if (tempReal > 1.0) {
        tempReal = optInFastLimit / tempReal;
        if (tempReal < optInSlowLimit) {
          tempReal = optInSlowLimit;
        }
      } else {
        tempReal = optInFastLimit;
      }
      mama = (tempReal * todayValue) + ((1 - tempReal) * mama);
      tempReal *= 0.5;
      fama = (tempReal * mama) + ((1 - tempReal) * fama);
      if (today >= startIdx) {
        outMAMA[outIdx] = mama;
        outFAMA[outIdx++] = fama;
      }
      Re = (0.2 * ((I2 * prevI2) + (Q2 * prevQ2))) + (0.8 * Re);
      Im = (0.2 * ((I2 * prevQ2) - (Q2 * prevI2))) + (0.8 * Im);
      prevQ2 = Q2;
      prevI2 = I2;
      tempReal = period;
      if ((Im != 0.0) && (Re != 0.0)) {
        period = 360.0 / (Math.atan(Im / Re) * rad2Deg);
      }
      tempReal2 = 1.5 * tempReal;
      if (period > tempReal2) {
        period = tempReal2;
      }
      tempReal2 = 0.67 * tempReal;
      if (period < tempReal2) {
        period = tempReal2;
      }
      if (period < 6) {
        period = 6;
      } else if (period > 50) {
        period = 50;
      }
      period = (0.2 * period) + (0.8 * tempReal);
      today++;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode mama(int startIdx,
          int endIdx,
          float inReal[],
          double optInFastLimit,
          double optInSlowLimit,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outMAMA[],
          double outFAMA[]) {
    int outIdx, i;
    int lookbackTotal, today;
    double tempReal, tempReal2;
    double adjustedPrevPeriod, period;
    int trailingWMAIdx;
    double periodWMASum, periodWMASub, trailingWMAValue;
    double smoothedValue;
    final double a = 0.0962;
    final double b = 0.5769;
    double hilbertTempReal;
    int hilbertIdx;
    double[] detrender_Odd = new double[3];
    double[] detrender_Even = new double[3];
    double detrender;
    double prev_detrender_Odd;
    double prev_detrender_Even;
    double prev_detrender_input_Odd;
    double prev_detrender_input_Even;
    double[] Q1_Odd = new double[3];
    double[] Q1_Even = new double[3];
    double Q1;
    double prev_Q1_Odd;
    double prev_Q1_Even;
    double prev_Q1_input_Odd;
    double prev_Q1_input_Even;
    double[] jI_Odd = new double[3];
    double[] jI_Even = new double[3];
    double jI;
    double prev_jI_Odd;
    double prev_jI_Even;
    double prev_jI_input_Odd;
    double prev_jI_input_Even;
    double[] jQ_Odd = new double[3];
    double[] jQ_Even = new double[3];
    double jQ;
    double prev_jQ_Odd;
    double prev_jQ_Even;
    double prev_jQ_input_Odd;
    double prev_jQ_input_Even;
    double Q2, I2, prevQ2, prevI2, Re, Im;
    double I1ForOddPrev2, I1ForOddPrev3;
    double I1ForEvenPrev2, I1ForEvenPrev3;
    double rad2Deg;
    double mama, fama, todayValue, prevPhase;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if (optInFastLimit == (-4e+37)) {
      optInFastLimit = 5.000000e-1;
    } else if ((optInFastLimit < 1.000000e-2) || (optInFastLimit > 9.900000e-1)) {
      return RetCode.BadParam;
    }
    if (optInSlowLimit == (-4e+37)) {
      optInSlowLimit = 5.000000e-2;
    } else if ((optInSlowLimit < 1.000000e-2) || (optInSlowLimit > 9.900000e-1)) {
      return RetCode.BadParam;
    }
    rad2Deg = 180.0 / (4.0 * Math.atan(1));
    lookbackTotal = 32 + (this.unstablePeriod[FuncUnstId.Mama.ordinal()]);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    trailingWMAIdx = startIdx - lookbackTotal;
    today = trailingWMAIdx;
    tempReal = inReal[today++];
    periodWMASub = tempReal;
    periodWMASum = tempReal;
    tempReal = inReal[today++];
    periodWMASub += tempReal;
    periodWMASum += tempReal * 2.0;
    tempReal = inReal[today++];
    periodWMASub += tempReal;
    periodWMASum += tempReal * 3.0;
    trailingWMAValue = 0.0;
    i = 9;
    do {
      tempReal = inReal[today++];
      {
        periodWMASub += tempReal;
        periodWMASub -= trailingWMAValue;
        periodWMASum += tempReal * 4.0;
        trailingWMAValue = inReal[trailingWMAIdx++];
        smoothedValue = periodWMASum * 0.1;
        periodWMASum -= periodWMASub;
      }
      ;
    } while (--i != 0);
    hilbertIdx = 0;
    {
      detrender_Odd[0] = 0.0;
      detrender_Odd[1] = 0.0;
      detrender_Odd[2] = 0.0;
      detrender_Even[0] = 0.0;
      detrender_Even[1] = 0.0;
      detrender_Even[2] = 0.0;
      detrender = 0.0;
      prev_detrender_Odd = 0.0;
      prev_detrender_Even = 0.0;
      prev_detrender_input_Odd = 0.0;
      prev_detrender_input_Even = 0.0;
    }
    ;
    {
      Q1_Odd[0] = 0.0;
      Q1_Odd[1] = 0.0;
      Q1_Odd[2] = 0.0;
      Q1_Even[0] = 0.0;
      Q1_Even[1] = 0.0;
      Q1_Even[2] = 0.0;
      Q1 = 0.0;
      prev_Q1_Odd = 0.0;
      prev_Q1_Even = 0.0;
      prev_Q1_input_Odd = 0.0;
      prev_Q1_input_Even = 0.0;
    }
    ;
    {
      jI_Odd[0] = 0.0;
      jI_Odd[1] = 0.0;
      jI_Odd[2] = 0.0;
      jI_Even[0] = 0.0;
      jI_Even[1] = 0.0;
      jI_Even[2] = 0.0;
      jI = 0.0;
      prev_jI_Odd = 0.0;
      prev_jI_Even = 0.0;
      prev_jI_input_Odd = 0.0;
      prev_jI_input_Even = 0.0;
    }
    ;
    {
      jQ_Odd[0] = 0.0;
      jQ_Odd[1] = 0.0;
      jQ_Odd[2] = 0.0;
      jQ_Even[0] = 0.0;
      jQ_Even[1] = 0.0;
      jQ_Even[2] = 0.0;
      jQ = 0.0;
      prev_jQ_Odd = 0.0;
      prev_jQ_Even = 0.0;
      prev_jQ_input_Odd = 0.0;
      prev_jQ_input_Even = 0.0;
    }
    ;
    period = 0.0;
    outIdx = 0;
    prevI2 = prevQ2 = 0.0;
    Re = Im = 0.0;
    mama = fama = 0.0;
    I1ForOddPrev3 = I1ForEvenPrev3 = 0.0;
    I1ForOddPrev2 = I1ForEvenPrev2 = 0.0;
    prevPhase = 0.0;
    while (today <= endIdx) {
      adjustedPrevPeriod = (0.075 * period) + 0.54;
      todayValue = inReal[today];
      {
        periodWMASub += todayValue;
        periodWMASub -= trailingWMAValue;
        periodWMASum += todayValue * 4.0;
        trailingWMAValue = inReal[trailingWMAIdx++];
        smoothedValue = periodWMASum * 0.1;
        periodWMASum -= periodWMASub;
      }
      ;
      if ((today % 2) == 0) {
        {
          hilbertTempReal = a * smoothedValue;
          detrender = -detrender_Even[hilbertIdx];
          detrender_Even[hilbertIdx] = hilbertTempReal;
          detrender += hilbertTempReal;
          detrender -= prev_detrender_Even;
          prev_detrender_Even = b * prev_detrender_input_Even;
          detrender += prev_detrender_Even;
          prev_detrender_input_Even = smoothedValue;
          detrender *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * detrender;
          Q1 = -Q1_Even[hilbertIdx];
          Q1_Even[hilbertIdx] = hilbertTempReal;
          Q1 += hilbertTempReal;
          Q1 -= prev_Q1_Even;
          prev_Q1_Even = b * prev_Q1_input_Even;
          Q1 += prev_Q1_Even;
          prev_Q1_input_Even = detrender;
          Q1 *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * I1ForEvenPrev3;
          jI = -jI_Even[hilbertIdx];
          jI_Even[hilbertIdx] = hilbertTempReal;
          jI += hilbertTempReal;
          jI -= prev_jI_Even;
          prev_jI_Even = b * prev_jI_input_Even;
          jI += prev_jI_Even;
          prev_jI_input_Even = I1ForEvenPrev3;
          jI *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * Q1;
          jQ = -jQ_Even[hilbertIdx];
          jQ_Even[hilbertIdx] = hilbertTempReal;
          jQ += hilbertTempReal;
          jQ -= prev_jQ_Even;
          prev_jQ_Even = b * prev_jQ_input_Even;
          jQ += prev_jQ_Even;
          prev_jQ_input_Even = Q1;
          jQ *= adjustedPrevPeriod;
        }
        ;
        if (++hilbertIdx == 3) {
          hilbertIdx = 0;
        }
        Q2 = (0.2 * (Q1 + jI)) + (0.8 * prevQ2);
        I2 = (0.2 * (I1ForEvenPrev3 - jQ)) + (0.8 * prevI2);
        I1ForOddPrev3 = I1ForOddPrev2;
        I1ForOddPrev2 = detrender;
        if (I1ForEvenPrev3 != 0.0) {
          tempReal2 = (Math.atan(Q1 / I1ForEvenPrev3) * rad2Deg);
        } else {
          tempReal2 = 0.0;
        }
      } else {
        {
          hilbertTempReal = a * smoothedValue;
          detrender = -detrender_Odd[hilbertIdx];
          detrender_Odd[hilbertIdx] = hilbertTempReal;
          detrender += hilbertTempReal;
          detrender -= prev_detrender_Odd;
          prev_detrender_Odd = b * prev_detrender_input_Odd;
          detrender += prev_detrender_Odd;
          prev_detrender_input_Odd = smoothedValue;
          detrender *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * detrender;
          Q1 = -Q1_Odd[hilbertIdx];
          Q1_Odd[hilbertIdx] = hilbertTempReal;
          Q1 += hilbertTempReal;
          Q1 -= prev_Q1_Odd;
          prev_Q1_Odd = b * prev_Q1_input_Odd;
          Q1 += prev_Q1_Odd;
          prev_Q1_input_Odd = detrender;
          Q1 *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * I1ForOddPrev3;
          jI = -jI_Odd[hilbertIdx];
          jI_Odd[hilbertIdx] = hilbertTempReal;
          jI += hilbertTempReal;
          jI -= prev_jI_Odd;
          prev_jI_Odd = b * prev_jI_input_Odd;
          jI += prev_jI_Odd;
          prev_jI_input_Odd = I1ForOddPrev3;
          jI *= adjustedPrevPeriod;
        }
        ;
        {
          hilbertTempReal = a * Q1;
          jQ = -jQ_Odd[hilbertIdx];
          jQ_Odd[hilbertIdx] = hilbertTempReal;
          jQ += hilbertTempReal;
          jQ -= prev_jQ_Odd;
          prev_jQ_Odd = b * prev_jQ_input_Odd;
          jQ += prev_jQ_Odd;
          prev_jQ_input_Odd = Q1;
          jQ *= adjustedPrevPeriod;
        }
        ;
        Q2 = (0.2 * (Q1 + jI)) + (0.8 * prevQ2);
        I2 = (0.2 * (I1ForOddPrev3 - jQ)) + (0.8 * prevI2);
        I1ForEvenPrev3 = I1ForEvenPrev2;
        I1ForEvenPrev2 = detrender;
        if (I1ForOddPrev3 != 0.0) {
          tempReal2 = (Math.atan(Q1 / I1ForOddPrev3) * rad2Deg);
        } else {
          tempReal2 = 0.0;
        }
      }
      tempReal = prevPhase - tempReal2;
      prevPhase = tempReal2;
      if (tempReal < 1.0) {
        tempReal = 1.0;
      }
      if (tempReal > 1.0) {
        tempReal = optInFastLimit / tempReal;
        if (tempReal < optInSlowLimit) {
          tempReal = optInSlowLimit;
        }
      } else {
        tempReal = optInFastLimit;
      }
      mama = (tempReal * todayValue) + ((1 - tempReal) * mama);
      tempReal *= 0.5;
      fama = (tempReal * mama) + ((1 - tempReal) * fama);
      if (today >= startIdx) {
        outMAMA[outIdx] = mama;
        outFAMA[outIdx++] = fama;
      }
      Re = (0.2 * ((I2 * prevI2) + (Q2 * prevQ2))) + (0.8 * Re);
      Im = (0.2 * ((I2 * prevQ2) - (Q2 * prevI2))) + (0.8 * Im);
      prevQ2 = Q2;
      prevI2 = I2;
      tempReal = period;
      if ((Im != 0.0) && (Re != 0.0)) {
        period = 360.0 / (Math.atan(Im / Re) * rad2Deg);
      }
      tempReal2 = 1.5 * tempReal;
      if (period > tempReal2) {
        period = tempReal2;
      }
      tempReal2 = 0.67 * tempReal;
      if (period < tempReal2) {
        period = tempReal2;
      }
      if (period < 6) {
        period = 6;
      } else if (period > 50) {
        period = 50;
      }
      period = (0.2 * period) + (0.8 * tempReal);
      today++;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public int t3Lookback(int optInTimePeriod,
          double optInVFactor) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 5;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    if (optInVFactor == (-4e+37)) {
      optInVFactor = 7.000000e-1;
    } else if ((optInVFactor < 0.000000e+0) || (optInVFactor > 1.000000e+0)) {
      return -1;
    }
    return 6 * (optInTimePeriod - 1) + (this.unstablePeriod[FuncUnstId.T3.ordinal()]);
  }

  public RetCode t3(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          double optInVFactor,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx, lookbackTotal;
    int today, i;
    double k, one_minus_k;
    double e1, e2, e3, e4, e5, e6;
    double c1, c2, c3, c4;
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
    if (optInVFactor == (-4e+37)) {
      optInVFactor = 7.000000e-1;
    } else if ((optInVFactor < 0.000000e+0) || (optInVFactor > 1.000000e+0)) {
      return RetCode.BadParam;
    }
    lookbackTotal = 6 * (optInTimePeriod - 1) + (this.unstablePeriod[FuncUnstId.T3.ordinal()]);
    if (startIdx <= lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outNBElement.value = 0;
      outBegIdx.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    today = startIdx - lookbackTotal;
    k = 2.0 / (optInTimePeriod + 1.0);
    one_minus_k = 1.0 - k;
    tempReal = inReal[today++];
    for (i = optInTimePeriod - 1; i > 0; i--) {
      tempReal += inReal[today++];
    }
    e1 = tempReal / optInTimePeriod;
    tempReal = e1;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      tempReal += e1;
    }
    e2 = tempReal / optInTimePeriod;
    tempReal = e2;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      tempReal += e2;
    }
    e3 = tempReal / optInTimePeriod;
    tempReal = e3;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      tempReal += e3;
    }
    e4 = tempReal / optInTimePeriod;
    tempReal = e4;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      tempReal += e4;
    }
    e5 = tempReal / optInTimePeriod;
    tempReal = e5;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      tempReal += e5;
    }
    e6 = tempReal / optInTimePeriod;
    while (today <= startIdx) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      e6 = (k * e5) + (one_minus_k * e6);
    }
    tempReal = optInVFactor * optInVFactor;
    c1 = -(tempReal * optInVFactor);
    c2 = 3.0 * (tempReal - c1);
    c3 = -6.0 * tempReal - 3.0 * (optInVFactor - c1);
    c4 = 1.0 + 3.0 * optInVFactor - c1 + 3.0 * tempReal;
    outIdx = 0;
    outReal[outIdx++] = c1 * e6 + c2 * e5 + c3 * e4 + c4 * e3;
    while (today <= endIdx) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      e6 = (k * e5) + (one_minus_k * e6);
      outReal[outIdx++] = c1 * e6 + c2 * e5 + c3 * e4 + c4 * e3;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode t3(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          double optInVFactor,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx, lookbackTotal;
    int today, i;
    double k, one_minus_k;
    double e1, e2, e3, e4, e5, e6;
    double c1, c2, c3, c4;
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
    if (optInVFactor == (-4e+37)) {
      optInVFactor = 7.000000e-1;
    } else if ((optInVFactor < 0.000000e+0) || (optInVFactor > 1.000000e+0)) {
      return RetCode.BadParam;
    }
    lookbackTotal = 6 * (optInTimePeriod - 1) + (this.unstablePeriod[FuncUnstId.T3.ordinal()]);
    if (startIdx <= lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outNBElement.value = 0;
      outBegIdx.value = 0;
      return RetCode.Success;
    }
    outBegIdx.value = startIdx;
    today = startIdx - lookbackTotal;
    k = 2.0 / (optInTimePeriod + 1.0);
    one_minus_k = 1.0 - k;
    tempReal = inReal[today++];
    for (i = optInTimePeriod - 1; i > 0; i--) {
      tempReal += inReal[today++];
    }
    e1 = tempReal / optInTimePeriod;
    tempReal = e1;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      tempReal += e1;
    }
    e2 = tempReal / optInTimePeriod;
    tempReal = e2;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      tempReal += e2;
    }
    e3 = tempReal / optInTimePeriod;
    tempReal = e3;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      tempReal += e3;
    }
    e4 = tempReal / optInTimePeriod;
    tempReal = e4;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      tempReal += e4;
    }
    e5 = tempReal / optInTimePeriod;
    tempReal = e5;
    for (i = optInTimePeriod - 1; i > 0; i--) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      tempReal += e5;
    }
    e6 = tempReal / optInTimePeriod;
    while (today <= startIdx) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      e6 = (k * e5) + (one_minus_k * e6);
    }
    tempReal = optInVFactor * optInVFactor;
    c1 = -(tempReal * optInVFactor);
    c2 = 3.0 * (tempReal - c1);
    c3 = -6.0 * tempReal - 3.0 * (optInVFactor - c1);
    c4 = 1.0 + 3.0 * optInVFactor - c1 + 3.0 * tempReal;
    outIdx = 0;
    outReal[outIdx++] = c1 * e6 + c2 * e5 + c3 * e4 + c4 * e3;
    while (today <= endIdx) {
      e1 = (k * inReal[today++]) + (one_minus_k * e1);
      e2 = (k * e1) + (one_minus_k * e2);
      e3 = (k * e2) + (one_minus_k * e3);
      e4 = (k * e3) + (one_minus_k * e4);
      e5 = (k * e4) + (one_minus_k * e5);
      e6 = (k * e5) + (one_minus_k * e6);
      outReal[outIdx++] = c1 * e6 + c2 * e5 + c3 * e4 + c4 * e3;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
}
