package com.kexin.dkt.core.entity.sale;

import lombok.Data;

import java.util.Date;

/**
 * @auther shr
 * @date 2017/11/3
 * @time 11:41
 * @desc
 **/
@Data
public class CustSaleElectric {

  private int id;
  private String customerId;
  private String buyElectricVolume;
  private String message;
  private int businessId;
  private String businessDesc;
  private Date createTime;
  private String updetor;
  private Date updateTime;
  private String remark;

}
