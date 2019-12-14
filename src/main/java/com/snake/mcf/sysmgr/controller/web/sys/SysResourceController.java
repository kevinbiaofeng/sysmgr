package com.snake.mcf.sysmgr.controller.web.sys;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.ResourceGroup;
import com.snake.mcf.sysmgr.service.sys.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统资源管理
 *
 * @ClassName:  SysResourceController
 * @author: hengoking
 * @date:   2018年12月31日 下午3:38:26
 *
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved.
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/resource")
public class SysResourceController {

	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 查询树形grid
	 *
	 * @author: hengoking
	 * @return
	 */
	@GetMapping(path = "/queryResourceTree")
	@ResponseBody
	public ResponseEntity<List<ResourceGroup>> queryResourceTree(){
		log.info("{}","queryResourceTree");
		String sysUserId = ShiroUtils.getSessionUserId();//传入系统用户ID
		//根据用户id查询所有菜单
		List<ResourceGroup> list = sysResourceService.queryResourceTree(sysUserId);
		return ResponseEntity.ok(list);
	}

	/**
	 * 新增资源
	 *
	 * @author: hengoking
	 * @return
	 */
	@PostMapping(path = "/saveResource")
	@ResponseBody
	public ResponseEntity<Result> saveResource(TbSysResourceDTO resourceDto) {
		log.info("新增资源:{}", JsonUtils.toString(resourceDto));
		try {
			//执行新增方法
			boolean flage = sysResourceService.saveResource(resourceDto);
			Result result = flage ? Result.succ() : Result.fail("新建资源失败") ;
			return ResponseEntity.ok(result);
		} catch (BusinessException e) {
			return ResponseEntity.ok(Result.fail(e.getMessage()));
		}
	}

	/**
	 * 根据id查询资源
	 *
	 * @author: hengoking
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/querySysResourceById")
	@ResponseBody
	public ResponseEntity<Result> querySysResourceById(String id){
		log.info("根据id查询资源,id={}",id);
		TbSysResourceDTO resourceDto = sysResourceService.querySysResourceById(id);
		Result result = resourceDto == null ? Result.fail("查询失败") : Result.succ(resourceDto);
		return ResponseEntity.ok(result);
	}

	/**
	 * 删除资源
	 *
	 * @author: hengoking
	 * @param ids
	 * @return
	 */
	@DeleteMapping(path = "/deleteSysResource")
	@ResponseBody
	public ResponseEntity<Result> deleteSysResource(String[] ids){
		log.info("删除资源,object={}",JsonUtils.toString(ids));
		Integer count = sysResourceService.deleteSysResource(ids);
		Result result = count == 0 ? Result.fail("删除失败") : Result.succ();
		return ResponseEntity.ok(result);
	}

	/**
	 * 修改资源
	 *
	 * @author: hengoking
	 * @param resourceDto
	 * @return
	 */
	@PutMapping(path = "/updateResource")
	@ResponseBody
	public ResponseEntity<Result> updateResource(TbSysResourceDTO resourceDto){
		log.info("修改资源,object={}",JsonUtils.toString(resourceDto));
		try {
			boolean flage = sysResourceService.updateResource(resourceDto);
			Result result = flage ? Result.succ() : Result.fail("修改资源失败");
			return ResponseEntity.ok(result);
		} catch (BusinessException e) {
			return ResponseEntity.ok(Result.fail(e.getMessage()));
		}

	}





}
