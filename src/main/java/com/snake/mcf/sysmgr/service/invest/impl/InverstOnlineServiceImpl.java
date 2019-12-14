package com.snake.mcf.sysmgr.service.invest.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.invest.InverstOnlineMapper;
import com.snake.mcf.sysmgr.repertory.entity.Onlinewechat;
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinewechatDTO;
import com.snake.mcf.sysmgr.repertory.mapper.OnlinewechatMapper;
import com.snake.mcf.sysmgr.service.invest.InverstOnlineService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName InverstOnlineServiceImpl
 * @Author 大帅
 * @Date 2019/6/28 18:13
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class InverstOnlineServiceImpl extends BaseServiceImpl implements InverstOnlineService {

    @Autowired
    private InverstOnlineMapper inverstOnlineMapper;

    @Autowired
    private OnlinewechatMapper onlinewechatMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<OnlinewechatDTO> queryOnLineWithPage(EasyPageFilter pageFilter, OnlinewechatDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<OnlinewechatDTO> pageResult = this.queryOnLineWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<OnlinewechatDTO> result = (EasyPageResult<OnlinewechatDTO>) pageResult;
            List<OnlinewechatDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                //是否禁用
                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                //标签类型
                Integer tagid = rd.getTagid();
                if(tagid != null){
                    String tagidDesc = DictionaryConstant.getTagidDesc(tagid);
                    rd.setTagidDesc(tagidDesc);
                }

            });

        }
        return pageResult;
    }

    private PageResult<OnlinewechatDTO> queryOnLineWithPage1(EasyPageFilter pageFilter, OnlinewechatDTO dto) {
        log.info("服务端:page={}",pageFilter.getPage());
        log.info("服务端:rows={}",pageFilter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(pageFilter);
        //过滤
        Example example = new Example(Onlinewechat.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            String configname = dto.getConfigname();
            if(StringUtils.isNotBlank(configname)){
                criteria.andLike("configname", PayplatformConstant.SPLIT_SYMBOL_PER + configname + PayplatformConstant.SPLIT_SYMBOL_PER);
            }

            Integer tagid = dto.getTagid();
            if(tagid != null){
                criteria.andEqualTo("tagid",tagid);
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
            example.setOrderByClause(" CollectDate DESC");
        }
        //查询
        List<Onlinewechat> list = onlinewechatMapper.selectByExample(example);
        //结果
        PageResult<OnlinewechatDTO> pageResult = easyPageQuery.queryResult(list, OnlinewechatDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public boolean saveOnLineWeChat(OnlinewechatDTO dto) {

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }
        dto.setAccount(ShiroUtils.getSessionLoginName());

        dto.setCollectdate(new Date());
        Onlinewechat record = new Onlinewechat();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = onlinewechatMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public OnlinewechatDTO queryOnLineWeChatById(OnlinewechatDTO dto) {
        log.info("{}",JsonUtils.toString(dto));
        if(dto == null) {
            throw new BusinessException("查询充值配置,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null) {
            throw new BusinessException("查询充值配置,传入对象 configid 为空", 1004);
        }
        Onlinewechat onlinewechat = onlinewechatMapper.selectByPrimaryKey(configid);
        OnlinewechatDTO dto1 = new OnlinewechatDTO();
        CommonBeans.copyPropertiesIgnoreNull(onlinewechat,dto1);
        return dto1;
    }

    @Override
    public boolean updateOnLineWeChat(OnlinewechatDTO dto) {
        log.info("{}",JsonUtils.toString(dto));
        if(dto == null) {
            throw new BusinessException("修改充值配置,传入对象为空", 1004);
        }
        Integer configid = dto.getConfigid();
        if(configid == null) {
            throw new BusinessException("修改充值配置,传入对象 configid 为空", 1004);
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Onlinewechat record = new Onlinewechat();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = onlinewechatMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeOnLineByIds(Integer[] configids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(configids)){
            list = Arrays.asList(configids);
        }

        int count = 0 ;
        Example example = new Example(Onlinewechat.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("configid",list);
        onlinewechatMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
