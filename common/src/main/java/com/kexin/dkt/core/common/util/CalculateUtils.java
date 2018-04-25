package com.kexin.dkt.core.common.util;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;

/**
 * Created by kexin on 2017/10/31.
 */
public class CalculateUtils {

    /*保留两位小数*/
    public static double get2PointValue(double value){
        return Math.round(value * 100)/100.00;
    }
    /*保留整数*/
    public static double roundNumber(double value){
        return Math.round(value);
    }

}
