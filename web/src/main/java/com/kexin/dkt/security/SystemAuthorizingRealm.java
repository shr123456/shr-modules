package com.kexin.dkt.security;

import com.kexin.dkt.core.dao.system.UserDao;
import com.kexin.dkt.core.entity.security.SecPortalMenu;
import com.kexin.dkt.core.entity.security.SecRole;
import com.kexin.dkt.core.entity.security.SecUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @auther shr
 * @date 2017/10/20
 * @time 16:28
 * @desc
 **/
@Slf4j
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Set<SecRole> roles = userDao.findRoles(username);
        Set<String> roleNames = new HashSet<String>();
        for (SecRole role : roles) {
            roleNames.add(role.getRoleName());
        }
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
        Set<SecPortalMenu> secPortalMenus = userDao.findSecPortalMenu(username);
        Set<String> secPortalMenuNames = new HashSet<String>();
        for (SecPortalMenu secPortalMenu : secPortalMenus) {
            secPortalMenuNames.add(secPortalMenu.getMenuName());
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(secPortalMenuNames);
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            log.warn("用户名不能为空");
            throw new AccountException("用户名不能为空");
        }
        SecUser user = null;
        try {
            user =  userDao.getUser(username);
        } catch(Exception ex) {
            log.warn("获取用户失败\n" + ex.getMessage());
            throw new UnknownAccountException("用户名密码错误");
        }
        if(user == null || user.getPassword().equals(upToken.getPassword())){
            log.warn("用户名密码错误");
            throw new UnknownAccountException("用户名密码错误");
        }

        log.info("用户【" + username + "】登录成功");
        session.setAttribute("usernameAndPassword",user);
       SimpleAuthenticationInfo info =  new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(), ByteSource.Util.bytes(user.getDynaSalt()),username);

        return info;
    }
}
