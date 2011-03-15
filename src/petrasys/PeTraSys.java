/*********************************************************************
 * File path:     /share/JavaDev/PeTraSys/src/petrasys/PeTraSys.java
 * Version:       
 * Description:   Entry point and some static vars.
 * Author:        Rick Charon <rickcharon@gmail.com>
 * Created at:    Tue Dec 14 12:20:59 2010
 * Modified at:   Tue Dec 14 12:21:32 2010
 ********************************************************************/
package petrasys;

import petrasys.utils.CommandLineHandler;
import com.tictactec.ta.lib.Core;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.swing.UIManager;
import petrasys.instruments.Instrument;

public class PeTraSys {

  private static final PeTraSysTopFrame1 topFrame = new PeTraSysTopFrame1();
  public static final Font DEFAULT_TITLE_FONT = new Font("DejaVu Sans", Font.BOLD, 14);
  private static ConcurrentSkipListMap<Integer, Instrument> instrumentList =
          new ConcurrentSkipListMap<Integer, Instrument>();
  private static Map<String, Instrument> instrumentSet = new HashMap<String, Instrument>();
  public static void setUIElements() {
    UIManager.put("ToolTip.font", new Font("dialog", Font.PLAIN, 18));
  }

  public static PeTraSysTopFrame1 getTopFrame() {
    return topFrame;
  }
  public static final Core TA = new Core();

  /**
   *
   * @param args
   */
  public static void writeToReport(String strIn) {
    topFrame.writeToReport(strIn);
  }

  public static void addInstrument(Instrument inst) {
    instrumentList.put(inst.getTickerId(), inst);
  }

  public static void removeInstrument(Instrument inst) {
    instrumentList.remove(inst.getTickerId());
  }

  public static Instrument fetchInstrument(int tickerId) {
    return instrumentList.get(tickerId);
  }

  public static void main(String args[]) {
    CommandLineHandler cl = new CommandLineHandler();
    if (args.length == 0) {
      PeTraSys.topFrame.setVisible(true);
    } else if (args.length > 0) {
      String argLine = args[0];
      for (int i = 1; i < args.length; i++) {
        argLine += " " + args[i];
      }
      Scanner sc = new Scanner(argLine);
      cl.processLineIn(sc);
    }
    Thread thread = new Thread(cl);
    thread.start();
    //int i = 3;


//     Get all available font faces names
//     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//     Font[] fonts = ge.getAllFonts();
//     String[] fnames = ge.getAvailableFontFamilyNames();
//     int i = 0;
  }
}
//    CommandLineParser parser = new PosixParser();
//    Options options = new Options();
//    options.addOption("t", "topframe", false, "Run the TopFrame.");
//    options.addOption("c", "charts", false, "Run Charts.");
//    options.addOption("h", "help", false, "Show help");
//    PeTraSys.setUIElements();
//    try {
//      CommandLine line = parser.parse(options, args);
//      if (line.hasOption("c")) {
//        System.out.println("Running Charts:");
//        new PeTraSysCharts().setVisible(true);
//      } else if (line.hasOption("h")) {
//        HelpFormatter formatter = new HelpFormatter();
//        formatter.printHelp("PeTraSys", options);
//      } else if (line.hasOption("t")) {
//        PeTraSys.topFrame.setVisible(true);
//      } else {
//        HelpFormatter formatter = new HelpFormatter();
//        formatter.printHelp("PeTraSys", options);
//      }
//    } catch (ParseException exp) {
//      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
//    }

