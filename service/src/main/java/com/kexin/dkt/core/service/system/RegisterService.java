package com.kexin.dkt.core.service.system;

import com.kexin.dkt.core.common.util.ValidationUtil;
import com.kexin.dkt.core.dao.jdbc.helper.CodeHelper;
import com.kexin.dkt.core.dao.system.RegisterDao;
import com.kexin.dkt.core.dao.system.UserDao;
import com.kexin.dkt.core.entity.custcustomer.CustPotential;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.RegisterVo;
import com.kexin.dkt.core.entity.vo.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @auther shr
 * @date 2017/10/14
 * @time 15:33
 * @desc
 **/
@Slf4j
@Service
public class RegisterService {


    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private CodeHelper helper;
    public boolean isEmpty2Account(String account){
        SecUser user = registerDao.loadSecUserByAccount(account);
        if(ValidationUtil.isEmpty(user)){
            return true;
        }else{
            return false;
        }
    }

    public void saveRegister(RegisterVo register){
        //插入账号表

        SecUser secUser = new SecUser();
        String secUserId = UUID.randomUUID().toString();
        secUser.setUserId(secUserId);
        secUser.setUserName(register.getAccount());
        secUser.setMobile(register.getLinkTel());
        secUser.setAccount(register.getAccount());
        secUser.setPassword(register.getPassword());
        secUser.setAccType("2");
        secUser.setCreateTime(new Date());
        secUser.setDeleted(0);
        secUser.setEnabled(1);
        //默认添加一个部门
        secUser.setDeptId("666");
        registerDao.saveSecUser(secUser);

        //新增潜在客户
        CustPotential custPotential = new CustPotential();
        String customerId = UUID.randomUUID().toString();
        custPotential.setPotentialId(customerId);
        custPotential.setPotentialName(register.getCustomerName());
        custPotential.setPttentialSource("APP注册");
        custPotential.setPotentialCode(helper.getCommnCode("KH"));
        custPotential.setDeleted(0);
        custPotential.setPttentialTel(register.getAccount());
        registerDao.saveCustPotential(custPotential);

        //插入用户信息表
        CustUser custUser = new CustUser();
        custUser.setCustomerId(customerId);
        custUser.setUserId(UUID.randomUUID().toString());
        custUser.setUserCode(helper.getCommnCode("KHYH"));
        custUser.setUserName(register.getAccount());
        custUser.setNickname(register.getNickName());
        custUser.setUserTele(register.getAccount());
        custUser.setSecUserId(secUserId);
        custUser.setCreator("用户注册");
        custUser.setDeleted(0);
        registerDao.saveCustUser(custUser);
//        try{
//        }catch (Exception e){
//            throw new RuntimeException("回滚事务");
//        }
    }

}
