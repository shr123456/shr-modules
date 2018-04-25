package com.kexin.dkt.core.entity.equipment;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EquipmentCollectConfig {

  private long id;
  private String customerId;
  private long electricalRoomId;
  private long cabinetId;
  private long meterId;
  private long genre;
  private String collectConfigCode;
  private String collectConfigName;
  private String manufactor;
  private String version;
  private String voltageLevel;
  private String remark;
  private String creator;
  private Date createTime;
  private String photoUrl;
  private String updater;
  private Date updateTime;
  private String ratedVoltage;
  private String ratedCurrent;
  private String ratedFrequency;
  private String ratedCapacity;
  private String factoryCode;
  private String productConformity;
  private String makeTime;
  private String address;
  private long deleted;

}