package com.kexin.dkt.core.entity.operation;


public class OperationMaintenanceOrderTypeDetails {

  private long id;
  private long orderId;
  private long orderType;
  private java.sql.Timestamp updateTime;
  private long lon;
  private long lat;
  private String userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public long getOrderType() {
    return orderType;
  }

  public void setOrderType(long orderType) {
    this.orderType = orderType;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public long getLon() {
    return lon;
  }

  public void setLon(long lon) {
    this.lon = lon;
  }


  public long getLat() {
    return lat;
  }

  public void setLat(long lat) {
    this.lat = lat;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}
