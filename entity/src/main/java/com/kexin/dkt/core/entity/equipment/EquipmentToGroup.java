package com.kexin.dkt.core.entity.equipment;


import java.util.Date;

public class EquipmentToGroup {

  private long id;
  private long groupId;
  private long equipmentId;
  private int checked;
  private Date createTime;
  private String creator;
  private long delete;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getGroupId() {
    return groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }


  public long getEquipmentId() {
    return equipmentId;
  }

  public void setEquipmentId(long equipmentId) {
    this.equipmentId = equipmentId;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public int getChecked() {
    return checked;
  }

  public void setChecked(int checked) {
    this.checked = checked;
  }

  public long getDelete() {
    return delete;
  }

  public void setDelete(long delete) {
    this.delete = delete;
  }

}
