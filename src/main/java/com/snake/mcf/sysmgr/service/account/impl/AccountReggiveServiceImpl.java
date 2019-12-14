package com.snake.mcf.sysmgr.service.account.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.DictionaryConstant;
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
import com.snake.mcf.sysmgr.repertory.entity.Registergive;
import com.snake.mcf.sysmgr.repertory.entity.dto.RegistergiveDTO;
import com.snake.mcf.sysmgr.repertory.mapper.RegistergiveMapper;
import com.snake.mcf.sysmgr.service.account.AccountReggiveService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName AccountReggiveServiceImpl
 * @Author 大帅
 * @Date 2019/6/27 9:59
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class AccountReggiveServiceImpl implements AccountReggiveService {

    @Autowired
    private RegistergiveMapper registergiveMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<RegistergiveDTO> queryReggiveWithPage(EasyPageFilter filter, RegistergiveDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(filter) , JsonUtils.toString(dto));
        PageResult<RegistergiveDTO> pageResult = this.queryReggiveWithPage1(filter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<RegistergiveDTO> result = (EasyPageResult<RegistergiveDTO>) pageResult;
            List<RegistergiveDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //注册赠送 /100
                Integer scorecount = rd.getScorecount();
                if(scorecount != null){
                    rd.setScorecountDouble(GoldUtils.reduce(scorecount.longValue()));
                }

                //升级赠送 /100
                Integer upgradescorecount = rd.getUpgradescorecount();
                if(upgradescorecount != null){
                    rd.setUpgradescorecountDouble(GoldUtils.reduce(upgradescorecount.longValue()));
                }

                //游客赠送 /100
                Integer visitorscorecount = rd.getVisitorscorecount();
                if(visitorscorecount != null){
                    rd.setVisitorscorecountDouble(GoldUtils.reduce(visitorscorecount.longValue()));
                }

                Integer platformtype = rd.getPlatformtype();
                if(platformtype != null){
                    String platformtypeDesc = DictionaryConstant.getPlatformtypeDesc(platformtype);
                    rd.setPlatformtypeDesc(platformtypeDesc);
                }

            });

        }
        return pageResult;
    }

    private PageResult<RegistergiveDTO> queryReggiveWithPage1(EasyPageFilter filter, RegistergiveDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Registergive.class);
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
        List<Registergive> list = registergiveMapper.selectByExample(example);
        //结果
        PageResult<RegistergiveDTO> pageResult = easyPageQuery.queryResult(list, RegistergiveDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    // true 存在 false 不存在
    @Override
    public boolean isExistPlatform(RegistergiveDTO dto) {
        if(dto == null){
            throw new BusinessException("保存注册,传入对象 为空", 1004);
        }
        if(dto.getPlatformtype() == null){
            throw new BusinessException("保存注册,传入 platformtype 对象 为空", 1004);
        }
        //判断是否已经存在平台
        Example example = new Example(Registergive.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("platformtype",dto.getPlatformtype());
        Integer configid = dto.getConfigid();
        if(configid != null){
            //criteria.andNotEqualTo("configid",criteria);
            List<Integer> ls = new ArrayList();
            ls.add(configid);
            criteria.andNotIn("configid",ls);
        }
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }
        List<Registergive> list = registergiveMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveReggive(RegistergiveDTO dto) {
        if(dto == null){
            throw new BusinessException("保存注册,传入对象 为空", 1004);
        }
        Integer platformtype = dto.getPlatformtype();
        if(platformtype == null){
            throw new BusinessException("保存注册,传入 platformtype 对象 为空", 1004);
        }
        if(dto.getDiamondcount() == null){
            throw new BusinessException("保存注册,传入 Diamondcount 对象 为空", 1004);
        }
        if(dto.getScorecount() == null){
            throw new BusinessException("保存注册,传入 Scorecount 对象 为空", 1004);
        }
        boolean existPlatform = this.isExistPlatform(dto);
        if(existPlatform){
            throw new BusinessException("保存注册,已存在平台注册", 1004);
        }
        //金币信息
        Integer scorecount = dto.getScorecount();
        //存入金币 * 100
        dto.setScorecount((int)GoldUtils.boots(scorecount.longValue()));
        // 游客赠送 * 100
        Integer visitorscorecount = dto.getVisitorscorecount();
        dto.setVisitorscorecount((int)GoldUtils.boots(visitorscorecount.longValue()));
        // 升级赠送 * 100
        Integer upgradescorecount = dto.getUpgradescorecount();
        dto.setUpgradescorecount((int)GoldUtils.boots(upgradescorecount.longValue()));
        //商户信息
        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());

        //是否是管理员  不是 需要加一个商户号
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }
        dto.setAccount(ShiroUtils.getSessionLoginName());
        //保存
        Registergive record = new Registergive();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = registergiveMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public RegistergiveDTO queryReggiveById(RegistergiveDTO dto) {
        if(dto == null){
            throw new BusinessException("查询注册赠送,传入对象 为空", 1004);
        }
        if(dto.getConfigid() == null){
            throw new BusinessException("保存注册,传入 Configid 对象 为空", 1004);
        }
        //执行查询
        Registergive registergive = registergiveMapper.selectByPrimaryKey(dto.getConfigid());
        RegistergiveDTO registergiveDTO = new RegistergiveDTO();
        CommonBeans.copyPropertiesIgnoreNull(registergive,registergiveDTO);

        //查询结果除以100
        Integer scorecount = registergiveDTO.getScorecount();
        if(scorecount != null){
            registergiveDTO.setScorecountDouble(GoldUtils.reduce(scorecount.longValue()));
            registergiveDTO.setScorecount((int)GoldUtils.reduce(scorecount.longValue()));
        }

        //升级赠送 /100
        Integer upgradescorecount = registergiveDTO.getUpgradescorecount();
        if(upgradescorecount != null){
            registergiveDTO.setUpgradescorecount((int)GoldUtils.reduce(upgradescorecount.longValue()));
            registergiveDTO.setUpgradescorecountDouble(GoldUtils.reduce(upgradescorecount.longValue()));
        }

        //游客赠送 /100
        Integer visitorscorecount = registergiveDTO.getVisitorscorecount();
        if(visitorscorecount != null){
            registergiveDTO.setVisitorscorecount((int)GoldUtils.reduce(visitorscorecount.longValue()));
            registergiveDTO.setVisitorscorecountDouble(GoldUtils.reduce(visitorscorecount.longValue()));
        }

        return registergiveDTO;
    }

    @Override
    public boolean updateReggive(RegistergiveDTO dto) {
        if(dto == null){
            throw new BusinessException("查询注册赠送,传入对象 为空", 1004);
        }
        if(dto.getConfigid() == null){
            throw new BusinessException("保存注册,传入 Configid 对象 为空", 1004);
        }
        //金币信息
        Integer scorecount = dto.getScorecount();
        //存入金币 * 100
        dto.setScorecount((int)GoldUtils.boots(scorecount.longValue()));
        // 游客赠送 * 100
        Integer visitorscorecount = dto.getVisitorscorecount();
        dto.setVisitorscorecount((int)GoldUtils.boots(visitorscorecount.longValue()));
        // 升级赠送 * 100
        Integer upgradescorecount = dto.getUpgradescorecount();
        dto.setUpgradescorecount((int)GoldUtils.boots(upgradescorecount.longValue()));
        //商户信息
        dto.setAccount(ShiroUtils.getSessionLoginName());
        dto.setUpdatedDate(new Date());
        //修改
        Registergive record = new Registergive();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = registergiveMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeReggiveByIds(RegistergiveDTO dto) {
        if(dto == null){
            throw new BusinessException("查询注册赠送,传入对象 为空", 1004);
        }
        if(StringUtils.isBlank(dto.getConfigids())){
            throw new BusinessException("查询注册赠送,传入对象 为空", 1004);
        }

        String[] arr = dto.getConfigids().split(",");
        List<String> idList = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(arr)){
            idList = Arrays.asList(arr);
        }

        int count = 0;
        Example example = new Example(Registergive.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",idList);
        registergiveMapper.deleteByExample(example);
        count++;

        return count > 0;

    }
}
