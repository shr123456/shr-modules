package com.kexin.dkt.core.entity.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by kexin on 2017/11/14.
 */
@Data
public class SaleElectricVo {

    //用电量
    private double userElectric;
    //购电量
    private double buyElectric;
    //偏差值
    private double deviation;
    //考核值
    private double asseOfValue;
    //总收益
    private double sumIncome;
    //客户收益
    private double custIncome;
    //可信收益
    private double consonIncome;
    //考核罚款
    private double assessFine;
    //偏差罚款
    private double biasFine;

}
