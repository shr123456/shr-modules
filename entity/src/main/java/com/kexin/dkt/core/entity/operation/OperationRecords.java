package com.kexin.dkt.core.entity.operation;

import java.util.Date;

public class OperationRecords {

  private long id;
  private long orderId;
  private String operationPeople;
  private Date operationTime;
  private String operationContent;
  private long status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }


  public String getOperationPeople() {
    return operationPeople;
  }

  public void setOperationPeople(String operationPeople) {
    this.operationPeople = operationPeople;
  }


  public Date getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Date operationTime) {
    this.operationTime = operationTime;
  }


  public String getOperationContent() {
    return operationContent;
  }

  public void setOperationContent(String operationContent) {
    this.operationContent = operationContent;
  }

  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }
}
