package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngOption {

  private String optionId;
  private String optionFather;
  private String optionCode;
  private String optionName;
  private String optionValue;
  private long optionType;
  private long optionStatus;
  private String creator;
  private String modifier;
  private Date modifyTime;
  private String menuRemark;
  private String optionCategory;
  private Date createTime;
  private long deleted;
  private String codePrefix;

  
}
