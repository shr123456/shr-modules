package com.kexin.dkt.core.entity.sale;

public class SaleAssessment {

  private long id;
  private String time;
  private String customerName;
  private double eleConsumption;
  private double powerPurchaseValue;
  private double deviation;
  private double asseOfValue;
  private double spreads;
  private double clientEarnings;
  private double comsonEarnings;
  private String customerId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }


  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }


  public double getEleConsumption() {
    return eleConsumption;
  }

  public void setEleConsumption(double eleConsumption) {
    this.eleConsumption = eleConsumption;
  }


  public double getPowerPurchaseValue() {
    return powerPurchaseValue;
  }

  public void setPowerPurchaseValue(double powerPurchaseValue) {
    this.powerPurchaseValue = powerPurchaseValue;
  }


  public double getDeviation() {
    return deviation;
  }

  public void setDeviation(double deviation) {
    this.deviation = deviation;
  }


  public double getAsseOfValue() {
    return asseOfValue;
  }

  public void setAsseOfValue(double asseOfValue) {
    this.asseOfValue = asseOfValue;
  }


  public double getSpreads() {
    return spreads;
  }

  public void setSpreads(double spreads) {
    this.spreads = spreads;
  }


  public double getClientEarnings() {
    return clientEarnings;
  }

  public void setClientEarnings(double clientEarnings) {
    this.clientEarnings = clientEarnings;
  }


  public double getComsonEarnings() {
    return comsonEarnings;
  }

  public void setComsonEarnings(double comsonEarnings) {
    this.comsonEarnings = comsonEarnings;
  }


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

}
