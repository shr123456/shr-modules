package com.kexin.dkt.core.entity.vo;

import lombok.Data;

/**
 * Created by kexin on 2017/11/22.
 */
@Data
public class PageBeanVo {

    private int id; //id
    private String customerId;//客户id
    private int coopType;//合作类型
    private int limit;   //每页显示的记录数
    private int page; //当前页数
}
