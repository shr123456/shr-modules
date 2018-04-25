package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentMeterRomConfig {

  private long id;
  private String meterTypeNo;
  private long addressSort;
  private String romAddress;
  private String romName;
  private String units;
  private String dataType;
  private long segmentNo;
  private long offset;
  private long needPt;
  private long needCt;
  private double calcfactor;
  private long msbBit;
  private long byteLength;
  private String functionTable1;
  private String functionTable2;
  private String functionTable3;
  private String functionField;
  private long tag;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;


}
