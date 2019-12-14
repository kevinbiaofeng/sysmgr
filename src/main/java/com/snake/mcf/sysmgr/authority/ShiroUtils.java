package com.snake.mcf.sysmgr.authority;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * shiro 工具类
 * 
 * @ClassName: ShiroUtils
 * @author: hengoking
 * @date: 2019年1月3日 上午10:41:59
 * 
 * @Copyright: 2019 www.payplatform.com Inc. All rights reserved. 注意：本内容仅限于
 *             内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class ShiroUtils {
	
	protected static final String ALGORITHM_NAME = "MD5";
	
	protected static final Integer HASH_ITERATIONS = 1024;
	
	public static final String DEFAULT_INDEX_URL = "/index";
	
	public static final String DEFAULT_LOGIN_URL = "/login";
	
	public static final String DEFAULT_REDIRECT_URL = "/redirect";

	/**
	 * @Description: 加盐
	 * @param password
	 * @return
	 * @throws @author
	 *             lao
	 * @Date 2017年12月26日上午9:59:53
	 * @version 1.00
	 */
	public static String getStrByMD5(String username , String password) {
		log.info("getStrByMD5: username={},password={}",username,password);
		ByteSource salt = getSalt(username);
		SimpleHash simpleHash = new SimpleHash(ALGORITHM_NAME, password,salt, HASH_ITERATIONS);
		log.info("simpleHash={}",simpleHash);
		String newPassword = simpleHash.toHex();
		return newPassword;
	}
	
	/**
	 * 获取盐值
	 * 
	 * @author: hengoking 
	 * @param username
	 * @return
	 */
	public static ByteSource getSalt(String username) {
		log.info("username={}",username);
		ByteSource salt = ByteSource.Util.bytes(username);
		log.info("salt={}",salt);
		return salt;
	}
	
	/**
	 * 获取盐值md5
	 * 
	 * @author: hengoking 
	 * @param username
	 * @return
	 */
	public static String getSaltByMd5(String username) {
		log.info("username={}",username);
		ByteSource salt = getSalt(username);
		byte[] bytes = salt.getBytes();
		log.info("bytes={},{}",bytes,bytes.length);
		return MD5Utils.md5Digest(bytes);
	}

	/**
	 * 密码加盐加密
	 * 
	 * @author: hengoking 
	 * @param password
	 * @param salt
	 * @return
	 * @throws Exception
	 */
	public static String getMD5Str(String password,String salt) throws Exception {
		return null;
		
	}

	/**
	 * @Description: 是否是Ajax请求
	 * @param request
	 * @return
	 * @throws @author
	 *             lao
	 * @Date 2018年1月15日下午4:45:31
	 * @version 1.00
	 */
	public static boolean isAjax(ServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}

	/**
	 * @Description: response 输出JSON
	 * @param response
	 * @param resultMap
	 * @throws IOException
	 * @author lao
	 * @Date 2018年1月15日下午4:46:16
	 * @version 1.00
	 */
	public static void out(ServletResponse response, Map<String, String> resultMap) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String jsonString = JsonUtils.toString(resultMap);
			out.print(jsonString);
		} catch (Exception e) {
			log.debug("{}", e);
			log.info("输出JSON报错!");
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 获取登录对象
	 * 
	 * @author: hengoking 
	 * @return
	 */
	public static TbSysUserDTO getSessionUser() {
		Subject subject = SecurityUtils.getSubject();
		boolean authenticated = subject.isAuthenticated();
		if(authenticated) {
			TbSysUserDTO sessionUser = (TbSysUserDTO) subject.getPrincipal();
			return sessionUser;
		}
		return null;
	}
	
	/**
	 * 获取登录用户名
	 * 
	 * @author: hengoking 
	 * @return
	 */
	public static String getSessionUserName() {
		TbSysUserDTO sessionUser = getSessionUser();
		if(sessionUser == null) {
			return PayplatformConstant.NULL_CHARACTER;
		}
		return sessionUser.getName();
	}

	public static String getSessionLoginName() {
		TbSysUserDTO sessionUser = getSessionUser();
		if(sessionUser == null) {
			return PayplatformConstant.NULL_CHARACTER;
		}
		return sessionUser.getLoginName();
	}
	
	/**
	 * 获取登录名ID
	 * 
	 * @author: hengoking 
	 * @return
	 */
	public static String getSessionUserId() {
		TbSysUserDTO sessionUser = getSessionUser();
		if(sessionUser == null) {
			return "1";
		}
		return String.valueOf(sessionUser.getId());
	}

	/**
	 * 当前账号是否是管理员
	 *
	 * @return true 是 false 不是
	 */
	public static boolean isAdminSessionUserId() {
		//账号是1 就是超级管理员
		if("1".equals(getSessionUserId())){
			return true;
		}
		List<TbSysRoleDTO> sessionRole = getSessionRole();
		if(CollectionUtils.isEmpty(sessionRole)){
			return false;
		}
        Set<String> codeSet = sessionRole.stream()
                .map(TbSysRoleDTO::getCode).collect(Collectors.toSet());
		//true 包含 false 不包含
		boolean contains = codeSet.contains("1");
		return contains;
	}

	/**
	 * 获取商户号
	 *
	 * @return
	 */
	public static String getSessionMerchant() {
		TbSysUserDTO sessionUser = getSessionUser();
		if(sessionUser == null) {
			return "1";
		}
		return sessionUser.getMerchant();
	}

	/**
	 * 获取当前用户所属角色
	 *
	 * @return
	 */
	public static List<TbSysRoleDTO> getSessionRole() {
		TbSysUserDTO sessionUser = getSessionUser();
		if(sessionUser == null) {
			List<TbSysRoleDTO> list = new ArrayList<>();
			TbSysRoleDTO role = new TbSysRoleDTO();
			role.setCode("1");
			list.add(role);
			return list;
		}
		return sessionUser.getRoleList();
	}

	public static int getPermission(){
		List<TbSysRoleDTO> roleList = getSessionRole();
		if(CollectionUtils.isEmpty(roleList)){
			return 0;
		}
		List<String> permList = roleList.stream().map(TbSysRoleDTO::getPermission).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(permList)){
			return 0;
		}
		String rest = String.join(",", permList);

		int count = 0;
		Set<String> set = Stream.of(rest.split(",")).collect(Collectors.toSet());
		/*set.stream().forEach((k)->{
			int authorityCode = AuthorityUtils.getAuthorityCode(k);
			count += authorityCode;
		});*/
		for (String k : set) {
			int authorityCode = AuthorityUtils.getAuthorityCode(k);
			count += authorityCode;
		}
		return count;
	}
	
	public static void main(String[] args) {
		log.info("{}",getSaltByMd5("admin"));
	}
	
	

}
