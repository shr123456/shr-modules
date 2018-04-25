package com.kexin.dkt.core.common.echarts;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.entity.echarts.EchartsSeries;

import java.util.List;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 19:16
 * @desc
 **/
public class EchartsUtils {
    public static String getOptionManyLine(String title,String[] color, String[] legend, String[] xAxis, List<EchartsSeries> series  ){

           String option = "{" +
                   "    backgroundColor: '#ffffff'," +
                   "    color:" + JSON.toJSONString(color)+","+
                   "    title: {" +
                   "        text: "  + JSON.toJSONString(title)+","+
                   "        x: 25," +
                   "        y: 25," +
                   "        textStyle: {" +
                   "            fontSize: 10" +
                   "        }" +
                   "    }," +
                   "    tooltip: {" +
                   "        trigger: 'axis'" +
                   "    }," +
                   "    legend: {" +
                   "        data:" + JSON.toJSONString(legend)+
                   "    }," +
                   "    grid: {" +
                   "        top: 70," +
                   "        bottom: 50," +
                   "        x: 60" +
                   "    }," +
                   "    xAxis: [" +
                   "        {" +
                   "            type: 'category'," +
                   "            axisTick: {" +
                   "                alignWithLabel: true" +
                   "            }," +
                   "            axisLine: {" +
                   "                lineStyle: {" +
                   "                }" +
                   "            }," +
                   "        data: "  + JSON.toJSONString(xAxis)+
                   "        }" +
                   "    ]," +
                   "    yAxis: [" +
                   "        {" +
                   "            type: 'value'" +
                   "        }" +
                   "    ]," +
                   "    series: " + JSON.toJSONString(series) +
                   "}";
                    
         option = option.toString().replaceAll(" +","");
        return option.trim();
    }
}
