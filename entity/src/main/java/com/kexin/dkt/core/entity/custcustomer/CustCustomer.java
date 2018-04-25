package com.kexin.dkt.core.entity.custcustomer;

import java.util.Date;

public class CustCustomer {
    private String customerFax;
    private String customerConnDept;
    private String customerId;
    private String customerLevelId;
    private String customerCode;
    private String customerName;
    private String customerTrade;
    private String customerConnLinker;
    private String customerConnTele;
    private String areaId;
    private String customerEmail;
    private String customerAbstract;
    private Integer electricityFlag;
    private String customerType;
    private Integer customerStatus;
    private String creator;
    private String modifier;
    private Date modifyTime;
    private String customerRemark;
    private Date createTime;
    private Integer deleted;
    private String addressId;
    private Integer pass;
    private String customerLogo;
    private String customerAddress;
    private String measureMethods;//计量方式 1 低压柜进线计量 2 高压柜进线计量 3 不计量

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerConnDept() {
        return customerConnDept;
    }

    public void setCustomerConnDept(String customerConnDept) {
        this.customerConnDept = customerConnDept;
    }



    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public String getCustomerFax() {
        return customerFax;
    }

    public void setCustomerFax(String customerFax) {
        this.customerFax = customerFax;
    }

    public String getCustomerLogo() {
        return customerLogo;
    }

    public void setCustomerLogo(String customerLogo) {
        this.customerLogo = customerLogo;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerLevelId() {
        return customerLevelId;
    }

    public void setCustomerLevelId(String customerLevelId) {
        this.customerLevelId = customerLevelId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTrade() {
        return customerTrade;
    }

    public void setCustomerTrade(String customerTrade) {
        this.customerTrade = customerTrade;
    }

    public String getCustomerConnLinker() {
        return customerConnLinker;
    }

    public void setCustomerConnLinker(String customerConnLinker) {
        this.customerConnLinker = customerConnLinker;
    }

    public String getCustomerConnTele() {
        return customerConnTele;
    }

    public void setCustomerConnTele(String customerConnTele) {
        this.customerConnTele = customerConnTele;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAbstract() {
        return customerAbstract;
    }

    public void setCustomerAbstract(String customerAbstract) {
        this.customerAbstract = customerAbstract;
    }

    public Integer getElectricityFlag() {
        return electricityFlag;
    }

    public void setElectricityFlag(Integer electricityFlag) {
        this.electricityFlag = electricityFlag;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Integer getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMeasureMethods() {
        return measureMethods;
    }

    public void setMeasureMethods(String measureMethods) {
        this.measureMethods = measureMethods;
    }

    @Override
    public String toString() {
        return "CustCustomer{" +
                "customerId='" + customerId + '\'' +
                ", customerLevelId='" + customerLevelId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerTrade='" + customerTrade + '\'' +
                ", customerConnLinker='" + customerConnLinker + '\'' +
                ", customerConnTele='" + customerConnTele + '\'' +
                ", areaId='" + areaId + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerAbstract='" + customerAbstract + '\'' +
                ", electricityFlag=" + electricityFlag +
                ", customerType='" + customerType + '\'' +
                ", customerStatus=" + customerStatus +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", modifyTime=" + modifyTime +
                ", customerRemark='" + customerRemark + '\'' +
                ", createTime=" + createTime +
                ", addressId='" + addressId + '\'' +
                '}';
    }
}
