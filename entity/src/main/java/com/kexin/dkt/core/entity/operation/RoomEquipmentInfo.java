package com.kexin.dkt.core.entity.operation;


public class RoomEquipmentInfo {

  private long id;
  private int equipmentTypeId;
  private String equipmentName;
  private String errorPhotoUrl;
  private String errorMessage;
  private String adviceMessage;
  private long equipmentStatus;
  private long errorMessageNum;
  private int lastErrorStatus;
  private long deleted;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getEquipmentTypeId() {
    return equipmentTypeId;
  }

  public void setEquipmentTypeId(int equipmentTypeId) {
    this.equipmentTypeId = equipmentTypeId;
  }

  public String getEquipmentName() {
    return equipmentName;
  }

  public void setEquipmentName(String equipmentName) {
    this.equipmentName = equipmentName;
  }


  public String getErrorPhotoUrl() {
    return errorPhotoUrl;
  }

  public void setErrorPhotoUrl(String errorPhotoUrl) {
    this.errorPhotoUrl = errorPhotoUrl;
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


  public long getEquipmentStatus() {
    return equipmentStatus;
  }

  public void setEquipmentStatus(long equipmentStatus) {
    this.equipmentStatus = equipmentStatus;
  }

  public long getErrorMessageNum() {
    return errorMessageNum;
  }

  public void setErrorMessageNum(long errorMessageNum) {
    this.errorMessageNum = errorMessageNum;
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
