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
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.RoleResourceGroup;
import com.snake.mcf.sysmgr.service.sys.SysRoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 角色管理
 * 
 * @ClassName:  SysRoleController   
 * @author: hengoking
 * @date:   2019年1月5日 下午4:46:18   
 *     
 * @Copyright: 2019 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/role")
public class SysRoleController {
	
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 分页查询
	 *
	 * @author: hengoking
	 * @param pageFilter
	 * @param roleDto
	 * @return
	 */
	@GetMapping(path = "/querySysRoleWithPage")
	@ResponseBody
	public ResponseEntity<PageResult<TbSysRoleDTO>> querySysRoleWithPage(EasyPageFilter pageFilter, TbSysRoleDTO roleDto) {
		log.info("分页参数:{},查询参数:{}",JsonUtils.toString(pageFilter) , JsonUtils.toString(roleDto));
		PageResult<TbSysRoleDTO> pageResult = sysRoleService.querySysRoleWithPage(pageFilter,roleDto);
		return ResponseEntity.ok(pageResult);
	}

	/**
	 * 保存角色信息
	 *
	 * @author: hengoking
	 * @param roleDto
	 * @return
	 */
	@PostMapping(path = "/saveSysRole")
	@ResponseBody
	public ResponseEntity<Result> saveSysRole(TbSysRoleDTO roleDto){
		log.info("保存角色：{}", JsonUtils.toString(roleDto));
		boolean flag = sysRoleService.saveSysRole(roleDto);//true 保存成功
		Result result = flag ? Result.succ() : Result.fail("保存角色失败");
		return ResponseEntity.ok(result);
	}

	/**
	 * 校验code
	 *
	 * @author: hengoking
	 * @param code
	 * @return
	 */
	@GetMapping(path = "/checkRoleCode")
	@ResponseBody
	public ResponseEntity<Result> checkRoleCode(String code){
		log.info("校验code：{}", code);
		boolean flag = sysRoleService.checkRoleCode(code);
		Result result = Result.succ(Boolean.valueOf(flag));
		return ResponseEntity.ok(result);
	}

	/**
	 * 修改角色状态
	 *
	 * @author: hengoking
	 * @param roleDto
	 * @return
	 */
	@PostMapping(path = "/checkedStatus")
	@ResponseBody
	public ResponseEntity<Result> checkedStatus(TbSysRoleDTO roleDto) {
		log.info("修改状态：{}", JsonUtils.toString(roleDto));
		boolean flag = sysRoleService.checkedStatus(roleDto);
		Result result = Result.succ(Boolean.valueOf(flag));
		return ResponseEntity.ok(result);
	}
	/**
	 * 根据id查询
	 *
	 * @author: hengoking
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/querySysRoleById")
	@ResponseBody
	public ResponseEntity<Result> querySysRoleById(String id){
		log.info("根据id查询：id={}", id);
		TbSysRoleDTO roleDto = sysRoleService.querySysRoleById(id);
		Result result = roleDto == null ? Result.fail("根据id查询失败") : Result.succ(roleDto);
		return ResponseEntity.ok(result);
	}

	/**
	 * 修改将角色
	 *
	 * @author: hengoking
	 * @param roleDto
	 * @return
	 */
	@PutMapping(path = "/updateSysRole")
	@ResponseBody
	public ResponseEntity<Result> updateSysRole(TbSysRoleDTO roleDto){
		log.info("修改角色：{}", JsonUtils.toString(roleDto));
		boolean flag = sysRoleService.updateSysRole(roleDto);//true 修改成功
		Result result = flag ? Result.succ() : Result.fail("修改角色失败");
		return ResponseEntity.ok(result);
	}

	/**
	 * 查询所有的角色信息
	 *
	 * @param roleDto
	 * @return
	 */
	@GetMapping(path = "/queryRoleDataList")
	@ResponseBody
	public ResponseEntity<List<TbSysRoleDTO>> queryRoleDataList(TbSysRoleDTO roleDto){
		log.info("查询角色角色：{}", JsonUtils.toString(roleDto));
		List<TbSysRoleDTO> list = sysRoleService.queryRoleDataList(roleDto);
		return ResponseEntity.ok(list);
	}

	/**
	 * 查询树形菜单信息
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/querySysRoleResource")
	@ResponseBody
	public ResponseEntity<List<RoleResourceGroup>> querySysRoleResource(String id){
		log.info("查询角色角色：{}", id);
		List<RoleResourceGroup> list = sysRoleService.querySysRoleResource(id);
		return ResponseEntity.ok(list);
	}

	/**
	 * 保存角色和资源关联信息
	 *
	 * @param roleResourceDTO
	 * @return
	 */
	@PostMapping(path = "/saveSysRoleResource")
	@ResponseBody
	public ResponseEntity<Result> saveSysRoleResource(TbSysRoleResourceDTO roleResourceDTO){
		log.info("保存角色和资源关联信息：{}", JsonUtils.toString(roleResourceDTO));
		boolean flag = sysRoleService.saveSysRoleResource(roleResourceDTO);
		Result result = flag ? Result.succ() : Result.fail("保存角色和资源关联信息失败");
		return ResponseEntity.ok(result);
	}




}
