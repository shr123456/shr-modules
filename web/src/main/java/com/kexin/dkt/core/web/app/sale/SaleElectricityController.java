package com.kexin.dkt.core.web.app.sale;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.common.util.ValidationUtil;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.sale.CustSaleElectric;
import com.kexin.dkt.core.entity.sale.SaleAssessment;
import com.kexin.dkt.core.entity.sale.SaleElectricityDetailed;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.vo.SaleElectricVo;
import com.kexin.dkt.core.service.customer.sale.SaleElectricService;
import com.kexin.dkt.core.service.echarts.SaleElectricityService;
import com.kexin.dkt.core.service.energy.EnergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 17:20
 * @desc
 **/
@Controller
@Slf4j
@RequestMapping("app/sale/electricity")
public class SaleElectricityController {

    @Autowired
    private SaleElectricityService saleElectricityService;

    @Autowired
    private EnergyService energyService;

    @Autowired
    private SaleElectricService saleElectricService;

    @ResponseBody
    @RequestMapping("ranking")
    public ActionResult loadEquipmentUseRanking(@RequestBody SaleElectricityDo saleElectricityDo){
        ActionResult actionResult = new ActionResult();
        actionResult.setContent(saleElectricityService.getUseRanking(saleElectricityDo));
        return actionResult;
    }

    /**
     * 按月获取客户总用电量
     * @param saleElectricityDo
     * @return
     */
    @ResponseBody
    @RequestMapping("loadElectricByCustomer")
    public ActionResult loadElectricByCustomer(@RequestBody SaleElectricityDo saleElectricityDo) throws Exception{
        ActionResult actionResult = new ActionResult();
        saleElectricityDo = saleElectricityService.loadElectricByCustomer(saleElectricityDo);
        if(ValidationUtil.isEmpty(saleElectricityDo.getElectric())){
            actionResult.setMessage("读取电量数据失败");
            actionResult.setCode(ResultCode.ERROR.getCode());
            actionResult.setSuccess(false);
        }else{
            actionResult.setContent(CalculateUtils.roundNumber(saleElectricityDo.getElectric()));
        }
        return  actionResult;
    }

    /**
     * 购电首页
     * @param paramDate
     * @return
     */
    @RequestMapping("home")
    @ResponseBody
    public ActionResult loadSellHomeData(@RequestBody ParamDate paramDate){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();

            //判断是否为当月
            Date date = DateUtils.converStrTimeToDate(paramDate.getEndDate());
            //获取当月的天数
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(calendar.MONTH,(calendar.get(calendar.MONTH)+1));
            calendar.set(calendar.DAY_OF_MONTH,0);
            int monthDay = calendar.get(calendar.DAY_OF_MONTH);
            int runDay = 0;
            if(DateUtils.date2Str(date,"yyyy-MM").equals(DateUtils.date2Str(new Date(),"yyyy-MM"))){
                runDay = Integer.parseInt(DateUtils.date2Str(new Date(),"dd"));
            }else{
                runDay = monthDay ;
            }


            //购电量(未知表)
            double buyEnergy = 0.0;

            //出清价差(未知公式：单位/厘)
            double priceSpread = 0.0;

            //用电量
            double useEnergy = 0.0;

            //查询客户月销售额
            List<SaleElectricityDetailed> saleAssessmentList = saleElectricService.findSaleAssessment(paramDate);
            if(saleAssessmentList !=null && saleAssessmentList.size() > 0){
                SaleElectricityDetailed detailed = saleAssessmentList.get(0);
                buyEnergy = detailed.getBiddingElctricity();
                priceSpread = detailed.getBiddingSpread();
                if(saleAssessmentList.get(0).getElectricityConsum() != 0){
                    useEnergy = saleAssessmentList.get(0).getElectricityConsum();
                }
            }


            //剩余电量
            double dumpEnergy = 0.0;
            //考核
            double assess = 0.0;
            if(buyEnergy != 0.0) {
                //用电量峰谷平
                ElecQuantity elecQuantity = null;
//                if(DateUtils.date2Str(DateUtils.converStrTimeToDate(paramDate.getEndDate()),"MM").equals(DateUtils.date2Str(new Date(),"MM"))){
//                    elecQuantity = energyService.findEnergyByBeforeMonth(paramDate);
//                }else {
                    elecQuantity = energyService.findAllEnergyByMonth(paramDate);
//                }

                if (elecQuantity != null && useEnergy == 0.0) {
                    useEnergy = elecQuantity.getSumElectric();
                }
                dumpEnergy = buyEnergy - useEnergy > 0.0 ? buyEnergy - useEnergy : 0.0;

                //考核(公式：1-用电量/合理量），如超过2%，则减去允许偏差+-2%)s
                assess = 0.0;
                if(runDay != 1) {
                    assess = Math.abs(1 - useEnergy / (buyEnergy / monthDay * (runDay - 1)));
                }
                assess = assess < 0.02 ? 0.0 : assess - 0.02;
            }
            //k值(计算公式：(用电量/购电量)/(当前天/月天数)

            double kValue =CalculateUtils.get2PointValue(CalculateUtils.get2PointValue(useEnergy/buyEnergy/runDay*monthDay));

            String option = "";
            //售电指数柱状图
            if("saleIndex".equals(paramDate.getTabName())){
                /*购电指数柱状图*/
                option = energyService.getSellIndexOption(paramDate, buyEnergy);
            }

            //购电指数柱状图
            map.put("option", JSON.parse(option));
            //购电量
            map.put("buyEnergy", buyEnergy);
            //用电量
            map.put("useEnergy", CalculateUtils.roundNumber(useEnergy));
            //出清价差
            map.put("priceSpread", priceSpread);
            //剩余电量
            map.put("dumpEnergy", CalculateUtils.roundNumber(dumpEnergy));
            //考核
            map.put("assess", CalculateUtils.get2PointValue(assess*100)+"%");
            //k值
            map.put("kValue", kValue);
            result.setContent(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 新增售电申报
     * @param custSaleElectric
     * @return
     */
    @ResponseBody
    @RequestMapping("saveSaleElectric")
    public ActionResult saveSaleElectric(@RequestBody CustSaleElectric custSaleElectric){
        ActionResult result = new ActionResult();
        saleElectricService.saveSaleElectric(custSaleElectric);
        result.setContent(custSaleElectric.getId());
        result.setMessage("提交成功");
        return result;
    }

    /**
     * 售电考核
     */
    @ResponseBody
    @RequestMapping("saleElectricAssess")
    public ActionResult loadSaleElectricAssess(@RequestBody SaleElectricityDo saleElectricityDo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            //获取客户年度收益信息
            SaleElectricVo saleAssessment = saleElectricService.findSaleElectricVoByYear(saleElectricityDo);
            //获取用户月度报表信息
            List<SaleAssessment> saleAssessmentList = saleElectricService.findSaleAssessmentList(saleElectricityDo);

            map.put("custIncomeMessage",saleAssessment);
            map.put("custIncomeSheet",saleAssessmentList);
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
