package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecRole {

  private String roleId;
  private String roleCode;
  private String roleName;
  private String roleDept;
  private String roleType;
  private String roleKind;
  private String upperCode;
  private long enabled;
  private long visible;
  private String createPerson;
  private Date createTime;
  private String remark;
  private long deleted;

}
