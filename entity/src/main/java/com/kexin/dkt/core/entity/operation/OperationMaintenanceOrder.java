package com.kexin.dkt.core.entity.operation;

import lombok.Data;

import java.util.Date;


@Data
public class OperationMaintenanceOrder {

  private long orderId;
  private String customerId;
  private String orderName;
  private String orderProjectName;
  private String orderCode;
  private String orderStatusName;
  private long projectId;
  private String operator;
  private String projectDetailAddress;
  private String equipmentInfo;
  private long serviceType;
  private String contacts;
  private String phone;
  private String salesman;
  private String salesmanPhone;
  private Date lastTime;
  private Date plannedTime;
  private String engineer;
  private String engineerPhone;
  private long orderType;
  private Date updateTime;
  private String workReportOne;
  private String workReportTwo;
  private String workReportThree;
  private Date workReportTimeOne;
  private Date workReportTimeTwo;
  private Date workReportTimeThree;
  private String photosBeforeWork;
  private String photosAtWork;
  private String photosAfterWork;
  private String photosProblem;
  private String cEvaluate;
  private Date cEvaluateTime;
  private String cEvaluateContent;
  private String cEvaluateLevel;
  private String dEvaluate;
  private Date dEvaluateTime;
  private String dEvaluateContent;
  private String dEvaluateLevel;
  private long deleted;
  private int readStatus;
  private int operProductsId;
  private String datePhoto;
  private String signaturePhoto;

}
