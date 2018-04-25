package com.kexin.dkt.core.entity.custcustomer;


import lombok.Data;

import java.util.Date;

@Data
public class CustBusinessCoop {

  private int id;
  private String customerId;
  private String contactName;
  private String contactTel;
    private int coopType;
  private String coopTypeName;
  private String contextDesc;
  private int coopStatus;
  private int businessId;
  private String businessDesc;
  private Date createTime;
  private String updetor;
  private Date updateTime;
  private String remark;

}
