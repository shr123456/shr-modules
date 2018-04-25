package com.kexin.dkt.core.entity.custcustomer;

import lombok.Data;

import java.util.Date;

@Data
public class CustUser {

  private String customerId;
  private String userId;
  private String areaId;
  private String addressId;
  private String userCode;
  private String userName;
  private String nickname;
  private String userLinker;
  private String userTele;
  private long boundStatus;
  private String creator;
  private String modifier;
  private Date  modifyTime;
  private Date createTime;
  private String secUserId;
  private String headImage;
  private long sex;
  private Date birthday;
  private String email;
  private long deleted;
  private long isMain;

}
