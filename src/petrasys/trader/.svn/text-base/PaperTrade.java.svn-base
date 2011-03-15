package petrasys.trader;

import java.sql.Timestamp;
import java.util.Date;

/**
 * PaperTrade.java Created Sep 23, 2010 12:50:32 PM in Project PeTraSys
 *  
 * @author Rick Charon
 * 
 */
public class PaperTrade {

  private Integer id;
  private Date enteredInDB;
  private Date beginTradeDateTime;
  private String symbol;
  private String position;
  private double entry;
  private double stopLoss;
  private double stopRisk;
  private double profitStop;
  private double profitpotential;
  private double outcome;
  private Date exitTradeDateTime;

  public PaperTrade() {
  }

  public PaperTrade(Integer id) {
    this.id = id;
  }

  public PaperTrade(Integer id, Date enteredInDB, Date beginTradeDateTime, String symbol, String position, double entry, double stopLoss, double stopRisk, double stopprofit, double profitpotential, double outcome, Date exitTradeDateTime) {
    this.id = id;
    this.enteredInDB = enteredInDB;
    this.beginTradeDateTime = beginTradeDateTime;
    this.symbol = symbol;
    this.position = position;
    this.entry = entry;
    this.stopLoss = stopLoss;
    this.stopRisk = stopRisk;
    this.profitStop = stopprofit;
    this.profitpotential = profitpotential;
    this.outcome = outcome;
    this.exitTradeDateTime = exitTradeDateTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getEnteredInDB() {
    return enteredInDB;
  }

  public void setEnteredInDB(Date enteredInDB) {
    this.enteredInDB = enteredInDB;
  }

  public Date getBeginTradeDateTime() {
    return beginTradeDateTime;
  }

  public Timestamp getBeginTradeTimestamp() {
    return new Timestamp(beginTradeDateTime.getTime());
  }

  public void setBeginTradeDateTime(Date beginTradeDateTime) {
    this.beginTradeDateTime = beginTradeDateTime;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public double getEntry() {
    return entry;
  }

  public void setEntry(double entry) {
    this.entry = entry;
  }

  public double getStopLoss() {
    return stopLoss;
  }

  public void setStopLoss(double stopLoss) {
    this.stopLoss = stopLoss;
  }

  public double getStopRisk() {
    return stopRisk;
  }

  public void setStopRisk(double stopRisk) {
    this.stopRisk = stopRisk;
  }

  public double getProfitStop() {
    return profitStop;
  }

  public void setProfitStop(double stopprofit) {
    this.profitStop = stopprofit;
  }

  public double getProfitpotential() {
    return profitpotential;
  }

  public void setProfitpotential(double profitpotential) {
    this.profitpotential = profitpotential;
  }

  public double getOutcome() {
    return outcome;
  }

  public void setOutcome(double outcome) {
    this.outcome = outcome;
  }

  public Date getExitTradeDateTime() {
    return exitTradeDateTime;
  }

  public Timestamp getExitTradeTimestamp() {
    return new Timestamp(exitTradeDateTime.getTime());
  }

  public void setExitTradeDateTime(Date exitTradeDateTime) {
    this.exitTradeDateTime = exitTradeDateTime;
  }
}
