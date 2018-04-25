package com.kexin.dkt.core.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by admin on 2017/10/25.
 */
@Data
public class EchartsData {
    private String name;
    private List<String> data;
    private List<String> xcontent;
    private String yaxisname;//Y轴名称
    private String xaxisname;//X轴名称
    private List<Object> dataList;//数据集合
    private List<Object> nameList;//数据集合
    private String optionCode;
}
