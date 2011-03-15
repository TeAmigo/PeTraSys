package petrasys.talib;

public class ADXimpl {

  private int[] unstablePeriod;
  private Compatibility compatibility;

  public int adxLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return (2 * optInTimePeriod) + (this.unstablePeriod[FuncUnstId.Adx.ordinal()]) - 1;
  }

  public RetCode adx(int startIdx,
          int endIdx,
          double inHigh[],
          double inLow[],
          double inClose[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int today, lookbackTotal, outIdx;
    double prevHigh, prevLow, prevClose;
    double prevMinusDM, prevPlusDM, prevTR;
    double tempReal, tempReal2, diffP, diffM;
    double minusDI, plusDI, sumDX, prevADX;
    int i;
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
    lookbackTotal = (2 * optInTimePeriod) +
            (this.unstablePeriod[FuncUnstId.Adx.ordinal()]) - 1;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    outBegIdx.value = today = startIdx;
    prevMinusDM = 0.0;
    prevPlusDM = 0.0;
    prevTR = 0.0;
    today = startIdx - lookbackTotal;
    prevHigh = inHigh[today];
    prevLow = inLow[today];
    prevClose = inClose[today];
    i = optInTimePeriod - 1;
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR += tempReal;
      prevClose = inClose[today];
    }
    sumDX = 0.0;
    i = optInTimePeriod;
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) &&
              (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) &&
                (tempReal < (0.00000000000001)))) {
          sumDX += (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
        }
      }
    }
    prevADX = (sumDX / optInTimePeriod);
    i = (this.unstablePeriod[FuncUnstId.Adx.ordinal()]);
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) &&
              (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) &&
                (tempReal < (0.00000000000001)))) {
          tempReal = (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
          prevADX =
                  (((prevADX * (optInTimePeriod - 1)) + tempReal) / optInTimePeriod);
        }
      }
    }
    outReal[0] = prevADX;
    outIdx = 1;
    while (today < endIdx) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) && (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) && (tempReal < (0.00000000000001)))) {
          tempReal = (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
          prevADX = (((prevADX * (optInTimePeriod - 1)) + tempReal) / optInTimePeriod);
        }
      }
      outReal[outIdx++] = prevADX;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode adx(int startIdx,
          int endIdx,
          float inHigh[],
          float inLow[],
          float inClose[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int today, lookbackTotal, outIdx;
    double prevHigh, prevLow, prevClose;
    double prevMinusDM, prevPlusDM, prevTR;
    double tempReal, tempReal2, diffP, diffM;
    double minusDI, plusDI, sumDX, prevADX;
    int i;
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
    lookbackTotal = (2 * optInTimePeriod) +
            (this.unstablePeriod[FuncUnstId.Adx.ordinal()]) - 1;
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    outBegIdx.value = today = startIdx;
    prevMinusDM = 0.0;
    prevPlusDM = 0.0;
    prevTR = 0.0;
    today = startIdx - lookbackTotal;
    prevHigh = inHigh[today];
    prevLow = inLow[today];
    prevClose = inClose[today];
    i = optInTimePeriod - 1;
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR += tempReal;
      prevClose = inClose[today];
    }
    sumDX = 0.0;
    i = optInTimePeriod;
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) &&
              (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) &&
                (tempReal < (0.00000000000001)))) {
          sumDX += (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
        }
      }
    }
    prevADX = (sumDX / optInTimePeriod);
    i = (this.unstablePeriod[FuncUnstId.Adx.ordinal()]);
    while (i-- > 0) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) &&
              (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) &&
                (tempReal < (0.00000000000001)))) {
          tempReal = (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
          prevADX = (((prevADX * (optInTimePeriod - 1)) + tempReal)
                  / optInTimePeriod);
        }
      }
    }
    outReal[0] = prevADX;
    outIdx = 1;
    while (today < endIdx) {
      today++;
      tempReal = inHigh[today];
      diffP = tempReal - prevHigh;
      prevHigh = tempReal;
      tempReal = inLow[today];
      diffM = prevLow - tempReal;
      prevLow = tempReal;
      prevMinusDM -= prevMinusDM / optInTimePeriod;
      prevPlusDM -= prevPlusDM / optInTimePeriod;
      if ((diffM > 0) && (diffP < diffM)) {
        prevMinusDM += diffM;
      } else if ((diffP > 0) && (diffP > diffM)) {
        prevPlusDM += diffP;
      }
      {
        tempReal = prevHigh - prevLow;
        tempReal2 = Math.abs(prevHigh - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
        tempReal2 = Math.abs(prevLow - prevClose);
        if (tempReal2 > tempReal) {
          tempReal = tempReal2;
        }
      }
      ;
      prevTR = prevTR - (prevTR / optInTimePeriod) + tempReal;
      prevClose = inClose[today];
      if (!(((-(0.00000000000001)) < prevTR) &&
              (prevTR < (0.00000000000001)))) {
        minusDI = (100.0 * (prevMinusDM / prevTR));
        plusDI = (100.0 * (prevPlusDM / prevTR));
        tempReal = minusDI + plusDI;
        if (!(((-(0.00000000000001)) < tempReal) &&
                (tempReal < (0.00000000000001)))) {
          tempReal = (100.0 * (Math.abs(minusDI - plusDI) / tempReal));
          prevADX = (((prevADX * (optInTimePeriod - 1)) + tempReal)
                  / optInTimePeriod);
        }
      }
      outReal[outIdx++] = prevADX;
    }
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
  /* Generated */

  public int adxrLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) ||
            ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    if (optInTimePeriod > 1) {
      return optInTimePeriod + adxLookback(optInTimePeriod) - 1;
    } else {
      return 3;
    }
  }

  public RetCode adxr(int startIdx,
          int endIdx,
          double inHigh[],
          double inLow[],
          double inClose[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] adx;
    int adxrLookback, i, j, outIdx, nbElement;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) ||
            ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    adxrLookback = adxrLookback(optInTimePeriod);
    if (startIdx < adxrLookback) {
      startIdx = adxrLookback;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    adx = new double[endIdx - startIdx + optInTimePeriod];
    retCode = adx(startIdx - (optInTimePeriod - 1), endIdx,
            inHigh, inLow, inClose,
            optInTimePeriod, outBegIdx, outNBElement, adx);
    if (retCode != RetCode.Success) {
      return retCode;
    }
    i = optInTimePeriod - 1;
    j = 0;
    outIdx = 0;
    nbElement = endIdx - startIdx + 2;
    while (--nbElement != 0) {
      outReal[outIdx++] = ((adx[i++] + adx[j++]) / 2.0);
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode adxr(int startIdx,
          int endIdx,
          float inHigh[],
          float inLow[],
          float inClose[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    double[] adx;
    int adxrLookback, i, j, outIdx, nbElement;
    RetCode retCode;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) ||
            ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    adxrLookback = adxrLookback(optInTimePeriod);
    if (startIdx < adxrLookback) {
      startIdx = adxrLookback;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    adx = new double[endIdx - startIdx + optInTimePeriod];
    retCode = adx(startIdx - (optInTimePeriod - 1), endIdx,
            inHigh, inLow, inClose,
            optInTimePeriod, outBegIdx, outNBElement, adx);
    if (retCode != RetCode.Success) {
      return retCode;
    }
    i = optInTimePeriod - 1;
    j = 0;
    outIdx = 0;
    nbElement = endIdx - startIdx + 2;
    while (--nbElement != 0) {
      outReal[outIdx++] = ((adx[i++] + adx[j++]) / 2.0);
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
}
