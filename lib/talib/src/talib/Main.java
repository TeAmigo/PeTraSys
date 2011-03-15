/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talib;

import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * //rpc - NOTE:3/9/10 1:05 PM - Switching to Hibernate to see if Ican get this
 * //working,
 * @author rickcharon
 */
public class Main {

  EntityManagerFactory emf; // = Persistence.createEntityManagerFactory("ta-libPU");
  EntityManager em; // = emf.createEntityManager();
  List<Quotes1min> testList;

  public Main() {
    try {
      emf = Persistence.createEntityManagerFactory("ta-libPU");
      this.em = emf.createEntityManager();
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  public void setupData() {
    try {
      Query q = em.createQuery("SELECT q FROM Quotes1min q WHERE q.quotes1minPK.symbol = :symbol"
              + " and q.quotes1minPK.datetime >= :datetime");
//      Query q = em.createNamedQuery("Quotes1min..findAll");

      q.setParameter("symbol", "GBP");
      Date dd = new Date(20, 03, 04);
      q.setParameter("datetime", new Date(20, 03, 04));
      testList = q.getResultList();
      int i = 1;
    } catch (Exception ex) {
      MsgBox.err2(ex);
    }
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Main main = new Main();
    main.setupData();
  }

  public RetCode obv(int startIdx,
          int endIdx,
          double inReal[],
          double inVolume[],
          MInteger outBegIdx,
          MInteger outNBElement,
          double outReal[]) {
    int i;
    int outIdx;
    double prevReal, tempReal, prevOBV;
    if (startIdx < 0) {
      return RetCode.OutOfRangeStartIndex;
    }
    if ((endIdx < 0) || (endIdx < startIdx)) {
      return RetCode.OutOfRangeEndIndex;
    }
    prevOBV = inVolume[startIdx];
    prevReal = inReal[startIdx];
    outIdx = 0;
    for (i = startIdx; i <= endIdx; i++) {
      tempReal = inReal[i];
      if (tempReal > prevReal) {
        prevOBV += inVolume[i];
      } else if (tempReal < prevReal) {
        prevOBV -= inVolume[i];
      }
      outReal[outIdx++] = prevOBV;
      prevReal = tempReal;
    }
    outBegIdx.value = startIdx;
    outNBElement.value = outIdx;
    return RetCode.Success;
  }
}
