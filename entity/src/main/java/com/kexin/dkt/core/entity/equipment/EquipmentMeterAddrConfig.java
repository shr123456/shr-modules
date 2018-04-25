package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentMeterAddrConfig {

  private long id;
  private String meterTypeNo;
  private long segmentStartAddr;
  private long segmentLen;
  private long segmentNo;
  private long tag;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;

}
