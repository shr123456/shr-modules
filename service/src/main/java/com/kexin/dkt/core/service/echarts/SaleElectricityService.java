package com.kexin.dkt.core.service.echarts;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.constant.ElecPrice;
import com.kexin.dkt.core.common.util.ValidationUtil;
import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.dao.echarts.SaleElectricityDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 17:35
 * @desc
 **/
@Service
public class SaleElectricityService {

    @Autowired
    private SaleElectricityDao saleElectricityDao;

    @Autowired
    private CustomerDao customerDao;

    /**
     * 获取客户设备用电排名
     * @param saleElectricityDo
     * @return
     */
    public List<SaleElectricityDo> getUseRanking(SaleElectricityDo saleElectricityDo){
        if(!ValidationUtil.isEmpty(saleElectricityDo.getCollectDate())){
            saleElectricityDo.setCollectDate(saleElectricityDo.getCollectDate() + "%");
        }
        if(ValidationUtil.isEmpty(saleElectricityDo.getGenre())){
            saleElectricityDo.setGenre(null);
        }

        if(!ValidationUtil.isEmpty(saleElectricityDo.getUserId()) && ValidationUtil.isEmpty(saleElectricityDo.getCustomerId())){
            List<CustCustomer> custList = customerDao.findCustomerIdAndMeth(saleElectricityDo.getUserId());
            if(!ValidationUtil.isEmpty(custList)){
                CustCustomer custCustomer = custList.get(0);
                saleElectricityDo.setCustomerId(custCustomer.getCustomerId());
            }
        }
        return saleElectricityDao.getUseRanking(saleElectricityDo);
    }

    /**
     * 按月获取客户总用电量
     * @param saleElectricityDo
     * @return
     */
    public SaleElectricityDo loadElectricByCustomer(SaleElectricityDo saleElectricityDo){
        if(!ValidationUtil.isEmpty(saleElectricityDo.getCollectDate())){
            saleElectricityDo.setCollectDate(saleElectricityDo.getCollectDate() + "%");
        }
        if(!ValidationUtil.isEmpty(saleElectricityDo.getUserId()) && ValidationUtil.isEmpty(saleElectricityDo.getCustomerId())){
            List<CustCustomer> custList = customerDao.findCustomerIdAndMeth(saleElectricityDo.getUserId());
            if(!ValidationUtil.isEmpty(custList)){
                CustCustomer custCustomer = custList.get(0);
                saleElectricityDo.setCustomerId(custCustomer.getCustomerId());
                //低压进线计量
                if(custCustomer.getMeasureMethods().equals(ElecPrice.METERING_MODE_LOW)){
                    saleElectricityDo.setGenre(1);
                }else if(custCustomer.getMeasureMethods().equals(ElecPrice.METERING_MODE_HIGH)){
                    //高压进线计量
                    saleElectricityDo.setGenre(0);
                }else{
                    //不计量，则使用低压进线计量  结果不作数
                    saleElectricityDo.setGenre(1);
                }
            }
        }
        return saleElectricityDao.loadElectricByCustomer(saleElectricityDo);
    }
}
