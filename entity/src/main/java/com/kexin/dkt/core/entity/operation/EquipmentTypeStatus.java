package com.kexin.dkt.core.entity.operation;



public class EquipmentTypeStatus {

  private long id;
  private long orderId;
  private long equipmentTypeId;
  private long finishStatus;//0:未完成 1:已完成
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


  public long getEquipmentTypeId() {
    return equipmentTypeId;
  }

  public void setEquipmentTypeId(long equipmentTypeId) {
    this.equipmentTypeId = equipmentTypeId;
  }


  public long getFinishStatus() {
    return finishStatus;
  }

  public void setFinishStatus(long finishStatus) {
    this.finishStatus = finishStatus;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }

}
