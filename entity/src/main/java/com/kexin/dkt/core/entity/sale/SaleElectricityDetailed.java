package com.kexin.dkt.core.entity.sale;


public class SaleElectricityDetailed {

  private long detailedId;
  private String saleElectricityId;
  private String declareMonth;
  private double electricityConsum;
  private double biddingElctricity;
  private double biddingSpread;
  private double longElctricity;
  private double longSpread;
  private long tag;
  private String createuser;
  private java.sql.Timestamp createdate;
  private String changeuser;
  private java.sql.Timestamp changedate;


  public long getDetailedId() {
    return detailedId;
  }

  public void setDetailedId(long detailedId) {
    this.detailedId = detailedId;
  }


  public String getSaleElectricityId() {
    return saleElectricityId;
  }

  public void setSaleElectricityId(String saleElectricityId) {
    this.saleElectricityId = saleElectricityId;
  }


  public String getDeclareMonth() {
    return declareMonth;
  }

  public void setDeclareMonth(String declareMonth) {
    this.declareMonth = declareMonth;
  }


  public double getElectricityConsum() {
    return electricityConsum;
  }

  public void setElectricityConsum(double electricityConsum) {
    this.electricityConsum = electricityConsum;
  }


  public double getBiddingElctricity() {
    return biddingElctricity;
  }

  public void setBiddingElctricity(double biddingElctricity) {
    this.biddingElctricity = biddingElctricity;
  }


  public double getBiddingSpread() {
    return biddingSpread;
  }

  public void setBiddingSpread(double biddingSpread) {
    this.biddingSpread = biddingSpread;
  }


  public double getLongElctricity() {
    return longElctricity;
  }

  public void setLongElctricity(double longElctricity) {
    this.longElctricity = longElctricity;
  }


  public double getLongSpread() {
    return longSpread;
  }

  public void setLongSpread(double longSpread) {
    this.longSpread = longSpread;
  }


  public long getTag() {
    return tag;
  }

  public void setTag(long tag) {
    this.tag = tag;
  }


  public String getCreateuser() {
    return createuser;
  }

  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }


  public java.sql.Timestamp getCreatedate() {
    return createdate;
  }

  public void setCreatedate(java.sql.Timestamp createdate) {
    this.createdate = createdate;
  }


  public String getChangeuser() {
    return changeuser;
  }

  public void setChangeuser(String changeuser) {
    this.changeuser = changeuser;
  }


  public java.sql.Timestamp getChangedate() {
    return changedate;
  }

  public void setChangedate(java.sql.Timestamp changedate) {
    this.changedate = changedate;
  }

}
