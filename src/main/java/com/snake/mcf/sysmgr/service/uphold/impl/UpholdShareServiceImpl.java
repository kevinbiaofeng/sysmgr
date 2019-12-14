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
import com.snake.mcf.sysmgr.mapper.uphold.UpholdShareMapper;
import com.snake.mcf.sysmgr.repertory.entity.Shareconfig;
import com.snake.mcf.sysmgr.repertory.entity.dto.ShareconfigDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.SharelogDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ShareconfigMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UpholdShareServiceImpl
 * @Author 大帅
 * @Date 2019/7/6 10:33
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdShareServiceImpl extends BaseServiceImpl implements UpholdShareService {

    @Autowired
    private UpholdShareMapper upholdShareMapper;

    @Autowired
    private ShareconfigMapper shareconfigMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<ShareconfigDTO> queryShareConfigWithPage(EasyPageFilter pageFilter, ShareconfigDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ShareconfigDTO> pageResult = this.queryShareConfigWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ShareconfigDTO> result = (EasyPageResult<ShareconfigDTO>) pageResult;
            List<ShareconfigDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //每日分享获得金币 /100
                Integer timesharegold = rd.getTimesharegold();
                if(timesharegold != null){
                    //rd.setTimesharegoldDouble(Double.valueOf(timesharegold/100));
                    rd.setTimesharegoldDouble(GoldUtils.reduce(timesharegold.longValue()));
                }

                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<ShareconfigDTO> queryShareConfigWithPage1(EasyPageFilter filter, ShareconfigDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Shareconfig.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

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
        }
        //查询
        List<Shareconfig> list = shareconfigMapper.selectByExample(example);
        //结果
        PageResult<ShareconfigDTO> pageResult = easyPageQuery.queryResult(list, ShareconfigDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean isExistShareConfig(ShareconfigDTO dto) {
        Example example = new Example(Shareconfig.class);
        Example.Criteria criteria = example.createCriteria();
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }else{
            criteria.andIsNull("merchant");
        }
        List<Shareconfig> list = shareconfigMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveShareConfig(ShareconfigDTO dto) {

        //每日分享获得金币 * 100
        Integer timesharegold = dto.getTimesharegold();
        if(timesharegold != null){
            //dto.setTimesharegold(timesharegold*100);
            dto.setTimesharegold((int)GoldUtils.boots(timesharegold.longValue()));
        }

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Shareconfig record = new Shareconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = shareconfigMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateShareConfig(ShareconfigDTO dto) {

        //每日分享获得金币 * 100
        Integer timesharegold = dto.getTimesharegold();
        if(timesharegold != null){
            //dto.setTimesharegold(timesharegold*100);
            dto.setTimesharegold((int)GoldUtils.boots(timesharegold.longValue()));
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Shareconfig record = new Shareconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = shareconfigMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public ShareconfigDTO queryShareConfigById(ShareconfigDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 id 为空", 1004);
        }
        Shareconfig shareconfig = shareconfigMapper.selectByPrimaryKey(id);
        ShareconfigDTO record = new ShareconfigDTO();
        CommonBeans.copyPropertiesIgnoreNull(shareconfig,record);

        //每日分享获得金币 /100
        Integer timesharegold = record.getTimesharegold();
        if(timesharegold != null){
            //record.setTimesharegold(timesharegold/100);
            //record.setTimesharegoldDouble(Double.valueOf(timesharegold/100));

            record.setTimesharegold((int)GoldUtils.reduce(timesharegold.longValue()));
            record.setTimesharegoldDouble(GoldUtils.reduce(timesharegold.longValue()));
        }

        return record;
    }

    @Override
    public PageResult<SharelogDTO> queryShareLogWithPage(EasyPageFilter pageFilter, SharelogDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<SharelogDTO> pageResult = this.queryShareLogWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<SharelogDTO> result = (EasyPageResult<SharelogDTO>) pageResult;
            List<SharelogDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                // 分享获得金币 /100
                Integer timesharegold = rd.getTimesharegold();
                if(timesharegold != null){
                    // rd.setTimesharegoldDouble(Double.valueOf(timesharegold/100));
                    rd.setTimesharegoldDouble(GoldUtils.reduce(timesharegold.longValue()));
                }

            });
        }
        return pageResult;
    }

    private PageResult<SharelogDTO> queryShareLogWithPage1(EasyPageFilter filter, SharelogDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        //Example example = new Example(Gamepackagegoods.class);
        //Example.Criteria criteria = example.createCriteria();
        Map<String,Object> map = new HashMap<>();

        if(dto != null){

            Integer gameid = dto.getGameid();
            if(gameid != null){
                map.put("gameid",gameid);
            }

            String logtimeStartStr = dto.getLogtimeStartStr();
            if(StringUtils.isNotBlank(logtimeStartStr)){
                map.put("logtimeStartStr",logtimeStartStr);
            }
            /*if(StringUtils.isNotBlank(logtimeStartStr)){
                Date date = DateUtils.parseDate(logtimeStartStr,DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                if(date != null){
                    String start = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                    map.put("logtimeStartStr",start);
                }
            }*/

            String logtimeEndStr = dto.getLogtimeEndStr();
            if(StringUtils.isNotBlank(logtimeEndStr)){
                map.put("logtimeStartStr",logtimeEndStr);
            }
            /*if(StringUtils.isNotBlank(logtimeEndStr)){
                Date date = DateUtils.parseDate(logtimeEndStr,DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                if(date != null){
                    String end = DateUtils.format(date, DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
                    map.put("logtimeEndStr",end);
                }
            }*/

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                map.put("merchant",ShiroUtils.getSessionMerchant());
            }

        }

        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);

        //查询
        //List<Gamepackagegoods> list = gamepackagegoodsMapper.selectByExample(example);
        List<SharelogDTO> list = upholdShareMapper.queryShareLogWithPage(map);
        //结果
        PageResult<SharelogDTO> pageResult = easyPageQuery.queryResult(list, SharelogDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }


}
