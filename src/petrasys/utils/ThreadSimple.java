/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package petrasys.utils;

/**
 *
 * @author rickcharon
 */
public class ThreadSimple implements Runnable {

  public ThreadSimple() {
    Thread tt = Thread.currentThread();
    String nm = tt.getName();
    System.out.println("In ThreadSimple.ctor, Thread name = " + nm);

  }

  public void doSomething() {
    Thread tt = Thread.currentThread();
    String nm = tt.getName();
    System.out.println("In ThreadSimple.doSomething() called from Frame, Thread name = " + nm);
  }

  private void myDo() {
    Thread tt = Thread.currentThread();
    String nm = tt.getName();
    System.out.println("In ThreadSimple.myDo() called from ThreadSimple.run(), Thread name = " + nm);

  }

  public void run() {
    Thread tt = Thread.currentThread();
    String nm = tt.getName();
    tt.setName("ThreadSimple-Thread");
    System.out.println("In ThreadSimple.run(), Thread name = " + nm);
    myDo();

  }
}
