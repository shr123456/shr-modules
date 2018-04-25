package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecUserCnmbl {

  private String userId;
  private String userName;
  private String account;
  private String password;
  private String accType;
  private String deptId;
  private String email;
  private String mobile;
  private long enabled;
  private long deleted;
  private String branchId;
  private String portalAccount;
  private String dynaSalt;

}
