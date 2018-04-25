package com.kexin.dkt.core.entity.security;

import lombok.Data;

import java.util.Date;

@Data
public class SecLoginInfo {

  private String loginId;
  private String userId;
  private String loginAddr;
  private long loginMode;
  private Date loginTime;
  private String accType;
  private String loginIp;

}
