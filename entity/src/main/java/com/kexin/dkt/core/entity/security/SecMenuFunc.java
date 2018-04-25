package com.kexin.dkt.core.entity.security;

import lombok.Data;

import java.util.Date;

@Data
public class SecMenuFunc {

  private String funcId;
  private String funcCode;
  private String menuId;
  private String funcName;
  private String remark;
  private String createPerson;
  private Date createTime;
  private long enabled;

}
