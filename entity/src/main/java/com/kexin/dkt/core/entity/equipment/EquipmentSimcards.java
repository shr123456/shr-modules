package com.kexin.dkt.core.entity.equipment;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentSimcards {

  private long id;
  private String simNo;
  private String customerId;
  private long tag;
  private long used;
  private String createuser;
  private Date createdate;
  private String changeuser;
  private Date changedate;
  private Date rowver;

}
