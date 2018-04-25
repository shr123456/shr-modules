package com.kexin.dkt.core.dao.echarts;

import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.file.SysAccessory;
import com.kexin.dkt.core.entity.sale.CustSaleElectric;
import com.kexin.dkt.core.entity.sale.SaleAssessment;
import com.kexin.dkt.core.entity.sale.SaleElectricity;
import com.kexin.dkt.core.entity.sale.SaleElectricityDetailed;
import com.kexin.dkt.core.entity.vo.ParamDate;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 17:36
 * @desc
 **/
@MapperScan
public interface SaleElectricityDao {
    public List<SaleElectricityDo> getUseRanking(SaleElectricityDo saleElectricityDo);

    public SaleElectricityDo loadElectricByCustomer(SaleElectricityDo saleElectricityDo);

    public Long saveSaleElectric(CustSaleElectric custSaleElectric);

    /*查询客户月电量销售额*/
    List<SaleElectricityDetailed> findSaleAssessment(ParamDate paramDate);

    /*查询月度购电量与价差*/
    SaleElectricityDetailed findSaleElectricityDetailed(SaleElectricityDo saleElectricityDo);

    /*查询客户与可信分成比例*/
    SaleElectricity findSaleElectricity(SaleElectricityDo saleDo);

    /*查询月度购电量与价差集合*/
    List<SaleElectricityDetailed> findSaleElectricityDetailedList(SaleElectricityDo saleParam);
}
