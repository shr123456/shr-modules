package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecPortalMenuCopy {

  private String menuId;
  private String menuName;
  private String linkType;
  private String linkMark;
  private String parentId;
  private String menuStatus;
  private String createPerson;
  private Date createTime;
  private String portalId;
  private long sortOrder;
  private long enabled;
  private long deleted;
  private long needControl;

}
