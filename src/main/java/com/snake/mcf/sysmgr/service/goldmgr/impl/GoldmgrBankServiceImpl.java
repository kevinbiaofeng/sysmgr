package com.snake.mcf.sysmgr.service.goldmgr.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.goldmgr.GoldmgrBankMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordinsureDTO;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoldmgrBankServiceImpl
 * @Author 大帅
 * @Date 2019/7/11 15:43
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class GoldmgrBankServiceImpl extends BaseServiceImpl implements GoldmgrBankService {

    @Autowired
    private GoldmgrBankMapper goldmgrBankMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;
    
    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<RecordinsureDTO> queryRecordInsureWithPage(EasyPageFilter pageFilter, RecordinsureDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecordinsureDTO> pageResult = this.queryRecordInsureWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecordinsureDTO> result = (EasyPageResult<RecordinsureDTO>) pageResult;
            List<RecordinsureDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //交易类型
                Integer tradetype = rd.getTradeType();
                if(tradetype != null){
                    String tradetypeDesc = DictionaryConstant.getTradetypeDesc(tradetype);
                    rd.setTradetypeDesc(tradetypeDesc);
                }

                //SourceGold：操作用户操作前金币 /100
                Long sourcegold = rd.getSourceGold();
                if(sourcegold != null){
                    rd.setSourcegoldDouble(GoldUtils.reduce(sourcegold.longValue()));
                }

                //SourceBank：操作用户操作前银行金币 /100
                Long sourcebank = rd.getSourceBank();
                if(sourcebank != null){
                    rd.setSourcebankDouble(GoldUtils.reduce(sourcebank.longValue()));
                }

                //TargetGold：接收用户接收前金币 /100
                Long targetgold = rd.getTargetGold();
                if(targetgold != null){
                    rd.setTargetgoldDouble(GoldUtils.reduce(targetgold.longValue()));
                }

                //TargetBank：接收用户接收前银行金币 /100
                Long targetbank = rd.getTargetBank();
                if(targetbank != null){
                    rd.setTargetbankDouble(GoldUtils.reduce(targetbank.longValue()));
                }

                //SwapScore：交易金额 /100
                Long swapscore = rd.getSwapScore();
                if(swapscore != null){
                    rd.setSwapscoreDouble(GoldUtils.reduce(swapscore.longValue()));
                }

                //服务费 /100
                Long revenue = rd.getRevenue();
                if(revenue != null){
                    rd.setRevenueDouble(GoldUtils.reduce(revenue.longValue()));
                }
                
                String remitter = rd.getRemitter();
                if(StringUtils.isNotEmpty(remitter)) {
                	rd.setRemitter(StringUtils.replaceStar(AESCBCUtils.decrypt(remitter, configResource.getAesUserKey()), null));
                }
                
                String payee = rd.getPayee();
                if(StringUtils.isNotEmpty(payee)) {
                	rd.setPayee(StringUtils.replaceStar(AESCBCUtils.decrypt(payee, configResource.getAesUserKey()), null));
                }
            });
        }
        return pageResult;
    }

    private PageResult<RecordinsureDTO> queryRecordInsureWithPage1(EasyPageFilter filter, RecordinsureDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Recordinsure.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            //汇款 gameID
            Integer sourceGameId = dto.getSourceGameId();
            if(sourceGameId != null){
                map.put("sourceGameId",sourceGameId);
            }

            //收款 gameID
            Integer targetGameId = dto.getTargetGameId();
            if(targetGameId != null){
                map.put("targetGameId",targetGameId);
            }

            //类型
            Integer tradetype = dto.getTradeType();
            if(tradetype != null && tradetype != 0){
                map.put("tradetype",tradetype);
            }

            String collectdateStartStr = dto.getCollectdateStartStr();
            if(StringUtils.isNotBlank(collectdateStartStr)){
                map.put("collectdateStartStr",collectdateStartStr);
            }

            String collectdateEndStr = dto.getCollectdateEndStr();
            if(StringUtils.isNotBlank(collectdateEndStr)){
                map.put("collectdateEndStr",collectdateEndStr);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        List<RecordinsureDTO> list = goldmgrBankMapper.queryRecordInsureWithPage(map);
        //结果
        PageResult<RecordinsureDTO> pageResult = easyPageQuery.queryResult(list, RecordinsureDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
