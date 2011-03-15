/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rickcharon
 */
@Embeddable
public class Q1mtestPK implements Serializable {
  @Basic(optional = false)
  @Column(name = "symbol")
  private String symbol;
  @Basic(optional = false)
  @Column(name = "expiry")
  private int expiry;
  @Basic(optional = false)
  @Column(name = "datetime")
  @Temporal(TemporalType.TIMESTAMP)
  private Date datetime;

  public Q1mtestPK() {
  }

  public Q1mtestPK(String symbol, int expiry, Date datetime) {
    this.symbol = symbol;
    this.expiry = expiry;
    this.datetime = datetime;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public int getExpiry() {
    return expiry;
  }

  public void setExpiry(int expiry) {
    this.expiry = expiry;
  }

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    this.datetime = datetime;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (symbol != null ? symbol.hashCode() : 0);
    hash += (int) expiry;
    hash += (datetime != null ? datetime.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Q1mtestPK)) {
      return false;
    }
    Q1mtestPK other = (Q1mtestPK) object;
    if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
      return false;
    }
    if (this.expiry != other.expiry) {
      return false;
    }
    if ((this.datetime == null && other.datetime != null) || (this.datetime != null && !this.datetime.equals(other.datetime))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "petrasys.Q1mtestPK[symbol=" + symbol + ", expiry=" + expiry + ", datetime=" + datetime + "]";
  }

}
