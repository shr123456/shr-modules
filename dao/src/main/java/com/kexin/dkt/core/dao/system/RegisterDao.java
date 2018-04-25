package com.kexin.dkt.core.dao.system;

import com.kexin.dkt.core.entity.custcustomer.CustPotential;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecUser;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @auther shr
 * @date 2017/12/26
 * @time 17:18
 * @desc
 **/
@MapperScan
public interface RegisterDao {
    public SecUser loadSecUserByAccount(String account);
    public void saveSecUser(SecUser secUser);
    public void saveCustPotential(CustPotential custPotential);
    public void saveCustUser(CustUser custUser);
}
