package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentDtuConfig {

  private long id;
  private String dtuNo;
  private String electricalRoomCode;
  private String simNo;
  private long timeInterval;
  private long tag;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;
  private String customerId;

}
