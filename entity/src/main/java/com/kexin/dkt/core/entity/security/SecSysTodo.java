package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecSysTodo {

  private String pkId;
  private String accessUrl;
  private String queryString;
  private String todoType;
  private String dataMark;
  private String showTitle;
  private Date createTime;
  private Date finishTime;
  private long closed;
  private String userId;
  private String belongModule;
  private String flowCode;
  private String activityName;
  private String prevUserName;
  private String prevActivityName;
  private String dealName;
  private String itemId;
  private long version;
  private String prevDealName;
  private String portalTodoId;
  private long deleted;
  private String applyCode;
  private String remark;
  private String proxyId;
}
