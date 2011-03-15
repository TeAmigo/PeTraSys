/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class ROC {

    public  RetCode roc(int startIdx,
          int endIdx,
          double inReal[],
          int optInTimePeriod,
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int inIdx, outIdx, trailingIdx;
    double tempReal;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    if ((int) optInTimePeriod == (Integer.MIN_VALUE)) {
      optInTimePeriod = 10;
    } else if (((int) optInTimePeriod < 1) || ((int) optInTimePeriod > 100000)) {
      return RetCode.BadParam;
    }
    if (startIdx < optInTimePeriod) {
      startIdx = optInTimePeriod;
    }
    if (startIdx > endIdx) {
      outBegIdx.value = 0;
      outNBElement.value = 0;
      return RetCode.Success;
    }
    outIdx = 0;
    inIdx = startIdx;
    trailingIdx = startIdx - optInTimePeriod;
    while (inIdx <= endIdx) {
      tempReal = inReal[trailingIdx++];
      if (tempReal != 0.0) {
        outReal[outIdx++] = ((inReal[inIdx] / tempReal) - 1.0) * 100.0;
      } else {
        outReal[outIdx++] = 0.0;
      }
      inIdx++;
    }
    outNBElement.value = outIdx;
    outBegIdx.value = startIdx;
    return RetCode.Success;
  }

}
