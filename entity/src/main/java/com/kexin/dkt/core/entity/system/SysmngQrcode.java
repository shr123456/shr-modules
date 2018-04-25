package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngQrcode {

  private String qrcodeId;
  private String belongId;
  private String type;
  private String url;
  private Date createTime;
  private Date modifyTime;
  private String creator;
  private String modifier;
  private long deleted;


}
