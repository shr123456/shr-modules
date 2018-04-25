package com.kexin.dkt.core.service.customer.sale;

import com.kexin.dkt.core.common.constant.ElecPrice;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.common.util.ValidationUtil;
import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.dao.echarts.SaleElectricityDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.sale.CustSaleElectric;
import com.kexin.dkt.core.entity.sale.SaleAssessment;
import com.kexin.dkt.core.entity.sale.SaleElectricity;
import com.kexin.dkt.core.entity.sale.SaleElectricityDetailed;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.vo.SaleElectricVo;
import com.kexin.dkt.core.service.echarts.SaleElectricityService;
import com.kexin.dkt.core.service.energy.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther shr
 * @date 2017/11/3
 * @time 10:55
 * @desc
 **/
@Service
public class SaleElectricService {

    @Autowired
    private SaleElectricityDao saleElectricityDao;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EnergyService energyService;

    public long saveSaleElectric(CustSaleElectric custSaleElectric){
        return saleElectricityDao.saveSaleElectric(custSaleElectric);
    }

    /*查询客户月度收益报表*/
    public List<SaleAssessment> findSaleAssessmentList(SaleElectricityDo saleElectricityDo){
        SaleElectricityDo saleDo = new SaleElectricityDo();
        saleDo.setCustomerId(saleElectricityDo.getCustomerId());
        //查询客户与可信分成比例，参数（2017）
        SaleElectricity saleElectricity = saleElectricityDao.findSaleElectricity(saleElectricityDo);
        List<SaleAssessment> saleAssessmentList = new ArrayList<SaleAssessment>();

        //设置客户计量方式
        List<CustCustomer> custlist= customerDao.findMethByCustomerId(saleElectricityDo.getCustomerId());
        if(!ValidationUtil.isEmpty(custlist)) {
            CustCustomer customer = custlist.get(0);
            if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_LOW) {
                saleElectricityDo.setGenre(1);
            } else if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_HIGH) {
                saleElectricityDo.setGenre(0);
            } else {
                saleElectricityDo.setGenre(2);
            }
        }
        List<ElecQuantity> elecQuantityList = energyService.findEnergyByYear(saleElectricityDo);

        List<SaleElectricityDetailed> detailedList = saleElectricityDao.findSaleElectricityDetailedList(saleElectricityDo);

        if(elecQuantityList !=null && elecQuantityList.size()>0) {
            int index = -1;
            if(detailedList !=null && detailedList.size()>0){
                outer:for(SaleElectricityDetailed electricityDetailedParam : detailedList){
                    boolean flag = false;
                    inner:for(ElecQuantity elecQuantityParam :elecQuantityList) {
                        if (electricityDetailedParam.getElectricityConsum() == 0 || electricityDetailedParam.getDeclareMonth().equals(elecQuantityParam.getDateStr())) {
                            flag = true;
                        }
                    }
                    if(!flag){
                        index ++;
                        ElecQuantity newElecQuantity = new ElecQuantity();
                        newElecQuantity.setDateStr(electricityDetailedParam.getDeclareMonth());
                        elecQuantityList.add(index,newElecQuantity);
                    }
                }
            }

            for (int i = 0; i < elecQuantityList.size(); i++){
                if(elecQuantityList.get(i).getDateStr().equals(DateUtils.date2Str(new Date(),"yyyy-MM"))){
                    continue;
                }
                SaleAssessment saleAssessment = new SaleAssessment();
                saleAssessment.setTime(elecQuantityList.get(i).getDateStr());
                saleAssessment.setEleConsumption(elecQuantityList.get(i).getSumElectric());
                //查询当月购电量、价差
                saleDo.setCollectDate(elecQuantityList.get(i).getDateStr());
                SaleElectricityDetailed saleElectricityDetailed = saleElectricityDao.findSaleElectricityDetailed(saleDo);
                if(saleElectricityDetailed !=null) {
                    //购电量
                    saleAssessment.setPowerPurchaseValue(CalculateUtils.roundNumber(saleElectricityDetailed.getBiddingElctricity()));
                    //价差
                    saleAssessment.setSpreads(saleElectricityDetailed.getBiddingSpread());
                    //如果用电量纠正了，那么就使用纠正后的用电量
                    if(saleElectricityDetailed.getElectricityConsum() != 0) {
                        saleAssessment.setEleConsumption(saleElectricityDetailed.getElectricityConsum());
                    }
                }
                //总收益 = 用电量 * 价差 /1000
                double sumEarning = saleAssessment.getEleConsumption() * saleAssessment.getSpreads() /1000;
                //客户收益 =  用电量 * 价差 /1000 * 分成比例
                if(saleElectricity !=null) {
                    saleAssessment.setClientEarnings(CalculateUtils.get2PointValue(sumEarning * saleElectricity.getDivideIntoUpMonth() / (saleElectricity.getDivideIntoUpMonth() + saleElectricity.getDivideIntoDownMonth())));
                }
                if(saleAssessment.getPowerPurchaseValue() != 0.0) {
                //偏差值 如果大于购电量偏差值为0 否则为 购电量-用电量
                double deviation = saleAssessment.getPowerPurchaseValue() - saleAssessment.getEleConsumption();
                deviation = deviation >0 ? deviation :0;
                saleAssessment.setDeviation(deviation);
                //考核值 (公式：1-用电量/购电量），如超过2%，则减去允许偏差+-2%)
                    double assess = Math.abs(1 - saleAssessment.getEleConsumption() / saleAssessment.getPowerPurchaseValue());
                    assess = assess < 0.02 ? 0.0 : assess - 0.02;
                    saleAssessment.setAsseOfValue(CalculateUtils.get2PointValue(assess * 100));
                }
                saleAssessmentList.add(saleAssessment);
            }
        }

        return saleAssessmentList;
    }

    /*查询客户年收益信息*/
    public SaleElectricVo findSaleElectricVoByYear(SaleElectricityDo saleElectricityDo){
        SaleElectricityDo saleDo = new SaleElectricityDo();
        saleDo.setCustomerId(saleElectricityDo.getCustomerId());
        //客户年用电量
        double userElectricByYear = 0.0;
        //客户年购电量
        double buyElectricByYear = 0.0;
        //客户年收益
        double custIncomeByYear = 0.0;
        //考核平均值
        double assessRatio = 0.0;
        int count = 0;
        //设置客户计量方式
        List<CustCustomer> custlist= customerDao.findMethByCustomerId(saleElectricityDo.getCustomerId());
        if(!ValidationUtil.isEmpty(custlist)) {
            CustCustomer customer = custlist.get(0);
            if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_LOW) {
                saleElectricityDo.setGenre(1);
            } else if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_HIGH) {
                saleElectricityDo.setGenre(0);
            } else {
                saleElectricityDo.setGenre(2);
            }
        }
        //查询客户与可信分成比例，参数（2017）
        SaleElectricity saleElectricity = saleElectricityDao.findSaleElectricity(saleElectricityDo);

        List<ElecQuantity> elecQuantityList = energyService.findEnergyByYear(saleElectricityDo);
        if(elecQuantityList !=null && elecQuantityList.size()>0){
            for (int i = 0; i < elecQuantityList.size(); i++) {
                if(elecQuantityList.get(i).getDateStr().equals(DateUtils.date2Str(new Date(), "yyyy-MM"))){
                    continue;
                }

                //查询当月购电量、价差
                saleDo.setCollectDate(elecQuantityList.get(i).getDateStr());
                SaleElectricityDetailed saleElectricityDetailed = saleElectricityDao.findSaleElectricityDetailed(saleDo);
                if(saleElectricityDetailed !=null) {
                    //年购电量
                    buyElectricByYear += saleElectricityDetailed.getBiddingElctricity();

                    //如果用电量纠正不为0，那么就使用自己统计的用电量
                    if(saleElectricityDetailed.getElectricityConsum() != 0){
                        userElectricByYear += saleElectricityDetailed.getElectricityConsum();
                    }else{
                        userElectricByYear += elecQuantityList.get(i).getSumElectric();
                    }

                    //客户收益 =  用电量 * 价差 /1000 * 分成比例
                    //总收益
                    if (saleElectricity != null) {
                        double sumEarning = elecQuantityList.get(i).getSumElectric() * saleElectricityDetailed.getBiddingSpread() / 1000;
                        custIncomeByYear += sumEarning * saleElectricity.getDivideIntoUpMonth() / (saleElectricity.getDivideIntoUpMonth() + saleElectricity.getDivideIntoDownMonth());
                    }
                    //考核值 (公式：1-用电量/购电量），如超过2%，则减去允许偏差+-2%)
                    double assess = 0.0;
                    if(saleElectricityDetailed.getBiddingElctricity() !=0.0) {
                        assess = Math.abs(1 - elecQuantityList.get(i).getSumElectric() / saleElectricityDetailed.getBiddingElctricity());
                    }
                    assess = assess < 0.02 ? 0.0 : assess - 0.02;
                    assessRatio += assess;
                }
                count ++;
            }
        }
        //客户年收益信息封装到实体类中
        SaleElectricVo saleElectricVo = new SaleElectricVo();
        saleElectricVo.setUserElectric(CalculateUtils.roundNumber(userElectricByYear));
        saleElectricVo.setBuyElectric(CalculateUtils.roundNumber(buyElectricByYear));
        saleElectricVo.setCustIncome(CalculateUtils.get2PointValue(custIncomeByYear));
        saleElectricVo.setAsseOfValue(CalculateUtils.get2PointValue(assessRatio * 100 / count ));

        return saleElectricVo;
    }

    /*查询客户月电量销售额*/
    public List<SaleElectricityDetailed> findSaleAssessment(ParamDate paramDate){
        paramDate = energyService.convertParamDate(paramDate);
        return saleElectricityDao.findSaleAssessment(paramDate);
    }

    public SaleElectricityDetailed findSaleElectricityDetailed(SaleElectricityDo saleDo){
        return saleElectricityDao.findSaleElectricityDetailed(saleDo);
    }
}
