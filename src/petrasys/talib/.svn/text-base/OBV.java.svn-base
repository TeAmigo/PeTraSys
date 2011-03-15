/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.talib;

/**
 *
 * @author rickcharon
 */
public class OBV {

  /**
   * rpc - 3/9/10 7:11 PM -On Balance Volume (OBV)
   * @see <a href="http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:on_balance_volume_ob">OBV</a>
   * @param startIdx
   * @param endIdx
   * @param inReal
   * @param inVolume
   * @param outBegIdx
   * @param outNBElement
   * @param outReal
   * @return
   */
  public  RetCode obv(int startIdx,
          int endIdx,
          //List<PriceData> pds,
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
