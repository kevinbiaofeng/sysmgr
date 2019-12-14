package com.snake.mcf.sysmgr.service.invest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.DictionaryConstant;
import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.GoldUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.invest.InverstSetupMapper;
import com.snake.mcf.sysmgr.repertory.entity.Apppayconfig;
import com.snake.mcf.sysmgr.repertory.entity.dto.ApppayconfigDTO;
import com.snake.mcf.sysmgr.repertory.mapper.ApppayconfigMapper;
import com.snake.mcf.sysmgr.service.invest.InverstSetupService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName InverstSetupServiceImpl
 * @Author 大帅
 * @Date 2019/6/27 18:30
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class InverstSetupServiceImpl implements InverstSetupService {

    @Autowired
    private InverstSetupMapper inverstSetupMapper;

    @Autowired
    private ApppayconfigMapper apppayconfigMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;


    @Override
    public PageResult<ApppayconfigDTO> querySetupWithPage(EasyPageFilter pageFilter, ApppayconfigDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<ApppayconfigDTO> pageResult = this.querySetupWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<ApppayconfigDTO> result = (EasyPageResult<ApppayconfigDTO>) pageResult;
            List<ApppayconfigDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                /**
                 * 充值到账数值
                 */
                Integer score = rd.getScore();
                if(score != null){
                	rd.setScoreText(GoldUtils.divide(score).toString());
                }

                // 充值类型
                Integer paytype = rd.getPaytype();
                if(paytype != null){
                    String paytypeDesc = DictionaryConstant.getPaytypeDesc(paytype);
                    rd.setPaytypeDesc(paytypeDesc);
                }

                // 首充类型
                Integer payidentity = rd.getPayidentity();
                if(payidentity != null){
                    String payidentityDesc = DictionaryConstant.getPayidentityDesc(payidentity);
                    rd.setPayidentityDesc(payidentityDesc);
                }

                //充值货币类型
                Integer scoretype = rd.getScoretype();
                if(scoretype != null){
                    String scoretypeDesc = DictionaryConstant.getScoretypeDesc(scoretype);
                    rd.setScoretypeDesc(scoretypeDesc);
                }

                //额外赠送(首充/普通)
                Integer presentscore = rd.getPresentscore();
                //额外赠送 / 100
                Integer fristpresent = rd.getFristpresent();
                if(fristpresent != null){
                	//首充 / 100
                	String extra = (GoldUtils.reduce(fristpresent.longValue())) + " / " + presentscore;
                	rd.setFristpresentText(GoldUtils.divide(fristpresent).toString());
                    rd.setExtra(extra);
                }
            });

        }
        return pageResult;
    }

    private PageResult<ApppayconfigDTO> querySetupWithPage1(EasyPageFilter pageFilter, ApppayconfigDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(Apppayconfig.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer paytype = dto.getPaytype();
            if(paytype != null){
                criteria.andEqualTo("paytype",paytype);
            }

            String payname = dto.getPayname();
            if(StringUtils.isNotBlank(payname)){
                criteria.andLike("payname", PayplatformConstant.SPLIT_SYMBOL_PER + payname + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()){
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(pageFilter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }else{
            example.setOrderByClause(" ConfigTime DESC");
        }
        //查询
        List<Apppayconfig> list = apppayconfigMapper.selectByExample(example);
        //结果
        PageResult<ApppayconfigDTO> pageResult = easyPageQuery.queryResult(list, ApppayconfigDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    //true 存在 false 不存在
    @Override
    public boolean isExistAppleid(ApppayconfigDTO dto) {
        Integer paytype = dto.getPaytype();
        if(paytype.intValue() == 0){
            return false;
        }
        String appleid = dto.getAppleid();
        if(StringUtils.isBlank(appleid)){
            throw new BusinessException("保存注册,传入 appleid 对象 为空", 1004);
        }
        Example example = new Example(Apppayconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("paytype",paytype);
        criteria.andEqualTo("appleid",appleid.trim());
        if(dto.getConfigid() != null){
            criteria.andNotEqualTo("configid",dto.getConfigid());
        }

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }

        List<Apppayconfig> list = apppayconfigMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveAppConfig(ApppayconfigDTO dto) {
        //金币换算 * 100
        String scoreText = dto.getScoreText();
        if(StringUtils.isNotBlank(scoreText)){
            dto.setScore(GoldUtils.boots(scoreText).intValue());
        }

        //额外赠送 * 100
        String fristpresentText = dto.getFristpresentText();
        if(StringUtils.isNotBlank(fristpresentText)){
            dto.setFristpresent(GoldUtils.boots(fristpresentText).intValue());
        }

        //商户信息
        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }
        dto.setAccount(ShiroUtils.getSessionLoginName());

        dto.setConfigtime(new Date());
        dto.setPresentscore(0);
        Apppayconfig record = new Apppayconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = apppayconfigMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public ApppayconfigDTO queryAppConfigById(ApppayconfigDTO dto) {
        log.info("{}",JsonUtils.toString(dto));
        if(dto == null) {
            throw new BusinessException("查询充值配置,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null) {
            throw new BusinessException("查询充值配置,传入对象 configid 为空", 1004);
        }
        Apppayconfig apppayconfig = apppayconfigMapper.selectByPrimaryKey(configid);
        ApppayconfigDTO dto1 = new ApppayconfigDTO();
        CommonBeans.copyPropertiesIgnoreNull(apppayconfig, dto1);
        //金币 / 100
        Integer score = dto1.getScore();
        if(score != null){
            dto1.setScore((int)GoldUtils.reduce(score.longValue()));
            dto1.setScoreText(GoldUtils.divide(score).toString());
        }

        //额外赠送 / 100
        Integer fristpresent = dto1.getFristpresent();
        if(fristpresent != null){
            dto1.setFristpresent((int)GoldUtils.reduce(fristpresent.longValue()));
            dto1.setFristpresentText(GoldUtils.divide(fristpresent).toString());
        }

        return dto1;
    }

    @Override
    public boolean updateAppConfig(ApppayconfigDTO dto) {
        /*boolean existAppleid = this.isExistAppleid(dto);
        if(existAppleid){
            return false;
        }*/
        log.info("{}",JsonUtils.toString(dto));
        if(dto == null) {
            throw new BusinessException("修改充值配置,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null) {
            throw new BusinessException("修改充值配置,传入对象 configid 为空", 1004);
        }
        //金币换算 * 100
        String scoreText = dto.getScoreText();
        if(StringUtils.isNotBlank(scoreText)){
            dto.setScore(GoldUtils.boots(scoreText).intValue());
        }

        //额外赠送 * 100
        String fristpresentText = dto.getFristpresentText();
        if(StringUtils.isNotBlank(fristpresentText)){
            dto.setFristpresent(GoldUtils.boots(fristpresentText).intValue());
        }

        //商户信息
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Apppayconfig record = new Apppayconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto, record);
        int count = apppayconfigMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeAppConfigIds(Integer[] configids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(configids)){
            list = Arrays.asList(configids);
        }

        int count = 0 ;
        Example example = new Example(Apppayconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",list);
        apppayconfigMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }





}
