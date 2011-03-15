/*
 * // rpc - 2/23/10 12:16 PM "Get Historical Data by Dates" was title, but obviously more than that
 * and open the template in the editor.
 */

/*
 * PeTraSysTopFrame.java
 *
 * Created on Feb 6, 2010, 4:13:12 PM
 */
package petrasys;

import com.toedter.calendar.JDateChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import petrasys.connections.HistoricalPriceDataDownloader;
import com.ib.client.Contract;
import java.awt.Component;

//import java.security.Timestamp;
import java.awt.Cursor;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import petrasys.connections.AccountUpdates;
import petrasys.connections.HistoricalFromTWS;
import petrasys.connections.IBConnectionManager;
import petrasys.connections.MySocket;
import petrasys.indicators.support.Indicator;
import petrasys.instruments.Instrument;
import petrasys.instruments.RunState;
import petrasys.utils.BarSize;
import petrasys.utils.Classes;
import petrasys.utils.ContractFactory;
import petrasys.utils.DBops;
import petrasys.utils.DateOps;
import petrasys.utils.MsgBox;
import petrasys.instruments.PriceBar;
import petrasys.instruments.PriceBars;
import petrasys.indicators.support.IndicatorResults;
import petrasys.utils.ReportFrame;

/**
 *
 * @author rickcharon
 * rpc - 3/3/10 5:54 PM - This is the big banana, everything to date starts here,
 * This is turn is started by PeTraSys class,
 */
public class PeTraSysTopFrame1 extends javax.swing.JFrame {

  private List<Class> indicatorsList;
  private ReportFrame reportFrame;
  private BlockingQueue<String> reportQueue;
  SimpleDateFormat dateStrFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
  private HistoricalFromTWS histFromTWS;

  /**
   *
   * @param strIn this is written both to a file and a report window
   * openable from this frame, the files name is set in the ctor of this class
   * setReportFrame(new ReportFrame("/share/PeTraSysReports/PeTraSysReport-" + dateTime, reportQueue)
   */
  public void writeToReport(String strIn) {
    String dateTime = dateStrFormat.format(new java.util.Date());
    try {
      strIn = dateTime + " - " + strIn + "\n";
      reportQueue.put(strIn);
    } catch (InterruptedException ex) {
      Logger.getLogger(PeTraSysTopFrame1.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @return
   */
  public ReportFrame getReportFrame() {
    return reportFrame;
  }

  /**
   *
   * @param reportFrame
   */
  public void setReportFrame(ReportFrame reportFrame) {
    this.reportFrame = reportFrame;
  }

  private class symbolInfo {

    String symbol;
    String exchange;
    int multiplier;
    int priceMagnifier;
    double minTick;
    String fullName;
  };
  HashMap symbolInfos;
  symbolInfo workingSI;
  private Vector<String> symbols;

  public Vector<String> getSymbols() {
    return symbols;
  }

  public JList getSymbolsList() {
    return symbolsList;
  }

  private void initLists() {
    getDistinctSymbolInfos();
    getDistinctSymbolNames();
    populateIndicators();

  }

  /** Creates new form PeTraSysTopFrame
   *
   */
  public PeTraSysTopFrame1() {
    histFromTWS = new HistoricalFromTWS();
    symbols = new Vector();
    symbolInfos = new HashMap();
    initLists();
    reportQueue = new LinkedBlockingQueue<String>();
    setReportFrame(new ReportFrame("/share/PeTraSysReports/PeTraSysReport-" + DateOps.nowFileNameString(),
            reportQueue));
    Thread reportThread = new Thread(getReportFrame());
    reportThread.setName("ReportThread");
    reportThread.start();
    writeToReport(this.getClass() + " Started");
    initComponents();
    IndicatorList.setSelectedValue("ADXandPlusMinusDi", true);
  }
  java.awt.Toolkit toolkit = this.getToolkit();
  java.awt.Image appIcon = toolkit.createImage("/share/icons/stock-market-icon.png");

  public void getDistinctSymbolInfos() {
    try {
      ResultSet res = DBops.distinctSymbolInfos().executeQuery();
      symbolInfos.clear();
      symbols.clear();
      while (res.next()) {
        symbolInfo si = new symbolInfo();
        si.symbol = res.getString("symbol");
        si.exchange = res.getString("exchange");
        si.multiplier = res.getInt("multiplier");
        si.priceMagnifier = res.getInt("priceMagnifier");
        si.minTick = res.getDouble("minTick");
        si.fullName = res.getString("fullName");
        symbolInfos.put(si.symbol, si);
        //symbols.add(res.getString("symbol"));
      }
      res.close();
    } catch (SQLException ex) {
      Logger.getLogger(PeTraSysTopFrame1.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  // rpc - 2/8/10 6:50 AM This works well, but decided I need more info, so
  // made getDistinctSymbolInfos() above. Still used for populating listbox,
  /**
   *
   */
  public void getDistinctSymbolNames() {
    //return symbols;   Vector<String>
    try {
      ResultSet res = DBops.distinctSymsProc().executeQuery();
      int rowSize = res.getRow();
      res.last();
      rowSize = res.getRow();
      res.first();
      rowSize = res.getRow();
      res.previous();
      rowSize = res.getRow();
      symbols.clear();
      while (res.next()) {
        symbols.add(res.getString("symbol"));
      }
      res.close();
    } catch (SQLException sqlex) {
      System.err.println("SQLException: " + sqlex.getMessage());
    }
  }

  public JList getIndicatorList() {
    return IndicatorList;
  }

  /**
   *
   * @return
   */
  public Vector<String> getIndicatorClassNames() {
    Vector<String> cNames = new Vector();
    String tname;
    for (Class<?> eClass : indicatorsList) {
      tname = eClass.getName();
      cNames.add(tname.substring(tname.lastIndexOf('.') + 1));
    }
    return cNames;
  }

  /**
   * rpc - 2/27/10 3:06 PM
   * @param namein a short form of the class name (no pkg info)
   * @return the corresponding Indicator Class Class object
   */
  public Class getIndicatorClassFromName(String namein) {
    String tname;
    for (Class<?> eClass : indicatorsList) {
      tname = eClass.getName();
      tname = tname.substring(tname.lastIndexOf('.') + 1);
      if (tname.equals(namein)) {
        return eClass;
      }
    }
    return null;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabbedPane = new javax.swing.JTabbedPane();
    setupPane = new javax.swing.JPanel();
    resetButton = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    symbolsList = new javax.swing.JList(symbols);
    jLabel1 = new javax.swing.JLabel();
    beginDateChooser = new com.toedter.calendar.JDateChooser();
    endDateChooser = new com.toedter.calendar.JDateChooser();
    chartButton = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    useHistoricalDataCheckBox = new javax.swing.JCheckBox();
    testButton = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    IndicatorList = new javax.swing.JList(getIndicatorClassNames());
    runExplorationButton = new javax.swing.JButton();
    forwardTestButton = new javax.swing.JButton();
    tradeButton = new javax.swing.JButton();
    continuousContractButton = new javax.swing.JButton();
    viewReportFormButton = new javax.swing.JButton();
    beginDateLabel = new javax.swing.JLabel();
    endDateLabel = new javax.swing.JLabel();
    bringCurrentButton = new javax.swing.JButton();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    useContinuousContractCheckBox = new javax.swing.JCheckBox();
    contractInfoButton = new javax.swing.JButton();
    javax.swing.JButton testChartButton = new javax.swing.JButton();
    downloadWaitLabel = new javax.swing.JLabel();
    bringAllCurrentButton = new javax.swing.JButton();
    runBacktestButton = new javax.swing.JButton();
    priceBarsCompressionPanel = new javax.swing.JPanel();
    jLabel8 = new javax.swing.JLabel();
    priceBarCompressionText = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    CurrentIndicatorValuesForAllButton = new javax.swing.JButton();
    UpdateAccountInfo = new javax.swing.JButton();
    chartPanel = new javax.swing.JPanel();
    jScrollPane5 = new javax.swing.JScrollPane();
    chartsRunningTable = new javax.swing.JTable();
    forwardTestPane = new javax.swing.JPanel();
    explorationPanel = new javax.swing.JPanel();
    tradePanel = new javax.swing.JPanel();
    backtestPanel = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    backtestInstrumentLabelWithID = new SimpleBean.JLabelWithID();
    saveIndicatorResultsButton = new javax.swing.JButton();
    jScrollPane3 = new javax.swing.JScrollPane();
    BackTestTextArea = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("petrasys/Bundle"); // NOI18N
    setTitle(bundle.getString("PeTraSysTopFrame1.title")); // NOI18N
    setFocusCycleRoot(false);
    setIconImage(appIcon);
    setName("PeTraSysTopFrame"); // NOI18N
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
      public void windowOpened(java.awt.event.WindowEvent evt) {
        formWindowOpened(evt);
      }
    });
    getContentPane().setLayout(new java.awt.GridLayout(1, 0));

    tabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    tabbedPane.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    tabbedPane.setName("tabbedPane"); // NOI18N

    setupPane.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    setupPane.setName("setupPane"); // NOI18N
    setupPane.setLayout(null);

    resetButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    resetButton.setText(bundle.getString("PeTraSysTopFrame1.resetButton.text")); // NOI18N
    resetButton.setName("resetButton"); // NOI18N
    resetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetButtonActionPerformed(evt);
      }
    });
    setupPane.add(resetButton);
    resetButton.setBounds(10, 350, 163, 29);

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    symbolsList.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    symbolsList.setModel(new javax.swing.AbstractListModel() {
      public int getSize() { return symbols.size(); }
      public Object getElementAt(int i) { return symbols.elementAt(i); }
    });
    symbolsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    symbolsList.setName("symbolsList"); // NOI18N
    symbolsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
        symbolsListValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(symbolsList);

    setupPane.add(jScrollPane1);
    jScrollPane1.setBounds(12, 35, 69, 130);

    jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel1.setText(bundle.getString("PeTraSysTopFrame1.jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N
    setupPane.add(jLabel1);
    jLabel1.setBounds(12, 12, 199, 17);

    beginDateChooser.setDateFormatString(bundle.getString("PeTraSysTopFrame1.beginDateChooser.dateFormatString")); // NOI18N
    beginDateChooser.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    beginDateChooser.setName("beginDateChooser"); // NOI18N
    //beginDateChooser.setVisible(false);
    setupPane.add(beginDateChooser);
    beginDateChooser.setBounds(430, 50, 269, 30);

    endDateChooser.setDateFormatString(bundle.getString("PeTraSysTopFrame1.endDateChooser.dateFormatString")); // NOI18N
    endDateChooser.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    endDateChooser.setName("endDateChooser"); // NOI18N
    //endDateChooser.setVisible(false);
    setupPane.add(endDateChooser);
    endDateChooser.setBounds(430, 110, 270, 30);

    chartButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    chartButton.setText(bundle.getString("PeTraSysTopFrame1.chartButton.text")); // NOI18N
    chartButton.setName("chartButton"); // NOI18N
    chartButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        chartButtonActionPerformed(evt);
      }
    });
    setupPane.add(chartButton);
    chartButton.setBounds(450, 250, 156, 29);

    jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel2.setText(bundle.getString("PeTraSysTopFrame1.jLabel2.text")); // NOI18N
    jLabel2.setName("jLabel2"); // NOI18N
    setupPane.add(jLabel2);
    jLabel2.setBounds(440, 90, 200, 17);

    jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel3.setText(bundle.getString("PeTraSysTopFrame1.jLabel3.text")); // NOI18N
    jLabel3.setName("jLabel3"); // NOI18N
    setupPane.add(jLabel3);
    jLabel3.setBounds(94, 86, 190, 17);

    useHistoricalDataCheckBox.setSelected(true);
    useHistoricalDataCheckBox.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    useHistoricalDataCheckBox.setText(bundle.getString("PeTraSysTopFrame1.useHistoricalDataCheckBox.text")); // NOI18N
    useHistoricalDataCheckBox.setName("useHistoricalDataCheckBox"); // NOI18N
    useHistoricalDataCheckBox.setRequestFocusEnabled(false);
    useHistoricalDataCheckBox.setRolloverEnabled(false);
    useHistoricalDataCheckBox.setVerifyInputWhenFocusTarget(false);
    //useHistoricalDataCheckBox.setVisible(false);
    useHistoricalDataCheckBox.setSelected(true);
    setupPane.add(useHistoricalDataCheckBox);
    useHistoricalDataCheckBox.setBounds(430, 210, 250, 22);

    testButton.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    testButton.setText(bundle.getString("PeTraSysTopFrame1.testButton.text")); // NOI18N
    testButton.setBorderPainted(false);
    testButton.setDefaultCapable(false);
    testButton.setName("testButton"); // NOI18N
    testButton.setOpaque(true);
    testButton.setRequestFocusEnabled(false);
    testButton.setRolloverEnabled(false);
    testButton.setVerifyInputWhenFocusTarget(false);
    testButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        testButtonActionPerformed(evt);
      }
    });
    testButton.setVisible(false);
    setupPane.add(testButton);
    testButton.setBounds(50, 380, 45, 34);

    jScrollPane2.setName("jScrollPane2"); // NOI18N

    IndicatorList.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    IndicatorList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    IndicatorList.setName("IndicatorList"); // NOI18N
    jScrollPane2.setViewportView(IndicatorList);

    setupPane.add(jScrollPane2);
    jScrollPane2.setBounds(710, 30, 250, 130);

    runExplorationButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    runExplorationButton.setText(bundle.getString("PeTraSysTopFrame1.runExplorationButton.text")); // NOI18N
    runExplorationButton.setName("runExplorationButton"); // NOI18N
    runExplorationButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        runExplorationButtonActionPerformed(evt);
      }
    });
    setupPane.add(runExplorationButton);
    runExplorationButton.setBounds(710, 170, 162, 30);

    forwardTestButton.setToolTipText("Updates historic first");
    forwardTestButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    forwardTestButton.setText(bundle.getString("PeTraSysTopFrame1.forwardTestButton.text")); // NOI18N
    forwardTestButton.setName("forwardTestButton"); // NOI18N
    forwardTestButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        forwardTestButtonActionPerformed(evt);
      }
    });
    setupPane.add(forwardTestButton);
    forwardTestButton.setBounds(710, 230, 180, 29);

    tradeButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    tradeButton.setText(bundle.getString("PeTraSysTopFrame1.tradeButton.text")); // NOI18N
    tradeButton.setName("tradeButton"); // NOI18N
    tradeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tradeButtonActionPerformed(evt);
      }
    });
    setupPane.add(tradeButton);
    tradeButton.setBounds(710, 260, 90, 29);

    continuousContractButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    continuousContractButton.setText(bundle.getString("PeTraSysTopFrame1.continuousContractButton.text")); // NOI18N
    continuousContractButton.setName("continuousContractButton"); // NOI18N
    continuousContractButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        continuousContractButtonActionPerformed(evt);
      }
    });
    setupPane.add(continuousContractButton);
    continuousContractButton.setBounds(12, 227, 290, 29);

    viewReportFormButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    viewReportFormButton.setText(bundle.getString("PeTraSysTopFrame1.viewReportFormButton.text")); // NOI18N
    viewReportFormButton.setName("viewReportFormButton"); // NOI18N
    viewReportFormButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        viewReportFormButtonActionPerformed(evt);
      }
    });
    setupPane.add(viewReportFormButton);
    viewReportFormButton.setBounds(12, 268, 200, 29);

    beginDateLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
    beginDateLabel.setText(bundle.getString("PeTraSysTopFrame1.beginDateLabel.text")); // NOI18N
    beginDateLabel.setName("beginDateLabel"); // NOI18N
    setupPane.add(beginDateLabel);
    beginDateLabel.setBounds(94, 58, 257, 22);

    endDateLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
    endDateLabel.setText(bundle.getString("PeTraSysTopFrame1.endDateLabel.text")); // NOI18N
    endDateLabel.setName("endDateLabel"); // NOI18N
    setupPane.add(endDateLabel);
    endDateLabel.setBounds(94, 109, 260, 22);

    bringCurrentButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    bringCurrentButton.setText(bundle.getString("PeTraSysTopFrame1.bringCurrentButton.text")); // NOI18N
    bringCurrentButton.setName("bringCurrentButton"); // NOI18N
    bringCurrentButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bringCurrentButtonActionPerformed(evt);
      }
    });
    bringCurrentButton.setToolTipText("Get Historical data to Now.");
    setupPane.add(bringCurrentButton);
    bringCurrentButton.setBounds(20, 180, 200, 30);

    jLabel4.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel4.setText(bundle.getString("PeTraSysTopFrame1.jLabel4.text")); // NOI18N
    jLabel4.setName("jLabel4"); // NOI18N
    setupPane.add(jLabel4);
    jLabel4.setBounds(710, 10, 84, 17);

    jLabel5.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel5.setText(bundle.getString("PeTraSysTopFrame1.jLabel5.text")); // NOI18N
    jLabel5.setName("jLabel5"); // NOI18N
    setupPane.add(jLabel5);
    jLabel5.setBounds(94, 35, 220, 17);

    jLabel6.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel6.setText(bundle.getString("PeTraSysTopFrame1.jLabel6.text")); // NOI18N
    jLabel6.setName("jLabel6"); // NOI18N
    setupPane.add(jLabel6);
    jLabel6.setBounds(430, 30, 210, 17);

    useHistoricalDataCheckBox.setSelected(true);
    useContinuousContractCheckBox.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    useContinuousContractCheckBox.setText(bundle.getString("PeTraSysTopFrame1.useContinuousContractCheckBox.text")); // NOI18N
    useContinuousContractCheckBox.setName("useContinuousContractCheckBox"); // NOI18N
    useContinuousContractCheckBox.setRequestFocusEnabled(false);
    useContinuousContractCheckBox.setRolloverEnabled(false);
    useContinuousContractCheckBox.setVerifyInputWhenFocusTarget(false);
    useContinuousContractCheckBox.setSelected(true);
    setupPane.add(useContinuousContractCheckBox);
    useContinuousContractCheckBox.setBounds(430, 180, 250, 22);

    contractInfoButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    contractInfoButton.setText(bundle.getString("PeTraSysTopFrame1.contractInfoButton.text")); // NOI18N
    contractInfoButton.setName("contractInfoButton"); // NOI18N
    contractInfoButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        contractInfoButtonActionPerformed(evt);
      }
    });
    setupPane.add(contractInfoButton);
    contractInfoButton.setBounds(10, 310, 270, 35);

    testChartButton.setText(bundle.getString("PeTraSysTopFrame1.testChartButton.text")); // NOI18N
    testChartButton.setName("testChartButton"); // NOI18N
    testChartButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        testChartButtonActionPerformed(evt);
      }
    });
    setupPane.add(testChartButton);
    testChartButton.setBounds(200, 390, 140, 32);

    downloadWaitLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    downloadWaitLabel.setForeground(new java.awt.Color(244, 19, 19));
    downloadWaitLabel.setText(bundle.getString("PeTraSysTopFrame1.downloadWaitLabel.text")); // NOI18N
    downloadWaitLabel.setName("downloadWaitLabel"); // NOI18N
    setupPane.add(downloadWaitLabel);
    downloadWaitLabel.setBounds(100, 140, 320, 17);

    bringAllCurrentButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    bringAllCurrentButton.setText(bundle.getString("PeTraSysTopFrame1.bringAllCurrentButton.text")); // NOI18N
    bringAllCurrentButton.setName("bringAllCurrentButton"); // NOI18N
    bringAllCurrentButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bringAllCurrentButtonActionPerformed(evt);
      }
    });
    bringCurrentButton.setToolTipText("Get Historical data to Now.");
    setupPane.add(bringAllCurrentButton);
    bringAllCurrentButton.setBounds(230, 180, 180, 30);

    runBacktestButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    runBacktestButton.setText(bundle.getString("PeTraSysTopFrame1.runBacktestButton.text")); // NOI18N
    runBacktestButton.setName("runBacktestButton"); // NOI18N
    runBacktestButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        runBacktestButtonActionPerformed(evt);
      }
    });
    setupPane.add(runBacktestButton);
    runBacktestButton.setBounds(710, 200, 156, 29);

    priceBarsCompressionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    priceBarsCompressionPanel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    priceBarsCompressionPanel.setName("priceBarsCompressionPanel"); // NOI18N

    jLabel8.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel8.setText(bundle.getString("PeTraSysTopFrame1.jLabel8.text")); // NOI18N
    jLabel8.setName("jLabel8"); // NOI18N

    priceBarCompressionText.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    priceBarCompressionText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    priceBarCompressionText.setText(bundle.getString("PeTraSysTopFrame1.priceBarCompressionFactor.text")); // NOI18N
    priceBarCompressionText.setName("priceBarCompressionFactor"); // NOI18N

    jLabel9.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel9.setText(bundle.getString("PeTraSysTopFrame1.jLabel9.text")); // NOI18N
    jLabel9.setName("jLabel9"); // NOI18N

    org.jdesktop.layout.GroupLayout priceBarsCompressionPanelLayout = new org.jdesktop.layout.GroupLayout(priceBarsCompressionPanel);
    priceBarsCompressionPanel.setLayout(priceBarsCompressionPanelLayout);
    priceBarsCompressionPanelLayout.setHorizontalGroup(
      priceBarsCompressionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(priceBarsCompressionPanelLayout.createSequentialGroup()
        .add(priceBarsCompressionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(priceBarsCompressionPanelLayout.createSequentialGroup()
            .addContainerGap()
            .add(jLabel8))
          .add(priceBarsCompressionPanelLayout.createSequentialGroup()
            .add(91, 91, 91)
            .add(priceBarsCompressionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(priceBarCompressionText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jLabel9))))
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    priceBarsCompressionPanelLayout.setVerticalGroup(
      priceBarsCompressionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(priceBarsCompressionPanelLayout.createSequentialGroup()
        .add(jLabel8)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jLabel9)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(priceBarCompressionText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    setupPane.add(priceBarsCompressionPanel);
    priceBarsCompressionPanel.setBounds(410, 290, 270, 80);

    forwardTestButton.setToolTipText("Updates historic first");
    CurrentIndicatorValuesForAllButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    CurrentIndicatorValuesForAllButton.setText(bundle.getString("PeTraSysTopFrame1.CurrentIndicatorValuesForAllButton.text")); // NOI18N
    CurrentIndicatorValuesForAllButton.setName("CurrentIndicatorValuesForAllButton"); // NOI18N
    CurrentIndicatorValuesForAllButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        CurrentIndicatorValuesForAllButtonActionPerformed(evt);
      }
    });
    setupPane.add(CurrentIndicatorValuesForAllButton);
    CurrentIndicatorValuesForAllButton.setBounds(980, 30, 270, 29);

    UpdateAccountInfo.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    UpdateAccountInfo.setText(bundle.getString("PeTraSysTopFrame1.UpdateAccountInfo.text")); // NOI18N
    UpdateAccountInfo.setName("UpdateAccountInfo"); // NOI18N
    UpdateAccountInfo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        UpdateAccountInfoActionPerformed(evt);
      }
    });
    setupPane.add(UpdateAccountInfo);
    UpdateAccountInfo.setBounds(1030, 80, 156, 29);

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.setupPane.TabConstraints.tabTitle"), setupPane); // NOI18N

    chartPanel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    chartPanel.setName("chartPanel"); // NOI18N

    jScrollPane5.setName("jScrollPane5"); // NOI18N

    chartsRunningTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, bundle.getString("PeTraSysTopFrame1.chartsRunningTable.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
    chartsRunningTable.setFont(new java.awt.Font("DejaVu Sans", 0, 18)); // NOI18N
    chartsRunningTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    chartsRunningTable.setGridColor(java.awt.Color.black);
    chartsRunningTable.setName("chartsRunningTable"); // NOI18N
    jScrollPane5.setViewportView(chartsRunningTable);

    org.jdesktop.layout.GroupLayout chartPanelLayout = new org.jdesktop.layout.GroupLayout(chartPanel);
    chartPanel.setLayout(chartPanelLayout);
    chartPanelLayout.setHorizontalGroup(
      chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 1287, Short.MAX_VALUE)
    );
    chartPanelLayout.setVerticalGroup(
      chartPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 629, Short.MAX_VALUE)
    );

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.chartPanel.TabConstraints.tabTitle"), chartPanel); // NOI18N

    forwardTestPane.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    forwardTestPane.setName("forwardTestPane"); // NOI18N

    org.jdesktop.layout.GroupLayout forwardTestPaneLayout = new org.jdesktop.layout.GroupLayout(forwardTestPane);
    forwardTestPane.setLayout(forwardTestPaneLayout);
    forwardTestPaneLayout.setHorizontalGroup(
      forwardTestPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 1287, Short.MAX_VALUE)
    );
    forwardTestPaneLayout.setVerticalGroup(
      forwardTestPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 629, Short.MAX_VALUE)
    );

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.forwardTestPane.TabConstraints.tabTitle"), forwardTestPane); // NOI18N

    explorationPanel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    explorationPanel.setName("explorationPanel"); // NOI18N

    org.jdesktop.layout.GroupLayout explorationPanelLayout = new org.jdesktop.layout.GroupLayout(explorationPanel);
    explorationPanel.setLayout(explorationPanelLayout);
    explorationPanelLayout.setHorizontalGroup(
      explorationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 1287, Short.MAX_VALUE)
    );
    explorationPanelLayout.setVerticalGroup(
      explorationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 629, Short.MAX_VALUE)
    );

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.explorationPanel.TabConstraints.tabTitle"), explorationPanel); // NOI18N

    tradePanel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    tradePanel.setName("tradePanel"); // NOI18N

    org.jdesktop.layout.GroupLayout tradePanelLayout = new org.jdesktop.layout.GroupLayout(tradePanel);
    tradePanel.setLayout(tradePanelLayout);
    tradePanelLayout.setHorizontalGroup(
      tradePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 1287, Short.MAX_VALUE)
    );
    tradePanelLayout.setVerticalGroup(
      tradePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 629, Short.MAX_VALUE)
    );

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.tradePanel.TabConstraints.tabTitle"), tradePanel); // NOI18N

    backtestPanel.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    backtestPanel.setName("backtestPanel"); // NOI18N

    jPanel1.setName("jPanel1"); // NOI18N
    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

    jLabel7.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    jLabel7.setText(bundle.getString("PeTraSysTopFrame1.jLabel7.text")); // NOI18N
    jLabel7.setName("jLabel7"); // NOI18N
    jPanel1.add(jLabel7);

    backtestInstrumentLabelWithID.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    backtestInstrumentLabelWithID.setMaximumSize(new java.awt.Dimension(192, 24));
    backtestInstrumentLabelWithID.setMinimumSize(new java.awt.Dimension(192, 24));
    backtestInstrumentLabelWithID.setName("backtestInstrumentLabelWithID"); // NOI18N
    jPanel1.add(backtestInstrumentLabelWithID);

    saveIndicatorResultsButton.setText(bundle.getString("PeTraSysTopFrame1.saveIndicatorResultsButton.text")); // NOI18N
    saveIndicatorResultsButton.setName("saveIndicatorResultsButton"); // NOI18N
    saveIndicatorResultsButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveIndicatorResultsButtonActionPerformed(evt);
      }
    });
    jPanel1.add(saveIndicatorResultsButton);

    jScrollPane3.setName("jScrollPane3"); // NOI18N

    BackTestTextArea.setColumns(20);
    BackTestTextArea.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    BackTestTextArea.setRows(5);
    BackTestTextArea.setName("BackTestTextArea"); // NOI18N
    jScrollPane3.setViewportView(BackTestTextArea);

    org.jdesktop.layout.GroupLayout backtestPanelLayout = new org.jdesktop.layout.GroupLayout(backtestPanel);
    backtestPanel.setLayout(backtestPanelLayout);
    backtestPanelLayout.setHorizontalGroup(
      backtestPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(backtestPanelLayout.createSequentialGroup()
        .add(backtestPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(backtestPanelLayout.createSequentialGroup()
            .addContainerGap()
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1263, Short.MAX_VALUE))
          .add(backtestPanelLayout.createSequentialGroup()
            .add(28, 28, 28)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 924, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    backtestPanelLayout.setVerticalGroup(
      backtestPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(backtestPanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 363, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(206, Short.MAX_VALUE))
    );

    tabbedPane.addTab(bundle.getString("PeTraSysTopFrame1.backtestPanel.TabConstraints.tabTitle"), backtestPanel); // NOI18N

    getContentPane().add(tabbedPane);
    tabbedPane.getAccessibleContext().setAccessibleName(bundle.getString("PeTraSysTopFrame1.tabbedPane.AccessibleContext.accessibleName")); // NOI18N

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
      //initLists();
      getDistinctSymbolInfos();
      getDistinctSymbolNames();
      symbolsList.setListData(symbols);
    }//GEN-LAST:event_resetButtonActionPerformed

  /**
   * rpc - 3/4/10 4:29 PM - setupForActionsOnInstrument() is called by
   * runBacktestButtonActionPerformed(), runExplorationButtonActionPerformed(),
   * forwardTestButtonActionPerformed(), and tradeButtonActionPerformed()
   * @param strActionType - like "Back Test"
   * @param runState - a RunState, like RunState.BackTest
   * @param tabToShow the pane to show in this tabbed window, like backtestPane
   */
  public void setupForActionsOnInstrument(String strActionType, RunState runState,
          Component tabToShow) {
    try {
      //rpc - WORKING HERE:3/4/10 4:16 PM - setupForActionsOnInstrument
      writeToReport(strActionType + " Started For: " + (String) symbolsList.getSelectedValue());
      String sym = symbolsList.getSelectedValue().toString();
      Instrument newInstrument = null;
      //newInstrument = new Instrument(currentSymbol, expiry, multiplier, priceMagnifier, exchange, fullName);
      if (useContinuousContractCheckBox.isSelected()) {
        workingSI = (symbolInfo) symbolInfos.get(sym);
        newInstrument = new Instrument(sym, DBops.maxExpiryWithDataBySymbol(sym), workingSI.multiplier,
                workingSI.priceMagnifier, workingSI.exchange, workingSI.fullName);
      } else {
        CurrentContractsDlg ccd = new CurrentContractsDlg(this, true, sym);
        newInstrument = ccd.startAndReturnInstrument();
      }
      if (newInstrument == null) {
        return;
      }
      //rpc - NOTE:3/12/10 12:32 PM - This will be appropriate for all, use realtime bars
      newInstrument.setMinutePriceBars(getPriceBars2());
      int compressionFactor = Integer.parseInt(priceBarCompressionText.getText());
      if (compressionFactor != 1) {
        newInstrument.compressPriceBars(compressionFactor);
      }
      Object[] indicators = IndicatorList.getSelectedValues();
      for (Object ind : indicators) {
        //rpc - NOTE:3/4/10 7:03 AM - To use newInstance(), must have a 0 arg ctor
        // see notes-java for a passing params to ctor
        newInstrument.addIndicator((Indicator) getIndicatorClassFromName(ind.toString()).newInstance());
      }
      newInstrument.setRunState(runState);
      int cont = JOptionPane.showConfirmDialog(this,
              "Starting " + strActionType + " of " + newInstrument.getShortName() + " continue?");
      // yes = 0, no = 1, cancel = 2
      if (cont == 0) {
        Thread thread = new Thread(newInstrument);
        thread.start();
//        if (tabToShow != null) {
//          tabbedPane.setSelectedComponent(tabToShow);
//        }
      }

    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  public JTextArea getBackTestTextArea() {
    return BackTestTextArea;
  }

    private void chartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartButtonActionPerformed
      setupForActionsOnInstrument("Chart ", RunState.Chart, chartPanel);
    }//GEN-LAST:event_chartButtonActionPerformed

    private void symbolsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_symbolsListValueChanged
      String sym = (String) symbolsList.getSelectedValue();
      try {
        ResultSet res = DBops.minMaxDatesBySym(sym).executeQuery();
        if (res.next()) {
          Timestamp minD = res.getTimestamp(1);
          Timestamp maxD = res.getTimestamp(2);
          Calendar minCal = new GregorianCalendar();
          minCal.setTime(minD);
          Calendar maxCal = new GregorianCalendar();
          maxCal.setTime(maxD);
          SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
          String beginTime = dateFormat.format(minCal.getTime());
          String endTime = dateFormat.format(maxCal.getTime());
          beginDateChooser.setCalendar(minCal);
          endDateChooser.setCalendar(maxCal);
          beginDateLabel.setText(DateOps.dbFormatString(minD));
          endDateLabel.setText(DateOps.dbFormatString(maxD));
        }
        //int i = 1;
      } catch (SQLException ex) {
        MsgBox.err2(ex);
      }
    }//GEN-LAST:event_symbolsListValueChanged

  /**
   *
   * @return
   */
  public boolean useHistoricalData() {
    return useHistoricalDataCheckBox.isSelected();
  }

  /**
   * rpc - 3/4/10 9:25 AM This keeps the window in the center instead of upper left corner.
   * @param evt
   */
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      dispose();
    }//GEN-LAST:event_formWindowClosing

  /**
   * @param evt
   */
    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
      try {

        //populateIndicators();
        PriceBars pbs = getPriceBars2();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy-hhmmss");
        String tabName = pbs.getSymbol() + "-test-"
                + formatter.format(pbs.getBeginDate()) + "-" + formatter.format(pbs.getEndDate());
        String createString = "CREATE TABLE IF NOT EXISTS `Trading`.`" + tabName + "` ("
                + "`symbol` VARCHAR( 15 ) NOT NULL , "
                + "`dt` DATETIME NOT NULL , "
                + "`open` DOUBLE NOT NULL , "
                + "`high` DOUBLE NOT NULL , "
                + "`low` DOUBLE NOT NULL , "
                + "`close` DOUBLE NOT NULL , "
                + "`volume` BIGINT( 20 ) NOT NULL, "
                + "PRIMARY KEY(`symbol`, `dt`))";
        Statement stmt;
        stmt = DBops.setuptradesConnection().createStatement();
        stmt.executeUpdate(createString);
        PreparedStatement stmtForQuotes = DBops.setuptradesConnection().prepareStatement(
                "INSERT INTO `" + tabName + "` VALUES (?, ? , ?, ?, ?, ?, ?)");
        int size = pbs.size();
        for (PriceBar priceBar : pbs.getDatas()) {
          java.sql.Timestamp dateIn = new java.sql.Timestamp(priceBar.getDate());
          //java.sql.Date dateIn = new java.sql.Date(udatein);
          stmtForQuotes.setString(1, pbs.getSymbol());
          stmtForQuotes.setTimestamp(2, dateIn);
          stmtForQuotes.setDouble(3, priceBar.getOpen());
          stmtForQuotes.setDouble(4, priceBar.getHigh());
          stmtForQuotes.setDouble(5, priceBar.getLow());
          stmtForQuotes.setDouble(6, priceBar.getClose());
          stmtForQuotes.setLong(7, priceBar.getVolume());
          stmtForQuotes.addBatch();
        }
        int[] updateCounts = stmtForQuotes.executeBatch();
        stmtForQuotes.close();
        //int i = 1;
      } catch (Exception ex) {
        Logger.getLogger(PeTraSysTopFrame1.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_testButtonActionPerformed

  private void testButtonActionPerformed_old(java.awt.event.ActionEvent evt) {
    List<PriceBar> pbs = getPriceBars();
    PriceBar pb;
    long dd;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    String ddt;
    for (int i = 0; i < 5; i++) {
      pb = pbs.get(i);
      dd = pb.getDate();
      ddt = dateFormat.format(dd);
      dd += 1;
    }
  }

  /**
   * rpc - 3/4/10 3:44 PM This is where explorations are run, all the work
   * is in setupForActionsOnInstrument()
   * @param evt not used
   */
    private void runExplorationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runExplorationButtonActionPerformed
      setupForActionsOnInstrument("Exploration", RunState.Exploration, explorationPanel);
    }//GEN-LAST:event_runExplorationButtonActionPerformed

  /**
   * rpc - 3/5/10 7:01 AM Forward Tests entry point
   * @param evt
   */
    private void forwardTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardTestButtonActionPerformed
      setupForActionsOnInstrument("Forward Test", RunState.ForwardTest, forwardTestPane);
    }//GEN-LAST:event_forwardTestButtonActionPerformed

    private void continuousContractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuousContractButtonActionPerformed
      ContinuousContractDlg dialog = new ContinuousContractDlg(this, false);
      dialog.setVisible(true);
    }//GEN-LAST:event_continuousContractButtonActionPerformed

  /**
   * rpc - 3/4/10 9:12 AM Show the report form
   * @param evt
   */
    private void viewReportFormButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewReportFormButtonActionPerformed
      getReportFrame().setVisible(true);
    }//GEN-LAST:event_viewReportFormButtonActionPerformed

  private void bringSymbolCurrent(String sym, MySocket socket) {
    try {
      int expiry = DBops.maxExpiryWithDataBySymbol(sym);
      Date expD = DateOps.dateFromExpiryFormatString(Integer.toString(expiry));
      if (expD.before(new Date())) {
        MsgBox.err("Need new Expiry (" + expiry + ") is latest in DB...", "Error in bringSymbolCurrent()");
        return;
      }
      PreparedStatement pstmt = DBops.exchangeBySymbolandExpiry(sym, expiry);
      ResultSet res = pstmt.executeQuery();
      String exchange;
      if (res.next()) {
        exchange = res.getString(1);
      } else {
        throw new Exception("No Exchange String returned.");
      }
      Contract contract = ContractFactory.makeContract(sym, "FUT", exchange, Integer.toString(expiry), "USD");
      Calendar startDate = Calendar.getInstance();
      Calendar endDate = Calendar.getInstance();
      endDate.setTime(new Date());
      startDate.setTime(DateOps.dateFromDbFormatString(endDateLabel.getText()));
      HistoricalPriceDataDownloader histDownloader = new HistoricalPriceDataDownloader(histFromTWS, socket);
      histFromTWS.setMyMate(histDownloader);
      histDownloader.setupDownloader(contract, startDate, endDate, BarSize.Min1);
      //rpc - NOTE:7/8/10 5:55 PM - Put a new dialog in here to start thread, and join it after
      // a few seconds, and in the dialog have a box to kill the thread...
      Thread thread = new Thread(histDownloader);
      thread.setName("histDownloader");
      thread.start();
      thread.join();
    } catch (Exception ex) {
      MsgBox.err2(ex);
    } finally {
      int j = 3;
    }
  }

//  private void bringSymbolCurrent(String sym) {
//    try {
//      int expiry = DBops.maxExpiryWithDataBySymbol(sym);
//      Date expD = DateOps.dateFromExpiryFormatString(Integer.toString(expiry));
//      if (expD.before(new Date())) {
//        MsgBox.err("Need new Expiry (" + expiry + ") is latest in DB...", "Error in bringSymbolCurrent()");
//        return;
//      }
//      PreparedStatement pstmt = DBops.exchangeBySymbolandExpiry(sym, expiry);
//      ResultSet res = pstmt.executeQuery();
//      String exchange;
//      if (res.next()) {
//        exchange = res.getString(1);
//      } else {
//        throw new Exception("No Exchange String returned.");
//      }
//      Contract contract = ContractFactory.makeContract(sym, "FUT", exchange, Integer.toString(expiry), "USD");
//      Calendar startDate = Calendar.getInstance();
//      Calendar endDate = Calendar.getInstance();
//      endDate.setTime(new Date());
//      startDate.setTime(DateOps.dateFromDbFormatString(endDateLabel.getText()));
//      HistoricalPriceDataDownloader histDownloader = new HistoricalPriceDataDownloader(histFromTWS);
//      histFromTWS.setMyMate(histDownloader);
//      histDownloader.setupDownloader(contract, startDate, endDate, BarSize.Min1);
//      Thread thread = new Thread(histDownloader);
//      thread.setName("histDownloader");
//      thread.start();
//      thread.join();
//    } catch (Exception ex) {
//      MsgBox.err2(ex);
//    } finally {
//      int j = 3;
//    }
//  }

  /**
   * rpc - 3/3/10 5:26 PM This brings the selected symbols 1 minute price date
   * up to the current datetime.
   **/
    private void bringCurrentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bringCurrentButtonActionPerformed
      String sym = (String) symbolsList.getSelectedValue();
      downloadWaitLabel.setText("Updating: " + sym + "....");
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      MySocket socket = IBConnectionManager.connect(histFromTWS);
      bringSymbolCurrent(sym, socket);
      socket.disConnect();
      downloadWaitLabel.setText("Updating Done: " + sym + "....");
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      socket.disConnect();

//      try {
//        int expiry = DBops.maxExpiryWithDataBySymbol(sym);
//        Date expD = DateOps.dateFromExpiryFormatString(Integer.toString(expiry));
//        if (expD.before(new Date())) {
//          downloadWaitLabel.setText("Need new Expiry (" + expiry + ") is latest in DB...");
//          return;
//        }
//        PreparedStatement pstmt = DBops.exchangeBySymbolandExpiry(sym, expiry);
//        ResultSet res = pstmt.executeQuery();
//        String exchange;
//        if (res.next()) {
//          exchange = res.getString(1);
//        } else {
//          throw new Exception("No Exchange String returned.");
//        }
//        Contract contract = ContractFactory.makeContract(sym, "FUT", exchange, Integer.toString(expiry), "USD");
//        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = Calendar.getInstance();
//        endDate.setTime(new Date());
//        startDate.setTime(DateOps.dateFromDbFormatString(endDateLabel.getText()));
//        downloadWaitLabel.setText("Updating: " + sym + "....");
//
//        HistoricalPriceDataDownloader histDownloader = new HistoricalPriceDataDownloader(histFromTWS);
//        histFromTWS.setMyMate(histDownloader);
//        histDownloader.setupDownloader(contract, startDate, endDate, BarSize.Min1);
//        Thread thread = new Thread(histDownloader);
//        thread.setName("histDownloader");
////        DownloadingWaitDlg dwd = new DownloadingWaitDlg(PeTraSys.getTopFrame(), false);
////        dwd.setVisible(true);
//        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        thread.start();
//        thread.join();
//        //dwd.dispose();
//        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        //downloadWaitLabel.setText("ok");
//        int j = 2;
//      } catch (Exception ex) {
//        MsgBox.err2(ex);
//      } finally {
//        downloadWaitLabel.setText("Updating Done: " + sym + "....");
//      }
    }//GEN-LAST:event_bringCurrentButtonActionPerformed

    private void tradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tradeButtonActionPerformed
      //rpc - WORKING HERE:3/4/10 4:32 PM - tradeButtonActionPerformed()
      //setupForActionsOnInstrument("Live Trading", RunState.Trade, tradePanel);
    }//GEN-LAST:event_tradeButtonActionPerformed

    private void contractInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractInfoButtonActionPerformed
      ContractInfoDialog cid = new ContractInfoDialog(this);
      cid.setVisible(true);
    }//GEN-LAST:event_contractInfoButtonActionPerformed

    private void testChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testChartButtonActionPerformed
      symbolsList.setSelectedValue("DX", true);
      //int year, int month, int dayOfMonth, int hourOfDay, int minute)
      //'2010-03-16 00:00:00', '2010-03-16 12:25:00'
      GregorianCalendar bdate = new GregorianCalendar(2010, 6, 20, 0, 0);
      GregorianCalendar edate = new GregorianCalendar(2010, 6, 23, 13, 58);
      beginDateChooser.setCalendar(bdate);
      endDateChooser.setCalendar(edate);
      IndicatorList.setSelectedValue("ADXandPlusMinusDi", true);
      priceBarCompressionText.setText("5");
      setupForActionsOnInstrument("Chart", RunState.Chart, chartPanel);
      //setupForActionsOnInstrument("Back Test", RunState.BackTest, backtestPanel);
//      List<PriceBar> pbs = getPriceBars();
//      ADXChart chart = new ADXChart(pbs);
//      chart.setExtendedState(Frame.MAXIMIZED_BOTH);
//      chart.setVisible(true);
    }//GEN-LAST:event_testChartButtonActionPerformed

    private void saveIndicatorResultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveIndicatorResultsButtonActionPerformed
      Instrument instrument = PeTraSys.fetchInstrument(backtestInstrumentLabelWithID.getId());
      new IndicatorResults(instrument).toDB();
    }//GEN-LAST:event_saveIndicatorResultsButtonActionPerformed

  public void bringAllCurrent() {
    //rpc - NOTE HERE:4/13/10 6:13 PM - Is fixed, try was killing socket, had to go outside for loop
    ListModel lm = symbolsList.getModel();
    String sym = null;
    MySocket socket = IBConnectionManager.connect(histFromTWS);
    try {
      for (int i = 0; i < lm.getSize(); i++) {
        sym = (String) lm.getElementAt(i);
        ResultSet res = DBops.minMaxDatesBySym(sym).executeQuery();
        if (res.next()) {
          Timestamp maxD = res.getTimestamp(2);
          endDateLabel.setText(DateOps.dbFormatString(maxD));
        }
        downloadWaitLabel.setText("Updating: " + sym + "....");
        repaint();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        bringSymbolCurrent(sym, socket);
        int j = 3;
      }
    } catch (Exception ex) {
      MsgBox.err2(ex);
    } finally {
      downloadWaitLabel.setText("Updating Done: " + sym + "....");
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      socket.disConnect();
    }
  }

    private void bringAllCurrentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bringAllCurrentButtonActionPerformed
      bringAllCurrent();
    }//GEN-LAST:event_bringAllCurrentButtonActionPerformed

    private void runBacktestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runBacktestButtonActionPerformed
      setupForActionsOnInstrument("Back Test", RunState.BackTest, backtestPanel);
    }//GEN-LAST:event_runBacktestButtonActionPerformed

    private void CurrentIndicatorValuesForAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CurrentIndicatorValuesForAllButtonActionPerformed
      //rpc - WORKING HERE:4/26/10 12:59 PM - CurrentIndicatorValuesForAllButtonActionPerformed()
      new ScanIndicators();
    }//GEN-LAST:event_CurrentIndicatorValuesForAllButtonActionPerformed

    private void UpdateAccountInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateAccountInfoActionPerformed
      AccountUpdates au = new AccountUpdates();
      Thread auThread = new Thread(au);
      auThread.run();
    }//GEN-LAST:event_UpdateAccountInfoActionPerformed

  public void setBacktestInstrumentLabelWithID(String instrument, int id) {
    backtestInstrumentLabelWithID.setText(instrument);
    backtestInstrumentLabelWithID.setId(id);


  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    PeTraSysTopFrame1 pt = new PeTraSysTopFrame1();
    pt.setVisible(true);
//    java.awt.EventQueue.invokeLater(new Runnable() {
//
//      @Override
//      public void run() {
//        new PeTraSysTopFrame1().setVisible(true);
//      }
//    });


  }

  /**
   *
   * @return
   */
  public double getBidAskSpread() {
    return 0;


  }

  /**
   *
   * @return
   */
  public double getSlippage() {
    return 0;


  }

  /**
   *
   * @return
   */
  public double getCommission() {
    return 3.00;


  }

  /**
   *
   * @return
   */
  public PriceBars getPriceBars2() {
    List<PriceBar> pbs = getPriceBars();
    String sym = (String) symbolsList.getSelectedValue();
    java.util.Date begind = new java.util.Date(beginDateChooser.getCalendar().getTimeInMillis());
    java.util.Date endd = new java.util.Date(endDateChooser.getCalendar().getTimeInMillis());
    PriceBars rpb = new PriceBars(pbs, sym, begind, endd);
    MsgBox.info(Integer.toString(rpb.size()), "Price Bars Returned");
    return rpb;
  }

  public JDateChooser getBeginDateChooser() {
    return beginDateChooser;
  }

  public JDateChooser getEndDateChooser() {
    return endDateChooser;
  }

  public List<PriceBar> getPriceBars() {
    ArrayList<PriceBar> priceBars = new ArrayList<PriceBar>();
    try {
      String sym = (String) symbolsList.getSelectedValue();
      workingSI = (symbolInfo) symbolInfos.get(sym);
      Calendar beginCal = beginDateChooser.getCalendar();
      Calendar endCal = endDateChooser.getCalendar();
      int min = beginCal.get(Calendar.MINUTE);
      CallableStatement datedRangeBySymbol =
              DBops.datedRangeBySymbol(sym, new Timestamp(beginCal.getTimeInMillis()),
              new Timestamp(endCal.getTimeInMillis()));
      ResultSet res = datedRangeBySymbol.executeQuery();
      int i = 0;
      while (res.next()) {
        PriceBar priceBar = new PriceBar(
                res.getTimestamp("datetime").getTime(),
                res.getDouble("open") / workingSI.priceMagnifier * workingSI.multiplier,
                res.getDouble("high") / workingSI.priceMagnifier * workingSI.multiplier,
                res.getDouble("low") / workingSI.priceMagnifier * workingSI.multiplier,
                res.getDouble("close") / workingSI.priceMagnifier * workingSI.multiplier,
                res.getLong("volume"));
        priceBars.add(priceBar);
        i++;
      }
    } catch (SQLException ex) {
      MsgBox.err2(ex);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Dates for bars set?", "getPriceBars()", JOptionPane.ERROR_MESSAGE);
    } finally {
      return priceBars;
    }
  }

  private void populateIndicators() {
    try {
      indicatorsList = Classes.getClassesForPackage("petrasys.indicators");  //indicators
      //strategiesList = Classes.getClassesForPackage("petrasys.strategies");
//      for(int i = 1; i < indicatorsList.size(); i++) {
//        System.out.println("Indicator: " + indicatorsList.get(i).getName());
//      }
    } catch (ClassNotFoundException ex) {
      MsgBox.err2(ex);


    }
  }
  private NumberFormat compressionFormat;
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextArea BackTestTextArea;
  private javax.swing.JButton CurrentIndicatorValuesForAllButton;
  private javax.swing.JList IndicatorList;
  private javax.swing.JButton UpdateAccountInfo;
  private SimpleBean.JLabelWithID backtestInstrumentLabelWithID;
  private javax.swing.JPanel backtestPanel;
  private com.toedter.calendar.JDateChooser beginDateChooser;
  private javax.swing.JLabel beginDateLabel;
  private javax.swing.JButton bringAllCurrentButton;
  private javax.swing.JButton bringCurrentButton;
  private javax.swing.JButton chartButton;
  private javax.swing.JPanel chartPanel;
  private javax.swing.JTable chartsRunningTable;
  private javax.swing.JButton continuousContractButton;
  private javax.swing.JButton contractInfoButton;
  private javax.swing.JLabel downloadWaitLabel;
  private com.toedter.calendar.JDateChooser endDateChooser;
  private javax.swing.JLabel endDateLabel;
  private javax.swing.JPanel explorationPanel;
  private javax.swing.JButton forwardTestButton;
  private javax.swing.JPanel forwardTestPane;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane4;
  private javax.swing.JScrollPane jScrollPane5;
  private javax.swing.JTextField priceBarCompressionText;
  private javax.swing.JPanel priceBarsCompressionPanel;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton runBacktestButton;
  private javax.swing.JButton runExplorationButton;
  private javax.swing.JButton saveIndicatorResultsButton;
  private javax.swing.JPanel setupPane;
  private javax.swing.JList symbolsList;
  private javax.swing.JTabbedPane tabbedPane;
  private javax.swing.JButton testButton;
  private javax.swing.JButton tradeButton;
  private javax.swing.JPanel tradePanel;
  private javax.swing.JCheckBox useContinuousContractCheckBox;
  private javax.swing.JCheckBox useHistoricalDataCheckBox;
  private javax.swing.JButton viewReportFormButton;
  // End of variables declaration//GEN-END:variables

  public JPanel getChartPanel() {
    return chartPanel;
  }
}
