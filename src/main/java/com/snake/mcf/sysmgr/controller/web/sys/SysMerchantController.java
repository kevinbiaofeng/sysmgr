package com.snake.mcf.sysmgr.controller.web.sys;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.service.sys.SysMerchantService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 商户管理
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/merchant")
public class SysMerchantController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysMerchantService sysMerchantService;
	
	/**
	 * 根据Id查询商户信息
	 *
	 * @author: hengoking
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/queryMerchantById")
	@ResponseBody
	public ResponseEntity<Result> querySysUserById(String id){
		log.info("根据id查询系统账户：id={}",id);
		Map<String, Object> data = new HashMap<String, Object>();
		
		TbSysUserDTO userDto = sysUserService.querySysUserById(id);
		Result result = null;
		if(userDto != null) {
			data.put("user", userDto);
			String merchantId = userDto.getMerchant();
			if(merchantId != null && StringUtils.isNotEmpty(merchantId)) {
				TbMerchant merchant = sysMerchantService.queryMerchantById(userDto.getMerchant());
				data.put("merchant", merchant);
			}
			result = Result.succ(data);
		}else {
			result = Result.fail("根据id查询失败");
		}
		return ResponseEntity.ok(result);
	}
	
}
