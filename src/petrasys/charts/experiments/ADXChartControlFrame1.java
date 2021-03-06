/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ADXChartControlFrame1.java
 *
 * Created on Apr 5, 2010, 6:54:48 AM
 */
package petrasys.charts.experiments;

import java.util.Date;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import petrasys.charts.ADXChart;

/**
 * rpc - 4/5/10 3:52 PM - implements AxisChangeListener taken out,
 * @author rickcharon
 */
public class ADXChartControlFrame1 extends javax.swing.JFrame {

  ADXChart chart;

  /** Creates new form ADXChartControlFrame1 */
  public ADXChartControlFrame1(ADXChart chart) {
    this.chart = chart;
    initComponents();
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

    datetimeChooserPanel = new javax.swing.JPanel();
    endDateChooser = new com.toedter.calendar.JDateChooser();
    jLabel2 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    beginDateChooser = new com.toedter.calendar.JDateChooser();
    datetimesToChartButton = new javax.swing.JButton();
    chartToDatesButton = new javax.swing.JButton();
    resetToAllDatesButton = new javax.swing.JButton();
    adjustRangesButton = new javax.swing.JButton();
    rangeAdjustPanel = new javax.swing.JPanel();
    sizeAdjustPanel = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    adjustPlotProportionsButton = new javax.swing.JButton();

    setTitle("ThirdChart Control");
    setIconImage(appIcon);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowOpened(java.awt.event.WindowEvent evt) {
        formWindowOpened(evt);
      }
    });

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

    adjustRangesButton.setText("Adjust Ranges");

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
              .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(72, 72, 72)
            .addComponent(adjustRangesButton)
            .addGap(1014, 1014, 1014))
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
          .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adjustRangesButton)
            .addGroup(datetimeChooserPanelLayout.createSequentialGroup()
              .addComponent(jLabel2)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(datetimeChooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(datetimesToChartButton)
          .addComponent(resetToAllDatesButton)
          .addComponent(chartToDatesButton))
        .addContainerGap(87, Short.MAX_VALUE))
    );

    rangeAdjustPanel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
    rangeAdjustPanel.setPreferredSize(new java.awt.Dimension(281, 120));

    javax.swing.GroupLayout rangeAdjustPanelLayout = new javax.swing.GroupLayout(rangeAdjustPanel);
    rangeAdjustPanel.setLayout(rangeAdjustPanelLayout);
    rangeAdjustPanelLayout.setHorizontalGroup(
      rangeAdjustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 167, Short.MAX_VALUE)
    );
    rangeAdjustPanelLayout.setVerticalGroup(
      rangeAdjustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 59, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout sizeAdjustPanelLayout = new javax.swing.GroupLayout(sizeAdjustPanel);
    sizeAdjustPanel.setLayout(sizeAdjustPanelLayout);
    sizeAdjustPanelLayout.setHorizontalGroup(
      sizeAdjustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 224, Short.MAX_VALUE)
    );
    sizeAdjustPanelLayout.setVerticalGroup(
      sizeAdjustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 84, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 100, Short.MAX_VALUE)
    );

    adjustPlotProportionsButton.setText("Adjust Plot Proportions");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(datetimeChooserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addGap(664, 664, 664)
            .addComponent(rangeAdjustPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addGap(1980, 1980, 1980)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(adjustPlotProportionsButton)
        .addGap(947, 947, 947)
        .addComponent(sizeAdjustPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(12, 12, 12)
        .addComponent(datetimeChooserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        .addGap(6, 6, 6)
        .addComponent(rangeAdjustPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
      .addGroup(layout.createSequentialGroup()
        .addGap(83, 83, 83)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
      .addGroup(layout.createSequentialGroup()
        .addGap(46, 46, 46)
        .addComponent(adjustPlotProportionsButton))
      .addGroup(layout.createSequentialGroup()
        .addGap(87, 87, 87)
        .addComponent(sizeAdjustPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    setLocationRelativeTo(null);
  }//GEN-LAST:event_formWindowOpened

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
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton adjustPlotProportionsButton;
  private javax.swing.JButton adjustRangesButton;
  private com.toedter.calendar.JDateChooser beginDateChooser;
  private javax.swing.JButton chartToDatesButton;
  private javax.swing.JPanel datetimeChooserPanel;
  private javax.swing.JButton datetimesToChartButton;
  private com.toedter.calendar.JDateChooser endDateChooser;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel rangeAdjustPanel;
  private javax.swing.JButton resetToAllDatesButton;
  private javax.swing.JPanel sizeAdjustPanel;
  // End of variables declaration//GEN-END:variables
  Date minBeginDate;
  Date maxEndDate;
  Date beginDate;
  Date endDate;
  DateAxis dateAxis;

  private void setupChoosers() {
    minBeginDate = beginDate = chart.getBeginDate();
    maxEndDate = endDate = chart.getEndDate();
    beginDateChooser.setDate(beginDate);
    endDateChooser.setDate(endDate);
    dateAxis = (DateAxis) chart.getComboPlot().getDomainAxis();
    dateAxis.setMinimumDate(beginDate);
    dateAxis.setMaximumDate(endDate);
  }

  //rpc - NOTE:4/5/10 3:52 PM - Not used now 
  public void axisChanged(AxisChangeEvent event) {
    DateAxis domainAxis = (DateAxis) event.getAxis();
//    if(beginDate.compareTo(domainAxis.getMinimumDate()) != 0) {
//      beginDate = domainAxis.getMinimumDate();
//      beginDateChooser.setDate(beginDate);
//    }
    if (endDate.compareTo(domainAxis.getMaximumDate()) != 0) {
      endDate = domainAxis.getMaximumDate();
      endDateChooser.setDate(endDate);
    }
    //int i = 1;
  }
}
