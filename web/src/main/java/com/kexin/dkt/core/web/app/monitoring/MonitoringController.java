package com.kexin.dkt.core.web.app.monitoring;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.service.customer.CustomerService;
import com.kexin.dkt.core.service.energy.EnergyService;
import com.kexin.dkt.core.service.monitoring.MonitoringService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/11/30.
 */
@Controller
@RequestMapping("app/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @Autowired
    private EnergyService energyService;

    /*
     *设备用电排名报表
     */
    @RequestMapping("elecRankingSheet")
    @ResponseBody
    public ActionResult getEquipmentElectricRanking(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> list = monitoringService.getEquipmentElectricRanking(paramDate);
            if(list !=null && list.size()>0){
                result.setContent(list);
            }else{
                list = new ArrayList<Map<String,Object>>();
                result.setContent(list);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("操作失败");
        }
        return result;
    }

    /**
     * 查询设备详情及用电量
     * @param config
     * @return
     */
    @RequestMapping("equipmentDetail")
    @ResponseBody
    public ActionResult loadEquipmentDetail(@RequestBody EquipmentMeterConfig config){
        ActionResult result = new ActionResult();
        try {
            //封装设备信息map
                Map<String,Object> equipMap = monitoringService.loadEquipmentDetail(config);
            result.setContent(equipMap);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 显示电量/电流/电压/功率因素图表
     * @param paramDate
     * @return
     */
    @RequestMapping("equipmentEcharts")
    @ResponseBody
    public ActionResult loadEchartsByType(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            //查询电流/电压/电量echarts图表
            String option = monitoringService.loadEquipmentEcharts(paramDate);
            map.put("option", JSON.parse(option));
            result.setContent(map);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 显示近一周的报表
     * @param paramDate
     * @return
     */
    @RequestMapping("weekSheet")
    @ResponseBody
    public ActionResult getWeekSheet(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            //显示近一周的报表
            List<Map<String,Object>> mapList = monitoringService.getWeekSheet(paramDate);
            result.setContent(mapList);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
            e.printStackTrace();
        }

        return result;
    }
}
