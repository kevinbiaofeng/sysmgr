package com.snake.mcf.sysmgr.service.withdraw.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.enums.cash.PayTypeEnum;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.BindBankCardsChangeRecord;
import com.snake.mcf.sysmgr.repertory.entity.Bindbankcards;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindbankcardsDTO;
import com.snake.mcf.sysmgr.repertory.mapper.AccountsInfoMapper;
import com.snake.mcf.sysmgr.repertory.mapper.BindBankCardsChangeRecordMapper;
import com.snake.mcf.sysmgr.repertory.mapper.BindbankcardsMapper;
import com.snake.mcf.sysmgr.service.withdraw.WithdrawBindcardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WithdrawBindcardServiceImpl
 * @Author 大帅
 * @Date 2019/7/24 15:11
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WithdrawBindcardServiceImpl extends BaseServiceImpl implements WithdrawBindcardService {

    @Autowired
    private AccountsInfoMapper accountsInfoMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private BindbankcardsMapper bindbankcardsMapper;

    @Autowired
    private BindBankCardsChangeRecordMapper bindBankCardsChangeRecordMapper;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<AccountsInfoDTO> queryAccountUserWithPage(EasyPageFilter pageFilter, AccountsInfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<AccountsInfoDTO> pageResult = this.queryAccountUserWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<AccountsInfoDTO> result = (EasyPageResult<AccountsInfoDTO>) pageResult;
            List<AccountsInfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
                //Gender 保密为0，男为1，女为2
                Integer gender = rd.getGender();
                if(gender != null){
                    String genderDesc = DictionaryConstant.getGenderDesc(gender);
                    rd.setGenderDesc(genderDesc);
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
                Integer registerOrigin = rd.getRegisterOrigin();
                if(registerOrigin != null){
                    String registerOriginDesc = DictionaryConstant.getRegisterOriginDesc(registerOrigin);
                    rd.setRegisterOriginDesc(registerOriginDesc);
                }

                // nullity 账号禁用标识
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }
                
                String nickName = rd.getNickName();
                if(StringUtils.isNotEmpty(nickName)) {
                	rd.setNickName(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }

            });
        }
        return pageResult;
    }

    private PageResult<AccountsInfoDTO> queryAccountUserWithPage1(EasyPageFilter filter, AccountsInfoDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(AccountsInfo.class);
        Example.Criteria criteria = example.createCriteria();

        //0代表用户  1代表机器人
        criteria.andEqualTo("isAndroid",0);

        if(dto != null){

            //昵称
            String nickName = dto.getNickName();
            if(StringUtils.isNotBlank(nickName)) {
                criteria.andLike("nickName", AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
            }

            //游戏ID
            Integer gameId = dto.getGameId();
            if(gameId != null){
                criteria.andEqualTo("gameId",gameId);
            }

            //是否启用
            Integer nullity = dto.getNullity();
            if(nullity != null){
                criteria.andEqualTo("nullity",nullity);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<AccountsInfo> list = accountsInfoMapper.selectByExample(example);
        //结果
        PageResult<AccountsInfoDTO> pageResult = easyPageQuery.queryResult(list, AccountsInfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public PageResult<BindbankcardsDTO> queryBindBankCardsWithPage(EasyPageFilter pageFilter, BindbankcardsDTO dto) {
        log.info("param : {}",JsonUtils.toString(dto));
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<BindbankcardsDTO> pageResult = this.queryBindBankCardsWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<BindbankcardsDTO> result = (EasyPageResult<BindbankcardsDTO>) pageResult;
            List<BindbankcardsDTO> rows = result.getRows();

            rows.forEach( (rd) -> {
                //绑定类型（1银行 2支付宝）
                Integer type = rd.getType();
                if(type != null){
                    String payTypeName = PayTypeEnum.getPayTypeName(type);
                    rd.setTypeNameDesc(payTypeName);
                }

                //绑定类型  银行名称
                Integer bankchoice = rd.getBankchoice();
                if(bankchoice != null){
                    String bankchoiceDesc = DictionaryConstant.getBankchoiceDesc(bankchoice);
                    rd.setBankchoiceDesc(bankchoiceDesc);
                }

                // nullity 账号启用/禁用标识
                Integer nullity = rd.getNullity();
                if(null != nullity){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                String idCardName = rd.getIdcardname();
                if(StringUtils.isNotEmpty(idCardName)) {
                	rd.setIdcardname(StringUtils.replaceStar(idCardName, null));
                }
            });
        }
        return pageResult;
    }

    private PageResult<BindbankcardsDTO> queryBindBankCardsWithPage1(EasyPageFilter filter, BindbankcardsDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Bindbankcards.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer userid = dto.getUserid();
            if(userid == null){
                throw new BusinessException("传入用户id不可以为空");
            }

            criteria.andEqualTo("userid",userid);

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Bindbankcards> list = bindbankcardsMapper.selectByExample(example);
        //结果
        PageResult<BindbankcardsDTO> pageResult = easyPageQuery.queryResult(list, BindbankcardsDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean saveBindBankCards(BindbankcardsDTO dto) {

        Long bindid = dto.getBindid();
        int count = 0;
        int result = 0;
        Example example = new Example(Bindbankcards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid", dto.getUserid());
        //查出名下所有银行卡信息
        List<Bindbankcards> bindbankcardsList = bindbankcardsMapper.selectByExample(example);
        if(bindid == null){
            //新增
            log.info("商户号:{}", ShiroUtils.getSessionMerchant());
            dto.setMerchant(ShiroUtils.getSessionMerchant());
            dto.setBindtime(new Date());

            Bindbankcards record = new Bindbankcards();
            CommonBeans.copyPropertiesIgnoreNull(dto,record);
            count = bindbankcardsMapper.insertSelective(record);
            //新增成功后
            result = insertBindBankCardsChangeRecord(record);
        }else{
            //TODO 此处代码有优化空间
            //编辑时如果要变更银行卡状态为启用时，该用户名下没有已启用的银行卡才能启用成功
            //0代表启用 1代表禁用  默认禁用   开启指定的则禁用其他所有的
            if (0 == dto.getNullity()) {
                if (bindbankcardsList.size() > 0) {
                    for (Bindbankcards bindbankcards: bindbankcardsList) {
                        //修改当前被操作数据的状态
                        if (bindbankcards.getBindid() == bindid) {
                            //修改
                            Bindbankcards record = new Bindbankcards();
                            CommonBeans.copyPropertiesIgnoreNull(dto,record);
                            count = bindbankcardsMapper.updateByPrimaryKeySelective(record);
                            //修改成功后   往银行卡变更记录表插入变更记录
                            //TODO 减少数据库操作 的代码中使用现有对象进行往 银行变更记录中插入数据
                            Bindbankcards card = bindbankcardsMapper.selectByPrimaryKey(bindid);
                            result = insertBindBankCardsChangeRecord(card);
                        }
                        //TODO 此处代码如果有多张卡片，这里将会根据以下条件影响卡片信息
//                        if (bindbankcards.getBindid() != bindid && 0 == bindbankcards.getNullity()) {
//                            bindbankcards.setNullity(1);
//                            bindbankcardsMapper.updateByPrimaryKeySelective(bindbankcards);
//                            //修改成功后   往银行卡变更记录表插入变更记录
//                            Bindbankcards card = bindbankcardsMapper.selectByPrimaryKey(bindbankcards.getBindid());
//                            insertBindBankCardsChangeRecord(card);
//                        }
                    }
                } else {
                    throw new BusinessException("编辑失败，该用户没有绑定银行卡！");
                }
            }

            if (1 == dto.getNullity()) {
                if (bindbankcardsList.size() > 1) {
                	Bindbankcards card = null;
                    for (Bindbankcards bindbankcards: bindbankcardsList) {
                        //修改当前被操作数据的状态
                        if (bindid == bindbankcards.getBindid()) {
                            //修改
                            Bindbankcards record = new Bindbankcards();
                            CommonBeans.copyPropertiesIgnoreNull(dto, record);
                            count = bindbankcardsMapper.updateByPrimaryKeySelective(record);
                            //修改成功后   往银行卡变更记录表插入变更记录
                            card = bindbankcardsMapper.selectByPrimaryKey(bindid);
                            break;
                        }
                    }
                    for (Bindbankcards bindbankcards: bindbankcardsList) {
                        if (bindbankcards.getBindid() != bindid && 1 == bindbankcards.getNullity()) {
                            bindbankcards.setNullity(0);
                            bindbankcardsMapper.updateByPrimaryKeySelective(bindbankcards);
                            //修改成功后   往银行卡变更记录表插入变更记录
                            card = bindbankcardsMapper.selectByPrimaryKey(bindbankcards.getBindid());
                            break;
                        }
                    }
                    String idCardName = card.getIdcardname();
                    if(StringUtils.isNotEmpty(idCardName)) {
                    	card.setIdcardname(AESCBCUtils.decrypt(idCardName, configResource.getAesUserKey()));
                    }
                    result = insertBindBankCardsChangeRecord(card);
                } else {
                    throw new BusinessException("编辑失败，用户名下只绑定一张银行卡时无法禁用！");
                }
            }
        }

        if (count <= 0) {
            throw new BusinessException("新增银行卡失败");
        }

        if (result <= 0) {
            throw new BusinessException("新增银行卡变更记录失败");
        }

        return true;
    }

    private Integer insertBindBankCardsChangeRecord(Bindbankcards record){
        BindBankCardsChangeRecord changeRecord = new BindBankCardsChangeRecord();
        changeRecord.setUserId(record.getUserid());
        changeRecord.setMerchant(record.getMerchant());
        changeRecord.setBankChoice(record.getBankchoice());
        changeRecord.setBankCardId(record.getBankcardid());
        changeRecord.setBankName(record.getBankname());
        changeRecord.setIdCardName(record.getIdcardname());
        changeRecord.setBindTime(record.getBindtime());
        changeRecord.setNullity(record.getNullity());
        changeRecord.setOperator(ShiroUtils.getSessionLoginName());
        changeRecord.setCreateTime(new Date());
        return bindBankCardsChangeRecordMapper.insertSelective(changeRecord);
    }

    @Override
    public BindbankcardsDTO queryBindBankCardsById(BindbankcardsDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空");
        }
        if(dto.getBindid() == null){
            throw new BusinessException("根据id查询,传入对象为空");
        }
        Bindbankcards bindbankcards = bindbankcardsMapper.selectByPrimaryKey(dto.getBindid());
        BindbankcardsDTO record = new BindbankcardsDTO();
        CommonBeans.copyPropertiesIgnoreNull(bindbankcards,record);

        return record;
    }

    @Override
    public boolean removeBindBankCardsByIds(Long[] ids) {
        List<Long> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Bindbankcards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("bindid",list);
        bindbankcardsMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
