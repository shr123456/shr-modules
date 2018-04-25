package com.kexin.dkt.core.entity.security;

import lombok.Data;

@Data
public class SecDeptCnmbl {

  private String deptId;
  private String deptName;
  private String deptType;
  private String parentId;
  private String pathName;
  private long deleted;

}
