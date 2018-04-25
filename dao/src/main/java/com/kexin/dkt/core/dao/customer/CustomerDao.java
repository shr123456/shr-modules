package com.kexin.dkt.core.dao.customer;

import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/27.
 */
@MapperScan
public interface CustomerDao {
    /**
     * 通过userId获取客户信息
     * @param userId
     * @return
     */
    List<CustCustomer> findCustomerIdAndMeth(String userId );

    /*通过客户获取客户信息*/
    List<CustCustomer> findMethByCustomerId(@Param("customerId") String customerId);

    /*查询客户列表*/
    List<Map<String,Object>> findCustomerList(Map<String,Object> paramMap);

}
