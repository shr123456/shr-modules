package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngWechatMsg {

  private String pkId;
  private String keyVal;
  private long msgType;
  private String msgContent;
  private String imgTitle;
  private String imgDir;
  private String imgUrl;
  private String creator;
  private Date createTime;
  private long delFlag;


}
