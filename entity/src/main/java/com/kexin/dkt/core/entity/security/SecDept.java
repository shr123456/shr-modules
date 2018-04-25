package com.kexin.dkt.core.entity.security;

import lombok.Data;

import java.util.Date;

@Data
public class SecDept {

  private String deptId;
  private String deptName;
  private String deptType;
  private String parentId;
  private String pathName;
  private long deleted;
  private String ouid;
  private String parentouid;
  private long orgLevel;
  private String companyId;
  private String companyName;
  private String branchName;
  private String branchId;
  private String departmentName;
  private String departmentId;
  private String hallName;
  private String hallId;
  private Date createTime;
  private long orderNo;
  private long hallLevel;
  private String channelCode;

}
