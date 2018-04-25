package com.kexin.dkt.core.web.system;

import com.github.pagehelper.PageInfo;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.UserParam;
import com.kexin.dkt.core.service.system.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @auther shr
 * @date 2017/10/24
 * @time 10:27
 * @desc 登录控制类
 **/
@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 返回页面视图
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return "login/index";
    }

    /**
     * 用户登陆
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("validate")
    @ResponseBody
    public ActionResult appLogin(String account, String password){
        ActionResult result = new ActionResult();
        //用户名密码校验
        if (account == null || password == null) {
            result.setSuccess(false);
            result.setMessage("用户名或密码不能为空");
            return result;
        } else {
            if ("".equals(account.trim())) {
                result.setSuccess(false);
                result.setMessage("用户名不能为空");
                return result;
            }
            if ("".equals(password.trim())) {
                result.setSuccess(false);
                result.setMessage("密码不能为空");
                return result;
            }
        }
        SecUser validateUser = loginService.getUser(account);
        if(validateUser ==null){
            result.setMessage("用户名不存在");
            result.setSuccess(false);
        }else {
            UserParam userParam = loginService.login(account,password);
            if(userParam !=null){
                result.setContent(userParam);
                result.setSuccess(true);
            }else{
                result.setMessage("用户名或密码错误");
                result.setSuccess(false);
            }
        }
        return result;
    }
}
