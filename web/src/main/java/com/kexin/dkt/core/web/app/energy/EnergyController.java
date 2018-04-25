package com.kexin.dkt.core.web.app.energy;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.service.energy.EnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kexin on 2017/10/24.
 */
@Slf4j
@Controller
@RequestMapping("app/energy")
public class EnergyController {

    @Autowired
    private EnergyService energyService;

    /**
     *返回能效首页数据
     * @param
     * @return ActionResult
     */
    @RequestMapping("home")
    @ResponseBody
    public ActionResult findEnergyHomeData( @RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        Map<String,Object> contents = new HashMap<String, Object>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前月份的第一天
            String firstDate = DateUtils.date2Str(new Date(), "yyyy-MM") + "-01";
            String endDateStr = sdf.format(new Date());

            //使用paramDate封装参数
            paramDate.setEndDate(endDateStr);
            paramDate.setFirstDate(firstDate);

            //获取当前监控设备数量
            Integer equipmentNum = energyService.findEquipmentCount(paramDate.getUserId());

            //获取企业名称
            String customerName = energyService.findCustomerName(paramDate.getUserId());

            //单天电费
            double sumElecPriceByDay = energyService.findElecPriceByDay(paramDate);

            //当月电费
            double sumElecPriceByMonth = energyService.findAllElecPriceByTime(paramDate);

            //上月电费
            double sumElecPriceByPreMonth = energyService.findElecPriceByPreMonth(paramDate);

            //平均电价
            double avgElecPrice = energyService.getAvgElecPrice(paramDate);

            //安全指数暂无计算公式
            Integer safetyIndex = 90;

            //能效指数
            long energyIndex = energyService.getEnergyIndex(paramDate);

            //未处理安全问题
            Integer safetyProblem = 0;

            String option = "";
            //月负荷折线图
            if("loadIndex".equals(paramDate.getTabName())){
                /*获取月负荷折线图*/
                option = energyService.getLoadOption(paramDate,paramDate.getTabName());
            }
            //设置返回值
            //当前监控设备数
            contents.put("equipmentNum", equipmentNum);
            //公司名称
            contents.put("customerName", customerName);
            //当天电费
            contents.put("sumElecPriceByDay", sumElecPriceByDay);
            //上月电费
            contents.put("sumElecPriceByPreMonth", sumElecPriceByPreMonth);
            //当月电费
            contents.put("sumElecPriceByMonth", CalculateUtils.roundNumber(sumElecPriceByMonth));
            //平均电价
            contents.put("avgElecPrice", CalculateUtils.get2PointValue(avgElecPrice));
            //安全指数
            contents.put("safetyIndex", safetyIndex);
            //能效指数
            contents.put("energyIndex", energyIndex);
            //未处理安全问题
            contents.put("safetyProblem", safetyProblem);
            //未处理安全问题
            contents.put("option", JSON.parse(option));
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        result.setContent(contents);
        return result;
    }
}
