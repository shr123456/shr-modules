package com.kexin.dkt.core.entity.system;

import lombok.Data;

@Data
public class SysmngFlowtemplet {

  private String workflowId;
  private String roleId;
  private String roleName;
  private String workflowType;
  private long workflowFrom;
  private long workflowTo;
  private String stepName;
  private String dealRst;
  private String versionCode;
  private String appRouteUrl;
  private long openFlag;


}
