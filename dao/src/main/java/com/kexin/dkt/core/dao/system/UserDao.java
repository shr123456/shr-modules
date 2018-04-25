package com.kexin.dkt.core.dao.system;

import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.security.SecPortalMenu;
import com.kexin.dkt.core.entity.security.SecRole;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.UserParam;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Set;
/**
 * @auther shr
 * @date 2017/10/14
 * @time 14:44
 * @desc
 **/
/**
 * 这里的@MapperScan就是上面所讲的Mapper扫描器中所需要的配置，会自动生成代理对象。
 * 注意，接口中的方法名称要和对应的MyBatis映射文件中的语句的id值一样，因为生成的
 * 动态代理，会根据这个匹配相应的Sql语句执行。另外就是方法的参数和返回值也需要注
 * 意。接口中的方法如何定义，对应的MyBatis映射文件就应该进行相应的定义。
 * 最后，标注中的userDao是用来作为Spring的Bean的id(或name)进行使用的，方便我
 * 们在Service层进行注入使用。
 */
@MapperScan
public interface UserDao {
    //此处的方法名必须和mapper中的映射文件中的id同名
    //回去映射文件中通过com.kexin.dkt.mapper

    //查询账户
    SecUser getUser(String username);

    //查询根据账户Id查询账户
    SecUser getUserById(String userId);

    //查询根据账户Id查询用户
    CustUser getCustUserById(String userId);

    //查询用户对应的角色
    Set<SecRole> findRoles(String username);

    //查询用户对应的权限
    Set<SecPortalMenu> findSecPortalMenu(String username);

    //用户登陆
    UserParam login(@Param("account")String account, @Param("password")String password);

    void updateNickNameToSec(SecUser user);

    void updateNickNameToCust(SecUser user);

    void updatePassword(SecUser user);
}