/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.indicators;

import javax.swing.JOptionPane;
import petrasys.indicators.support.BasicIndicators;
import petrasys.indicators.support.Indicator;
import petrasys.indicators.support.IndicatorValue;
import petrasys.utils.MsgBox;

/**
 * @author rickcharon 
 * rpc - 3/12/10 11:39 AM - The ADXandPlusMinusDi is calculated, for a given time period as the
 * range for the calculation, so, for the PriceDatas, where the date is stored in
 * milliseconds, each calculated data needs to span however many bars back to get that
 * range, need to have this range mutable
 */
public class ADXandPlusMinusDi extends Indicator {

  double[] plusDM;
  double[] minusDM;
  double[] plusDmSumForPeriod;
  double[] minusDmSumForPeriod;
  double[] TR;
  double[] TRSumForPeriod;
  double[] plusDI;
  double[] minusDI;
  double[] DIDiff;
  double[] DISum;
  double[] DX;
  double[] ADX;
  private int period; // param to adx

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }



  public ADXandPlusMinusDi() {
    //String str = JOptionPane.showInputDialog("Enter Integer Value For Periods (ADXandPlusMinusDi): ", "13");
    //period = Integer.parseInt(str)
    period = 13;

  }

  private void initializeArrays() {
    initializePrimaryArrays();
    plusDM = new double[inHigh.length];
    minusDM = new double[inHigh.length];
    plusDmSumForPeriod = new double[inHigh.length];
    minusDmSumForPeriod = new double[inHigh.length];
    TR = new double[inHigh.length];
    TRSumForPeriod = new double[inHigh.length];
    plusDI = new double[inHigh.length];
    minusDI = new double[inHigh.length];
    DIDiff = new double[inHigh.length];
    DISum = new double[inHigh.length];
    DX = new double[inHigh.length];
    ADX = new double[inHigh.length];
  }

  private void initializeIndicators() {
    indicatorValues.put("PlusDI", new IndicatorValue("PlusDI"));
    indicatorValues.put("MinusDI", new IndicatorValue("MinusDI"));
    indicatorValues.put("ADX", new IndicatorValue("ADX"));
  }

  @Override
  public String getName() {
    return "ADXandPlusMinusDi";
  }

  /**
   * rpc - 3/21/10 6:02 PM - Put the values from arrays into the indicatorValues
   */
  private void setupIndicators() {
    for (int i = 0; i < inHigh.length; i++) {
      indicatorValues.get("PlusDI").insert(inDate[i], plusDI[i]);
      indicatorValues.get("MinusDI").insert(inDate[i], minusDI[i]);
      indicatorValues.get("ADX").insert(inDate[i], ADX[i]);
    }
  }

  @Override
  public void run() {
    try {
      //if (instrument.getRunState() == RunState.BackTest) {
      //Run batch on PriceDatas
      initializeArrays();
      initializeIndicators();
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
        if (TRSumForPeriod[i] == 0.0) {
          plusDI[i] = 0.0;
          minusDI[i] = 0.0;
        } else {
          plusDI[i] = plusDmSumForPeriod[i] / TRSumForPeriod[i] * 100;
          minusDI[i] = minusDmSumForPeriod[i] / TRSumForPeriod[i] * 100;
        }
        DIDiff[i] = Math.abs(plusDI[i] - minusDI[i]);
        DISum[i] = plusDI[i] + minusDI[i];
        if (DISum[i] == 0.0) {
          DX[i] = 0.0;
        } else {
          DX[i] = DIDiff[i] / DISum[i] * 100;
        }
        int j = 3;
      }
      //Setup ADX DX len = 8, period = 5, DXSlice len = 5
      double[] DXSlice = new double[period];
      if ((DX.length - period - 1) >= period) {
        System.arraycopy(DX, period - 1, DXSlice, 0, period);
        ADX[(period - 1) * 2] = BasicIndicators.getStats(DXSlice).getSum() / period;
        for (int i = (((period - 1) * 2) + 1); i < inHigh.length; i++) {
          ADX[i] = BasicIndicators.ADXsingle(period, ADX[i - 1], DX[i]);
        }
      }
      setupIndicators();
//      int i = 0;
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  public static void main(String args[]) {
    int period = 5;
    double[] DXSlice = new double[period];
    double[] DX = new double[8];
    try {
      System.arraycopy(DX, period - 1, DXSlice, 0, period);
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }
}
