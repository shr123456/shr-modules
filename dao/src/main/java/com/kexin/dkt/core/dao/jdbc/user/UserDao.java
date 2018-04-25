package com.kexin.dkt.core.dao.jdbc.user;

import com.kexin.dkt.core.entity.security.SecUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @auther shr
 * @date 2017/10/26
 * @time 19:01
 * @desc
 **/
@Slf4j
@Component("jdbcUserDao")
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SecUser getUser(){
        SecUser user = new SecUser();

        String sql = "select * from sec_user where user_id in ('05f853d8-50d4-4cee-b945-df91e920d9e6')";
        user = jdbcTemplate.queryForObject(sql, new SecUser());
        return user;
    }
}
