package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentMeterType {

  private long id;
  private String meterTypeNo;
  private String meterType;
  private String ptAddress;
  private String ctAddress;
  private long threePhase;
  private long tag;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;


}
