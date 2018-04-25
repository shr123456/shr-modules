package com.kexin.dkt.core.entity.vo;

import lombok.Data;

/**
 * Created by kexin on 2017/11/7.
 */
@Data
public class OrderVo {

    //工单id
    private int orderId;
    //客户id
    private String customerId;
    //用户id
    private String userId;
    //echarts图标标识
    private String TabName;
    //起始时间
    private String firstTime;
    //终止时间
    private String endTime;
    //订单类型
    private int orderType ;
    //项目id
    private int projectId;
    //服务类型
    private int serviceType;
    //搜索关键字
    private String queryValue;

}
