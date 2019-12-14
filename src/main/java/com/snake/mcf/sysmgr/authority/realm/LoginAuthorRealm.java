package com.snake.mcf.sysmgr.authority.realm;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.menu.filter.LayMenuFilter;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.sysmgr.authority.token.AuthorityUsernamePasswordToken;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.service.authority.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 登录认证器
 *
 * @ClassName: LoginAuthorRealm
 * @author: dashuai
 * @date: 2019/4/7 21:03
 * @Copyright: 2019 www.wondersgroup.com Inc. All rights reserved.
 * @matters: 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class LoginAuthorRealm extends AbstractLoginAuthorRealm {

    @Autowired
    private AuthorityService authorityService;

    /**
     * 登陆认证
     *
     * @param authenticationToken
     * @return
     */
    @Override
    public AuthenticationInfo builderAuthenticationInfo(AuthenticationToken authenticationToken) {
        log.info("{}","login authenticationInfo");
        AuthorityUsernamePasswordToken token = null;
        if(authenticationToken instanceof AuthorityUsernamePasswordToken){
            token = (AuthorityUsernamePasswordToken)authenticationToken;
            if(token == null){
                throw new ClassCastException("param [authenticationToken] is cast class exception");
            }
        }

        /*UsernamePasswordToken usernamePasswordToken = null;

        if(authenticationToken instanceof UsernamePasswordToken){
            usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        }*/

        //获取密文
        String username = token.getUsername();
        log.info("从token中获取用户名：{}",username);
        if(StringUtils.isBlank(username)) {
            throw new BusinessException("传入用户名为空", 2001);
        }

        String password = new String(token.getPassword());
        log.info("从token中获取明文密码：{}",password);
        if(StringUtils.isBlank(password)) {
            throw new BusinessException("传入密码为空", 2002);
        }

        //3 从数据库查询
        log.info("authorityService={}",authorityService);
        TbSysUserDTO sysUserDto = authorityService.querySysUserByLoginName(username);
        if(sysUserDto == null) {
            throw new AccountException("该账户不存在");
        }

        String pwd = MD5Utils.md5Digest(password);
        String loginPassword = sysUserDto.getLoginPassword();
        if(!loginPassword.equals(pwd)){
            throw new AccountException("密码错误");
        }

        //是否锁定
        if("1".equals(sysUserDto.getIsLocked())) {
            throw new DisabledAccountException("该账户已经被锁定");
        }

        //是否删除
        if("1".equals(sysUserDto.getIsDeleted())) {
            throw new DisabledAccountException("该账户已经被删除");
        }

        //根据userid查询角色 一个用户可以有多个角色
        List<TbSysRoleDTO> roleList = authorityService.queryUserRoleByUserId(sysUserDto.getId());
        sysUserDto.setRoleList(roleList);

        //设置用户权限
        int permission = authorityService.getPermissionByUserId(sysUserDto.getId());
        sysUserDto.setPermission(permission);

        //根据用户id查询菜单信息
        Map<String,Object> map = authorityService.queryUserMenuByUserId(sysUserDto.getId());
        sysUserDto.setMenuMap(map);

        //存入session中用户
        TbSysUserDTO sessionUser = new TbSysUserDTO();
        CommonBeans.copyPropertiesIgnoreNull(sysUserDto, sessionUser);
        sessionUser.setLoginPassword(null);
        Object principal = sessionUser;
        //加密后结果
        Object credentials = loginPassword;
        //realmName 当前 realm对象的name 调用父类的 getNmae 方法即可
        String realmName = this.getName();

        //定义返回值
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, credentials, realmName);

        authorityService.recordLog(token, sysUserDto, roleList);
        //已经登录成功 记录日志
        /*TbSysLogDTO redord = new TbSysLogDTO();
        //功能模块名称
        redord.setModule("登录功能");
        //account
        redord.setAccount(token.getUsername());
        //操作人iP
        redord.setIp(CusAccessObjectUtil.getIpAddress(token.getRequest()));
        //请求方式
        redord.setMethod(CusAccessObjectUtil.getMethod(token.getRequest()));
        //请求参数
        Map<String,Object> map1 = new HashMap<>();
        map1.put("loginName",token.getUsername());
        //map1.put("loginPassword",sysUserDto.getLoginPassword());
        redord.setParams(JsonUtils.toString(map1));
        //角色名称
        //List<TbSysRoleDTO> sessionRole = ShiroUtils.getSessionRole();
        //只取roleName
        List<String> roleName = authorityService.getRoleName(roleList);
        redord.setRoleName(JsonUtils.toString(roleName));
        //操作功能
        redord.setDescription(token.getUsername() + "登录成功");
        //商户号
        redord.setMerchant(sysUserDto.getMerchant());
        authorityService.saveSysLong(redord);*/

        return authenticationInfo;
    }

    /**
     * 登陆授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    public AuthorizationInfo builderAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("{}","authorize authorizationInfo");
        /*SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(null);
        info.setStringPermissions(null);*/

        //从principals获取主身份
        //将getPrimaryPrincipal方法返回值转为真实身份类型
        TbSysUser tbSysUser =  (TbSysUser) principalCollection.getPrimaryPrincipal();
        log.info("用户ID:{}", tbSysUser.getId());
        //根据用户id查询菜单信息
        List<LayMenuFilter> list = authorityService.queryUserPermissionByUserId(tbSysUser.getId());
        List<String> permissions = new ArrayList<>();
        for (LayMenuFilter layMenuFilter: list) {
            if (StringUtils.isNotBlank(layMenuFilter.getPermission())) {
                permissions.add(layMenuFilter.getPermission());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        info.addStringPermissions(permissions);
        return info;
    }

    public static void main(String[] args) {
        String admin = MD5Utils.md5Digest("admin");
        System.out.println(admin);
    }




}
