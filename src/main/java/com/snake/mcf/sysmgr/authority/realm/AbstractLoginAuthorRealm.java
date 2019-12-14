package com.snake.mcf.sysmgr.authority.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @ClassName: AbstractLoginAuthorRealm
 * @author: dashuai
 * @date: 2019/4/7 20:57
 * @Copyright: 2019 www.wondersgroup.com Inc. All rights reserved.
 * @matters: 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public abstract class AbstractLoginAuthorRealm extends AuthorizingRealm {

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo:{}","登录认证");
        return this.builderAuthenticationInfo(authenticationToken);
    }

    /**
     * 构建登录认证
     *
     * @param authenticationToken
     * @return
     */
    public abstract AuthenticationInfo builderAuthenticationInfo(AuthenticationToken authenticationToken);

    /**
     * 登陆授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo:{}","登陆授权");
        return this.builderAuthorizationInfo(principalCollection);
    }

    /**
     * 构建登陆授权
     *
     * @param principalCollection
     * @return
     */
    public abstract AuthorizationInfo builderAuthorizationInfo(PrincipalCollection principalCollection);

    /**
     * 清空当前用户权限信息
     *
     * @author: litiantong
     */
    public  void clearCachedAuthorizationInfo() {
        log.info("{}","clearCachedAuthorizationInfo");
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 指定principalCollection 清除
     *
     * @param principalCollection
     * @see AuthorizingRealm#clearCachedAuthorizationInfo(PrincipalCollection)
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("{}","clearCachedAuthorizationInfo");
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

}
