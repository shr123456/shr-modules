package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngArea {

  private String areaId;
  private String upAreaId;
  private String areaCode;
  private String areaName;
  private String areaRemark;
  private String areaAbbr;
  private String creator;
  private Date createTime;
  private long areaLevel;
  private long deleted;


}
