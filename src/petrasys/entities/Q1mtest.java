/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package petrasys.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author rickcharon
 */
@Entity
@Table(name = "q1mtest", catalog = "Trading", schema = "")
@NamedQueries({
  @NamedQuery(name = "Q1mtest.findAll", query = "SELECT q FROM Q1mtest q"),
  @NamedQuery(name = "Q1mtest.findBySymbol", query = "SELECT q FROM Q1mtest q WHERE q.q1mtestPK.symbol = :symbol"),
  @NamedQuery(name = "Q1mtest.findByExpiry", query = "SELECT q FROM Q1mtest q WHERE q.q1mtestPK.expiry = :expiry"),
  @NamedQuery(name = "Q1mtest.findByDatetime", query = "SELECT q FROM Q1mtest q WHERE q.q1mtestPK.datetime = :datetime"),
  @NamedQuery(name = "Q1mtest.findByOpen", query = "SELECT q FROM Q1mtest q WHERE q.open = :open"),
  @NamedQuery(name = "Q1mtest.findByHigh", query = "SELECT q FROM Q1mtest q WHERE q.high = :high"),
  @NamedQuery(name = "Q1mtest.findByLow", query = "SELECT q FROM Q1mtest q WHERE q.low = :low"),
  @NamedQuery(name = "Q1mtest.findByClose", query = "SELECT q FROM Q1mtest q WHERE q.close = :close"),
  @NamedQuery(name = "Q1mtest.findByVolume", query = "SELECT q FROM Q1mtest q WHERE q.volume = :volume")})
public class Q1mtest implements Serializable {
  @Transient
  private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected Q1mtestPK q1mtestPK;
  @Basic(optional = false)
  @Column(name = "open")
  private double open;
  @Basic(optional = false)
  @Column(name = "high")
  private double high;
  @Basic(optional = false)
  @Column(name = "low")
  private double low;
  @Basic(optional = false)
  @Column(name = "close")
  private double close;
  @Basic(optional = false)
  @Column(name = "volume")
  private long volume;

  public Q1mtest() {
  }

  public Q1mtest(Q1mtestPK q1mtestPK) {
    this.q1mtestPK = q1mtestPK;
  }

  public Q1mtest(Q1mtestPK q1mtestPK, double open, double high, double low, double close, long volume) {
    this.q1mtestPK = q1mtestPK;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public Q1mtest(String symbol, int expiry, Date datetime) {
    this.q1mtestPK = new Q1mtestPK(symbol, expiry, datetime);
  }

  public Q1mtestPK getQ1mtestPK() {
    return q1mtestPK;
  }

  public void setQ1mtestPK(Q1mtestPK q1mtestPK) {
    this.q1mtestPK = q1mtestPK;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    double oldOpen = this.open;
    this.open = open;
    changeSupport.firePropertyChange("open", oldOpen, open);
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    double oldHigh = this.high;
    this.high = high;
    changeSupport.firePropertyChange("high", oldHigh, high);
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    double oldLow = this.low;
    this.low = low;
    changeSupport.firePropertyChange("low", oldLow, low);
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    double oldClose = this.close;
    this.close = close;
    changeSupport.firePropertyChange("close", oldClose, close);
  }

  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    long oldVolume = this.volume;
    this.volume = volume;
    changeSupport.firePropertyChange("volume", oldVolume, volume);
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (q1mtestPK != null ? q1mtestPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Q1mtest)) {
      return false;
    }
    Q1mtest other = (Q1mtest) object;
    if ((this.q1mtestPK == null && other.q1mtestPK != null) || (this.q1mtestPK != null && !this.q1mtestPK.equals(other.q1mtestPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "petrasys.Q1mtest[q1mtestPK=" + q1mtestPK + "]";
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.removePropertyChangeListener(listener);
  }

}
