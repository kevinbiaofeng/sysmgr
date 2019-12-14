package com.snake.mcf.sysmgr.controller.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.util.StringUtil;
import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.cache.redis.RedisConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.cache.RedisUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.enums.account.AccountInfoUnlockCodeEnum;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgrantgameidDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordtreasureserialDTO;
import com.snake.mcf.sysmgr.service.account.AccountUserService;
import com.snake.mcf.sysmgr.service.sys.SysMerchantService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AccountUserController
 * @Author 大帅
 * @Date 2019/6/24 12:30
 */
@Slf4j
@Controller
@RequestMapping(path = "/account/user")
public class AccountUserController extends BaseController {

    @Autowired
    private AccountUserService accountUserService;
    
    @Autowired
    private RedisUtils redisUtils;
	
    @Autowired
	private SysMerchantService sysMerchantService;
    
    /**
     * 分页查询用户信息
     *
     * @param infoDTO
     * @return
     */
    @GetMapping(path = "/queryAccountUserWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<AccountsInfoDTO>> queryAccountUserWithPage(EasyPageFilter pageFilter,AccountsInfoDTO infoDTO){
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(infoDTO));
        PageResult<AccountsInfoDTO> pageResult = accountUserService.queryAccountUserWithPage(pageFilter,infoDTO);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据userId查询账户
     *
     * @param infoDTO
     * @return
     */
    @GetMapping(path = "/queryAccountUserById")
    @ResponseBody
    public ResponseEntity<Result> queryAccountUserById(AccountsInfoDTO infoDTO){
        log.info("根据userId查询账户:{}",JsonUtils.toString(infoDTO));
        infoDTO = accountUserService.queryAccountUserById(infoDTO);
        
        StringBuilder key = new StringBuilder();
    	key.append(RedisConstant.UNLOCK_ACCOUNT_INFO_KEY).append(ShiroUtils.getSessionUserId());
    	
    	boolean unlockBool = redisUtils.hasKey(String.valueOf(key));
    	
    	Integer unlockCount = null;  //页面状态
    	if(!unlockBool) {
    		unlockCount = AccountInfoUnlockCodeEnum.INIT_PAGE.getCode();
    	}else {
    		unlockCount = new Integer(redisUtils.get(key.toString()).toString());
    	}
    	
        if(unlockCount.intValue() != AccountInfoUnlockCodeEnum.SUCCESS.getCode().intValue()) {   //密码错误，未解锁
        	/** 用户详情页**/
            String nickName = infoDTO.getNickName();
            if(StringUtils.isNotEmpty(nickName)) {
            	infoDTO.setNickName(StringUtils.replaceStar(nickName, null));
            }

            String accounts = infoDTO.getAccounts();
            if(StringUtils.isNotEmpty(accounts)) {
                infoDTO.setAccounts(StringUtils.replaceStar(accounts, null));
            }
            
            String account = infoDTO.getAccounts();
            if(StringUtils.isNotEmpty(account)) {
            	infoDTO.setAccounts(StringUtils.replaceStar(account, null));
            }
            
            String regAccount = infoDTO.getRegAccounts();
            if(StringUtils.isNotEmpty(regAccount)) {
            	infoDTO.setRegAccounts(StringUtils.replaceStar(regAccount, null));
            }
            
            String passportId = infoDTO.getPassPortId();
            if(StringUtils.isNotEmpty(passportId)) {
            	infoDTO.setPassPortId(StringUtils.replaceStar(passportId, null));
            }
            
            String compellation = infoDTO.getCompellation();
            if(StringUtils.isNotEmpty(compellation)) {
            	infoDTO.setCompellation(StringUtils.replaceStar(compellation, null));
            }
            
            String lastLogonMobile = infoDTO.getLastLogonMobile();
            if(StringUtils.isNotEmpty(lastLogonMobile)) {
            	infoDTO.setLastLogonMobile(StringUtils.replaceStar(lastLogonMobile, StringUtils.PHONE));
            }
            
            String registerMobile = infoDTO.getRegisterMobile();
            if(StringUtils.isNotEmpty(registerMobile)) {
            	infoDTO.setRegisterMobile(StringUtils.replaceStar(registerMobile, StringUtils.EMAIL));
            }
        }
        infoDTO.setUnlock(unlockCount);
        
        Result result = infoDTO == null ? Result.fail("根据id查询失败") : Result.succ(infoDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * 打开页面
     * @return
     */
    @GetMapping(path = "/toAccountLinePage")
    public String toAccountLinePage(Integer spreaderId, ModelMap modelMap){
        modelMap.put("spreaderId",spreaderId);
        return "account/user/account_line";
    }

    /**
     * 打开页面
     * @return
     */
    @GetMapping(path = "/toAccountEditPage")
    public String toAccountEditPage(Integer userId, ModelMap modelMap){
        modelMap.put("userId",userId);
        return "account/user/account_edit";
    }

    /**
     * 冻结解冻
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "操作用户冻结解冻" , description = "用户系统->账号管理->批量冻结解冻功能:nullity 状态值(0 正常 1 冻结)")
    @PutMapping(path = "/nullityUpate")
    @ResponseBody
    public ResponseEntity<Result> nullityUpate(AccountsInfoDTO infoDTO){
        log.info("操作用户冻结解冻:{}",JsonUtils.toString(infoDTO));
        boolean flag = accountUserService.nullityUpate(infoDTO);
        Result result = flag ? Result.succ() : Result.fail("操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量转账权限
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "操作用户批量转账权限" , description = "用户系统->账号管理->批量转账权限功能:设置批量转账权限 userRight | 64  , 取消批量转账权限 userRight ^ 64")
    @PutMapping(path = "/batchTransfer")
    @ResponseBody
    public ResponseEntity<Result>  batchTransfer(AccountsInfoDTO infoDTO){
        log.info("操作用户批量转账权限:{}",JsonUtils.toString(infoDTO));
        boolean flag = accountUserService.batchTransfer(infoDTO);
        Result result = flag ? Result.succ() : Result.fail("操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量转账权限
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "操作所有用户转账权限" , description = "用户系统->账号管理->所有用户转账权限功能:设置所有用户转账权限 userRight | 64  , 取消所有用户转账权限 userRight ^ 64")
    @PutMapping(path = "/batchTransferAll")
    @ResponseBody
    public ResponseEntity<Result>  batchTransferAll(AccountsInfoDTO infoDTO){
        log.info("操作所有用户转账权限:{}",JsonUtils.toString(infoDTO));
        boolean flag = accountUserService.batchTransferAll(infoDTO);
        Result result = flag ? Result.succ() : Result.fail("操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改账户信息
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "修改账户信息" , description = "用户系统->账号管理->修改用户账号信息功能:修改用户的银行密码,用户状态,真实姓名,身份证号,个性签名")
    @PutMapping(path = "/updateAccountUser")
    @ResponseBody
    public ResponseEntity<Result> updateAccountUser(AccountsInfoDTO infoDTO){
        log.info("操作所有用户转账权限:{}",JsonUtils.toString(infoDTO));
        boolean flag = accountUserService.updateAccountUser(infoDTO);
        Result result = flag ? Result.succ() : Result.fail("操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 查询金币列表
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRecordtreasureserialWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordtreasureserialDTO>> queryRecordtreasureserialWithPage(EasyPageFilter pageFilter,RecordtreasureserialDTO dto){
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordtreasureserialDTO> pageResult = accountUserService.queryRecordtreasureserialWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 查询人工手动存提流水
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryHandRecord")
    @ResponseBody
    public ResponseEntity<PageResult<RecordtreasureserialDTO>> queryHandRecord(EasyPageFilter pageFilter,RecordtreasureserialDTO dto){
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordtreasureserialDTO> pageResult = accountUserService.queryHandRecordWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 刷新靓号
     *
     * @return
     */
    @GetMapping(path = "/freshenGiveNum")
    @ResponseBody
    public ResponseEntity<Result> freshenGiveNum(){
        Integer num = accountUserService.freshenGiveNum();
        return ResponseEntity.ok(Result.succ(num));
    }

    /**
     * 赠送靓号
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "赠送靓号" , description = "用户系统->账号管理->赠送靓号功能:修改用户现有的游戏ID")
    @PutMapping(path = "/giveNumAccountUser")
    @ResponseBody
    public ResponseEntity<Result> giveNumAccountUser(AccountsInfoDTO infoDTO){
        try{
            accountUserService.giveNumAccountUser(infoDTO);
            Result result = Result.succ("赠送靓号成功") ;
            return ResponseEntity.ok(result);

        } catch (BusinessException e){
            log.error("赠送靓号失败：状态码{}----------错误信息：{}", e.getHttpStatusCode(), e.getMessage());
            Result r = Result.fail(e.getMessage());
            return ResponseEntity.ok(r);
        }

    }

    /**
     * 打开赠送记录表
     *
     * @param userid
     * @param modelMap
     * @return
     */
    @GetMapping(path = "/toGivenumRecordPage")
    public String toGivenumRecordPage(Integer userid, ModelMap modelMap){
        modelMap.put("userid",userid);
        return "account/user/account_givenum_record";
    }

    /**
     * 分页查询赠送记录
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGivenumRecordWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordgrantgameidDTO>> queryGivenumRecordWithPage(EasyPageFilter pageFilter,RecordgrantgameidDTO dto){
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordgrantgameidDTO> pageResult = accountUserService.queryGivenumRecordWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 赠送金币
     *
     * @param infoDTO
     * @return
     */
    @AopLog(module = "赠送金币" , description = "用户系统->账号管理->赠送金币功能:赠送用户金币")
    @PutMapping(path = "/giveGoldAccountUser")
    @ResponseBody
    public ResponseEntity<Result> giveGoldAccountUser(AccountsInfoDTO infoDTO){
        try {
            boolean flag = accountUserService.giveGoldAccountUser(infoDTO);
            Result result = flag ? Result.succ() : Result.fail("抱歉，赠送金币失败");
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            return ResponseEntity.ok(Result.fail(e.getMessage()));
        }

    }

    /**
     * 校验赠送的金币
     *
     * @param giveGold
     * @return
     */
    @GetMapping(path = "/validatorGiveGold")
    @ResponseBody
    public ResponseEntity<Result> validatorGiveGold(Long giveGold,Integer userId){
        log.info("校验赠送的金币:{}",giveGold);
        boolean flag = accountUserService.validatorGiveGold(giveGold,userId);
        return ResponseEntity.ok(Result.succ(Boolean.valueOf(flag)));
    }


    
    /**
     * 加密批量修改历史数据
     *
     * @param giveGold
     * @return
     */
    @GetMapping(path = "/updateall")
    @ResponseBody
    public ResponseEntity<Result> updateall(){
        accountUserService.updateall();
        return ResponseEntity.ok(Result.succ(true));
    }
    
    /**
     * 跳转至解锁页面
     * @param userId
     * @param pwd
     * @return
     */
//    @RequestMapping(path = "/skipUnlockAccountPage", method = {RequestMethod.GET})
//    @ResponseBody
//    public ResponseEntity<Result> skipUnlockAccountPage(@RequestParam Integer userId, @RequestParam String pwd){
//    	
//        return ResponseEntity.ok(result);
//    }
    
    /**
     * 解锁获取用户详细信息
     *
     * @param giveGold
     * @return
     */
    @RequestMapping(path = "/unlockAccountInfo", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Result> unlockAccountInfo(@RequestParam Integer userId, @RequestParam String pwd){
    	log.debug(userId + "=" + pwd);
    	Result result = null;
    	String merchant = ShiroUtils.getSessionMerchant();
    	StringBuilder key = new StringBuilder();
    	key.append(RedisConstant.UNLOCK_ACCOUNT_INFO_KEY).append(ShiroUtils.getSessionUserId());
    	Integer unlockStatus = null;
    	AccountsInfoDTO infoDTO = null;
		if(merchant != null && StringUtils.isNotEmpty(merchant)) {
			TbMerchant merchantDto = sysMerchantService.queryMerchantById(merchant);
			String safePassword = merchantDto.getSafePassword();
			
	    	boolean unlockBool = redisUtils.hasKey(String.valueOf(key));
			if(StringUtil.isNotEmpty(safePassword) && pwd.equals(safePassword)) {  //密码正确
				unlockStatus = AccountInfoUnlockCodeEnum.SUCCESS.getCode();
				redisUtils.set(String.valueOf(key), unlockStatus, RedisConstant.HOUR);	//code 99 正确解锁
				AccountsInfo accountInfo = accountUserService.queryAccountUserById(Integer.valueOf(userId));
            	if(accountInfo != null) {
            		infoDTO = new AccountsInfoDTO();
            		CommonBeans.copyPropertiesIgnoreNull(accountInfo, infoDTO);
            		result = Result.succ(infoDTO);
            	}else {
            		result = Result.fail("数据不存在");
            		result.setData(unlockStatus);
            	}
			}else { //密码错误
		    	if(unlockBool) { //判断该用户是否输入过密码
		    		Integer unlockCount = new Integer(redisUtils.get(key.toString()).toString());
		    		if(unlockCount < 3) {
		    			redisUtils.set(String.valueOf(key), ++unlockCount, RedisConstant.HOUR);
		    			result = Result.fail(AccountInfoUnlockCodeEnum.WRONG.getDesc());
		    			result.setData(unlockStatus);
		    			unlockStatus = AccountInfoUnlockCodeEnum.WRONG.getCode();
		        	}else{
		        		redisUtils.set(String.valueOf(key), AccountInfoUnlockCodeEnum.THREE_TIMES_WRONG.getCode(), RedisConstant.HOUR);
		        		result = Result.fail(AccountInfoUnlockCodeEnum.THREE_TIMES_WRONG.getDesc());
		        		result.setData(unlockStatus);
		        		unlockStatus = AccountInfoUnlockCodeEnum.THREE_TIMES_WRONG.getCode();
		        	}
		    	}else {
		    		result = Result.fail(AccountInfoUnlockCodeEnum.WRONG.getDesc());
		    		result.setData(unlockStatus);
		    		redisUtils.set(String.valueOf(key), 1, RedisConstant.HOUR);
		    		unlockStatus = AccountInfoUnlockCodeEnum.WRONG.getCode();
		    	}
			}
		}else {//管理员
			redisUtils.set(String.valueOf(key), AccountInfoUnlockCodeEnum.SUCCESS.getCode(), RedisConstant.HOUR);	//code 99 正确解锁
			AccountsInfo accountInfo = accountUserService.queryAccountUserById(Integer.valueOf(userId));
			unlockStatus = AccountInfoUnlockCodeEnum.SUCCESS.getCode();
        	if(accountInfo != null) {
        		infoDTO = new AccountsInfoDTO();
    			CommonBeans.copyPropertiesIgnoreNull(accountInfo, infoDTO);
    			infoDTO.setUnlock(unlockStatus);
        		result = Result.succ(infoDTO);
        	}else {
        		result = Result.fail("数据不存在");
        	}
		}
        return ResponseEntity.ok(result);
    }
}
