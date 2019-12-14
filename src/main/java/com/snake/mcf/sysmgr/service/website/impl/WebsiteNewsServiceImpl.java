package com.snake.mcf.sysmgr.service.website.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.constant.DictionaryConstant;
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
import com.snake.mcf.sysmgr.mapper.website.WebsiteNewsMapper;
import com.snake.mcf.sysmgr.repertory.entity.Systemnotice;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemnoticeDTO;
import com.snake.mcf.sysmgr.repertory.mapper.SystemnoticeMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteNewsService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName WebsiteNewsServiceImpl
 * @Author 大帅
 * @Date 2019/7/10 12:32
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteNewsServiceImpl extends BaseServiceImpl implements WebsiteNewsService {

    @Autowired
    private WebsiteNewsMapper websiteNewsMapper;

    @Autowired
    private SystemnoticeMapper systemnoticeMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<SystemnoticeDTO> querySystemNoticeWithPage(EasyPageFilter pageFilter, SystemnoticeDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<SystemnoticeDTO> pageResult = this.querySystemNoticeWithPage1(pageFilter,dto);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<SystemnoticeDTO> result = (EasyPageResult<SystemnoticeDTO>) pageResult;
            List<SystemnoticeDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

                Integer nullity = rd.getNullity();
                if(nullity != null){
                    String nullityDesc = DictionaryConstant.getNullityDesc(nullity);
                    rd.setNullityDesc(nullityDesc);
                }

                Integer platformtype = rd.getPlatformtype();
                if(platformtype != null){
                    String platformtypeDesc = DictionaryConstant.getAdPlatformtypeDesc(platformtype);
                    rd.setPlatformtypeDesc(platformtypeDesc);
                }

            });
        }
        return pageResult;
    }

    private PageResult<SystemnoticeDTO> querySystemNoticeWithPage1(EasyPageFilter filter, SystemnoticeDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Systemnotice.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            Integer platformtype = dto.getPlatformtype();
            if(platformtype != null){
                criteria.andEqualTo("platformtype" , platformtype);
            }

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
                criteria.andEqualTo("merchant",ShiroUtils.getSessionMerchant());
            }

        }
        //排序
        filter.setSort("SortID");
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}",orderByClause);
        if(StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Systemnotice> list = systemnoticeMapper.selectByExample(example);
        //结果
        PageResult<SystemnoticeDTO> pageResult = easyPageQuery.queryResult(list, SystemnoticeDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public SystemnoticeDTO querySystemNoticeById(SystemnoticeDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer noticeid = dto.getNoticeid();
        if(noticeid == null){
            throw new BusinessException("根据id查询,传入对象 noticeid 为空", 1004);
        }
        Systemnotice systemnotice = systemnoticeMapper.selectByPrimaryKey(noticeid);
        if(systemnotice == null){
            return null;
        }
        SystemnoticeDTO record = new SystemnoticeDTO();
        CommonBeans.copyPropertiesIgnoreNull(systemnotice,record);
        return record;
    }

    @Override
    public boolean saveSystemNotice(SystemnoticeDTO dto) {

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Systemnotice record = new Systemnotice();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = systemnoticeMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateSystemNotice(SystemnoticeDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        Integer noticeid = dto.getNoticeid();
        if(noticeid == null){
            throw new BusinessException("根据id修改,传入对象 noticeid 为空", 1004);
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Systemnotice record = new Systemnotice();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = systemnoticeMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeSystemNoticeByIds(Integer[] noticeids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(noticeids)){
            list = Arrays.asList(noticeids);
        }

        int count = 0;
        Example example = new Example(Systemnotice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("noticeid",list);
        systemnoticeMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
