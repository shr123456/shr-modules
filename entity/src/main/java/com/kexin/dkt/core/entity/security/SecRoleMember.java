package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecRoleMember {

  private String pkId;
  private String roleId;
  private String entityType;
  private String entityCode;
  private String grantPerson;
  private Date grantTime;
  private String assignType;
  private Date disableTime;
  private long enabled;
  private String remark;

}
