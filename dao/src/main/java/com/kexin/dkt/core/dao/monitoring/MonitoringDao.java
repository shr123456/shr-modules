package com.kexin.dkt.core.dao.monitoring;

import com.kexin.dkt.core.entity.collect.CollectBaseInfo;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import com.kexin.dkt.core.entity.vo.ParamDate;
import org.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/11/30.
 */
@MapperScan
public interface MonitoringDao {

    /*设备用电排及dtu与address*/
    List<Map<String, Object>> getEquipmentElectricRanking(ParamDate paramDate) ;

    /*查询设备的实时信息*/
    Map<String,Object> findEquipmentRuntime(ParamDate paramDate);

    /*查询dtu与地址在一天内的电量*/
    List<ElecQuantity> findEnergyRunTime(ParamDate paramDate);

    /*查询三相电压数据*/
    List<CollectBaseInfo> findVoltageRunTime(ParamDate paramDate);

    /*查询dtu与地址在一天内的三相电流*/
    List<CollectBaseInfo> findElectricityRunTime(ParamDate paramDate);

    /*查询dtu与地址在一天内的总功率因素*/
    List<CollectBaseInfo> findPowerFactorRunTime(ParamDate paramDate);

    /*查询每天的最大电压/最小电压/最大电流/最小功率因素*/
    Map<String,BigDecimal> findVoltageAndElec(ParamDate paramDate);
}
