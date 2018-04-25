package com.kexin.dkt.core.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by kexin on 2017/10/25.
 */
@Data
public class ParamDate {

    //用户id
    private String userId;
    //图表区分标识
    private String tabName;
    //初始日期
    private String firstDate;
    //结束日期
    private String endDate;
    //设备id
    private int lpId;
    //客户id
    private String customerId;
    //电量统计方式
    private int genre;
    //dtu_no
    private String dtuNo;
    //电表地址
    private long meterAddress;
    //表名称
    private String table;
    //设备分组id
    private long groupId;
    //设备分组id数组字符串
    private List<String> idList;
}
