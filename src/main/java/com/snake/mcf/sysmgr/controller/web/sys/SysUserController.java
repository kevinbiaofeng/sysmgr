package com.snake.mcf.sysmgr.controller.web.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserRoleDTO;
import com.snake.mcf.sysmgr.service.sys.SysUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统用户
 * 
 * @ClassName:  SysUserController   
 * @author: hengoking
 * @date:   2019年1月3日 下午6:59:16   
 *     
 * @Copyright: 2019 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 分页查询
	 *
	 * @author: hengoking
	 * @return
	 */
	@GetMapping(path = "/queryUserWithPage")
	@ResponseBody
	public ResponseEntity<PageResult<TbSysUserDTO>> queryUserWithPage(EasyPageFilter pageFilter , TbSysUserDTO sysUserDTO){
		log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(sysUserDTO));
		PageResult<TbSysUserDTO> pageResult = sysUserService.queryUserWithPage(pageFilter, sysUserDTO);
		return ResponseEntity.ok(pageResult);
	}

	/**
	 * 校验登录名
	 *
	 * @author: hengoking
	 * @param loginName
	 * @return
	 */
	@GetMapping(path = "/checkLoginName")
	@ResponseBody
	public ResponseEntity<Result> checkLoginName(String loginName){
		log.info("校验登录名：{}", loginName);
		boolean flag = sysUserService.checkLoginName(loginName);
		Result result = Result.succ(Boolean.valueOf(flag));
		return ResponseEntity.ok(result);
	}

	/**
	 * 保存系统用户
	 *
	 * @author: hengoking
	 * @param userDto
	 * @return
	 */
	@PostMapping(path = "/saveSysUser")
	@ResponseBody
	public ResponseEntity<Result> saveSysUser(TbSysUserDTO userDto){
		log.info("新增管理员信息：{}", JsonUtils.toString(userDto));
		//true 保存成功
		boolean flag = sysUserService.saveSysUser(userDto);
		Result result = flag ? Result.succ() : Result.fail("保存系统用户失败");
		return ResponseEntity.ok(result);
	}

	/**
	 * 根据Id查询系统用户
	 *
	 * @author: hengoking
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/querySysUserById")
	@ResponseBody
	public ResponseEntity<Result> querySysUserById(String id){
		log.info("根据id查询系统账户：id={}",id);
		TbSysUserDTO userDto = sysUserService.querySysUserById(id);
		Result result = userDto == null ? Result.fail("根据id查询失败") : Result.succ(userDto);
		return ResponseEntity.ok(result);
	}

	/**
	 * 修改系统用户密码
	 *
	 * @author: hengoking
	 * @param userDto
	 * @return
	 */
	@PutMapping(path = "/updateSysUser")
	@ResponseBody
	public ResponseEntity<Result> updateSysUser(TbSysUserDTO userDto){
		log.info("修改管理员信息：{}", JsonUtils.toString(userDto));
		boolean flag = sysUserService.updateSysUser(userDto);//true 修改成功
		Result result = flag ? Result.succ() : Result.fail("修改系统用户失败");
		return ResponseEntity.ok(result);
	}

	/**
	 * 修改状态
	 *
	 * @author: hengoking
	 * @param userDto
	 * @return
	 */
	@PostMapping(path = "/checkedStatus")
	@ResponseBody
	public ResponseEntity<Result> checkedStatus(TbSysUserDTO userDto){
		log.info("修改状态：{}", JsonUtils.toString(userDto));
		boolean flag = sysUserService.checkedStatus(userDto);
		Result result = Result.succ(Boolean.valueOf(flag));
		return ResponseEntity.ok(result);
	}

	/**
	 * 保存用户角色关联信息
	 *
	 * @param sysUserRoleDTO
	 * @return
	 */
	@PostMapping(path = "/saveSysUserRole")
	@ResponseBody
	public ResponseEntity<Result> saveSysUserRole(TbSysUserRoleDTO sysUserRoleDTO){
		log.info("用户角色关联信息：{}", JsonUtils.toString(sysUserRoleDTO));
		boolean flag = sysUserService.saveSysUserRole(sysUserRoleDTO);
		Result result = flag ? Result.succ() : Result.fail("保存用户角色关联信息失败");
		return ResponseEntity.ok(result);
	}

	/**
	 * 查询当前用户所属角色
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/queryCurrUserRole")
	@ResponseBody
	public ResponseEntity<List<TbSysRoleDTO>> queryCurrUserRole(String userId){
		log.info("查询当前用户所属角色：userId={}", userId);
		List<TbSysRoleDTO> list =  sysUserService.queryCurrUserRole(userId);
		return ResponseEntity.ok(list);
	}



	/**
	 * 查询当前用户所属角色
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/queryCurrUserById")
	@ResponseBody
	public ResponseEntity<TbSysUserDTO> queryCurrUserById(){
		String userId = ShiroUtils.getSessionUserId();
		log.info("查询当前用户信息：userId={}", userId);
		TbSysUserDTO dto =  sysUserService.querySysUserById(userId);
		return ResponseEntity.ok(dto);
	}

	
}
