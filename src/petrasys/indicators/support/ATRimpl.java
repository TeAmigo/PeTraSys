/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.indicators.support;

import java.util.List;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class ATRimpl {

  public static void getATR(int period, double[] inHigh, double[] inLow,
          double[] inClose,double[] outATR) {

    double trueHigh, trueLow, trueRange;
    if (!(inHigh.length == inLow.length) && (inHigh.length == inClose.length)
            && (inHigh.length == outATR.length)) {
      MsgBox.err("All Lists must be same size", "Error in ATR");
      return;
    }
    outATR[0] = inHigh[0] - inLow[0];
    for (int idx = 1; idx < inHigh.length; idx++) {
      trueHigh = Math.max(inHigh[idx], inClose[idx - 1]);
      trueLow = Math.min(inClose[idx - 1], inLow[idx]);
      outATR[idx] = trueHigh - trueLow;
    }

  }
  
//public static void getATR(int period, List<Double> inHigh, List<Double> inLow,
//          List<Double> inClose, List<Double> outATR) {
//
//    double trueHigh, trueLow, trueRange;
//    if (!(inHigh.size() == inLow.size()) && (inHigh.size() == inClose.size())
//            && (inHigh.size() == outATR.size())) {
//      MsgBox.err("All Lists must be same size", "Error in ATRimpl");
//      return;
//    }
//    for (int idx = 1; idx < inHigh.size(); idx++) {
//      trueHigh = Math.max(inHigh.get(idx), inClose.get(idx - 1));
//      trueLow = Math.min(inClose.get(idx - 1), inLow.get(idx));
//      trueRange = trueHigh - trueLow;
//
//
//    }
//
//  }

  public static void main(String args[]) {
  }
}

