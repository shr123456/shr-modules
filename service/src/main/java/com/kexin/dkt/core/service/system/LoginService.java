package com.kexin.dkt.core.service.system;

import com.kexin.dkt.core.dao.system.UserDao;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @auther shr
 * @date 2017/10/14
 * @time 15:33
 * @desc
 **/
@Slf4j
@Service
public class LoginService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private com.kexin.dkt.core.dao.jdbc.user.UserDao jdbcUserDao;

    @Value("${upload_hosts}")
    //访问路径前缀
    private String uploadHosts;

    /*通过账号查询账户*/
    public SecUser getUser(String account) {
        return userDao.getUser(account);
    }

    /*验证用户登陆*/
    public UserParam login(String account, String password) {
        return userDao.login(account,password);
    }

    public SecUser getUserById(String userId){
        return userDao.getUserById(userId);
    }

    public CustUser getCustUserById(String userId){
        CustUser custUser = userDao.getCustUserById(userId);
        if(custUser !=null){
            custUser.setHeadImage(uploadHosts+custUser.getHeadImage());
        }
        return custUser;
    }

    public void updateNickName(SecUser user) throws Exception{
//        userDao.updateNickNameToSec(user);
        userDao.updateNickNameToCust(user);
    }

    public void updatePassworde(SecUser user) throws Exception{
        userDao.updatePassword(user);
    }
}
