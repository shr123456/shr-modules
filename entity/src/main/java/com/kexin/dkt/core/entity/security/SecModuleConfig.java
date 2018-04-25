package com.kexin.dkt.core.entity.security;

import lombok.Data;

import java.util.Date;

@Data
public class SecModuleConfig {

  private String moduleId;
  private String moduleName;
  private long enabled;
  private String createPerson;
  private Date createTime;
  private String remark;
  private long deleted;
  private String accessContext;

}
