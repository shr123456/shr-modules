package com.kexin.dkt.core.entity.operation;


/**
 * 设备工作项关联
 */
public class EquipmentWorkContent {

  private long id;
  private int equipmentId;
  private String workProjectName;
  private int workProjectId;
  private String errorPhoto;
  private String errorMessage;
  private String adviceMessage;
  private long lastErrorStatus;//1:没问题 0:有问题
  private long workProjectStauts;//1:没问题 0:有问题
  private int deleted;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getWorkProjectName() {
    return workProjectName;
  }

  public void setWorkProjectName(String workProjectName) {
    this.workProjectName = workProjectName;
  }

  public int getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(int equipmentId) {
    this.equipmentId = equipmentId;
  }

  public int getWorkProjectId() {
    return workProjectId;
  }

  public void setWorkProjectId(int workProjectId) {
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

  public long getLastErrorStatus() {
    return lastErrorStatus;
  }

  public void setLastErrorStatus(long lastErrorStatus) {
    this.lastErrorStatus = lastErrorStatus;
  }

  public long getWorkProjectStauts() {
    return workProjectStauts;
  }

  public void setWorkProjectStauts(long workProjectStauts) {
    this.workProjectStauts = workProjectStauts;
  }

  public int getDeleted() {
    return deleted;
  }

  public void setDeleted(int deleted) {
    this.deleted = deleted;
  }
}
