package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysConnConfig {

  private String pkId;
  private String dbType;
  private String host;
  private long port;
  private String userName;
  private String password;
  private String shortName;
  private String remark;
  private String dbName;
  private String createPersonName;
  private Date createTime;
  private long deleted;
  private String dbVersion;
  
}
