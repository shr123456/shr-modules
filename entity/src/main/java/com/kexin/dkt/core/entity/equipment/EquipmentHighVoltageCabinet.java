package com.kexin.dkt.core.entity.equipment;


import lombok.Data;

import java.util.Date;

@Data
public class EquipmentHighVoltageCabinet {

  private long id;
  private String customerId;
  private String highCabinetCode;
  private String highCabinetName;
  private String manufactor;
  private String version;
  private String voltageLevel;
  private String coreDesc;
  private String remark;
  private String creator;
  private Date createTime;
  private String photoUrl;
  private String updater;
  private Date updateTime;
  private long electricalRoomId;
  private String ratedVoltage;
  private String ratedCurrent;
  private String ratedCapacity;
  private String ratedFrequency;
  private String productConformity;
  private String makeTime;
  private String factoryCode;
  private String address;
  private long deleted;
  private long inputed;
  private long used;


}
