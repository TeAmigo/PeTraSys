/*
 *  Copyright (C) 2010 Rick Charon
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package petrasys.trader;

import java.util.Date;

/**
 * Order.java Created Aug 25, 2010 4:23:55 PM in Project PeTraSys
 *  
 * @author Rick Charon
 * 
 */
public class Order {

  private Integer idx = null;
  private String ul = null;
  private Integer expiry = null;
  private String buySell = null;
  private Integer totalQuantity = 0;
  private Integer filledQuantity = 0;
  private Integer remainingQuantity = 0;
  private Double limitPrice = 0.0;
  private Double auxprice = 0.0;
  private Double avgFillPrice = 0.0;
  private String orderType = null;
  private String tif = null;
  private Double translatedPrice = null;
  private Date barTime = null;
  private Double lossOrGain = null;
  private String oCAGroup = null;
  private Short ocaType = null;
  private Integer orderID = null;
  private Integer parentID = null;
  private Integer permID = null;
  private Date entryDateTime = null;
  private Date executedDateTime = null;
  private String status = "PendingSubmit";

  public Order() {
  }

  public Order(Integer idx, String ul, Integer expiry, String buySell, Integer totalQuantity, Integer filledQuantity, Double limitPrice, Double auxprice, Double avgFillPrice, String orderType, String tif, Double translatedPrice, Date barTime, Short ocaType, Integer orderID, Integer permID, Date entryDateTime, String status) {
    this.idx = idx;
    this.ul = ul;
    this.expiry = expiry;
    this.buySell = buySell;
    this.totalQuantity = totalQuantity;
    this.filledQuantity = filledQuantity;
    this.limitPrice = limitPrice;
    this.auxprice = auxprice;
    this.avgFillPrice = avgFillPrice;
    this.orderType = orderType;
    this.tif = tif;
    this.translatedPrice = translatedPrice;
    this.barTime = barTime;
    this.ocaType = ocaType;
    this.orderID = orderID;
    this.permID = permID;
    this.entryDateTime = entryDateTime;
    this.status = status;
  }

  

  public Integer getIdx() {
    return idx;
  }

  public void setIdx(Integer idx) {
    this.idx = idx;
  }

  public String getUl() {
    return ul;
  }

  public void setUl(String ul) {
    this.ul = ul;
  }

  public Integer getExpiry() {
    return expiry;
  }

  public void setExpiry(Integer expiry) {
    this.expiry = expiry;
  }

  public String getBuySell() {
    return buySell;
  }

  public void setBuySell(String buySell) {
    this.buySell = buySell;
  }

  public Integer getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(Integer totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public Integer getFilledQuantity() {
    return filledQuantity;
  }

  public void setFilledQuantity(Integer filledQuantity) {
    this.filledQuantity = filledQuantity;
  }

  public Integer getRemainingQuantity() {
    return remainingQuantity;
  }

  public void setRemainingQuantity(Integer remainingQuantity) {
    this.remainingQuantity = remainingQuantity;
  }

  public Double getLimitPrice() {
    return limitPrice;
  }

  public void setLimitPrice(Double limitPrice) {
    this.limitPrice = limitPrice;
  }

  public Double getAuxprice() {
    return auxprice;
  }

  public void setAuxprice(Double auxprice) {
    this.auxprice = auxprice;
  }

  public Double getAvgFillPrice() {
    return avgFillPrice;
  }

  public void setAvgFillPrice(Double avgFillPrice) {
    this.avgFillPrice = avgFillPrice;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getTif() {
    return tif;
  }

  public void setTif(String tif) {
    this.tif = tif;
  }

  public Double getTranslatedPrice() {
    return translatedPrice;
  }

  public void setTranslatedPrice(Double translatedPrice) {
    this.translatedPrice = translatedPrice;
  }

  public Date getBarTime() {
    return barTime;
  }

  public void setBarTime(Date barTime) {
    this.barTime = barTime;
  }

  public Double getLossOrGain() {
    return lossOrGain;
  }

  public void setLossOrGain(Double lossOrGain) {
    this.lossOrGain = lossOrGain;
  }

  public String getOCAGroup() {
    return oCAGroup;
  }

  public void setOCAGroup(String oCAGroup) {
    this.oCAGroup = oCAGroup;
  }

  public Short getOcaType() {
    return ocaType;
  }

  public void setOcaType(Short ocaType) {
    this.ocaType = ocaType;
  }

  public Integer getOrderID() {
    return orderID;
  }

  public void setOrderID(Integer orderID) {
    this.orderID = orderID;
  }

  public Integer getParentID() {
    return parentID;
  }

  public void setParentID(Integer parentID) {
    this.parentID = parentID;
  }

  public Integer getPermID() {
    return permID;
  }

  public void setPermID(Integer permID) {
    this.permID = permID;
  }

  public Date getEntryDateTime() {
    return entryDateTime;
  }

  public void setEntryDateTime(Date entryDateTime) {
    this.entryDateTime = entryDateTime;
  }

  public Date getExecutedDateTime() {
    return executedDateTime;
  }

  public void setExecutedDateTime(Date executedDateTime) {
    this.executedDateTime = executedDateTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
