/*********************************************************************
 * File path:     /share/JavaDev/PeTraSys/src/petrasys/CommandLineParser.java
 * Version:       
 * Description:   Continuously scan System.in for commands.
 * Author:        Rick Charon <rickcharon@gmail.com>
 * Created at:    Tue Dec 14 12:19:21 2010
 * Modified at:   Tue Dec 14 12:21:45 2010
 ********************************************************************/
package petrasys.utils;

import java.util.Scanner;
import org.joda.time.DateTime;
import petrasys.PeTraSys;
import petrasys.utils.MemoryUsage;
import petrasys.indicators.support.Indicator;
import petrasys.instruments.Instrument;
import petrasys.instruments.RunState;
import petrasys.runners.Runner;
import petrasys.utils.DBops;
import petrasys.utils.MsgBox;

/**
 *
 * @author rickcharon
 */
public class CommandLineHandler implements Runnable {

  private Scanner console = new Scanner(System.in);
  private Scanner lineTokenizer;
  private int lineNum = 0;
  private final String prompt = ":pts> ";

  public CommandLineHandler() {
  }

  public void processLineIn(Scanner input) {
    //input.useDelimiter(",");
    while (input.hasNext()) {
      String inStr = input.next();
      if (inStr.equals("cmd")) {
        return;
      }
      if (inStr.equals("ot")) {
        System.out.println("New Line test");
        return;
      }
      if (inStr.equals("exit")) {
        System.exit(0);
      }
      if (inStr.equals("-m")) {
        MemoryUsage mu = new MemoryUsage(30000);
        mu.startMeUp();
        return;
      }
      if (inStr.equals("-u")) {
        PeTraSys.getTopFrame().bringAllCurrent();
        return;
      }
      if (inStr.equals("-d")) { //Setup Price and Indicator data
        String sym = input.next();
        String beginDate = input.next();
        DateTime begD = new DateTime(beginDate);
        //Date begD = DateOps.dateFromStdShortFormatString(beginDate);
        String endDate = input.next();
        DateTime endD = new DateTime(endDate);
        //Date endD = DateOps.dateFromStdShortFormatString(endDate);
        int compressionFactor = input.nextInt();
        Instrument instrument = DBops.getInstrumentDetails(sym);
        DBops.getPriceDatasCompressed(instrument, begD.getMillis(), endD.getMillis(), compressionFactor);
        System.out.println(instrument.getPriceBars().size() + " price bars in " + instrument.getFullName());
        System.out.println("From " + instrument.getPriceBars().getBeginDate() + " to " +
                instrument.getPriceBars().getEndDate());
        try {
          Object[] indicators = PeTraSys.getTopFrame().getIndicatorList().getSelectedValues();
          for (Object ind : indicators) {
            //rpc - NOTE:3/4/10 7:03 AM - To use newInstance(), must have a 0 arg ctor
            // see notes-java for a passing params to ctor
            instrument.addIndicator(
                    (Indicator) PeTraSys.getTopFrame().getIndicatorClassFromName(ind.toString()).newInstance());
          }
        } catch (Exception ex) {
          MsgBox.err2(ex);
        }
        Runner runner = new Runner(instrument);
        runner.setupIndicators();
        runner.runIndicators();
      }
      if (inStr.equals("-c")) {
        String sym = input.next();
        String beginDate = input.next();
        DateTime begD = new DateTime(beginDate);
        //Date begD = DateOps.dateFromStdShortFormatString(beginDate);
        String endDate = input.next();
        DateTime endD = new DateTime(endDate);
        //Date endD = DateOps.dateFromStdShortFormatString(endDate);
        int compressionFactor = input.nextInt();
        Instrument instrument = DBops.getInstrumentDetails(sym);
        DBops.getPriceDatasCompressed(instrument, begD.getMillis(), endD.getMillis(), compressionFactor);
        try {
          Object[] indicators = PeTraSys.getTopFrame().getIndicatorList().getSelectedValues();
          for (Object ind : indicators) {
            //rpc - NOTE:3/4/10 7:03 AM - To use newInstance(), must have a 0 arg ctor
            // see notes-java for a passing params to ctor
            instrument.addIndicator(
                    (Indicator) PeTraSys.getTopFrame().getIndicatorClassFromName(ind.toString()).newInstance());
          }
        } catch (Exception ex) {
          MsgBox.err2(ex);
        }
        instrument.setRunState(RunState.Chart);
        Thread thread = new Thread(instrument);
        thread.start();
      }
      return;
    }
  }

  public void run() {
    System.out.println("PeTraSys Version 0.01:");
    System.out.print(lineNum + prompt);
    while (console.hasNextLine()) {  // Blocks waiting for input

      lineTokenizer = new Scanner(console.nextLine());
      processLineIn(lineTokenizer);
      lineNum++;
      System.out.print(lineNum + prompt);
    }
  }
}
