package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;
import java.util.List;


public class EquipmentMeterConfig {

  private long id;
  private String dtuNo;
  private long meterAddress;
  private String meterTypeNo;
  private long pt;
  private long ct;
  private long tag;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;
  private String customerId;
  private long used;
  private int measuremethods;
  private int genre;
  private int collectConfigId;
  private List<String> collectConfigIdList;//设备分组明细id拼接的字符串

  public EquipmentMeterConfig() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public String getMeterTypeNo() {
    return meterTypeNo;
  }

  public void setMeterTypeNo(String meterTypeNo) {
    this.meterTypeNo = meterTypeNo;
  }

  public long getPt() {
    return pt;
  }

  public void setPt(long pt) {
    this.pt = pt;
  }

  public long getCt() {
    return ct;
  }

  public void setCt(long ct) {
    this.ct = ct;
  }

  public long getTag() {
    return tag;
  }

  public void setTag(long tag) {
    this.tag = tag;
  }

  public String getCreateuser() {
    return createuser;
  }

  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }

  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  public String getChangeuser() {
    return changeuser;
  }

  public void setChangeuser(String changeuser) {
    this.changeuser = changeuser;
  }

  public Date getChangedate() {
    return changedate;
  }

  public void setChangedate(Date changedate) {
    this.changedate = changedate;
  }

  public Date getRowver() {
    return rowver;
  }

  public void setRowver(Date rowver) {
    this.rowver = rowver;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public long getUsed() {
    return used;
  }

  public void setUsed(long used) {
    this.used = used;
  }

  public int getMeasuremethods() {
    return measuremethods;
  }

  public void setMeasuremethods(int measuremethods) {
    this.measuremethods = measuremethods;
  }

  public int getGenre() {
    return genre;
  }

  public void setGenre(int genre) {
    this.genre = genre;
  }

  public int getCollectConfigId() {
    return collectConfigId;
  }

  public void setCollectConfigId(int collectConfigId) {
    this.collectConfigId = collectConfigId;
  }

  public List<String> getCollectConfigIdList() {
    return collectConfigIdList;
  }

  public void setCollectConfigIdList(List<String> collectConfigIdList) {
    this.collectConfigIdList = collectConfigIdList;
  }

}
