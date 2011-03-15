package petrasys.utils;

/**
 *
 * @author rickcharon
 */
public class tt extends javax.swing.JFrame {

  /** Creates new form tt */
  public tt() {
    initComponents();
  }

  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Simple Test Frame");
    setAlwaysOnTop(true);
    addWindowListener(new java.awt.event.WindowAdapter() {

      @Override
      public void windowOpened(java.awt.event.WindowEvent evt) {
        formWindowOpened(evt);
      }
    });

    jLabel1.setText("Push button to debug test code");
    jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
    jButton1.setText("Go");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)).addGroup(layout.createSequentialGroup().addGap(104, 104, 104).addComponent(jButton1))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    Thread tt = Thread.currentThread();
    tt.setName("Main-Thread");
    String nm = tt.getName();
    System.out.println("In testButton, Thread name = " + nm);
    ThreadSimple ts = new ThreadSimple();
    Thread t2 = new Thread(ts);
    ts.run();
    ts.doSomething();
    int i = 1;
  }

  private void formWindowOpened(java.awt.event.WindowEvent evt) {
    setLocationRelativeTo(null);
  }

  private void stackTraceWork() {
    //Logger logger = Logger.getLogger(this.getName());
    Exception ex = new Exception();
    String msg = ex.getStackTrace()[0].getMethodName();
    msg += ex.getStackTrace()[0].getClassName();
    String title = ex.getStackTrace()[0].getClassName() + "." + ex.getStackTrace()[0].getMethodName();
    MsgBox.err(ex.getMessage(), title);
    int i = 1;

  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new tt().setVisible(true);
      }
    });
  }
  private javax.swing.JButton jButton1;
  private javax.swing.JLabel jLabel1;
}
