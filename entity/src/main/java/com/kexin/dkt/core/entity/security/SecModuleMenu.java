package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecModuleMenu {

  private String menuId;
  private String menuName;
  private String parentId;
  private String menuUrl;
  private long enabled;
  private String remark;
  private String moduleId;
  private String createPerson;
  private Date createTime;
  private long referCount;
  private long deleted;

}
