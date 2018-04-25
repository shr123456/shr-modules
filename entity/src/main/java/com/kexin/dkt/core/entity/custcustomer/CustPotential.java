package com.kexin.dkt.core.entity.custcustomer;

import lombok.Data;

@Data
public class CustPotential {

  private String areaId;
  private String addressId;
  private String potentialId;
  private String potentialCode;
  private String potentialName;
  private String pttentialSource;
  private String pttentialTel;
  private long deleted;
  private java.sql.Timestamp createTime;
  private String customerTrade;
  private String potentialLevelId;
  private String potentialAddress;
  private String customerConnLinker;
  private String customerEmail;

}
