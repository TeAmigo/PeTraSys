/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rickcharon
 */
@Embeddable
public class FuturesContractDetailsPK implements Serializable {
  @Basic(optional = false)
  @Column(name = "symbol")
  private String symbol;
  @Basic(optional = false)
  @Column(name = "expiry")
  private int expiry;

  public FuturesContractDetailsPK() {
  }

  public FuturesContractDetailsPK(String symbol, int expiry) {
    this.symbol = symbol;
    this.expiry = expiry;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (symbol != null ? symbol.hashCode() : 0);
    hash += (int) expiry;
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FuturesContractDetailsPK)) {
      return false;
    }
    FuturesContractDetailsPK other = (FuturesContractDetailsPK) object;
    if ((this.symbol == null && other.symbol != null) || (this.symbol != null && !this.symbol.equals(other.symbol))) {
      return false;
    }
    if (this.expiry != other.expiry) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "petrasys.FuturesContractDetailsPK[symbol=" + symbol + ", expiry=" + expiry + "]";
  }

}
