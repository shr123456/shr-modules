package com.kexin.dkt.core.entity.operation;


import java.util.Date;

public class RoomEquipmentType {

  private long id;
  private int electricalRoomId;
  private String equipmentTypeName;
  private int equipmentNumber;
  private String creator;
  private Date createTime;
  private int equipmentTypeStatus;//1 没问题 0 有问题
  private int lastErrorStatus;//1 没问题 0 有问题
  private long deleted;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getElectricalRoomId() {
    return electricalRoomId;
  }

  public void setElectricalRoomId(int electricalRoomId) {
    this.electricalRoomId = electricalRoomId;
  }

  public String getEquipmentTypeName() {
    return equipmentTypeName;
  }

  public void setEquipmentTypeName(String equipmentTypeName) {
    this.equipmentTypeName = equipmentTypeName;
  }

  public int getEquipmentNumber() {
    return equipmentNumber;
  }

  public void setEquipmentNumber(int equipmentNumber) {
    this.equipmentNumber = equipmentNumber;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getEquipmentTypeStatus() {
    return equipmentTypeStatus;
  }

  public void setEquipmentTypeStatus(int equipmentTypeStatus) {
    this.equipmentTypeStatus = equipmentTypeStatus;
  }

  public int getLastErrorStatus() {
    return lastErrorStatus;
  }

  public void setLastErrorStatus(int lastErrorStatus) {
    this.lastErrorStatus = lastErrorStatus;
  }

  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
