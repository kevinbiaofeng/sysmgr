package com.snake.mcf.sysmgr.controller.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.sysmgr.repertory.api.form.BankRecordForm;
import com.snake.mcf.sysmgr.repertory.api.form.BaseRequestForm;
import com.snake.mcf.sysmgr.repertory.api.form.GameIntroListForm;
import com.snake.mcf.sysmgr.repertory.api.form.GameListForm;
import com.snake.mcf.sysmgr.repertory.api.form.GroupBattleRecordForm;
import com.snake.mcf.sysmgr.repertory.api.form.HallBattleRecordForm;
import com.snake.mcf.sysmgr.repertory.api.form.MobileLoginDataForm;
import com.snake.mcf.sysmgr.repertory.api.form.MobileLoginLaterForm;
import com.snake.mcf.sysmgr.repertory.api.form.QuestionListForm;
import com.snake.mcf.sysmgr.repertory.api.form.RankingDataForm;
import com.snake.mcf.sysmgr.repertory.api.form.ReceiveRankingAwardForm;
import com.snake.mcf.sysmgr.repertory.api.form.RecordTreasureTradeForm;
import com.snake.mcf.sysmgr.repertory.api.form.TimesRewardForm;
import com.snake.mcf.sysmgr.repertory.api.form.UserFaceGetForm;
import com.snake.mcf.sysmgr.repertory.api.form.UserFaceListForm;
import com.snake.mcf.sysmgr.repertory.api.form.UserFaceSetForm;
import com.snake.mcf.sysmgr.repertory.api.form.UserWealthForm;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.UserLoginDataVO;
import com.snake.mcf.sysmgr.repertory.api.vo.loginlater.UserLoginDataLaterVO;
import com.snake.mcf.sysmgr.repertory.api.vo.user.UserInfoVO;
import com.snake.mcf.sysmgr.repertory.api.vo.wealth.UserWealthVO;
import com.snake.mcf.sysmgr.service.account.AccountUserService;
import com.snake.mcf.sysmgr.service.api.BankRecordService;
import com.snake.mcf.sysmgr.service.api.GameListService;
import com.snake.mcf.sysmgr.service.api.HallBattleRecordService;
import com.snake.mcf.sysmgr.service.api.MobileLoginDataService;
import com.snake.mcf.sysmgr.service.api.MobileLoginLaterService;
import com.snake.mcf.sysmgr.service.api.UserFaceService;
import com.snake.mcf.sysmgr.service.api.UserWealthService;
import com.snake.mcf.sysmgr.service.goldmgr.RecordTreasureSerialService;
import com.snake.mcf.sysmgr.service.website.WebsiteQuestionService;
import com.snake.mcf.sysmgr.service.website.WebsiteRuleService;

/**
 * 所有API接口放置在此，将来根据架构分散
 */
@RequestMapping(path = "/api")
@RestController(value = "remoteAPIController")
public class RemoteAPIController extends BaseController{
	
	@Autowired
    private MobileLoginDataService mobileLoginDataService;
	
	@Autowired
    private MobileLoginLaterService mobileLoginLaterService;
	
	@Autowired
    private AccountUserService accountUserService;
	
	@Autowired
    private GameListService gameListService;
	
	@Autowired
    private UserFaceService userFaceService;
	
	@Autowired
    private BankRecordService bankRecordService;
	
	@Autowired
    private UserWealthService userWealthService;
	
	@Autowired
    private HallBattleRecordService hallBattleRecordService;
	
	@Autowired
    private WebsiteQuestionService websiteQuestionService;
	
	@Autowired
    private RecordTreasureSerialService recordTreasureSerialService;
	
	@Autowired
    private WebsiteRuleService websiteRuleService;
	
	@Autowired
	private ConfigResource configResource;

	/**
	 * 客户端接口 用户登陆成功获取大厅相关信息
	 * @param dto
	 * @param result
	 * @return
	 */
    @RequestMapping(path = "/getMobileLoginData", method = RequestMethod.POST, produces = "application/json")
    public UserLoginDataVO getMobileLoginData(@Validated @RequestBody BaseRequestForm baseRequestForm) throws Exception{
    	MobileLoginDataForm from = GsonUtils.toBean(baseRequestForm.getData(), MobileLoginDataForm.class);
    	return mobileLoginDataService.getMobileLoginData(from.getPlatformType(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 客户端接口 用户登陆之后获取相关信息
	 * @param dto
	 * @param result
	 * @return
	 */
    @RequestMapping(path = "/getMobileLoginLater", method = RequestMethod.POST, produces = "application/json")
    public UserLoginDataLaterVO getMobileLoginLater(@Validated @RequestBody BaseRequestForm baseRequestForm){
    	MobileLoginLaterForm mobileLoginLaterForm = GsonUtils.toBean(baseRequestForm.getData(), MobileLoginLaterForm.class);
    	String localURL = this.getBasePath(request);
    	return mobileLoginLaterService.getMobileLoginLater(mobileLoginLaterForm, localURL, Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取用户信息接口
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getUserInfo", method = RequestMethod.POST, produces = "application/json")
    public UserInfoVO getUserInfo(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	MobileLoginLaterForm mobileLoginLaterForm = GsonUtils.toBean(baseRequestForm.getData(), MobileLoginLaterForm.class);
    	return accountUserService.queryAPIAccountUserById(Integer.valueOf(mobileLoginLaterForm.getUserId()), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 游戏列表接口
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getGameList", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getGameList(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	GameListForm gameListForm = GsonUtils.toBean(baseRequestForm.getData(), GameListForm.class);
    	return gameListService.getGameList(gameListForm.getType(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 设置用户头像
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/setUserFace", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> setUserFace(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	UserFaceSetForm userFaceSetForm = GsonUtils.toBean(baseRequestForm.getData(), UserFaceSetForm.class);
    	return userFaceService.setUserFaceByCustomId(userFaceSetForm.getUserId(), userFaceSetForm.getCustomId(), userFaceSetForm.getFaceUrl(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取用户设置头像
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getUserFace", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getUserFace(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	UserFaceGetForm userFaceGetForm = GsonUtils.toBean(baseRequestForm.getData(), UserFaceGetForm.class);
    	return userFaceService.getUserFaceByCustomId(userFaceGetForm.getUserId(), userFaceGetForm.getCustomId(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取头像列表
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getUserFaceList", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getUserFaceList(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	UserFaceListForm userFaceListForm = GsonUtils.toBean(baseRequestForm.getData(), UserFaceListForm.class);
    	return userFaceService.getUserFaceList(Integer.valueOf(userFaceListForm.getPage()), Integer.valueOf(userFaceListForm.getCount()), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取银行记录 黑金版
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getBankRecord", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getBankRecord(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	BankRecordForm bankRecordForm = GsonUtils.toBean(baseRequestForm.getData(), BankRecordForm.class);
    	return bankRecordService.getBankRecordByUserId(bankRecordForm, Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取玩家财富信息
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getUserWealth", method = RequestMethod.POST, produces = "application/json")
    public UserWealthVO getUserWealth(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	UserWealthForm userWealthForm = GsonUtils.toBean(baseRequestForm.getData(), UserWealthForm.class);
    	return userWealthService.getUserWealth(Long.valueOf(userWealthForm.getUserId()), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取金币场、约战战绩信息
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getHallBattleRecord", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getHallBattleRecord(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	HallBattleRecordForm hallBattleForm = GsonUtils.toBean(baseRequestForm.getData(), HallBattleRecordForm.class);
    	return hallBattleRecordService.getHallBattleRecord(Long.valueOf(hallBattleForm.getUserId()), Integer.valueOf(hallBattleForm.getType()), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取亲友圈战绩信息
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getGroupBattleRecord", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getGroupBattleRecord(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	GroupBattleRecordForm groupBattleForm = GsonUtils.toBean(baseRequestForm.getData(), GroupBattleRecordForm.class);
    	return hallBattleRecordService.getGourpBattleRecord(Long.valueOf(groupBattleForm.getUserId()), Integer.valueOf(groupBattleForm.getGroupId()), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 根据商户号获取常见问题
	 * @param dto
	 * @param result
	 * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
	 */
    @RequestMapping(path = "/getQuestionList", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getQuestionList(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	QuestionListForm questionListForm = GsonUtils.toBean(baseRequestForm.getData(), QuestionListForm.class);
    	return websiteQuestionService.queryQuestionList(questionListForm.getMerchant(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 账户流水
	 */
    @RequestMapping(path = "/getRecordTreasureTrade", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getRecordTreasureTrade(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	RecordTreasureTradeForm recordTreasureTradeForm = GsonUtils.toBean(baseRequestForm.getData(), RecordTreasureTradeForm.class);
    	Integer page = Integer.valueOf(recordTreasureTradeForm.getPage());
    	Integer rows = Integer.valueOf(recordTreasureTradeForm.getSize());
    	return recordTreasureSerialService.queryRecordTreasureSerialListByCondition(page, rows, recordTreasureTradeForm.getUserId(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取游戏玩法
	 */
    @RequestMapping(path = "/getGameIntroList", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getGameIntroList(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	GameIntroListForm gameIntroListForm = GsonUtils.toBean(baseRequestForm.getData(), GameIntroListForm.class);
    	return websiteRuleService.queryGameRuleList(gameIntroListForm.getUserId(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取排行榜
	 */
    @RequestMapping(path = "/getRankingData", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getRankingData(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	RankingDataForm rankingDataForm = GsonUtils.toBean(baseRequestForm.getData(), RankingDataForm.class);
    	return mobileLoginLaterService.getDayRankData(rankingDataForm.getUserId(), rankingDataForm.getTypeId(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 *  获取排行奖励  暂未实现，返回null
	 */
    @RequestMapping(path = "/receiveRankingAward", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> receiveRankingAward(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	ReceiveRankingAwardForm receiveRankingAwardForm = GsonUtils.toBean(baseRequestForm.getData(), ReceiveRankingAwardForm.class);
    	return mobileLoginLaterService.receiveRankingAward(receiveRankingAwardForm.getUserId(), receiveRankingAwardForm.getDateId(), receiveRankingAwardForm.getTypeId(), receiveRankingAwardForm.getIp(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 * 获取每日分享奖励
	 */
    @RequestMapping(path = "/getShareReward", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> getShareReward(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	GameIntroListForm gameIntroListForm = GsonUtils.toBean(baseRequestForm.getData(), GameIntroListForm.class);
    	return mobileLoginDataService.getShareReward(gameIntroListForm.getUserId(), Integer.valueOf(configResource.getApiVersion()));
    }
    
    /**
	 *  获取玩家分享奖励
	 */
    @RequestMapping(path = "/shareTimesReward", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> shareTimesReward(@Validated @RequestBody BaseRequestForm baseRequestForm) throws IllegalAccessException, InvocationTargetException{
    	TimesRewardForm timesRewardForm = GsonUtils.toBean(baseRequestForm.getData(), TimesRewardForm.class);
    	return mobileLoginDataService.getTimesReward(timesRewardForm.getUserId(), timesRewardForm.getStrClientIp(), Integer.valueOf(configResource.getApiVersion()));
    }
}
