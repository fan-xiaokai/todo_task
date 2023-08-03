package com.todo.task.shiro;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.todo.task.web.userManager.vo.UserVo;
import com.todo.task.web.userManager.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

/**
 * @Author xtf
 * @Date 06/06/2020 15:29
 * @Description shiro认证配置
 * @Version 1.0
 */
public class CustomRealm extends AuthorizingRealm {

    public static final String SHIRO_PREFIX =  "shiro:session:";

    @Autowired
    private UserService userService;

    /**
     * 建议重写此方法，提供唯一的缓存Key
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return this.getAuthenticationCacheKey(principals);
    }

    /**
     * 建议重写此方法，提供唯一的缓存Key
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        StringBuilder sb = new StringBuilder();
        principals.forEach(principal -> {
            sb.append(((UserVo) principal).getId());
        });

        return sb.toString();
    }

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        UserVo user = (UserVo) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        HashSet<String> stringHashSet = new HashSet<>();
//        if (user.getPcPerms() != null && user.getPcPerms().size() > 0) {
//            stringHashSet.addAll(user.getPcPerms());
//        }
//        if (user.getAppPerms() != null && user.getAppPerms().size() > 0) {
//            stringHashSet.addAll(user.getAppPerms());
//        }
//
//        info.setRoles(user.getRoles());
//        user.setPerms(stringHashSet);
        info.setStringPermissions(stringHashSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String account = upToken.getUsername();

        UserVo userVo = userService.getUserInfoByAccount(account);

        if (userVo == null){
            throw new ApiException("当前账号不存在!");
        }
        userVo.setToken(userVo.getId());
        //还需要将权限设置进去
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userVo, userVo.getPassword(), getName());

        // 加盐
        if (userVo.getSalt() != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(userVo.getSalt()));
        }
        return info;
    }
}
