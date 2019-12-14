package com.snake.mcf.sysmgr.service.account.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.utils.security.CBm53AES;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.enums.cash.TreasureTypeEnum;
import com.snake.mcf.sysmgr.mapper.account.AccountUserMapper;
import com.snake.mcf.sysmgr.mapper.invest.InvestRecordtreasureserialMapper;
import com.snake.mcf.sysmgr.repertory.api.vo.user.UserInfoVO;
import com.snake.mcf.sysmgr.repertory.entity.*;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgrantgameidDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordtreasureserialDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.mapper.*;
import com.snake.mcf.sysmgr.service.account.AccountUserService;
import com.snake.mcf.sysmgr.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName AccountUserServiceImpl
 * @Author 大帅
 * @Date 2019/6/24 12:31
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AccountUserServiceImpl extends BaseServiceImpl implements AccountUserService {

    @Autowired
    private AccountsInfoMapper accountsInfoMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private GameScoreInfoMapper gameScoreInfoMapper;

    @Autowired
    private AccountUserMapper accountUserMapper;

    @Autowired
    private AccountsfaceMapper accountsfaceMapper;

    @Autowired
    private ConfiginfoMapper configinfoMapper;

    @Autowired
    private RecordtreasureserialMapper recordtreasureserialMapper;

    @Autowired
    private InvestRecordtreasureserialMapper investRecordtreasureserialMapper;

    @Autowired
    private RecordgrantgameidMapper recordgrantgameidMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RecordgranttreasureMapper recordgranttreasureMapper;

    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<AccountsInfoDTO> queryAccountUserWithPage(EasyPageFilter pageFilter, AccountsInfoDTO infoDTO) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(infoDTO));
        PageResult<AccountsInfoDTO> pageResult = this.queryAccountUserWithPage1(pageFilter,infoDTO);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        //数据转换
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<AccountsInfoDTO> result = (EasyPageResult<AccountsInfoDTO>) pageResult;
            List<AccountsInfoDTO> rows = result.getRows();
            rows.forEach(suDto -> {
                //Gender 保密为0，男为1，女为2
                Integer gender = suDto.getGender();
                if(gender != null){
                    String genderDesc = DictionaryConstant.getGenderDesc(gender);
                    suDto.setGenderDesc(genderDesc);
                }

                //注册来源
                /*
                RegisterOrigin
                0：PC
                1：模拟器
                17：Android
                32：iTouch
                50：iPhone
                66：iPad
                80：web
                 */
                Integer registerOrigin = suDto.getRegisterOrigin();
                if(registerOrigin != null){
                    String registerOriginDesc = DictionaryConstant.getRegisterOriginDesc(registerOrigin);
                    suDto.setRegisterOriginDesc(registerOriginDesc);
                }

                // nullity 账号禁用标识
                Integer nullity = suDto.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    suDto.setNullityDesc(nullityDesc);
                }

                //携带金币
                Long score = suDto.getScore();
                //携带金币除以100
                suDto.setScoreDouble(GoldUtils.reduce(score.longValue()));

                //保险柜金币
                Long insurescore = suDto.getInsurescore();
                //保险柜金币 除以 100
                suDto.setInsurescoreDouble(GoldUtils.reduce(insurescore));

                //服务费
                Long revenue = suDto.getRevenue();
                //服务费 除以 100
                suDto.setRevenueDouble(GoldUtils.reduce(revenue));

                String nickName = suDto.getNickName();
                if(StringUtils.isNotEmpty(nickName)) {
                	suDto.setNickName(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }

                String account = suDto.getAccounts();
                if(StringUtils.isNotEmpty(account)) {
                	suDto.setAccounts(StringUtils.replaceStar(AESCBCUtils.decrypt(account, configResource.getAesUserKey()), null));
                }
            });
        }
        return pageResult;
    }

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param infoDTO
     * @return
     */
    private PageResult<AccountsInfoDTO> queryAccountUserWithPage1(EasyPageFilter pageFilter, AccountsInfoDTO infoDTO) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        //Example example = new Example(AccountsInfo.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(infoDTO != null){

            //昵称
            String nickName = infoDTO.getNickName();
            if(StringUtils.isNotBlank(nickName)) {
                map.put("nickName", AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
            }

            //账号
            String accounts = infoDTO.getAccounts();
            if(StringUtils.isNotBlank(accounts)) {
                map.put("accounts", AESCBCUtils.encrypt(accounts, configResource.getAesUserKey()));
            }

            //游戏ID
            Integer gameId = infoDTO.getGameId();
            if(gameId != null){
                map.put("gameId",gameId);
            }

            //是否启用
            Integer nullity = infoDTO.getNullity();
            if(nullity != null){
                map.put("nullity",nullity);
            }

            //代理ID
            Integer agentId = infoDTO.getAgentId();
            if(agentId != null){
                map.put("agentId",agentId);
            }

            //推广ID
            Integer spreaderId = infoDTO.getSpreaderId();
            if(spreaderId != null){
                map.put("spreaderId",spreaderId);
            }

            //携带金币
            Long scoreStart = infoDTO.getScoreStart();
            if(scoreStart != null){
                map.put("scoreStart",GoldUtils.boots(scoreStart.longValue()));
            }
            Long scoreEnd = infoDTO.getScoreEnd();
            if(scoreEnd != null){
                map.put("scoreEnd",GoldUtils.boots(scoreEnd.longValue()));
            }

            //保险柜金币
            Long insurescoreStart = infoDTO.getInsurescoreStart();
            if(insurescoreStart != null){
                map.put("insurescoreStart",GoldUtils.boots(insurescoreStart.longValue()));
            }
            Long insurescoreEnd = infoDTO.getInsurescoreEnd();
            if(insurescoreEnd != null){
                map.put("insurescoreEnd",GoldUtils.boots(insurescoreEnd.longValue()));
            }

            //服务费
            Long revenueStart = infoDTO.getRevenueStart();
            if(revenueStart != null){
                map.put("revenueStart",GoldUtils.boots(revenueStart.longValue()));
            }
            Long revenueEnd = infoDTO.getRevenueEnd();
            if(revenueStart != null){
                map.put("revenueEnd",GoldUtils.boots(revenueEnd.longValue()));
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        /*if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }*/
        //查询
        //List<AccountsInfo> accountsInfos = accountsInfoMapper.selectByExample(example);
        List<AccountsInfoDTO> list = accountUserMapper.queryAccountUserWithPage(map);
        //结果
        PageResult<AccountsInfoDTO> pageResult = easyPageQuery.queryResult(list, AccountsInfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }




    @Override
    public boolean nullityUpate(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("冻结解冻,传入对象为空", 1004);
        }
        if(infoDTO.getNullity() == null) {
            throw new BusinessException("冻结解冻,传入对象Nullity为空", 1004);
        }
        String userIds = infoDTO.getUserIds();
        if(StringUtils.isBlank(userIds)) {
            throw new BusinessException("冻结解冻,传入对象userIds为空", 1004);
        }
        int count = 0;
        String[] userIdArr = userIds.split(",");
        for (String userId:userIdArr) {
            AccountsInfo record = new AccountsInfo();
            record.setUserId(Integer.valueOf(userId));
            record.setNullity(infoDTO.getNullity());
            accountsInfoMapper.updateByPrimaryKeySelective(record);
            count++;
        }
        return count > 0;
    }

    @Override
    public boolean batchTransfer(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("转账,传入对象为空", 1004);
        }
        Integer transfer = infoDTO.getTransfer();
        if(transfer == null) {
            throw new BusinessException("转账,传入对象 transfer 为空", 1004);
        }
        String userIds = infoDTO.getUserIds();
        if(StringUtils.isBlank(userIds)) {
            throw new BusinessException("转账,传入对象 userIds 为空", 1004);
        }
        String[] arr = userIds.split(",");
        List<String> list = Arrays.asList(arr);
        Map<String,Object> map = new HashMap<>();
        map.put("userIdList",list);
        map.put("userRight",64);
       // int count = 0;
        // 0 设置转账标识  1 取消转账标识
        if(transfer == 0){
            for (String userId : list) {
                AccountsInfo accountsInfo = accountsInfoMapper.selectByPrimaryKey(Integer.valueOf(userId));
                if ((accountsInfo.getUserRight() & 64) != 64) {
                    throw new BusinessException("账号" + accountsInfo.getAccounts() + "的转账权限已开启！");
                }
            }
            map.put("calc","^");
        }else{
            for (String userId : list) {
                AccountsInfo accountsInfo = accountsInfoMapper.selectByPrimaryKey(Integer.valueOf(userId));
                if ((accountsInfo.getUserRight() & 64) == 64) {
                    throw new BusinessException("账号" + accountsInfo.getAccounts() + "的转账权限已禁用！");
                }
            }
            map.put("calc","|");
        }
        int count = accountUserMapper.batchTransfer(map);
        return count > 0 ;
    }

    @Override
    public boolean batchTransferAll(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("转账,传入对象为空", 1004);
        }
        Integer transfer = infoDTO.getTransfer();
        if(transfer == null) {
            throw new BusinessException("转账,传入对象 transfer 为空", 1004);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userRight",64);
        // int count = 0;
        // 0 设置转账标识  1 取消转账标识
        if(transfer == 0){
            //count = accountUserMapper.batchSetTransfer(map);
            map.put("calc","^");
        }else{
            //count = accountUserMapper.batchCancleTransfer(map);
            map.put("calc","|");
        }
        int count = accountUserMapper.batchTransferAll(map);
        return count > 0 ;
    }

    @Override
    public AccountsInfo queryAccountUserById(Integer userId) {
    	AccountsInfo accountInfo = accountsInfoMapper.selectByPrimaryKey(userId);
    	this.decryptAccountInfo(accountInfo);
    	return accountInfo;
    }

    @Override
    public AccountsInfoDTO queryAccountUserById(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("查询账户,传入对象为空", 1004);
        }
        Integer userId = infoDTO.getUserId();
        if(userId == null) {
            throw new BusinessException("查询账户,传入对象 userId 为空", 1004);
        }
        AccountsInfo accountsInfo = accountsInfoMapper.selectByPrimaryKey(userId);
        CommonBeans.copyPropertiesIgnoreNull(accountsInfo,infoDTO);

        Date lastLogonDate = infoDTO.getLastLogonDate();
        if(lastLogonDate != null){
            String lastLogonDateStr = DateUtils.format(lastLogonDate, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
            infoDTO.setLastLogonDateStr(lastLogonDateStr);
        }

        Date registerDate = infoDTO.getRegisterDate();
        if(registerDate != null){
            String registerDateStr = DateUtils.format(registerDate, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
            infoDTO.setRegisterDateStr(registerDateStr);
        }


        //Gender 保密为0，男为1，女为2
        Integer gender = infoDTO.getGender();
        String genderDesc = DictionaryConstant.getGenderDesc(gender);
        infoDTO.setGenderDesc(genderDesc);

        //注册来源
                /*
                RegisterOrigin
                0：PC
                1：模拟器
                17：Android
                32：iTouch
                50：iPhone
                66：iPad
                80：web
                 */
        Integer registerOrigin = infoDTO.getRegisterOrigin();
        String registerOriginDesc = DictionaryConstant.getRegisterOriginDesc(registerOrigin);
        infoDTO.setRegisterOriginDesc(registerOriginDesc);

        // nullity 账号禁用标识
        Integer nullity = infoDTO.getNullity();
        String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
        infoDTO.setNullityDesc(nullityDesc);

        //model.AgentID > 0 ? "代理" : "非代理");
//        if(infoDTO.getAgentId() > 0){
//            infoDTO.setAgentStatus("代理");
//        }else{
//            infoDTO.setAgentStatus("非代理");
//        }
        this.decryptDTO(infoDTO); //解密

        //查询头像
        Integer customId = infoDTO.getCustomId();
        if(customId != null){
            Accountsface accountsface = accountsfaceMapper.selectByPrimaryKey(customId);
            if(accountsface != null){
                String faceurl = accountsface.getFaceurl();
                infoDTO.setImgFaceSrc(faceurl);
            }else{
                String defaultImgFace = getDefaultImgFace();
                infoDTO.setImgFaceSrc(defaultImgFace);
            }
        }else{
            String defaultImgFace = getDefaultImgFace();
            infoDTO.setImgFaceSrc(defaultImgFace);
        }
        return infoDTO;
    }

    /**
     * 获取默认头像
     *
     * @return
     */
    private String getDefaultImgFace(){
        Configinfo configinfo = configinfoMapper.selectByPrimaryKey(3);
        String field2 = configinfo.getField2();
        String src = field2 + "/image/custom.png";
        return src;
    }

    @Override
    public boolean updateAccountUser(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("查询账户,传入对象为空", 1004);
        }
        Integer userId = infoDTO.getUserId();
        if(userId == null) {
            throw new BusinessException("查询账户,传入对象 userId 为空", 1004);
        }
        String insurePass = infoDTO.getInsurePass();
        if(StringUtils.isNotBlank(insurePass)){
            insurePass = MD5Utils.md5Digest(insurePass).toUpperCase();
            infoDTO.setInsurePass(insurePass);
        }
        //修改
        AccountsInfo record = new AccountsInfo();
        CommonBeans.copyPropertiesIgnoreNull(infoDTO, record);
        this.encryptAccountInfo(record);
        int count = accountsInfoMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public PageResult<RecordtreasureserialDTO> queryRecordtreasureserialWithPage(EasyPageFilter pageFilter, RecordtreasureserialDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordtreasureserialDTO> pageResult = this.queryRecordtreasureserialWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordtreasureserialDTO> result = (EasyPageResult<RecordtreasureserialDTO>) pageResult;
            List<RecordtreasureserialDTO> rows = result.getRows();
            //金币类型字典
            rows.forEach( (rd) -> {
                Integer typeid = rd.getTypeid();
                String typeIdDesc = TreasureTypeEnum.getDescByCode(typeid);
                rd.setTypeidDesc(typeIdDesc);

                //金币单位转换
                rd.setCurScoreDesc(GoldUtils.divide(rd.getCurscore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                rd.setCurinSureScoreDesc(GoldUtils.divide(rd.getCurinsurescore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                rd.setChangeScoreDesc(GoldUtils.divide(rd.getChangescore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                //根据userId查accounts和gameId
//                AccountsInfo accountsInfo = accountsInfoMapper.selectByPrimaryKey(rd.getUserid());
                //把账号和游戏ID往该数据里面塞
//                rd.setAccounts(StringUtils.replaceStar(AESCBCUtils.decrypt(accountsInfo.getAccounts(), configResource.getAesUserKey()), null));
//                rd.setGameId(accountsInfo.getGameId());
            });
        }
        return pageResult;

    }

    private PageResult<RecordtreasureserialDTO> queryRecordtreasureserialWithPage1(EasyPageFilter pageFilter, RecordtreasureserialDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);

        //根据账户查到userId或根据gameId查到userId
        /*if (StringUtils.isNotBlank(dto.getAccounts()) || dto.getGameId() != null) {
            Example exampleAccounts = new Example(AccountsInfo.class);
            Example.Criteria criteriaAccounts = exampleAccounts.createCriteria();
            if (StringUtils.isNotBlank(dto.getAccounts())) {
                criteriaAccounts.andEqualTo("accounts", AESCBCUtils.encrypt(dto.getAccounts(), configResource.getAesUserKey()));
            }
            if (dto.getGameId() != null) {
                criteriaAccounts.andEqualTo("gameId", dto.getGameId());
            }
            AccountsInfo accountsInfo = accountsInfoMapper.selectOneByExample(exampleAccounts);
            if (accountsInfo != null) {
                dto.setUserid(accountsInfo.getUserId());
            } else {
                log.error("没有该账号！-------{}", dto.getAccounts());
                return easyPageQuery.queryResult(null, RecordtreasureserialDTO.class);
            }
        }*/

        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(Recordtreasureserial.class);
        Example.Criteria criteria = example.createCriteria();

        if(dto != null){

            Integer userid = dto.getUserid();
            if(userid != null){
                criteria.andEqualTo("userid",userid);
            }

            //金币类型
            Integer typeid = dto.getTypeid();
            if(typeid != null && typeid.intValue() != 100){
                criteria.andEqualTo("typeid" , typeid);
            }

            //时间
            // 开始
            String collectdateStartStr = dto.getCollectdateStartStr();
            if(StringUtils.isNotBlank(collectdateStartStr)){
//                Date date = DateUtils.parseDate(collectdateStartStr, DateUtils.DF_YYYYMMDDHHMMSS);
                criteria.andGreaterThanOrEqualTo("collectdate" , collectdateStartStr);
            }

            //结束
            String collectdateEndStr = dto.getCollectdateEndStr();
            if(StringUtils.isNotBlank(collectdateEndStr)){
//                Date date = DateUtils.parseDate(collectdateEndStr, DateUtils.DF_YYYYMMDDHHMMSS);
                criteria.andLessThanOrEqualTo("collectdate" , collectdateEndStr);
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" collectdate DESC");
        }
        //查询
        List<Recordtreasureserial> list = recordtreasureserialMapper.selectByExample(example);

        //结果
        PageResult<RecordtreasureserialDTO> pageResult = easyPageQuery.queryResult(list, RecordtreasureserialDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public PageResult<RecordtreasureserialDTO> queryHandRecordWithPage(EasyPageFilter pageFilter, RecordtreasureserialDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordtreasureserialDTO> pageResult = this.queryHandRecordWithPage1(pageFilter,dto);
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordtreasureserialDTO> result = (EasyPageResult<RecordtreasureserialDTO>) pageResult;
            List<RecordtreasureserialDTO> rows = result.getRows();
            //金币类型字典
            rows.forEach( (rd) -> {
                Integer typeid = rd.getTypeid();
                String typeIdDesc = TreasureTypeEnum.getDescByCode(typeid);
                rd.setTypeidDesc(typeIdDesc);
                rd.setAccounts(StringUtils.replaceStar(AESCBCUtils.decrypt(rd.getAccounts(), configResource.getAesUserKey()), null));

                //金币单位转换
                rd.setCurScoreDesc(GoldUtils.divide(rd.getCurscore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                rd.setCurinSureScoreDesc(GoldUtils.divide(rd.getCurinsurescore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                rd.setChangeScoreDesc(GoldUtils.divide(rd.getChangescore().intValue()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                //操作管理员
                Integer masterid = rd.getMasterid();
                if(masterid != null){
                    TbSysUserDTO userDTO = sysUserService.querySysUserById(String.valueOf(masterid));
                    if(userDTO != null){
                        rd.setMasterName(userDTO.getName() + "(" + userDTO.getLoginName() + ")");
                    }
                }
            });
        }
        return pageResult;

    }

    private PageResult<RecordtreasureserialDTO> queryHandRecordWithPage1(EasyPageFilter pageFilter, RecordtreasureserialDTO dto) {
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);

        //开始分页
        easyPageQuery.startPage(pageFilter);

        if (StringUtils.isNotBlank(dto.getAccounts())) {
            dto.setAccounts(AESCBCUtils.encrypt(dto.getAccounts(), configResource.getAesUserKey()));
        }

        //如果不是管理员 加条件 merchantAggent
        if (!ShiroUtils.isAdminSessionUserId()) {
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        //查询
        List<RecordtreasureserialDTO> list = investRecordtreasureserialMapper.selectHandRecord(dto);

        //结果
        PageResult<RecordtreasureserialDTO> pageResult = easyPageQuery.queryResult(list, RecordtreasureserialDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public Integer freshenGiveNum() {
        int num = (int)((Math.random()*9+1)*100000);
        boolean exisGiveNum = this.isExisGiveNum(num);
        //如果存在
        if(exisGiveNum){
            freshenGiveNum();
        }
        return num;
    }

    //是否存在 游戏id 存在 true 不存在 false
    private boolean isExisGiveNum(Integer num){
        Example example = new Example(AccountsInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gameId",num);
        List<AccountsInfo> accountsInfos = accountsInfoMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(accountsInfos);
    }

    @Override
    public boolean giveNumAccountUser(AccountsInfoDTO infoDTO) {
        log.info("{}",JsonUtils.toString(infoDTO));
        if(infoDTO == null) {
            throw new BusinessException("赠送靓号,传入对象为空", 1004);
        }
        Integer userId = infoDTO.getUserId();
        if(userId == null) {
            throw new BusinessException("赠送靓号,传入对象 userId 为空", 1004);
        }

        Integer giveNum = infoDTO.getGiveNum();
        if(giveNum == null) {
            throw new BusinessException("赠送靓号,传入对象 giveNum 为空", 1004);
        }

        // 校验
        boolean exisGiveNum = this.isExisGiveNum(giveNum);
        if(exisGiveNum){
            throw new BusinessException("抱歉，赠送的靓号已占用", 1004);
        }

        //新增 RecordGrantGameID 记录
        Recordgrantgameid record = new Recordgrantgameid();
        record.setMasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        record.setUserid(userId);
        AccountsInfo accountsInfo = accountsInfoMapper.selectByPrimaryKey(userId);
        log.info("accountsInfo.getAccounts():{}", accountsInfo.getAccounts());
        if(accountsInfo != null){
            record.setCurgameid(accountsInfo.getGameId());
        }
        record.setRegameid(giveNum);
        record.setIdlevel(0);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        record.setClientip(CusAccessObjectUtil.getIpAddress(request));
        record.setReason(infoDTO.getReason());
        record.setCollectdate(new Date());
        recordgrantgameidMapper.insertSelective(record);

        //更新 ReserveIdentifier
        //reserveidentifierMapper.updateByPrimaryKeySelective();

        //更新用户表 AccountsInfo
        AccountsInfo record1 = new AccountsInfo();
        record1.setUserId(userId);
        record1.setGameId(giveNum);
        accountsInfoMapper.updateByPrimaryKeySelective(record1);

        return true;
    }

    @Override
    public PageResult<RecordgrantgameidDTO> queryGivenumRecordWithPage(EasyPageFilter pageFilter, RecordgrantgameidDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordgrantgameidDTO> pageResult = this.queryGivenumRecordWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordgrantgameidDTO> result = (EasyPageResult<RecordgrantgameidDTO>) pageResult;
            List<RecordgrantgameidDTO> rows = result.getRows();

            //金币类型字典
            rows.forEach( (rd) -> {
                Integer masterid = rd.getMasterid();
                if(masterid != null){
                    TbSysUserDTO userDTO = sysUserService.querySysUserById(String.valueOf(masterid));
                    if(userDTO != null){
                        rd.setMasterName(userDTO.getName());
                    }
                }
            });

        }
        return pageResult;
    }

    private PageResult<RecordgrantgameidDTO> queryGivenumRecordWithPage1(EasyPageFilter pageFilter, RecordgrantgameidDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        if(dto.getUserid() == null){
            return null;
        }
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(Recordgrantgameid.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer userid = dto.getUserid();
            if(userid != null){
                criteria.andEqualTo("userid",userid);
            }
        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" collectdate DESC");
        }
        //查询
        List<Recordgrantgameid> list = recordgrantgameidMapper.selectByExample(example);
        //结果
        PageResult<RecordgrantgameidDTO> pageResult = easyPageQuery.queryResult(list, RecordgrantgameidDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;

    }

    @Override
    public boolean validatorGiveGold(Long giveGold, Integer userId) {
        if(userId == null) {
            throw new BusinessException("校验金币,传入对象 userId 为空", 1004);
        }
        if(giveGold == null) {
            throw new BusinessException("校验金币,传入对象 giveGold 为空", 1004);
        }
        //根据userid查询金币信息
        GameScoreInfo gameScoreInfo = gameScoreInfoMapper.selectByPrimaryKey(userId);
        //如果金币记录不存在 可以新增
        if(gameScoreInfo == null){
            return true;
        }else{
            // 金币 大于等于 0 可以相加
            if(giveGold >= 0){
                return true;
            }else{
                //页面传递的 金币
                long absGiveGold = Math.abs(giveGold);
                //当前用户金币
                Long score = gameScoreInfo.getScore();
                //最终一定要保证 传递的金币 要 小于等于 当前用户金币
                return absGiveGold <= score.longValue();
            }
        }
    }

    @Override
    public boolean giveGoldAccountUser(AccountsInfoDTO infoDTO) {
        //1 校验参数
        if(infoDTO == null) {
            throw new BusinessException("赠送金币,传入对象为空", 1004);
        }

        Long giveGold = infoDTO.getGiveGold();
        if(giveGold == null) {
            throw new BusinessException("赠送金币,传入对象 giveGold 为空", 1004);
        }

        //将赠送金币数 乘以 100
        //giveGold = giveGold * 100;
        giveGold = GoldUtils.boots(giveGold.longValue());

        Integer userId = infoDTO.getUserId();
        AccountsInfo accountsInfo = null;
        if(userId != null) {
            //根据userid查询用户
            accountsInfo = accountsInfoMapper.selectByPrimaryKey(userId);
            log.info("accountsInfo.getAccounts():{}", AESCBCUtils.decrypt(accountsInfo.getAccounts(), configResource.getAesUserKey()));
//            throw new BusinessException("赠送金币,传入对象 userId 为空", 1004);
        }
        if (StringUtils.isNotBlank(infoDTO.getAccounts()) && StringUtils.isNotBlank(infoDTO.getNickName()) && infoDTO.getGameId() != null) {
            Example example = new Example(AccountsInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("accounts", AESCBCUtils.encrypt(infoDTO.getAccounts(), configResource.getAesUserKey()));
            criteria.andEqualTo("nickName", AESCBCUtils.encrypt(infoDTO.getNickName(), configResource.getAesUserKey()));
            criteria.andEqualTo("gameId", infoDTO.getGameId());
            accountsInfo = accountsInfoMapper.selectOneByExample(example);
            if (accountsInfo != null) {
                    userId = accountsInfo.getUserId();
            } else {
                throw new BusinessException("被操作账户不存在，请重新输入");
            }
        }

        //1代表该玩家只是游客
        Integer rankId = accountsInfo.getRankId();
        if (null != rankId && rankId == 1) {
            throw new BusinessException("无法赠送游客，需升级正式账号才可进行赠送");
        }

        if(accountsInfo == null){
            throw new BusinessException("赠送金币,用户对象不存在", 1004);
        }

        Integer nullity = accountsInfo.getNullity();
        if(nullity == 1){
            throw new BusinessException("赠送金币,账户已禁用", 1004);
        }

        int count = 0;
        //当前用户金币
        Long curgold = 0L;
        Long curinsurescore = 0L;
        //根据userid查询金币信息
        GameScoreInfo gameScoreInfo = gameScoreInfoMapper.selectByPrimaryKey(accountsInfo.getUserId());
        if(gameScoreInfo == null){
            //新增 INSERT INTO GameScoreInfo(UserID,Score,InsureScore,RegisterIP,RegisterDate,RegisterMachine) VALUES(@UserID,@AddGold,0,@RegisterIP,@RegisterDate,@RegisterMachine)
            GameScoreInfo record = new GameScoreInfo();
            record.setUserid(userId);
            //record.setScore(giveGold < 0 ? 0 : giveGold);
            //record.setInsurescore(0L);
            record.setScore(0L);
            record.setInsurescore(giveGold < 0 ? 0 : giveGold);
            record.setRegisterip(accountsInfo.getRegisterIp());
            record.setRegisterdate(accountsInfo.getRegisterDate());
            record.setRegistermachine(accountsInfo.getRegisterMachine());
            gameScoreInfoMapper.insertSelective(record);
            count ++;
        }else {
            // 判断 传入的金币小于0 那么 金币绝对值 大于 当前用户金币 无法进行修改
            //true 可以操作 false 不可以操作
            //修改 UPDATE GameScoreInfo SET Score = Score+@AddGold WHERE UserID=@UserID
            GameScoreInfo record = new GameScoreInfo();
            record.setUserid(userId);
            long newScore;
            if (infoDTO.getDeposiWithdrawalState() == null || infoDTO.getDeposiWithdrawalState() == 1) {
                newScore = gameScoreInfo.getScore() + giveGold;
            } else {
                newScore = gameScoreInfo.getScore() - giveGold;
            }
            // record.setScore(newScore < 0 ? 0 : newScore);
            record.setScore(newScore < 0 ? 0 : newScore);
            gameScoreInfoMapper.updateByPrimaryKeySelective(record);
            curgold = gameScoreInfo.getScore();
            curinsurescore = gameScoreInfo.getInsurescore();
            count++;
        }

        // 写入赠送记录
        /*
         * INSERT INTO WHQJRecordDB.dbo.RecordGrantTreasure(MasterID,ClientIP,CollectDate,UserID,
         * CurGold,AddGold,Reason)
         * VALUES(@MasterID,@ClientIP,@DateTime,@UserID,@CurScore,@AddGold,@Reason)
         */
        Recordgranttreasure record1 = new Recordgranttreasure();
        record1.setMasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        record1.setClientip(CusAccessObjectUtil.getIpAddress(request));
        record1.setCollectdate(new Date());
        record1.setUserid(accountsInfo.getUserId());
        record1.setCurgold(curgold);
        record1.setAddgold(giveGold);
        record1.setReason(infoDTO.getReason());
        int a = recordgranttreasureMapper.insertSelective(record1);
        count += a;

        // 写入流水记录
        /**
         * INSERT INTO WHQJRecordDB.dbo.RecordTreasureSerial
         * (SerialNumber,MasterID,UserID,TypeID,CurScore,CurInsureScore,ChangeScore,ClientIP,CollectDate)
         * 	VALUES(dbo.WF_GetSerialNumber(),@MasterID,@UserID,0,@CurScore,@CurInsureScore,@AddGold,@ClientIP,@DateTime)
         */
        Recordtreasureserial record2 = new Recordtreasureserial();
//        String serialnumber = GeneratotUtils.getSerialnumber(accountsInfo.getMerchant(),accountsInfo.getAccounts());
       // record2.setSerialnumber(String.valueOf(idWorker.nextId())); //分布式id
        record2.setMasterid(Integer.valueOf(ShiroUtils.getSessionUserId()));
        record2.setUserid(userId);
        //0代表后台赠送  17代表后台充值  18代表后台扣除
        if (infoDTO.getDeposiWithdrawalState() == null) {
            record2.setTypeid(0);
            record2.setSerialnumber(DateUtils.getNow(DateUtils.DF_YYMMDDHHMMSS) + 00 + NumberUtils.changeRandomNum(5));
        } else if (infoDTO.getDeposiWithdrawalState() == 1) {
            record2.setTypeid(19);
            record2.setSerialnumber(DateUtils.getNow(DateUtils.DF_YYMMDDHHMMSS) + 19 + NumberUtils.changeRandomNum(5));
        } else if (infoDTO.getDeposiWithdrawalState() == 2) {
            record2.setTypeid(20);
            record2.setSerialnumber(DateUtils.getNow(DateUtils.DF_YYMMDDHHMMSS) + 20 + NumberUtils.changeRandomNum(5));
        }
        record2.setCurscore(curgold);
        record2.setCurinsurescore(curinsurescore);
        record2.setChangescore(giveGold);
        record2.setClientip(CusAccessObjectUtil.getIpAddress(request));
        record2.setCollectdate(new Date());
        int b = recordtreasureserialMapper.insertSelective(record2);
        count += b;

        return count > 0;
    }

    @Override
    public List<AccountsInfo> selectAccountInfoByUserList(List<Integer> userIdList){
    	Map<String, Object> condition = new HashMap<String, Object>();
    	condition.put("userIdList", userIdList);
    	return accountUserMapper.selectAccountInfoByUserIds(condition);
    }

    @Override
    public Map<Integer, AccountsInfo> selectAccountInfoMapByUserList(List<Integer> userIdList){
    	List<AccountsInfo> list = this.selectAccountInfoByUserList(userIdList);
    	Map<Integer, AccountsInfo> map = new HashMap<Integer, AccountsInfo>();
    	for (AccountsInfo accountsInfo : list) {
    		map.put(accountsInfo.getUserId(), accountsInfo); //Key = userId
		}
		return map;
    }

    public void updateall() {
    	Example example = new Example(AccountsInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isAndroid", 0);
        criteria.andCondition("len(accounts) < ", "30");
        List<AccountsInfo> accountsInfos = accountsInfoMapper.selectByExample(example);

        for (AccountsInfo accountsInfo : accountsInfos) {
        	if(accountsInfo.getAccounts() != null && StringUtils.isNotEmpty(accountsInfo.getAccounts()))
        	accountsInfo.setAccounts(CBm53AES.encrypt(accountsInfo.getAccounts()));
        	if(accountsInfo.getNickName() != null && StringUtils.isNotEmpty(accountsInfo.getNickName())) {
        		log.info("nickname :" + accountsInfo.getNickName() + "=" + CBm53AES.encrypt(accountsInfo.getNickName()));
        		accountsInfo.setNickName(CBm53AES.encrypt(accountsInfo.getNickName()));
        	}

        	if(accountsInfo.getRegAccounts() != null && StringUtils.isNotEmpty(accountsInfo.getRegAccounts()))
        	accountsInfo.setRegAccounts(CBm53AES.encrypt(accountsInfo.getRegAccounts()));
        	if(accountsInfo.getLastLogonMobile() != null && StringUtils.isNotEmpty(accountsInfo.getLastLogonMobile()))
        	accountsInfo.setLastLogonMobile(CBm53AES.encrypt(accountsInfo.getLastLogonMobile()));
        	if(accountsInfo.getRegisterMobile() != null && StringUtils.isNotEmpty(accountsInfo.getRegisterMobile()))
        	accountsInfo.setRegisterMobile(CBm53AES.encrypt(accountsInfo.getRegisterMobile()));
        	accountsInfoMapper.updateByPrimaryKey(accountsInfo);
		}

        log.info("accountsInfos.size()" + accountsInfos.size());
    }

    /**
     * 解密用户信息
     * @param infoDTO
     * @return
     */
    private void decryptDTO(AccountsInfoDTO infoDTO) {
    	String nickName = infoDTO.getNickName();
        if(StringUtils.isNotEmpty(nickName)) {
        	infoDTO.setNickName(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
        }

        String account = infoDTO.getAccounts();
        if(StringUtils.isNotEmpty(account)) {
        	infoDTO.setAccounts(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
        }

        String compellation = infoDTO.getCompellation();
        if(StringUtils.isNotEmpty(compellation)) {
        	infoDTO.setCompellation(AESCBCUtils.decrypt(compellation, configResource.getAesUserKey()));
        }

        String passPortId = infoDTO.getPassPortId();
        if(StringUtils.isNotEmpty(passPortId)) {
        	infoDTO.setPassPortId(AESCBCUtils.decrypt(passPortId, configResource.getAesUserKey()));
        }

        String regAccounts = infoDTO.getRegAccounts();
        if(StringUtils.isNotEmpty(regAccounts)) {
        	infoDTO.setRegAccounts(AESCBCUtils.decrypt(regAccounts, configResource.getAesUserKey()));
        }

        String lastLogonMobile = infoDTO.getLastLogonMobile();
        if(StringUtils.isNotEmpty(lastLogonMobile)) {
        	infoDTO.setLastLogonMobile(AESCBCUtils.decrypt(lastLogonMobile, configResource.getAesUserKey()));
        }

        String registerMobile = infoDTO.getRegisterMobile();
        if(StringUtils.isNotEmpty(registerMobile)) {
        	infoDTO.setRegisterMobile(AESCBCUtils.decrypt(registerMobile, configResource.getAesUserKey()));
        }
    }

    /**
     * 解密用户信息
     * @param infoDTO
     * @return
     */
    private void decryptAccountInfo(AccountsInfo info) {
    	String nickName = info.getNickName();
        if(StringUtils.isNotEmpty(nickName)) {
        	info.setNickName(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
        }

        String account = info.getAccounts();
        if(StringUtils.isNotEmpty(account)) {
        	info.setAccounts(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()));
        }

        String compellation = info.getCompellation();
        if(StringUtils.isNotEmpty(compellation)) {
        	info.setCompellation(AESCBCUtils.decrypt(compellation, configResource.getAesUserKey()));
        }

        String passPortId = info.getPassPortId();
        if(StringUtils.isNotEmpty(passPortId)) {
        	info.setPassPortId(AESCBCUtils.decrypt(passPortId, configResource.getAesUserKey()));
        }

        String regAccounts = info.getRegAccounts();
        if(StringUtils.isNotEmpty(regAccounts)) {
        	info.setRegAccounts(AESCBCUtils.decrypt(regAccounts, configResource.getAesUserKey()));
        }

        String lastLogonMobile = info.getLastLogonMobile();
        if(StringUtils.isNotEmpty(lastLogonMobile)) {
        	info.setLastLogonMobile(AESCBCUtils.decrypt(lastLogonMobile, configResource.getAesUserKey()));
        }

        String registerMobile = info.getRegisterMobile();
        if(StringUtils.isNotEmpty(registerMobile)) {
        	info.setRegisterMobile(AESCBCUtils.decrypt(registerMobile, configResource.getAesUserKey()));
        }
    }

    /**
     * 加密用户信息
     * @param infoDTO
     * @return
     */
    private void encryptAccountInfo(AccountsInfo info) {
    	String nickName = info.getNickName();
        if(StringUtils.isNotEmpty(nickName)) {
        	info.setNickName(AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
        }

        String account = info.getAccounts();
        if(StringUtils.isNotEmpty(account)) {
        	info.setAccounts(AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
        }

        String compellation = info.getCompellation();
        if(StringUtils.isNotEmpty(compellation)) {
        	info.setCompellation(AESCBCUtils.encrypt(compellation, configResource.getAesUserKey()));
        }

        String passPortId = info.getPassPortId();
        if(StringUtils.isNotEmpty(passPortId)) {
        	info.setPassPortId(AESCBCUtils.encrypt(passPortId, configResource.getAesUserKey()));
        }

        String regAccounts = info.getRegAccounts();
        if(StringUtils.isNotEmpty(regAccounts)) {
        	info.setRegAccounts(AESCBCUtils.encrypt(regAccounts, configResource.getAesUserKey()));
        }

        String lastLogonMobile = info.getLastLogonMobile();
        if(StringUtils.isNotEmpty(lastLogonMobile)) {
        	info.setLastLogonMobile(AESCBCUtils.encrypt(lastLogonMobile, configResource.getAesUserKey()));
        }

        String registerMobile = info.getRegisterMobile();
        if(StringUtils.isNotEmpty(registerMobile)) {
        	info.setRegisterMobile(AESCBCUtils.encrypt(registerMobile, configResource.getAesUserKey()));
        }
    }

    @Override
    public UserInfoVO queryAPIAccountUserById(Integer userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
    	AccountsInfoDTO user = new AccountsInfoDTO();
    	user.setUserId(Integer.valueOf(userId));
    	AccountsInfo account = this.queryAccountUserById(user);
    	UserInfoVO vo = null;
    	if(account != null) {
    		vo = new UserInfoVO();
    		BeanUtils.copyProperties(account, vo);
    		vo.setApiVersion(apiVersion);
    		vo.setValid(true);
    		vo.setDateTime(System.currentTimeMillis());
    	}
    	return vo;
    }

    @Override
	public int updateAccountInfoByUserId(AccountsInfo accountsInfo) {
    	return accountsInfoMapper.updateByPrimaryKeySelective(accountsInfo);
    }
}
