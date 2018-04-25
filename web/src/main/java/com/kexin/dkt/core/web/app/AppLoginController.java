package com.kexin.dkt.core.web.app;

import com.kexin.dkt.core.common.constant.ElecPrice;
import com.kexin.dkt.core.common.util.ValidationUtil;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.UserParam;
import com.kexin.dkt.core.service.customer.CustomerService;
import com.kexin.dkt.core.service.system.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by kexin on 2017/10/24.
 */
@Slf4j
@Controller
@RequestMapping("app/login")
public class AppLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("operationValidate")
    @ResponseBody
    public ActionResult appOperationLogin(@RequestBody SecUser user){
        ActionResult result = new ActionResult();
        String account = user.getAccount();
        String password = user.getPassword();
        if (account == null || password == null) {
            result.setSuccess(false);
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("用户名或密码不能为空");
            return result;
        } else {
            if ("".equals(account.trim())) {
                result.setSuccess(false);
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("用户名不能为空");
                return result;
            }
            if ("".equals(password.trim())) {
                result.setSuccess(false);
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("密码不能为空");
                return result;
            }
        }
        SecUser validateUser = loginService.getUser(account);
        if(validateUser ==null || validateUser.getDeptId().equals(ElecPrice.CUSTOMER_ROLE_ID)){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("用户不存在");
            result.setSuccess(false);
        }else {
            UserParam userParam = loginService.login(account,password);
            if(userParam !=null){
                //查询账户的客户信息
                List<CustCustomer> custList = customerService.findCustomerIdAndMeths(userParam.getUserId());
                if(!ValidationUtil.isEmpty(custList)){
                    result.setExtend(custList.get(0));
                }
                //查询账户的用户信息
                CustUser custUser = loginService.getCustUserById(userParam.getUserId());
                if(!ValidationUtil.isEmpty(custUser)){
                    result.setCustom(custUser);
                    userParam.setHeadImage(custUser.getHeadImage());
                }
                result.setContent(userParam);
                result.setSuccess(true);
            }else{
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("用户名或密码错误");
                result.setSuccess(false);
            }
        }
        return result;
    }

    @RequestMapping("validate")
    @ResponseBody
    public ActionResult appLogin(@RequestBody SecUser user){
        ActionResult result = new ActionResult();
        String account = user.getAccount();
        String password = user.getPassword();
        if (account == null || password == null) {
            result.setSuccess(false);
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("用户名或密码不能为空");
            return result;
        } else {
            if ("".equals(account.trim())) {
                result.setSuccess(false);
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("用户名不能为空");
                return result;
            }
            if ("".equals(password.trim())) {
                result.setSuccess(false);
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("密码不能为空");
                return result;
            }
        }
        SecUser validateUser = loginService.getUser(account);
        if(validateUser ==null || !validateUser.getDeptId().equals(ElecPrice.CUSTOMER_ROLE_ID)){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("用户不存在");
            result.setSuccess(false);
        }else {
            UserParam userParam = loginService.login(account,password);
            if(userParam !=null){
                //查询账户的客户信息
                List<CustCustomer> custList = customerService.findCustomerIdAndMeths(userParam.getUserId());
                if(!ValidationUtil.isEmpty(custList)){
                    result.setExtend(custList.get(0));
                }
                //查询账户的用户信息
                CustUser custUser = loginService.getCustUserById(userParam.getUserId());
                if(!ValidationUtil.isEmpty(custUser)){
                    result.setCustom(custUser);
                    userParam.setHeadImage(custUser.getHeadImage());
                }
                result.setContent(userParam);
                result.setSuccess(true);
            }else{
                result.setCode(ResultCode.ERROR.getCode());
                result.setMessage("用户名或密码错误");
                result.setSuccess(false);
            }
        }
        return result;
    }

    @RequestMapping("updateNickName")
    @ResponseBody
    public ActionResult appModifyNickName(@RequestBody SecUser user) throws Exception{
        ActionResult result = new ActionResult();
        String userId = user.getUserId();
        String password = user.getPassword();
        String userName = user.getUserName();
        if(null == userName){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("昵称不能为空");
            result.setSuccess(false);
            return result;
        }
        if(null == password){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("密码不能为空");
            result.setSuccess(false);
            return result;
        }
        SecUser secUser = loginService.getUserById(userId);
        if(null == secUser){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("修改昵称失败");
            result.setSuccess(false);
            return result;
        }
        if(secUser.getPassword().equals(password)){
            secUser.setUserName(userName);
            loginService.updateNickName(secUser);
            //查询账户的用户信息
            CustUser custUser = loginService.getCustUserById(secUser.getUserId());
            if(!ValidationUtil.isEmpty(custUser)){
                result.setCustom(custUser);
            }
            result.setContent(secUser);
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMessage("昵称修改成功");
//            result.setSuccess(false);
            return result;
        }else{
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("密码不正确");
            result.setSuccess(false);
            return result;
        }
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public ActionResult appModifyPassword(@RequestBody UserParam user) throws Exception{
        ActionResult result = new ActionResult();
        String userId = user.getUserId();
        String password = user.getPassword();
        String newPassword = user.getNewPassword();
        if(null == password){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("旧密码不能为空");
            result.setSuccess(false);
            return result;
        }
        if(null == newPassword){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("新密码不能为空");
            result.setSuccess(false);
            return result;
        }
        SecUser secUser = loginService.getUserById(userId);
        if(secUser.getPassword().equals(password)){
            secUser.setPassword(user.getNewPassword());
            loginService.updatePassworde(secUser);
            result.setContent(secUser);
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setMessage("密码修改成功");
//            result.setSuccess(false);
            return result;
        }else{
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("旧密码不正确");
            result.setSuccess(false);
            return result;
        }
    }

}
