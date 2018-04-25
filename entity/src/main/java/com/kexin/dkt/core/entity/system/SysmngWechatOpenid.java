package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngWechatOpenid {

  private String pkId;
  private String account;
  private String openid;
  private Date createTime;
  private long deleted;


}
