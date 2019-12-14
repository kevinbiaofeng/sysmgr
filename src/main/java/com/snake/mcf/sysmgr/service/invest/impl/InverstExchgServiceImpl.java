package com.snake.mcf.sysmgr.service.invest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.invest.InverstExchgMapper;
import com.snake.mcf.sysmgr.repertory.entity.Currencyexchconfig;
import com.snake.mcf.sysmgr.repertory.entity.dto.CurrencyexchconfigDTO;
import com.snake.mcf.sysmgr.repertory.mapper.CurrencyexchconfigMapper;
import com.snake.mcf.sysmgr.service.invest.InverstExchgService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName InverstExchgServiceImpl
 * @Author 大帅
 * @Date 2019/6/28 15:10
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class InverstExchgServiceImpl extends BaseServiceImpl implements InverstExchgService {

    @Autowired
    private InverstExchgMapper inverstExchgMapper;

    @Autowired
    private CurrencyexchconfigMapper currencyexchconfigMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<CurrencyexchconfigDTO> queryExchgWithPage(EasyPageFilter pageFilter, CurrencyexchconfigDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<CurrencyexchconfigDTO> pageResult = this.queryExchgWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<CurrencyexchconfigDTO> result = (EasyPageResult<CurrencyexchconfigDTO>) pageResult;
            List<CurrencyexchconfigDTO> rows = result.getRows();

            rows.forEach((rd)->{

                //赠送金币数
                Long exchgold = rd.getExchgold();
                if(exchgold != null){
                    //rd.setExchgoldDouble(Double.valueOf(exchgold/100));
                    rd.setExchgoldDouble(GoldUtils.reduce(exchgold.longValue()));
                }

            });

        }
        return pageResult;
    }

    private PageResult<CurrencyexchconfigDTO> queryExchgWithPage1(EasyPageFilter pageFilter, CurrencyexchconfigDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(Currencyexchconfig.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            String configname = dto.getConfigname();
            if(StringUtils.isNotBlank(configname)){
                criteria.andLike("configname", PayplatformConstant.SPLIT_SYMBOL_PER + configname + PayplatformConstant.SPLIT_SYMBOL_PER);
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
        List<Currencyexchconfig> list = currencyexchconfigMapper.selectByExample(example);
        //结果
        PageResult<CurrencyexchconfigDTO> pageResult = easyPageQuery.queryResult(list, CurrencyexchconfigDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    // true 存在  false 不存在
    @Override
    public boolean isExistDiamond(CurrencyexchconfigDTO dto) {
       Integer diamond = dto.getDiamond();
        if(diamond == null){
            return false;
        }
        //查询
        Example example = new Example(Currencyexchconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("diamond",diamond);
        if(dto.getConfigid() != null){
            criteria.andNotEqualTo("configid",dto.getConfigid());
        }

        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
        }

        List<Currencyexchconfig> list = currencyexchconfigMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(list);
    }

    @Override
    public boolean saveExchConfig(CurrencyexchconfigDTO dto) {
       /* boolean existDiamond = this.isExistDiamond(dto);
        if(existDiamond){
            return false;
        }*/
        //赠送金币数 * 100
        Long exchgold = dto.getExchgold();
        //dto.setExchgold(exchgold * 100);
        if(exchgold != null){
            dto.setExchgold(GoldUtils.boots(exchgold.longValue()));
        }

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Currencyexchconfig record = new Currencyexchconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = currencyexchconfigMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public CurrencyexchconfigDTO queryExchgById(CurrencyexchconfigDTO dto) {
        log.info("{}",JsonUtils.toString(dto));
        if(dto == null) {
            throw new BusinessException("查询充值配置,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null) {
            throw new BusinessException("查询充值配置,传入对象 configid 为空", 1004);
        }
        Currencyexchconfig record = currencyexchconfigMapper.selectByPrimaryKey(configid);
        CurrencyexchconfigDTO dto1 = new CurrencyexchconfigDTO();
        CommonBeans.copyPropertiesIgnoreNull(record,dto1);

        // 赠送金币数 / 100
        Long exchgold = dto1.getExchgold();
        if(exchgold != null){
//            dto1.setExchgold(exchgold/100);
//            dto1.setExchgoldDouble(Double.valueOf(exchgold/100));
            dto1.setExchgold((long)GoldUtils.reduce(exchgold.longValue()));
            dto1.setExchgoldDouble(GoldUtils.reduce(exchgold.longValue()));
        }

        return dto1;
    }

    @Override
    public boolean updateExchConfig(CurrencyexchconfigDTO dto) {
        /*boolean existAppleid = this.isExistDiamond(dto);
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

        //赠送金币数 * 100
        Long exchgold = dto.getExchgold();
        //dto.setExchgold(exchgold * 100);
        if(exchgold != null){
            dto.setExchgold(GoldUtils.boots(exchgold.longValue()));
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Currencyexchconfig record = new Currencyexchconfig();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = currencyexchconfigMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeExchgConfigIds(Integer[] configids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(configids)){
            list = Arrays.asList(configids);
        }

        int count = 0 ;
        Example example = new Example(Currencyexchconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",list);
        currencyexchconfigMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }




}
