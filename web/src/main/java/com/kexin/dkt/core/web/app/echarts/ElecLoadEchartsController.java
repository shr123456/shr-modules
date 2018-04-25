package com.kexin.dkt.core.web.app.echarts;

import com.alibaba.druid.filter.AutoLoad;
import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.util.BasicElecPrice;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.service.energy.EnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kexin on 2017/10/31.
 */
@Controller
@Slf4j
@RequestMapping("app/echarts")
public class ElecLoadEchartsController {

    @Autowired
    private EnergyService energyService;

    @RequestMapping("electricLoad")
    @ResponseBody
    public ActionResult findElecLoadData(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();

            //月最大负荷值
            double maxLoadValue = energyService.getMaxLoad(paramDate);

            //变压器容量
            int transformerCapacity = energyService.getTransCapacity(paramDate);

            //基本电费
            double basicElecCost = BasicElecPrice.getBasicElecPrice(transformerCapacity);
            //最大负荷率
            double MaxLoadProb = 0;
            if(transformerCapacity !=0){
                MaxLoadProb = maxLoadValue/transformerCapacity;
            }

            String option = "";
            //月负荷折线图
            if("loadIndex".equals(paramDate.getTabName())){
                /*获取月负荷折线图*/
                option = energyService.getLoadOption(paramDate,paramDate.getTabName());
            }
            //月度负荷率统计
            map.put("option", JSON.parse(option));
            //最大负荷值
            map.put("maxLoadValue", CalculateUtils.get2PointValue(maxLoadValue));
            //基本电费
            map.put("basicElecCost",CalculateUtils.roundNumber(basicElecCost));
            //变压器容量
            map.put("transformerCapacity",CalculateUtils.roundNumber(transformerCapacity));
            //最大负荷率
            map.put("MaxLoadProb",MaxLoadProb+"%");
            result.setContent(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }
}
