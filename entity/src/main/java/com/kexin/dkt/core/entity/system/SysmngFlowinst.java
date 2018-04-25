package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngFlowinst {

  private String flowinstId;
  private String workflowType;
  private String appId;
  private String isOpen;
  private String actorId;
  private Date createDate;
  private Date endDate;
  private long step;
  private String stepName;


}
