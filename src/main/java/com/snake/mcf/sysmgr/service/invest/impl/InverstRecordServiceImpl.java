package com.snake.mcf.sysmgr.service.invest.impl;

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
import com.snake.mcf.sysmgr.mapper.invest.InverstRecordMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinepayorderDTO;
import com.snake.mcf.sysmgr.repertory.mapper.OnlinepayorderMapper;
import com.snake.mcf.sysmgr.service.invest.InverstRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InverstRecordServiceImpl
 * @Author 大帅
 * @Date 2019/6/28 22:02
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class InverstRecordServiceImpl extends BaseServiceImpl implements InverstRecordService {

    @Autowired
    private InverstRecordMapper inverstRecordMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<OnlinepayorderDTO> queryRecordWithPage(EasyPageFilter pageFilter, OnlinepayorderDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<OnlinepayorderDTO> pageResult = this.queryRecordWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<OnlinepayorderDTO> result = (EasyPageResult<OnlinepayorderDTO>) pageResult;
            List<OnlinepayorderDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //充值数值
                Integer score1 = rd.getScore();
                if(score1 != null){
                    //rd.setScoreDouble(Double.valueOf(score1/100));
                    rd.setScoreDouble(GoldUtils.reduce(score1.longValue()));
                }

                //额外赠送
                Integer otherpresent1 = rd.getOtherpresent();
                if(otherpresent1 != null){
                    //rd.setOtherpresentDouble(Double.valueOf(otherpresent1/100));
                    rd.setOtherpresentDouble(GoldUtils.reduce(otherpresent1.longValue()));
                }


                //充值货币类型
                Integer scoretype = rd.getScoretype();
                if(scoretype != null){
                    String scoretypeDesc = DictionaryConstant.getScoretypeDesc(scoretype);
                    rd.setScoretypeDesc(scoretypeDesc);
                }

                //订单状态
                Integer orderstatus = rd.getOrderstatus();
                if(orderstatus != null){
                    String orderstatusDesc = DictionaryConstant.getOrderstatusDesc(orderstatus);
                    rd.setOrderstatusDesc(orderstatusDesc);
                }

                //充值类型
                Integer shareid = rd.getShareid();
                if(shareid != null){
                    String shareidDesc = DictionaryConstant.getShareidDesc(shareid);
                    rd.setShareidDesc(shareidDesc);
                }
                
                String nickName = rd.getNickname();
                if(StringUtils.isNotEmpty(nickName)) {
                	rd.setNickname(StringUtils.replaceStar(AESCBCUtils.decrypt(nickName, configResource.getAesUserKey()), null));
                }

                //充值前 后数量
                if(orderstatus == 0){
                    //充值后数量
                    rd.setAfterAllScore("——");
                    //充值前数量
                    rd.setBeforeAllScore("——");
                }else{
                    //充值后数量
                    Long beforescore = rd.getBeforescore();
                    Integer score = rd.getScore();
                    Integer otherpresent = rd.getOtherpresent();
                    long allscore = beforescore.longValue() + score.intValue() + otherpresent.intValue();
                    // 充值后数量 / 100
                    //rd.setAfterAllScore(String.valueOf(Double.valueOf(allscore/100)));
                    rd.setAfterAllScore(String.valueOf(GoldUtils.reduce(allscore)));
                    //充值前数量 /100
                    //rd.setBeforeAllScore(String.valueOf(Double.valueOf(rd.getBeforescore()/100)));
                    rd.setBeforeAllScore(String.valueOf(GoldUtils.reduce(beforescore.longValue())));
                }

            });
        }
        return pageResult;
    }

    private PageResult<OnlinepayorderDTO> queryRecordWithPage1(EasyPageFilter pageFilter, OnlinepayorderDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        //Example example = new Example(Onlinepayorder.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();
        if(dto != null){

            Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            Integer orderstatus = dto.getOrderstatus();
            if(orderstatus != -1){
                map.put("orderstatus",orderstatus);
            }

            Integer shareid = dto.getShareid();
            if(shareid != 0){
                map.put("shareid",shareid);
            }

            String orderdateStartStr = dto.getOrderdateStartStr();
            if(StringUtils.isNotBlank(orderdateStartStr)){
                map.put("orderdateStartStr",orderdateStartStr);
            }

            String orderdateEndStr = dto.getOrderdateEndStr();
            if(StringUtils.isNotBlank(orderdateEndStr)){
                map.put("orderdateEndStr",orderdateEndStr);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Onlinepayorder> list = onlinepayorderMapper.selectByExample(example);
        List<OnlinepayorderDTO> list = inverstRecordMapper.queryRecordWithPage(map);
        //结果
        PageResult<OnlinepayorderDTO> pageResult = easyPageQuery.queryResult(list, OnlinepayorderDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }


}
