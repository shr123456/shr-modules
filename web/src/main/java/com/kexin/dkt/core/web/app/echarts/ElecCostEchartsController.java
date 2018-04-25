package com.kexin.dkt.core.web.app.echarts;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by kexin on 2017/10/25.
 */
@Controller
@Slf4j
@RequestMapping("app/echarts")
public class ElecCostEchartsController {

    @Autowired
    private EnergyService energyService;

    @RequestMapping("userCost")
    @ResponseBody
    public ActionResult findElecCostData(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();

            //能效指数
            long energyIndex = energyService.getEnergyIndex(paramDate);

            String option = "";
            //能效指数折线图
            if("energyIndex".equals(paramDate.getTabName())){
                /*获取能效指数折线图*/
                option = energyService.getEnergyIndexOption(paramDate);
            }else {
                /*获取时间和电费类型分类饼状图*/
                option = energyService.getTimeTypeOption(paramDate);
            }
            map.put("option",JSON.parse(option));
            map.put("energyIndex",energyIndex+"%");
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
