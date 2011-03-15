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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author rickcharon
 */
@Entity
@Table(name = "futuresContractDetails")
@NamedQueries({
  @NamedQuery(name = "FuturesContractDetails.findAll", query = "SELECT f FROM FuturesContractDetails f"),
  @NamedQuery(name = "FuturesContractDetails.findBySymbol", query = "SELECT f FROM FuturesContractDetails f WHERE f.futuresContractDetailsPK.symbol = :symbol"),
  @NamedQuery(name = "FuturesContractDetails.findByExpiry", query = "SELECT f FROM FuturesContractDetails f WHERE f.futuresContractDetailsPK.expiry = :expiry"),
  @NamedQuery(name = "FuturesContractDetails.findByMultiplier", query = "SELECT f FROM FuturesContractDetails f WHERE f.multiplier = :multiplier"),
  @NamedQuery(name = "FuturesContractDetails.findByExchange", query = "SELECT f FROM FuturesContractDetails f WHERE f.exchange = :exchange"),
  @NamedQuery(name = "FuturesContractDetails.findByMinTick", query = "SELECT f FROM FuturesContractDetails f WHERE f.minTick = :minTick"),
  @NamedQuery(name = "FuturesContractDetails.findByBeginDate", query = "SELECT f FROM FuturesContractDetails f WHERE f.beginDate = :beginDate"),
  @NamedQuery(name = "FuturesContractDetails.findByEndDate", query = "SELECT f FROM FuturesContractDetails f WHERE f.endDate = :endDate"),
  @NamedQuery(name = "FuturesContractDetails.findByActive", query = "SELECT f FROM FuturesContractDetails f WHERE f.active = :active")})
public class FuturesContractDetails implements Serializable {
  @Transient
  private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected FuturesContractDetailsPK futuresContractDetailsPK;
  @Basic(optional = false)
  @Column(name = "multiplier")
  private int multiplier;
  @Basic(optional = false)
  @Column(name = "exchange")
  private String exchange;
  @Basic(optional = false)
  @Column(name = "minTick")
  private double minTick;
  @Basic(optional = false)
  @Column(name = "beginDate")
  @Temporal(TemporalType.DATE)
  private Date beginDate;
  @Basic(optional = false)
  @Column(name = "endDate")
  @Temporal(TemporalType.DATE)
  private Date endDate;
  @Basic(optional = false)
  @Column(name = "active")
  private boolean active;

  public FuturesContractDetails() {
  }

  public FuturesContractDetails(FuturesContractDetailsPK futuresContractDetailsPK) {
    this.futuresContractDetailsPK = futuresContractDetailsPK;
  }

  public FuturesContractDetails(FuturesContractDetailsPK futuresContractDetailsPK, int multiplier, String exchange, double minTick, Date beginDate, Date endDate, boolean active) {
    this.futuresContractDetailsPK = futuresContractDetailsPK;
    this.multiplier = multiplier;
    this.exchange = exchange;
    this.minTick = minTick;
    this.beginDate = beginDate;
    this.endDate = endDate;
    this.active = active;
  }

  public FuturesContractDetails(String symbol, int expiry) {
    this.futuresContractDetailsPK = new FuturesContractDetailsPK(symbol, expiry);
  }

  public FuturesContractDetailsPK getFuturesContractDetailsPK() {
    return futuresContractDetailsPK;
  }

  public void setFuturesContractDetailsPK(FuturesContractDetailsPK futuresContractDetailsPK) {
    this.futuresContractDetailsPK = futuresContractDetailsPK;
  }

  public int getMultiplier() {
    return multiplier;
  }

  public void setMultiplier(int multiplier) {
    int oldMultiplier = this.multiplier;
    this.multiplier = multiplier;
    changeSupport.firePropertyChange("multiplier", oldMultiplier, multiplier);
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    String oldExchange = this.exchange;
    this.exchange = exchange;
    changeSupport.firePropertyChange("exchange", oldExchange, exchange);
  }

  public double getMinTick() {
    return minTick;
  }

  public void setMinTick(double minTick) {
    double oldMinTick = this.minTick;
    this.minTick = minTick;
    changeSupport.firePropertyChange("minTick", oldMinTick, minTick);
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    Date oldBeginDate = this.beginDate;
    this.beginDate = beginDate;
    changeSupport.firePropertyChange("beginDate", oldBeginDate, beginDate);
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    Date oldEndDate = this.endDate;
    this.endDate = endDate;
    changeSupport.firePropertyChange("endDate", oldEndDate, endDate);
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    boolean oldActive = this.active;
    this.active = active;
    changeSupport.firePropertyChange("active", oldActive, active);
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (futuresContractDetailsPK != null ? futuresContractDetailsPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof FuturesContractDetails)) {
      return false;
    }
    FuturesContractDetails other = (FuturesContractDetails) object;
    if ((this.futuresContractDetailsPK == null && other.futuresContractDetailsPK != null) || (this.futuresContractDetailsPK != null && !this.futuresContractDetailsPK.equals(other.futuresContractDetailsPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "petrasys.FuturesContractDetails[futuresContractDetailsPK=" + futuresContractDetailsPK + "]";
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.removePropertyChangeListener(listener);
  }

}
