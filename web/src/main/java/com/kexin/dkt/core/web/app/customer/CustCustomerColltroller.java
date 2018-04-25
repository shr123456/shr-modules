package com.kexin.dkt.core.web.app.customer;

import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 11:04
 * @desc
 **/
@Slf4j
@Controller
@RequestMapping("app/customer")
public class CustCustomerColltroller {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("getCustomerByUser")
    @ResponseBody
    public ActionResult getCustomerByUser(@RequestBody SecUser user){
        ActionResult actionResult = new ActionResult();
        List<CustCustomer> custList = customerService.findCustomerIdAndMeths(user.getUserId());
        if(null == custList || custList.isEmpty()){
            actionResult.setSuccess(false);
            actionResult.setContent(ResultCode.ERROR.getCode());
            actionResult.setMessage(ResultCode.ERROR.getMessage());
            return actionResult;
        }else{
            actionResult.setContent(custList.get(0));
            return actionResult;
        }
    }
}
