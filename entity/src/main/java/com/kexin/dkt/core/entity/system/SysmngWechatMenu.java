package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngWechatMenu {

  private String pkId;
  private String menuId;
  private String menuName;
  private String parentId;
  private long linkType;
  private String linkMark;
  private String creator;
  private Date createTime;
  private long sortOrder;
  private long enabled;
  private long deleted;


}
