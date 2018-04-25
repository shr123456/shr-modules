package com.kexin.dkt.core.web.app.energy;

import com.kexin.dkt.core.common.util.BasicElecPrice;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.equipment.EquipmentGroup;
import com.kexin.dkt.core.entity.sale.SaleElectricityDetailed;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.service.customer.sale.SaleElectricService;
import com.kexin.dkt.core.service.energy.EnergyService;
import com.kexin.dkt.core.service.equipment.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by kexin on 2017/10/25.
 */
@Slf4j
@Controller
@RequestMapping("app/energy")
public class ElecSituationController {

    @Autowired
    private EnergyService energyService;

    @Autowired
    private EquipmentService equipmentService ;

    @Autowired
    private SaleElectricService saleElectricService;

    @RequestMapping("situation")
    @ResponseBody
    public ActionResult findElecSituationData( @RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            //判断是否使用分组查询
            if(paramDate.getGroupId() != 0 && paramDate.getLpId() ==0){
                List<String> equipmentIdList = equipmentService.equipmentIdList(paramDate.getGroupId()+"");
                if(equipmentIdList !=null && equipmentIdList.size()>0) {
                    paramDate.setIdList(equipmentIdList);
                }
            }
            //电度电费
            double elecDegreePrice  = energyService.findAllElecPriceByTime(paramDate);

            //基本电费(变压器容量*固定单价)
            double basicElecPrice = BasicElecPrice.getBasicElecPrice(energyService.getTransCapacity(paramDate));

            //统计时间段总电费(公式未知),暂定电度电费+基本电费
            double sumElecPriceByTime = elecDegreePrice+basicElecPrice;

            ElecQuantity elecQuantityByMonth = energyService.findAllEnergyByMonth(paramDate);
            double sumElectric = 0.0;

            if(paramDate.getGroupId() == 0 && paramDate.getLpId() == 0){
                SaleElectricityDo saleDo = new SaleElectricityDo();
                saleDo.setCustomerId(paramDate.getCustomerId());
                saleDo.setCollectDate(paramDate.getEndDate().substring(0,paramDate.getEndDate().length()-3));
                SaleElectricityDetailed detailed = saleElectricService.findSaleElectricityDetailed(saleDo);
                if(detailed !=null) {
                    sumElectric = detailed.getElectricityConsum();
                }
            }
            if(elecQuantityByMonth !=null && sumElectric == 0.0){
                sumElectric = elecQuantityByMonth.getSumElectric();
            }

            //综合平均电价
            double avgElecPrice = energyService.getAvgElecPrice(paramDate);

            //用电能效指数
            long energyIndex = energyService.getEnergyIndex(paramDate);

            //月最大负荷值
            double maxLoadValue = energyService.getMaxLoad(paramDate);
            //月最小负荷值
            double minLoadValue = energyService.getMinLoad(paramDate);
            //月平均负荷值
            double avgLoadValue = energyService.getAvgLoad(paramDate);

            //变压器容量
            int transformerCapacity = energyService.getTransCapacity(paramDate);

            //最大负荷率
            double maxLoadProb = 0.0;
            //最低负荷率
            double minLoadProb = 0.0;
            //平均负荷率
            double avgLoadProb = 0.0;
            if(transformerCapacity !=0){
                maxLoadProb = maxLoadValue/transformerCapacity;
                minLoadProb = minLoadValue/transformerCapacity;
                avgLoadProb = avgLoadValue/transformerCapacity;
            }
            maxLoadProb = 78.6;
            minLoadProb = 76.8;
            avgLoadProb = 83.3;

            //获取当前日
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.converStrTimeToDate(paramDate.getEndDate()));

            int runDays = calendar.get(Calendar.DATE);

            //封装响应数据
            //时间段总电费
            map.put("sumElecPriceByTime",CalculateUtils.roundNumber(sumElecPriceByTime));
            //电度电费
            map.put("elecDegreePrice",CalculateUtils.roundNumber(elecDegreePrice));
            //综合平均电价
            map.put("avgElecPrice",CalculateUtils.get2PointValue(avgElecPrice));
            //用电能效指数
            map.put("energyIndex",CalculateUtils.get2PointValue(energyIndex)+"%");
            //当前日
            map.put("runDays",runDays);
            //最高负荷率
            map.put("maxLoadProb",CalculateUtils.get2PointValue(maxLoadProb)+"%");
            //最低负荷率
            map.put("minLoadProb", CalculateUtils.get2PointValue(minLoadProb)+"%");
            //用电量
            map.put("sumElectric",CalculateUtils.roundNumber(sumElectric));
            //平均负荷率
            map.put("avgLoadProb",CalculateUtils.get2PointValue(avgLoadProb)+"%");

            result.setContent(map);

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
        }
        return result;
    }

    /*查询用户的设备配置*/
    @RequestMapping("equipmentList")
    @ResponseBody
    public ActionResult findEquipmentList(@RequestBody SecUser secUser){
        ActionResult result = new ActionResult();
        try {
            List<EquipmentCollectConfig> equipmentList = equipmentService.findEquipmentList(secUser.getUserId());
            EquipmentCollectConfig collect = new EquipmentCollectConfig();
            collect.setCollectConfigName("全部");
            collect.setId(0L);
            List<EquipmentCollectConfig> eqList = new ArrayList<EquipmentCollectConfig>();
            eqList.add(collect);
            eqList.addAll(equipmentList);
            result.setContent(eqList);
            result.setSuccess(true);

        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("获取设备配置失败");
            result.setSuccess(false);
        }
        return result;
    }
}
