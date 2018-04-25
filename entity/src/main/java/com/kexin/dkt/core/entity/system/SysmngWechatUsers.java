package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngWechatUsers {

  private String pkId;
  private String openid;
  private String userName;
  private long sex;
  private String photo;
  private Date followTime;
  private Date unfollowTime;
  private String remark;
  private long groupId;
  private long deleted;


}
