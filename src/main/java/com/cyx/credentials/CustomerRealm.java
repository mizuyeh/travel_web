package com.cyx.credentials;

import com.cyx.entity.User;
import com.cyx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @date 2021/3/3
 */
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("权限认证：" + username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(userService.findRoles(username));
        simpleAuthorizationInfo.setStringPermissions(userService.findPermissions(username));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("realm------------------------");
        UsernamePasswordToken myToken = (UsernamePasswordToken) authenticationToken;
        String username = myToken.getUsername();

        //根据用户名查询数据·库中的密码
        User user = userService.findByUsername(username);
        if (user != null) {
            //如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
            if(user.getLocked() == true) {
                throw new LockedAccountException("账户被锁定");
            }
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                    ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
        }
        return null;
    }
}