/*
 * From ta-lib 0.4
 */
package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class LinearRegression {

  public int linearRegLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode linearReg(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m, b;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      b = (SumY - m * SumX) / (double) optInTimePeriod;
      outReal[outIdx++] = b + m * (double) (optInTimePeriod - 1);
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode linearReg(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m, b;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      b = (SumY - m * SumX) / (double) optInTimePeriod;
      outReal[outIdx++] = b + m * (double) (optInTimePeriod - 1);
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }


  public int linearRegAngleLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode linearRegAngle(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegAngleLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      outReal[outIdx++] = Math.atan(m) * (180.0 / 3.14159265358979323846);
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode linearRegAngle(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegAngleLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      outReal[outIdx++] = Math.atan(m) * (180.0 / 3.14159265358979323846);
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }


  public int linearRegInterceptLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode linearRegIntercept(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegInterceptLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      outReal[outIdx++] = (SumY - m * SumX) / (double) optInTimePeriod;
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode linearRegIntercept(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    double m;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegInterceptLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      m = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      outReal[outIdx++] = (SumY - m * SumX) / (double) optInTimePeriod;
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public int linearRegSlopeLookback(int optInTimePeriod) {
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 14;
    } else if (((int) optInTimePeriod < 2) || ((int) optInTimePeriod > 100000)) {
      return -1;
    }
    return optInTimePeriod - 1;
  }

  public RetCode linearRegSlope(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegSlopeLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      outReal[outIdx++] = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }

  public RetCode linearRegSlope(int startIdx,
          int endIdx,
          float inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int outIdx;
    int today, lookbackTotal;
    double SumX, SumXY, SumY, SumXSqr, Divisor;
    int i;
    double tempValue1;
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
    lookbackTotal = linearRegSlopeLookback(optInTimePeriod);
    if (startIdx < lookbackTotal) {
      startIdx = lookbackTotal;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    today = startIdx;
    SumX = optInTimePeriod * (optInTimePeriod - 1) * 0.5;
    SumXSqr = optInTimePeriod * (optInTimePeriod - 1) * (2 * optInTimePeriod - 1) / 6;
    Divisor = SumX * SumX - optInTimePeriod * SumXSqr;
    while (today <= endIdx) {
      SumXY = 0;
      SumY = 0;
      for (i = optInTimePeriod; i-- != 0;) {
        SumY += tempValue1 = inReal[today - i];
        SumXY += (double) i * tempValue1;
      }
      outReal[outIdx++] = (optInTimePeriod * SumXY - SumX * SumY) / Divisor;
      today++;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
}
