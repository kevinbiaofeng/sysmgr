package com.snake.mcf.sysmgr.service.goldmgr.impl;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.goldmgr.GoldmgrGamerdMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecorddrawinfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.RecorddrawinfoMapper;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrGamerdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoldmgrGamerdServiceImpl
 * @Author 大帅
 * @Date 2019/7/12 14:30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class GoldmgrGamerdServiceImpl extends BaseServiceImpl implements GoldmgrGamerdService {

    @Autowired
    private GoldmgrGamerdMapper goldmgrGamerdMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Autowired
    private RecorddrawinfoMapper recorddrawinfoMapper;

    @Override
    public PageResult<RecorddrawinfoDTO> queryRecordDrawInfoWithPage(EasyPageFilter pageFilter, RecorddrawinfoDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<RecorddrawinfoDTO> pageResult = this.queryRecordDrawInfoWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RecorddrawinfoDTO> result = (EasyPageResult<RecorddrawinfoDTO>) pageResult;
            List<RecorddrawinfoDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //是否安卓
                Integer isandroid = rd.getIsandroid();
                if(isandroid != null){
                    String isAndroidDesc = DictionaryConstant.getIsAndroidDesc(isandroid);
                    rd.setIsandroidDesc(isAndroidDesc);
                }

                //输赢金币 /100
                Long score = rd.getScore();
                if(score != null){
                    rd.setScoreDouble(GoldUtils.reduce(score.longValue()));
                }

                //损耗 /100
                Long waste = rd.getWaste();
                if(waste != null){
                    rd.setWasteDouble(GoldUtils.reduce(waste.longValue()));
                }

                //服务费 /100
                Long revenue = rd.getRevenue();
                if(revenue != null){
                    rd.setRevenueDouble(GoldUtils.reduce(revenue.longValue()));
                }

            });
        }
        return pageResult;
    }

    private PageResult<RecorddrawinfoDTO> queryRecordDrawInfoWithPage1(EasyPageFilter filter, RecorddrawinfoDTO dto) {
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

            // gameID
           Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            String inserttimeStartStr = dto.getInserttimeStartStr();
            if(StringUtils.isNotBlank(inserttimeStartStr)){
                map.put("inserttimeStartStr",inserttimeStartStr);
            }

            String inserttimeEndStr = dto.getInserttimeEndStr();
            if(StringUtils.isNotBlank(inserttimeEndStr)){
                map.put("inserttimeEndStr",inserttimeEndStr);
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
        long start = System.currentTimeMillis();
        List<RecorddrawinfoDTO> list = goldmgrGamerdMapper.queryRecordDrawInfoWithPage(map);
        long end = System.currentTimeMillis();
        log.info("sql执行时间：{}", end - start);
        //结果
        PageResult<RecorddrawinfoDTO> pageResult = easyPageQuery.queryResult(list, RecorddrawinfoDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
}
