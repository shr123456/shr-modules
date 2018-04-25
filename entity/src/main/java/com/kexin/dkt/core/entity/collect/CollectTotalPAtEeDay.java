package com.kexin.dkt.core.entity.collect;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class CollectTotalPAtEeDay {

  private String dtuNo;
  private long meterAddress;
  private Date collectDate;
  private double beginTotalPAtEe;
  private double endTotalPAtEe;
  private double diffTotalPAtEe;
  private double peakTotalPAtEe;
  private double valleyTotalPAtEe;
  private double flatTotalPAtEe;
  private String beginTime;
  private String endTime;
  private String dtuAndMeterAressList;

  public String getDtuNo() {
    return dtuNo;
  }

  public void setDtuNo(String dtuNo) {
    this.dtuNo = dtuNo;
  }

  public long getMeterAddress() {
    return meterAddress;
  }

  public void setMeterAddress(long meterAddress) {
    this.meterAddress = meterAddress;
  }

  public Date getCollectDate() {
    return collectDate;
  }

  public void setCollectDate(Date collectDate) {
    this.collectDate = collectDate;
  }

  public double getBeginTotalPAtEe() {
    return beginTotalPAtEe;
  }

  public void setBeginTotalPAtEe(double beginTotalPAtEe) {
    this.beginTotalPAtEe = beginTotalPAtEe;
  }

  public double getEndTotalPAtEe() {
    return endTotalPAtEe;
  }

  public void setEndTotalPAtEe(double endTotalPAtEe) {
    this.endTotalPAtEe = endTotalPAtEe;
  }

  public double getDiffTotalPAtEe() {
    return diffTotalPAtEe;
  }

  public void setDiffTotalPAtEe(double diffTotalPAtEe) {
    this.diffTotalPAtEe = diffTotalPAtEe;
  }

  public double getPeakTotalPAtEe() {
    return peakTotalPAtEe;
  }

  public void setPeakTotalPAtEe(double peakTotalPAtEe) {
    this.peakTotalPAtEe = peakTotalPAtEe;
  }

  public double getValleyTotalPAtEe() {
    return valleyTotalPAtEe;
  }

  public void setValleyTotalPAtEe(double valleyTotalPAtEe) {
    this.valleyTotalPAtEe = valleyTotalPAtEe;
  }

  public double getFlatTotalPAtEe() {
    return flatTotalPAtEe;
  }

  public void setFlatTotalPAtEe(double flatTotalPAtEe) {
    this.flatTotalPAtEe = flatTotalPAtEe;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDtuAndMeterAressList() {
    return dtuAndMeterAressList;
  }

  public void setDtuAndMeterAressList(String dtuAndMeterAressList) {
    this.dtuAndMeterAressList = dtuAndMeterAressList;
  }
}
