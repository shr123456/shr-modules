package com.kexin.dkt.core.entity.system;

import lombok.Data;

import java.util.Date;

@Data
public class SysmngFlowtrack {

  private String flowtrackId;
  private String appId;
  private String dealId;
  private String dealName;
  private String dealResult;
  private String dealRemind;
  private Date dealTime;
  private String workflowType;
  private String dealStep;


}
