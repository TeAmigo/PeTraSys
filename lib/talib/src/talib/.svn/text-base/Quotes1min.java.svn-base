/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package talib;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rickcharon
 */
@Entity
@Table(name = "quotes1min")
@NamedQueries({
  @NamedQuery(name = "Quotes1min.findAll", query = "SELECT q FROM Quotes1min q"),
  @NamedQuery(name = "Quotes1min.findBySymbol", query = "SELECT q FROM Quotes1min q WHERE q.quotes1minPK.symbol = :symbol"),
  @NamedQuery(name = "Quotes1min.findBySymbolandDateTime",
  query = "SELECT q FROM Quotes1min q WHERE q.quotes1minPK.symbol = :symbol"),
  @NamedQuery(name = "Quotes1min.findByExpiry", query = "SELECT q FROM Quotes1min q WHERE q.quotes1minPK.expiry = :expiry"),
  @NamedQuery(name = "Quotes1min.findByDatetime", query = "SELECT q FROM Quotes1min q WHERE q.quotes1minPK.datetime = :datetime"),
  @NamedQuery(name = "Quotes1min.findByOpen", query = "SELECT q FROM Quotes1min q WHERE q.open = :open"),
  @NamedQuery(name = "Quotes1min.findByHigh", query = "SELECT q FROM Quotes1min q WHERE q.high = :high"),
  @NamedQuery(name = "Quotes1min.findByLow", query = "SELECT q FROM Quotes1min q WHERE q.low = :low"),
  @NamedQuery(name = "Quotes1min.findByClose", query = "SELECT q FROM Quotes1min q WHERE q.close = :close"),
  @NamedQuery(name = "Quotes1min.findByVolume", query = "SELECT q FROM Quotes1min q WHERE q.volume = :volume")})
public class Quotes1min implements Serializable {
  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected Quotes1minPK quotes1minPK;
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

  public Quotes1min() {
  }

  public Quotes1min(Quotes1minPK quotes1minPK) {
    this.quotes1minPK = quotes1minPK;
  }

  public Quotes1min(Quotes1minPK quotes1minPK, double open, double high, double low, double close, long volume) {
    this.quotes1minPK = quotes1minPK;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
  }

  public Quotes1min(String symbol, int expiry, Date datetime) {
    this.quotes1minPK = new Quotes1minPK(symbol, expiry, datetime);
  }

  public Quotes1minPK getQuotes1minPK() {
    return quotes1minPK;
  }

  public void setQuotes1minPK(Quotes1minPK quotes1minPK) {
    this.quotes1minPK = quotes1minPK;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    this.volume = volume;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (quotes1minPK != null ? quotes1minPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Quotes1min)) {
      return false;
    }
    Quotes1min other = (Quotes1min) object;
    if ((this.quotes1minPK == null && other.quotes1minPK != null) || (this.quotes1minPK != null && !this.quotes1minPK.equals(other.quotes1minPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "talib.Quotes1min[quotes1minPK=" + quotes1minPK + "]";
  }

}
