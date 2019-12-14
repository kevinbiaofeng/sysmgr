package com.snake.mcf.sysmgr.controller.web.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;
import com.snake.mcf.sysmgr.repertory.entity.dto.MerchantDomainDTO;
import com.snake.mcf.sysmgr.service.sys.SysMerchantDomainService;

import lombok.extern.slf4j.Slf4j;

/**
 * 商户域名
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/merchant/domain")
public class SysMerchantDomainController {

	@Autowired
	private SysMerchantDomainService sysMerchantDomainService;
	
	
	/**
	 * 根据用户id 获取商户Domain列表
	 * @return
	 */
	@GetMapping(path = "/list")
	@ResponseBody
	public ResponseEntity<PageResult<MerchantDomainDTO>> domainList(EasyPageFilter pageFilter, String userId, String status, String type){
		log.info("根据userId查询商户Domain列表：id={}",userId);
		PageResult<MerchantDomainDTO> result = sysMerchantDomainService.queryPageListByExample(pageFilter, userId, status, type);
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 保存Domain信息
	 * @return
	 */
	@PostMapping(path = "/save")
	@ResponseBody
	public ResponseEntity<Result> save(TbMerchantDomain domain){
		log.info("新增Domain信息：{}", JsonUtils.toString(domain));
		boolean flag = sysMerchantDomainService.saveMerchantDomain(domain);
		Result result = flag ? Result.succ() : Result.fail("保存Domain信息失败");
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 根据Id删除Domain信息
	 * @return
	 */
	@PostMapping(path = "/deleteMerchantDomainById")
	@ResponseBody
	public ResponseEntity<Result> deleteMerchantDomainById(String id){
		log.info("删除Domain信息根据Id：{}", id);
		boolean flag = sysMerchantDomainService.deleteMerchantDomainById(id);
		Result result = flag ? Result.succ() : Result.fail("删除Domain信息失败");
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 根据Id查询详情
	 * @return
	 */
	@GetMapping(path = "/queryMerchantDomainById")
	@ResponseBody
	public ResponseEntity<Result> queryMerchantDomainById(String id){
		log.info("根据Id查询详情：{}", JsonUtils.toString(id));
		TbMerchantDomain merchantDomain = sysMerchantDomainService.getMerchantDomainById(id);
		Result result = null;
		if(merchantDomain != null) {
			result = Result.succ(merchantDomain);
		}else {
			result = Result.fail("根据id查询失败");
		}
		return ResponseEntity.ok(result);
	}
}
