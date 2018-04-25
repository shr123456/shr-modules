package com.kexin.dkt.core.entity.system;


public class SecPortalMenu {

  private String menuId;
  private String menuName;
  private String linkType;
  private String linkMark;
  private String parentId;
  private String menuStatus;
  private String createPerson;
  private java.sql.Timestamp createTime;
  private String portalId;
  private long sortOrder;
  private long enabled;
  private long deleted;
  private long needControl;


  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }


  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }


  public String getLinkType() {
    return linkType;
  }

  public void setLinkType(String linkType) {
    this.linkType = linkType;
  }


  public String getLinkMark() {
    return linkMark;
  }

  public void setLinkMark(String linkMark) {
    this.linkMark = linkMark;
  }


  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }


  public String getMenuStatus() {
    return menuStatus;
  }

  public void setMenuStatus(String menuStatus) {
    this.menuStatus = menuStatus;
  }


  public String getCreatePerson() {
    return createPerson;
  }

  public void setCreatePerson(String createPerson) {
    this.createPerson = createPerson;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public String getPortalId() {
    return portalId;
  }

  public void setPortalId(String portalId) {
    this.portalId = portalId;
  }


  public long getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(long sortOrder) {
    this.sortOrder = sortOrder;
  }


  public long getEnabled() {
    return enabled;
  }

  public void setEnabled(long enabled) {
    this.enabled = enabled;
  }


  public long getDeleted() {
    return deleted;
  }

  public void setDeleted(long deleted) {
    this.deleted = deleted;
  }


  public long getNeedControl() {
    return needControl;
  }

  public void setNeedControl(long needControl) {
    this.needControl = needControl;
  }

}
