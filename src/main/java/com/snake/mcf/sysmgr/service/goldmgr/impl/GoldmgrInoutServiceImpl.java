package com.snake.mcf.sysmgr.service.goldmgr.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.goldmgr.GoldmgrInoutMapper;
import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecorduserinoutDTO;
import com.snake.mcf.sysmgr.repertory.entity.vo.RecorduserinoutVO;
import com.snake.mcf.sysmgr.repertory.mapper.AccountsInfoMapper;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrInoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName GoldmgrInoutServiceImpl
 * @Author 大帅
 * @Date 2019/7/12 10:06
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class GoldmgrInoutServiceImpl extends BaseServiceImpl implements GoldmgrInoutService {

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private GoldmgrInoutMapper goldmgrInoutMapper;

    @Autowired
    private AccountsInfoMapper accountsInfoMapper;

    @Autowired
    private ConfigResource configResource;

    @Override
    public PageResult<RecorduserinoutDTO> queryRecordUserInoutWithPage(EasyPageFilter pageFilter, RecorduserinoutVO vo) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(vo));
        PageResult<RecorduserinoutDTO> pageResult = this.queryRecordUserInoutWithPage1(pageFilter,vo);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecorduserinoutDTO> result = (EasyPageResult<RecorduserinoutDTO>) pageResult;
            List<RecorduserinoutDTO> rows = result.getRows();

            DecimalFormat df = new DecimalFormat("0.00");

            rows.forEach( (rd) -> {

                // 离开原因
                Integer leavereason = rd.getLeavereason();
                if(leavereason != null){
                    String leavereasonDesc = DictionaryConstant.getLeavereasonDesc(leavereason);
                    rd.setLeavereasonDesc(leavereasonDesc);
                }

                //携带金币 /100
                Long enterscore = rd.getEnterscore();
                if(enterscore != null){
                    rd.setEnterscoreDouble(GoldUtils.reduce(enterscore.longValue()));
                }

                //银行金币 /100
                Long enterinsure = rd.getEnterinsure();
                if(enterinsure != null){
                    rd.setEnterinsureDouble(GoldUtils.reduce(enterinsure.longValue()));
                }

                //携带金币变化 /100
                Long score = rd.getScore();
                if(score != null){
                    String scoreFormat = df.format(GoldUtils.reduce(score.longValue()));
                    rd.setScoreDouble(scoreFormat);
                }

                //银行金币变化 /100
                Long insure = rd.getInsure();
                if(insure != null){
                    String insureFormat = df.format(GoldUtils.reduce(insure.longValue()));
                    rd.setInsureDouble(insureFormat);
                }

                //游戏服务费 /100
                Long revenue = rd.getRevenue();
                if(revenue != null){
                    rd.setRevenueDouble(GoldUtils.reduce(revenue.longValue()));
                }

                String remitter = rd.getRemitter();
                if(StringUtils.isNotEmpty(remitter)) {
                	rd.setRemitter(StringUtils.replaceStar(AESCBCUtils.decrypt(remitter, configResource.getAesUserKey()), null));
                }

                Date leavetime = rd.getLeavetime();
                log.info("leavetime：{}", leavetime);
                if (leavetime == null) {
                    rd.setLeavetimeStr("正在游戏中...");
                } else {
                    rd.setLeavetimeStr(DateUtils.format(leavetime, DateUtils.DF_YYYY_MM_DD_HH_MM_SS));
                    log.info("leavetime.toString()：{}", DateUtils.format(leavetime, DateUtils.DF_YYYY_MM_DD_HH_MM_SS));
                }
            });
        }
        return pageResult;
    }

    private PageResult<RecorduserinoutDTO> queryRecordUserInoutWithPage1(EasyPageFilter filter, RecorduserinoutVO vo) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Recordinsure.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(vo != null){

            // gameID
            Integer gameid = vo.getGameid();
            if(null != gameid){
                map.put("gameid",gameid);
            }

            //根据昵称去查用户ID
            String nickName = vo.getNickName().trim();
            if(StringUtils.isNotBlank(nickName)){
                AccountsInfo accountsInfo = accountsInfoMapper.selectUserIdByNickName(AESCBCUtils.encrypt(nickName, configResource.getAesUserKey()));
                if (null != accountsInfo){
                    map.put("userid",accountsInfo.getUserId());
                } else {
                    log.info("该用户不存在！");
                    return easyPageQuery.queryResult(new ArrayList(), RecorduserinoutDTO.class);
                }
            }

            //游戏名称
            String kindname = vo.getKindname();
            if(StringUtils.isNotBlank(kindname) && !"0".equals(kindname)) {
                map.put("kindname",kindname);
            }

            String entertimeStartStr = vo.getEntertimeStartStr();
            if(StringUtils.isNotBlank(entertimeStartStr)){
                map.put("entertimeStartStr",entertimeStartStr);
            }

            String entertimeEndStr = vo.getEntertimeEndStr();
            if(StringUtils.isNotBlank(entertimeEndStr)){
                map.put("entertimeEndStr",entertimeEndStr);
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
        //List<Recordinsure> list = recordinsureMapper.selectByExample(example);
        List<RecorduserinoutDTO> list = goldmgrInoutMapper.queryRecordUserInoutWithPage(map);
        //结果
        PageResult<RecorduserinoutDTO> pageResult = easyPageQuery.queryResult(list, RecorduserinoutDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
