/*
 * //rpc - WORKING HERE:4/1/10 10:56 AM - To evaluate current bar's efficacy for either
 * long or short position taken at the current bar, evaluate greatest downward excursion from this bar to the
 * end of period desired, greatest upward excursion, greatest drawdown, greatest draw up, and end of period
 * excursion, each of these needs a datetime stamp as well,
 */
package petrasys.indicators;

import petrasys.indicators.support.Indicator;
import petrasys.indicators.support.IndicatorValue;
import petrasys.instruments.PriceBars;

/**
 *
 * @author rickcharon
 */
public class ForwardLookingEvaluator extends Indicator {
  double[] greatestDownwardExcursion;
  double[] greatesUpwardExcursion;
  double[] greatestDrawUp;
  double[] greatestDrawDown;
  private int period;


  public ForwardLookingEvaluator(PriceBars pbsIn) {
    super(pbsIn);
  }

  @Override
  public String getName() {
    return "ForwardLookingEvaluator";
  }

  private void initializeIndicators() {
    indicatorValues.put("UpwardExcursion", new IndicatorValue("UpwardExcursion"));
    indicatorValues.put("DownwardExcursion", new IndicatorValue("DownwardExcursion"));
    indicatorValues.put("Drawdown", new IndicatorValue("Drawdown"));
    indicatorValues.put("DrawUp", new IndicatorValue("DrawUp"));
    indicatorValues.put("EndExcursion", new IndicatorValue("EndExcursion"));
  }

  public void intializeArrays() {
    initializePrimaryArrays();
    greatestDownwardExcursion = new double[inHigh.length];
    greatesUpwardExcursion = new double[inHigh.length];
    greatestDrawUp = new double[inHigh.length];
    greatestDrawDown = new double[inHigh.length];
  }
  private void setupIndicators() {
  }

  public void evalExcursionsForEachBar() {
  }

  @Override
  public void run() {
    super.run();
  }



}
