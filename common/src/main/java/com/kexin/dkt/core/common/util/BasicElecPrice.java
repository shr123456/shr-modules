package com.kexin.dkt.core.common.util;

/**
 * Created by kexin on 2017/10/31.
 */
public class BasicElecPrice {

    //基本计费单位
    private static int BasicUnit = 23;

    /**
     * 获取基本电费
     * @param transformerCapacity 变压器容量
     * @return
     */
    public static double getBasicElecPrice(int transformerCapacity){

        return transformerCapacity * BasicUnit;
    }
}
