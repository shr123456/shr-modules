package com.kexin.dkt.core.entity.operation;

import java.util.Date;

/**
 * 电房实体类
 */
public class ElectricRoomInfo {

  private long id;
  private String customerId;
  private String electricalRoomName;
  private String creator;
  private String charger;
  private String photoUrl;
  private String chargeTel;
  private Date createTime;
  private String address;
  private int worked;


  private long deleted;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }


  public String getElectricalRoomName() {
    return electricalRoomName;
  }

  public void setElectricalRoomName(String electricalRoomName) {
    this.electricalRoomName = electricalRoomName;
  }


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }


  public String getCharger() {
    return charger;
  }

  public void setCharger(String charger) {
    this.charger = charger;
  }


  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }


  public String getChargeTel() {
    return chargeTel;
  }

  public void setChargeTel(String chargeTel) {
    this.chargeTel = chargeTel;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getWorked() {
    return worked;
  }

  public void setWorked(int worked) {
    this.worked = worked;
  }

  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
