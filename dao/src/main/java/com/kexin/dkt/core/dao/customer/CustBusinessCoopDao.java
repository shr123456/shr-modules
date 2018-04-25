package com.kexin.dkt.core.dao.customer;

import com.kexin.dkt.core.entity.custcustomer.CustBusinessCoop;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.vo.PageBeanVo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;


/**
 * @auther shr
 * @date 2017/11/1
 * @time 11:41
 * @desc
 **/
@MapperScan
public interface CustBusinessCoopDao {

    public int saveBusiness(CustBusinessCoop custBusinessCoop);

    /*根据合作类型查询相应列表*/
    List<CustBusinessCoop> findCoopListByType(PageBeanVo pageBeanVo);

    /*查询某个合作类型详情*/
    CustBusinessCoop findCoopById(CustBusinessCoop custBusinessCoop );
}
