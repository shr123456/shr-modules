package com.kexin.dkt.core.entity.equipment;

import lombok.Data;
import java.util.Date;

@Data
public class ElectricalRoom {

  private long id;
  private String customerId;
  private String electricalRoomCode;
  private String electricalRoomName;
  private String creator;
  private Date createTime;
  private double temperature;
  private double dampness;
  private String charger;
  private String chargeTel;
  private String remark;
  private String photoUrl;
  private String updater;
  private Date updateTime;
  private String addressId;
  private long deleted;
  private String latitude;
  private String longitude;
  private String areaId;


}
