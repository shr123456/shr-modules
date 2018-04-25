package com.kexin.dkt.core.entity.operation;


import java.util.Date;

public class EquipmentErrorMessage {

  private long id;
  private long orderId;
  private long roomId;
  private long equipmentTypeId;
  private long equipmentId;
  private long workProjectId;
  private String errorPhoto;
  private String errorMessage;
  private String adviceMessage;
  private Date createTime;
  private long workStatus;//1 没问题 0 有问题
  private long deleted;


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


  public long getRoomId() {
    return roomId;
  }

  public void setRoomId(long roomId) {
    this.roomId = roomId;
  }


  public long getEquipmentTypeId() {
    return equipmentTypeId;
  }

  public void setEquipmentTypeId(long equipmentTypeId) {
    this.equipmentTypeId = equipmentTypeId;
  }


  public long getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(long equipmentId) {
    this.equipmentId = equipmentId;
  }


  public long getWorkProjectId() {
    return workProjectId;
  }

  public void setWorkProjectId(long workProjectId) {
    this.workProjectId = workProjectId;
  }


  public String getErrorPhoto() {
    return errorPhoto;
  }

  public void setErrorPhoto(String errorPhoto) {
    this.errorPhoto = errorPhoto;
  }


  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  public String getAdviceMessage() {
    return adviceMessage;
  }

  public void setAdviceMessage(String adviceMessage) {
    this.adviceMessage = adviceMessage;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public long getWorkStatus() {
    return workStatus;
  }

  public void setWorkStatus(long workStatus) {
    this.workStatus = workStatus;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
