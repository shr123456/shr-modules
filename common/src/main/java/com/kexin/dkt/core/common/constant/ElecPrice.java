package com.kexin.dkt.core.common.constant;

/**
 * Created by kexin on 2017/10/25.
 */
public class ElecPrice {

    //峰值电价
    public static final double PEAK_PRICE = 1.03476;

    //谷值电价
    public static final double VALLEY_PRICE = 0.3351;

    //平值电价
    public static final double FLAT_PRICE = 0.6393;

    //峰谷平总电价
    public static final double TOTAL_PRICE = PEAK_PRICE+VALLEY_PRICE+FLAT_PRICE;

    /**
     * 低压进线
     */
    public static  final int METERING_MODE_LOW = 0;

    /**
     * 高压进线
     */
    public static  final int METERING_MODE_HIGH = 1;


    /**
     * 不计量
     */
    public static  final int METERING_MODE_LOOP = 2;

    /**
     * 客戶角色ID
     */
    public static final String CUSTOMER_ROLE_ID = "666";

}
