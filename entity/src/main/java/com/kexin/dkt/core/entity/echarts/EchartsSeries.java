package com.kexin.dkt.core.entity.echarts;

import lombok.Data;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 19:47
 * @desc
 **/
@Data
public class EchartsSeries {
    private String name;
    private String type;
    private String stack;
    private String[] data;
}
