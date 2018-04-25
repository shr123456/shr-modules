package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngUserAccount {

  private String accountId;
  private String userAccountId;
  private String userId;
  private Date createTime;
  private String creator;


}
