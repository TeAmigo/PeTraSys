/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ADXChartControlFrame.java
 *
 * Created on Apr 6, 2010, 10:25:39 AM
 */
package petrasys.charts;

import java.awt.HeadlessException;
import java.util.Date;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;

/**
 *
 * @author rickcharon
 */
public class ADXChartControlFrame extends javax.swing.JFrame {

  ADXChart chart;
  Date minBeginDate;
  Date maxEndDate;
  Date beginDate;
  Date endDate;
  DateAxis dateAxis;

  // CTor for main() here, just to check layout, etc.
  public ADXChartControlFrame() {
    initComponents();
  }



  /** Creates new form ADXChartControlFrame1 */
  public ADXChartControlFrame(ADXChart chart) {
    this.chart = chart;
    initComponents();
    stopAdjustTextPane.setText(chart.getInstrument().getTraderFrame().getStopLossAmount().toString());
    profitAdjustTextPane.setText(chart.getInstrument().getTraderFrame().getProfitStopAmount().toString());
    setupChoosers();
  }
  
  java.awt.Toolkit toolkit = this.getToolkit();
  java.awt.Image appIcon = toolkit.createImage("/usr/share/icons/cab_view.png");

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    daysMinButtonGroup = new javax.swing.ButtonGroup();
    datetimeChooserPanel = new javax.swing.JPanel();
    endDateChooser = new com.toedter.calendar.JDateChooser();
    jLabel2 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    beginDateChooser = new com.toedter.calendar.JDateChooser();
    datetimesToChartButton = new javax.swing.JButton();
    chartToDatesButton = new javax.swing.JButton();
    resetToAllDatesButton = new javax.swing.JButton();
    adjustRangesPanel = new javax.swing.JPanel();
    adjustRangesButton = new javax.swing.JButton();
    crosshairsPanel = new javax.swing.JPanel();
    restoreCrossHairsButton = new javax.swing.JButton();
    saveCrossHairsButton = new javax.swing.JButton();
    traderPanel = new javax.swing.JPanel();
    stopAdjustButton = new javax.swing.JButton();
    stopAdjustTextPane = new javax.swing.JTextPane();
    profitAdjustBotton = new javax.swing.JButton();
    profitAdjustTextPane = new javax.swing.JTextPane();
    jumpPanel = new javax.swing.JPanel();
    JumpAmtTxt = new javax.swing.JTextPane();
    jumpButton = new javax.swing.JButton();
    daysRadioButton = new javax.swing.JRadioButton("", true);
    minutesRadioButton = new javax.swing.JRadioButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setIconImage(appIcon);

    datetimeChooserPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    datetimeChooserPanel.setPreferredSize(new java.awt.Dimension(600, 115));

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("petrasys/Bundle"); // NOI18N
    endDateChooser.setDateFormatString(bundle.getString("PeTraSysTopFrame1.endDateChooser.dateFormatString")); // NOI18N
    endDateChooser.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    //endDateChooser.setVisible(false);

    jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel2.setText(bundle.getString("PeTraSysTopFrame1.jLabel2.text")); // NOI18N

    jLabel6.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    jLabel6.setText(bundle.getString("PeTraSysTopFrame1.jLabel6.text")); // NOI18N

    beginDateChooser.setDateFormatString(bundle.getString("PeTraSysTopFrame1.beginDateChooser.dateFormatString")); // NOI18N
    beginDateChooser.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
    beginDateChooser.setMinimumSize(new java.awt.Dimension(38, 42));
    //beginDateChooser.setVisible(false);

    datetimesToChartButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    datetimesToChartButton.setText("Above to Chart");
    datetimesToChartButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        datetimesToChartButtonActionPerformed(evt);
      }
    });

    chartToDatesButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    chartToDatesButton.setText("Chart to Above");
    chartToDatesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        chartToDatesButtonActionPerformed(evt);
      }
    });

    resetToAllDatesButton.setFont(new java.awt.Font("DejaVu Sans", 1, 14));
    resetToAllDatesButton.setText("All DateTimes");
    resetToAllDatesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetToAllDatesButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout datetimeChooserPanelLayout = new javax.swing.GroupLayout(datetimeChooserPanel);
    datetimeChooserPanel.setLayout(datetimeChooserPanelLayout);
    datetimeChooserPanelLayout.setHorizontalGroup(
      datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
            .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(beginDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
          .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addComponent(datetimesToChartButton)
            .addGap(48, 48, 48)
            .addComponent(chartToDatesButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resetToAllDatesButton)
            .addGap(1339, 1339, 1339))))
    );
    datetimeChooserPanelLayout.setVerticalGroup(
      datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
            .addComponent(jLabel6)
            .addGap(3, 3, 3)
            .addComponent(beginDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(datetimesToChartButton)
          .addComponent(resetToAllDatesButton)
          .addComponent(chartToDatesButton))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    adjustRangesPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

    adjustRangesButton.setText("Adjust Ranges");
    adjustRangesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        adjustRangesButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout adjustRangesPanelLayout = new javax.swing.GroupLayout(adjustRangesPanel);
    adjustRangesPanel.setLayout(adjustRangesPanelLayout);
    adjustRangesPanelLayout.setHorizontalGroup(
      adjustRangesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(adjustRangesPanelLayout.createSequentialGroup()
        .addGap(34, 34, 34)
        .addComponent(adjustRangesButton)
        .addContainerGap(49, Short.MAX_VALUE))
    );
    adjustRangesPanelLayout.setVerticalGroup(
      adjustRangesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, adjustRangesPanelLayout.createSequentialGroup()
        .addContainerGap(53, Short.MAX_VALUE)
        .addComponent(adjustRangesButton)
        .addContainerGap())
    );

    crosshairsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    crosshairsPanel.setLayout(new java.awt.BorderLayout());

    restoreCrossHairsButton.setText("RestoreCrossHairs");
    crosshairsPanel.add(restoreCrossHairsButton, java.awt.BorderLayout.CENTER);

    saveCrossHairsButton.setText("SaveCrossHairs");
    saveCrossHairsButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveCrossHairsButtonActionPerformed(evt);
      }
    });
    crosshairsPanel.add(saveCrossHairsButton, java.awt.BorderLayout.PAGE_START);

    traderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Trader Vars"));

    stopAdjustButton.setText("stop adj.");
    stopAdjustButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopAdjustButtonActionPerformed(evt);
      }
    });

    stopAdjustTextPane.setBackground(java.awt.Color.white);
    stopAdjustTextPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    stopAdjustTextPane.setFont(new java.awt.Font("DejaVu Sans", 0, 18));

    profitAdjustBotton.setText("Profit adj.");
    profitAdjustBotton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        profitAdjustBottonActionPerformed(evt);
      }
    });

    profitAdjustTextPane.setBackground(java.awt.Color.white);
    profitAdjustTextPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    profitAdjustTextPane.setFont(new java.awt.Font("DejaVu Sans", 0, 18));

    javax.swing.GroupLayout traderPanelLayout = new javax.swing.GroupLayout(traderPanel);
    traderPanel.setLayout(traderPanelLayout);
    traderPanelLayout.setHorizontalGroup(
      traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, traderPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(stopAdjustTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
          .addComponent(profitAdjustTextPane, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
          .addComponent(stopAdjustButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(profitAdjustBotton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    traderPanelLayout.setVerticalGroup(
      traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, traderPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
          .addComponent(stopAdjustTextPane)
          .addComponent(stopAdjustButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(traderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(traderPanelLayout.createSequentialGroup()
            .addComponent(profitAdjustBotton)
            .addGap(33, 33, 33))
          .addGroup(traderPanelLayout.createSequentialGroup()
            .addComponent(profitAdjustTextPane, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(24, 24, 24))))
    );

    jumpPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Jump Forward Amount"));

    JumpAmtTxt.setBackground(java.awt.Color.white);
    JumpAmtTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    JumpAmtTxt.setFont(new java.awt.Font("DejaVu Sans", 0, 18));

    jumpButton.setText("Jump!");
    jumpButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jumpButtonActionPerformed(evt);
      }
    });

    daysMinButtonGroup.add(daysRadioButton);
    daysRadioButton.setText("Days");

    daysMinButtonGroup.add(minutesRadioButton);
    minutesRadioButton.setText("Minutes");

    javax.swing.GroupLayout jumpPanelLayout = new javax.swing.GroupLayout(jumpPanel);
    jumpPanel.setLayout(jumpPanelLayout);
    jumpPanelLayout.setHorizontalGroup(
      jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jumpPanelLayout.createSequentialGroup()
        .addGap(28, 28, 28)
        .addGroup(jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(minutesRadioButton)
          .addComponent(daysRadioButton))
        .addContainerGap(116, Short.MAX_VALUE))
      .addGroup(jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jumpPanelLayout.createSequentialGroup()
          .addGap(11, 11, 11)
          .addComponent(JumpAmtTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(jumpButton, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
          .addGap(12, 12, 12)))
    );
    jumpPanelLayout.setVerticalGroup(
      jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jumpPanelLayout.createSequentialGroup()
        .addContainerGap(52, Short.MAX_VALUE)
        .addComponent(daysRadioButton)
        .addGap(3, 3, 3)
        .addComponent(minutesRadioButton)
        .addContainerGap())
      .addGroup(jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jumpPanelLayout.createSequentialGroup()
          .addGap(11, 11, 11)
          .addGroup(jumpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
            .addComponent(JumpAmtTxt)
            .addComponent(jumpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addContainerGap(72, Short.MAX_VALUE)))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
          .addGroup(layout.createSequentialGroup()
            .addGap(369, 369, 369)
            .addComponent(jumpPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(traderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
            .addComponent(datetimeChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(adjustRangesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(crosshairsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(51, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(datetimeChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(crosshairsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(adjustRangesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jumpPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(traderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap(18, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void datetimesToChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datetimesToChartButtonActionPerformed
      beginDate = beginDateChooser.getDate();
      endDate = endDateChooser.getDate();
      dateAxis.setMaximumDate(endDate);
      dateAxis.setMinimumDate(beginDate);
}//GEN-LAST:event_datetimesToChartButtonActionPerformed

    private void chartToDatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartToDatesButtonActionPerformed
      beginDate = dateAxis.getMinimumDate();
      endDate = dateAxis.getMaximumDate();
      beginDateChooser.setDate(beginDate);
      endDateChooser.setDate(endDate);
}//GEN-LAST:event_chartToDatesButtonActionPerformed

    private void resetToAllDatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetToAllDatesButtonActionPerformed
      beginDate = minBeginDate;
      endDate = maxEndDate;
      beginDateChooser.setDate(beginDate);
      endDateChooser.setDate(endDate);
      dateAxis.setMaximumDate(endDate);
      dateAxis.setMinimumDate(beginDate);
}//GEN-LAST:event_resetToAllDatesButtonActionPerformed

    private void adjustRangesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adjustRangesButtonActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_adjustRangesButtonActionPerformed

    private void saveCrossHairsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCrossHairsButtonActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_saveCrossHairsButtonActionPerformed

    private void stopAdjustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopAdjustButtonActionPerformed
      chart.getInstrument().getTraderFrame().setStopLossAmount(Double.parseDouble(stopAdjustTextPane.getText()));
    }//GEN-LAST:event_stopAdjustButtonActionPerformed

    private void profitAdjustBottonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profitAdjustBottonActionPerformed
     chart.getInstrument().getTraderFrame().setProfitStopAmount(Double.parseDouble(profitAdjustTextPane.getText()));
    }//GEN-LAST:event_profitAdjustBottonActionPerformed

    private void jumpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumpButtonActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_jumpButtonActionPerformed

  private void setupChoosers() {
    minBeginDate = beginDate = chart.getBeginDate();
    maxEndDate = endDate = chart.getEndDate();
    beginDateChooser.setDate(beginDate);
    endDateChooser.setDate(endDate);
    dateAxis = (DateAxis) chart.getComboPlot().getDomainAxis();
    dateAxis.setMinimumDate(beginDate);
    dateAxis.setMaximumDate(endDate);
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new ADXChartControlFrame().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextPane JumpAmtTxt;
  private javax.swing.JButton adjustRangesButton;
  private javax.swing.JPanel adjustRangesPanel;
  private com.toedter.calendar.JDateChooser beginDateChooser;
  private javax.swing.JButton chartToDatesButton;
  private javax.swing.JPanel crosshairsPanel;
  private javax.swing.JPanel datetimeChooserPanel;
  private javax.swing.JButton datetimesToChartButton;
  private javax.swing.ButtonGroup daysMinButtonGroup;
  private javax.swing.JRadioButton daysRadioButton;
  private com.toedter.calendar.JDateChooser endDateChooser;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JButton jumpButton;
  private javax.swing.JPanel jumpPanel;
  private javax.swing.JRadioButton minutesRadioButton;
  private javax.swing.JButton profitAdjustBotton;
  private javax.swing.JTextPane profitAdjustTextPane;
  private javax.swing.JButton resetToAllDatesButton;
  private javax.swing.JButton restoreCrossHairsButton;
  private javax.swing.JButton saveCrossHairsButton;
  private javax.swing.JButton stopAdjustButton;
  private javax.swing.JTextPane stopAdjustTextPane;
  private javax.swing.JPanel traderPanel;
  // End of variables declaration//GEN-END:variables
}