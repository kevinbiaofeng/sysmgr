package com.snake.mcf.sysmgr.service.uphold.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.*;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdRankMapper;
import com.snake.mcf.sysmgr.repertory.entity.Rankingconfig;
import com.snake.mcf.sysmgr.repertory.entity.dto.CacherankawardDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RankingconfigDTO;
import com.snake.mcf.sysmgr.repertory.mapper.RankingconfigMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdRankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @ClassName UpholdRankServiceImpl
 * @Author 大帅
 * @Date 2019/7/2 10:49
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdRankServiceImpl extends BaseServiceImpl implements UpholdRankService {

    @Autowired
    private UpholdRankMapper upholdRankMapper;

    @Autowired
    private RankingconfigMapper rankingconfigMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<RankingconfigDTO> queryRankWithPage(EasyPageFilter pageFilter, RankingconfigDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RankingconfigDTO> pageResult = this.queryRankWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RankingconfigDTO> result = (EasyPageResult<RankingconfigDTO>) pageResult;
            List<RankingconfigDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //奖励金币数
                Long gold = rd.getGold();
                if(gold != null){
                    //rd.setGoldDouble(Double.valueOf(gold/100));
                    rd.setGoldDouble(GoldUtils.reduce(gold.longValue()));
                }


                Integer typeid = rd.getTypeid();
                if(typeid != null){
                    String rankTypeidDesc = DictionaryConstant.getRankTypeidDesc(typeid);
                    rd.setTypeidDesc(rankTypeidDesc);
                }

                Integer ranktype = rd.getRanktype();
                if(ranktype != null){
                    String ranktypeDesc = DictionaryConstant.getRankRanktypeDesc(ranktype);
                    rd.setRanktypeDesc(ranktypeDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<RankingconfigDTO> queryRankWithPage1(EasyPageFilter filter, RankingconfigDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Rankingconfig.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer ranktype = dto.getRanktype();
            if(ranktype != null){
                criteria.andEqualTo("ranktype" , ranktype);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" TypeID ASC , RankType ASC");
        }
        //查询
        List<Rankingconfig> list = rankingconfigMapper.selectByExample(example);
        //结果
        PageResult<RankingconfigDTO> pageResult = easyPageQuery.queryResult(list, RankingconfigDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean saveRank(RankingconfigDTO dto) {

        //奖励金币数 * 100
        Long gold = dto.getGold();
        //dto.setGold(gold * 100);
        if(gold != null){
            dto.setGold(GoldUtils.boots(gold.longValue()));
        }

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        dto.setCollectdate(new Date());

        Rankingconfig record = new Rankingconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = rankingconfigMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean isExistRank(RankingconfigDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer ranktype = dto.getRanktype();
        if(ranktype == null){
            throw new BusinessException("根据id查询,传入对象 ranktype 为空", 1004);
        }
        Integer typeid = dto.getTypeid();
        if(typeid == null){
            throw new BusinessException("根据id查询,传入对象 typeid 为空", 1004);
        }
        Example example = new Example(Rankingconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ranktype",ranktype);
        criteria.andEqualTo("typeid",typeid);
        //不存在id
        if(dto.getConfigid() != null){
            criteria.andNotEqualTo("configid",dto.getConfigid());
        }
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }
        List<Rankingconfig> list = rankingconfigMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public RankingconfigDTO queryRankById(RankingconfigDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null){
            throw new BusinessException("根据id查询,传入对象 configid 为空", 1004);
        }
        Rankingconfig rankingconfig = rankingconfigMapper.selectByPrimaryKey(configid);
        RankingconfigDTO record = new RankingconfigDTO();
        CommonBeans.copyPropertiesIgnoreNull(rankingconfig,record);

        // 奖励金币数 / 100
        Long gold = record.getGold();
        //record.setGold(gold/100);
        if(gold != null){
            record.setGold((long)GoldUtils.reduce(gold.longValue()));
        }

        return record;
    }

    @Override
    public boolean updateRank(RankingconfigDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null){
            throw new BusinessException("根据id查询,传入对象 configid 为空", 1004);
        }
        //奖励金币数 * 100
        Long gold = dto.getGold();
        if(gold != null){
            //dto.setGold(gold * 100);
            dto.setGold(GoldUtils.boots(gold.longValue()));
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Rankingconfig record = new Rankingconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = rankingconfigMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeRankByIds(Integer[] configids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(configids)){
            list = Arrays.asList(configids);
        }

        int count = 0;
        Example example = new Example(Rankingconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",list);
        rankingconfigMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }

    @Override
    public PageResult<CacherankawardDTO> queryRankAwardWithPage(EasyPageFilter pageFilter, CacherankawardDTO dto) {
        log.info("param: {}" , JsonUtils.toString(dto));
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<CacherankawardDTO> pageResult = this.queryRankAwardWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<CacherankawardDTO> result = (EasyPageResult<CacherankawardDTO>) pageResult;
            List<CacherankawardDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //奖励金币  / 100
                Long gold = rd.getGold();
                if(gold != null){
                    //rd.setGoldDouble(Double.valueOf(gold/100));
                    rd.setGoldDouble(GoldUtils.reduce(gold.longValue()));
                }

                Integer typeid = rd.getTypeid();
                if(typeid != null){
                    String rankTypeidDesc = DictionaryConstant.getRankTypeidDesc(typeid);
                    rd.setTypeidDesc(rankTypeidDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<CacherankawardDTO> queryRankAwardWithPage1(EasyPageFilter pageFilter, CacherankawardDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            Integer typeid = dto.getTypeid();
            if(typeid != null){
                map.put("typeid",typeid);
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
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)){
           // map.put("order",orderByClause);
        }else{
           // map.put("order"," a.CollectDate desc ");
        }

        //查询
        List<CacherankawardDTO> list = upholdRankMapper.queryRankAwardWithPage(map);

        //结果
        PageResult<CacherankawardDTO> pageResult = easyPageQuery.queryResult(list, CacherankawardDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
