package com.kexin.dkt.core.web.app.echarts;

import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.equipment.EquipmentGroup;
import com.kexin.dkt.core.entity.vo.EchartsByTime;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.collect.CollectTotalPAtEeDay;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.vo.EchartsData;
import com.kexin.dkt.core.service.customer.CustomerService;
import com.kexin.dkt.core.service.echarts.EchartsService;
import com.kexin.dkt.core.service.equipment.EquipmentMeterConfigService;
import com.kexin.dkt.core.service.equipment.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/25.
 */
@Slf4j
@Controller
@RequestMapping("app/echarts")
public class EchartsController  {
    @Autowired
    private EchartsService echartsService;

    @Autowired
    private EquipmentMeterConfigService equipmentMeterConfigService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EquipmentService equipmentService;


    /**
     * 查询某设备某段时间的总峰平谷数据
     * @return
     */
    @RequestMapping("/collectTotalPAt")
    @ResponseBody
    public ActionResult viewList(@RequestBody EchartsByTime echartsByTime) {
        ActionResult actionResult = new ActionResult();
        String dataList= "{tooltip: {trigger: 'axis',axisPointer: {type: 'shadow'}},legend: {data: ['总','峰','平','谷']},grid: {left: '3%',right: '4%',bottom: '3%',containLabel: true},yAxis: {type: 'value'},xAxis: {type: 'category',data: []},series: []}";
        EquipmentMeterConfig meterconfig=new EquipmentMeterConfig();
        CollectTotalPAtEeDay collectTotalPAtEeDay=new CollectTotalPAtEeDay();
        try {
            String userId=echartsByTime.getUserId();
            //    String userId="c4b13b60-d375-45cb-8096-8b422965d19e";
            StringBuffer sb = new StringBuffer();
            List<CustCustomer> custcustomer =customerService.findCustomerIdAndMeths(userId);
            if(custcustomer.size()>0){
                String  customerId=custcustomer.get(0).getCustomerId();
                int   measuremethods=Integer.parseInt(custcustomer.get(0).getMeasureMethods());
                String genre =null;
                //meterconfig.setMeasuremethods(2);
                if(measuremethods == 0){
                    genre = "1";
                }else if(measuremethods == 1){
                    genre = "0";
                }else {
                    genre = "2";
                }
                meterconfig.setCustomerId(customerId);
                meterconfig.setGenre(Integer.parseInt(genre));
                if(echartsByTime.getGroupId()!=0){
                    List<String> equipmentIdList = equipmentService.equipmentIdList(echartsByTime.getGroupId()+"");
                    if(equipmentIdList !=null && equipmentIdList.size()>0) {
                        meterconfig.setCollectConfigIdList(equipmentIdList);
                    }else{
                        meterconfig.setCollectConfigIdList(new ArrayList<String>());
                    }
                }else {
                meterconfig.setCollectConfigId(echartsByTime.getCollectConfigId());
                }
                List<EquipmentMeterConfig>  dtumeter= equipmentMeterConfigService.seldmByCollectConfigId(meterconfig);
                List<EchartsData>  jsonDatas=null;
                if(dtumeter.size()>0){
                collectTotalPAtEeDay.setBeginTime(echartsByTime.getBeginTime());
                collectTotalPAtEeDay.setEndTime(echartsByTime.getEndTime());
                if(dtumeter.size()==1){
                    collectTotalPAtEeDay.setDtuNo(dtumeter.get(0).getDtuNo());
                    collectTotalPAtEeDay.setMeterAddress(dtumeter.get(0).getMeterAddress());
                    jsonDatas = echartsService.findByEnergyCount(collectTotalPAtEeDay);
                }else if(dtumeter.size()>1){
                    jsonDatas=echartsService.findByEnergyCounts(dtumeter,collectTotalPAtEeDay);
                }
                if(jsonDatas!=null){
                     dataList=equipmentMeterConfigService.SetOption(jsonDatas);
                    if(dataList.length()>0){
                        actionResult.setSuccess(true);
                    }else {
                        actionResult.setMessage("数据转json失败！");
                        actionResult.setSuccess(false);
                    }
                }else {
                    actionResult.setMessage("没查到总峰平谷数据！");
                    actionResult.setSuccess(false);
                }
                }else {
                    actionResult.setMessage("客户没有安装电可托电表采集设备！");
                    dataList="{}";
                    actionResult.setSuccess(false);
                }
            }else {
                actionResult.setMessage("没查到客户id！");
                actionResult.setSuccess(false);
            }
        } catch (Exception e) {
            actionResult.setMessage("操作失败");
            actionResult.setCode(ResultCode.ERROR.getCode());
            actionResult.setSuccess(false);
            e.printStackTrace();
        }
        actionResult.setContent(JSON.parse(dataList));
        return  actionResult;
    }

    /**
     * 查询某客户当前月的总峰平谷数据
     * @param paramdate
     * @return
     */
    @RequestMapping("/collectByMonth")
    @ResponseBody
    public ActionResult viewListByMonth(@RequestBody  ParamDate paramdate) {
        ActionResult actionResult = new ActionResult();
        String dataList= "{tooltip: {trigger: 'axis',axisPointer: {type: 'shadow'}},legend: {data: ['总','峰','平','谷']},grid: {left: '3%',right: '4%',bottom: '3%',containLabel: true},yAxis: {type: 'value'},xAxis: {type: 'category',data: []},series: []}";
        try{
            EquipmentMeterConfig meterconfig = new EquipmentMeterConfig();
            String userId=paramdate.getUserId();
            List<CustCustomer> custcsustomer =customerService.findCustomerIdAndMeths(userId);
            if(custcsustomer.size()>0){
                String  customerId=custcsustomer.get(0).getCustomerId();
                int   measuremethods=Integer.parseInt(custcsustomer.get(0).getMeasureMethods());
                String genre =null;
                // meterconfig.setMeasuremethods(2);
                if(measuremethods == 0){
                    genre = "1";
                }else if(measuremethods == 1){
                    genre = "0";
                }else {
                    genre = "2";
                }
                meterconfig.setCustomerId(customerId);
                meterconfig.setGenre(Integer.parseInt(genre));
                List<EchartsData>  jsonDatas =equipmentMeterConfigService.viewListByMonth(meterconfig);
                if(jsonDatas!=null){
                 dataList=equipmentMeterConfigService.SetOption(jsonDatas);
                    if(dataList.length()>0){
                        actionResult.setContent(JSON.parse(dataList));
                    }else {
                        actionResult.setMessage("数据转json失败！");
                        actionResult.setSuccess(false);
                    }
                }else {
                    actionResult.setMessage("没查到总峰平谷数据！");
                    actionResult.setSuccess(false);
                }
                actionResult.setMessage("查询成功！");
            }else {
                actionResult.setMessage("查询失败！");
                actionResult.setSuccess(false);
            }
        } catch (Exception e) {
            actionResult.setMessage("操作失败");
            actionResult.setCode(ResultCode.ERROR.getCode());
            actionResult.setSuccess(false);
            e.printStackTrace();
        }
        actionResult.setContent(JSON.parse(dataList));
        return actionResult;
    }
    /**
     * 查询某客户当前月的总峰平谷费用
     * @param paramdate
     * @return
     */
    @RequestMapping("/collectByMonthMoney")
    @ResponseBody
    public ActionResult viewListByMonthMoney(@RequestBody ParamDate paramdate) {
        ActionResult actionResult = new ActionResult();
        String dataList= "{tooltip: {trigger: 'axis',axisPointer: {type: 'shadow'}},legend: {data: ['总','峰','平','谷']},grid: {left: '3%',right: '4%',bottom: '3%',containLabel: true},yAxis: {type: 'value'},xAxis: {type: 'category',data: []},series: []}";
        try{
            EquipmentMeterConfig meterconfig = new EquipmentMeterConfig();
            String userId=paramdate.getUserId();
            List<CustCustomer> custcustomer =customerService.findCustomerIdAndMeths(userId);
            if(custcustomer.size()>0){
                String  customerId=custcustomer.get(0).getCustomerId();
                int   measuremethods=Integer.parseInt(custcustomer.get(0).getMeasureMethods());
                String genre =null;
                if(measuremethods == 0){
                    genre = "1";
                }else if(measuremethods == 1){
                    genre = "0";
                }else {
                    genre = "2";
                }
                meterconfig.setCustomerId(customerId);
                meterconfig.setGenre(Integer.parseInt(genre));
                List<EchartsData>  jsonDatas =equipmentMeterConfigService.viewListByMonthMoney(meterconfig);
                if(jsonDatas!=null){
                     dataList=equipmentMeterConfigService.SetOption(jsonDatas);
                    if(dataList.length()>0){
                        actionResult.setMessage("查询成功！");
                        actionResult.setSuccess(true);
                    }else {
                        actionResult.setMessage("数据转json失败！");
                        actionResult.setSuccess(false);
                    }
                }else {
                    actionResult.setMessage("没查到总峰平谷数据！");
                    actionResult.setSuccess(false);
                }
            }else {
                actionResult.setMessage("查询失败！");
                actionResult.setSuccess(false);
            }
        } catch (Exception e) {
            actionResult.setMessage("操作失败");
            actionResult.setCode(ResultCode.ERROR.getCode());
            actionResult.setSuccess(false);
            e.printStackTrace();
        }
        actionResult.setContent(JSON.parse(dataList));
        return  actionResult;
    }
}
