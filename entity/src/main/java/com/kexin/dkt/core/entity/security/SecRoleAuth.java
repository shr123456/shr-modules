package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecRoleAuth {

  private String pkId;
  private String entityType;
  private String entityCode;
  private String authType;
  private String authCode;
  private String authAttr;
  private String grantPerson;
  private Date grantTime;

}
