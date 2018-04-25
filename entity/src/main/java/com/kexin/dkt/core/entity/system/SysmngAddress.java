package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngAddress {

  private String addressId;
  private String areaId;
  private String addressCode;
  private String detailAddress;
  private String addressLongitude;
  private String addressLatitude;
  private String creator;
  private Date creeateTime;
  private long deleted;

}