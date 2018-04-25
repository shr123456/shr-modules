package com.kexin.dkt.core.web.system;

import com.kexin.dkt.core.dao.jdbc.helper.CodeHelper;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.custcustomer.CustPotential;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.RegisterVo;
import com.kexin.dkt.core.service.system.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

/**
 * @auther shr
 * @date 2017/12/26
 * @time 17:03
 * @desc
 **/
@Slf4j
@Controller
@RequestMapping
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @RequestMapping("app/register/addAccount")
    @ResponseBody
    public ActionResult addAccount(@RequestBody RegisterVo register){
        ActionResult result = new ActionResult();
        if(register.getAccount() == null){
            result.setMessage("请输入账号。");
            result.setSuccess(false);
        }else{
            if(registerService.isEmpty2Account(register.getAccount())){
                registerService.saveRegister(register);
                result.setMessage("新用户注册成功。");
                result.setSuccess(true);
            }else{
                result.setMessage("账号已经存在，请重新输入。");
                result.setSuccess(false);
            }
        }
        return result;
    }
}
