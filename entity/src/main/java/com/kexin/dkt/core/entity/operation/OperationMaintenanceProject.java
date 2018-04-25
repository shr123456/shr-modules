package com.kexin.dkt.core.entity.operation;


import lombok.Data;

import java.util.Date;

@Data
public class OperationMaintenanceProject {

  private long projectId;
  private String projectNo;
  private String projectName;
  private String customerId;
  private String customerName;
  private String projectDetailAddress;
  private String equipmentInfo;
  private String contacts;
  private String phone;
  private String salesmanId;
  private String ifFirst;
  private Date signTime;
  private long life;
  private Date startTime;
  private Date endTime;
  private double amount;
  private double receivedAmount;
  private String serviceContent;
  private String inspection;
  private long inspectionCount;
  private java.sql.Date inspectionTime;
  private String test;
  private long testCount;
  private java.sql.Date testTime;
  private String maintain;
  private long maintainCount;
  private java.sql.Date maintainTime;
  private String areaId;
  private long deleted;
  private String schemeOne;
  private String schemeTwo;
  private String salesmanPhone;
  private int operProductsInspectionId;

}
