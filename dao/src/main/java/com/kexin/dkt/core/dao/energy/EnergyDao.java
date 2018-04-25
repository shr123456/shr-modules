package com.kexin.dkt.core.dao.energy;

import com.kexin.dkt.core.entity.echarts.EchartsSeries;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;
import java.util.List;

/**
 * Created by kexin on 2017/10/24.
 */
@MapperScan
public interface EnergyDao {

    /*通过用户名获取当前监控设备数量*/
    Integer findEquipmentCount(String customerId) ;

    /*获取公司名称*/
    String findCustomerName(String customerId );

    /*获取当天电量*/
    ElecQuantity findEnergyByDay(ParamDate paramDate);

    /*获取当月的峰谷平电量*/
    ElecQuantity findAllEnergyByMonth(ParamDate paramDate);

    /*获取一个客户一月中每天的峰谷平*/
    List<ElecQuantity> findEnergyByDayList(ParamDate paramDate);

    /*获取月度最大负载*/
    Double getMaxLoad(ParamDate paramDate);

    /*获取变压器容量*/
    Integer getTransCapacity(ParamDate paramDate);

    /*获取月度最小负载*/
    Double getMinLoad(ParamDate paramDate);

    /*获取月度平均负载*/
    Double getAvgLoad(ParamDate paramDate);

    /*获取每月中每天最大小时用电量的集合*/
    List<ElecQuantity> findMaxEnergyByDayList(ParamDate paramDate);

    /*获取当前月的时间数组*/
    List<ElecQuantity> getMonthTimes(ParamDate paramDate);

    /*查询往前月的总电量*/
    ElecQuantity findEnergyByBeforeMonth(ParamDate paramDate);

    /*获取年峰谷平用电量*/
    List<ElecQuantity> findEnergyByYear(SaleElectricityDo saleElectricityDo);

    /*用小时表查询每天的用电量*/
    List<ElecQuantity> findElectricByDay(ParamDate paramDate);
}
