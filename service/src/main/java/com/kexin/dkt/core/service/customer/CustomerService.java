package com.kexin.dkt.core.service.customer;

import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/27.
 */
@Slf4j
@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 获取客户id和计量方式
     * @param userId
     * @return
     */
    public List<CustCustomer> findCustomerIdAndMeths(String userId ){
        List<CustCustomer> meterconfigList =customerDao.findCustomerIdAndMeth(userId);
        return meterconfigList;
    }

    public List<Map<String,Object>> findCustomerList(Map<String,Object> paramMap){
        List<Map<String,Object>> customerList = customerDao.findCustomerList(paramMap);
        if(customerList ==null){
            customerList = new ArrayList<Map<String, Object>>();
        }
        return customerList;
    }

    /**
     * 查询客户信息
     * @param customerId
     * @return
     */
    public CustCustomer findCustomer(String customerId) {
        List<CustCustomer>  custCustomerList = customerDao.findMethByCustomerId(customerId);
        if(custCustomerList !=null && custCustomerList.size()>0){
            return custCustomerList.get(0);
        }
        return null;
    }
}
