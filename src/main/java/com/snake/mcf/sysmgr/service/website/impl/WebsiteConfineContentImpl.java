package com.snake.mcf.sysmgr.service.website.impl;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.ConfineContent;
import com.snake.mcf.sysmgr.repertory.mapper.ConfineContentMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteConfineContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName WebsiteNewsServiceImpl
 * @Author 大帅
 * @Date 2019/7/10 12:32
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteConfineContentImpl extends BaseServiceImpl implements WebsiteConfineContentService {

    @Autowired
    private ConfineContentMapper confineContentMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<ConfineContent> queryConfineContentService(EasyPageFilter pageFilter, ConfineContent entity) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter), JsonUtils.toString(entity));
        PageResult<ConfineContent> pageResult = this.queryConfineContentWithPage1(pageFilter, entity);
        log.info("分页结果:{}", JsonUtils.toString(pageResult));
        return pageResult;
    }

    private PageResult<ConfineContent> queryConfineContentWithPage1(EasyPageFilter filter, ConfineContent entity) {
        log.info("服务端:page={}", filter.getPage());
        log.info("服务端:rows={}", filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}", easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(ConfineContent.class);
        Example.Criteria criteria = example.createCriteria();
        if (entity != null) {
            String string = entity.getString();
            if (null != string) {
                criteria.andLike("string", PayplatformConstant.SPLIT_SYMBOL_PER + string + PayplatformConstant.SPLIT_SYMBOL_PER);
            }
        }
        //排序
        String orderByClause = easyPageQuery.sortOrderByClause(filter);
        log.info("排序:orderByClause={}", orderByClause);
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<ConfineContent> list = confineContentMapper.selectByExample(example);
        //结果
        PageResult<ConfineContent> pageResult = easyPageQuery.queryResult(list, ConfineContent.class);
        log.info("分页结果:pageResult={}", JsonUtils.toString(pageResult));
        return pageResult;
    }

    @Override
    public ConfineContent queryConfineContentById(ConfineContent entity) {
        if (entity == null) {
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer contentId = entity.getContentId();
        if (contentId == null) {
            throw new BusinessException("根据id查询,传入对象 contentId 为空", 1004);
        }
        ConfineContent confineContent = confineContentMapper.selectByPrimaryKey(contentId);
        if (confineContent == null) {
            return null;
        }
        return confineContent;
    }

    @Override
    public boolean saveConfineContent(ConfineContent entity) {
        Example example = new Example(ConfineContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("string", entity.getString());
        List<ConfineContent> list = confineContentMapper.selectByExample(example);
        if (list.size() > 0) {
            return false;
        }
        if (null == entity.getEnjoinOverDate()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.DF_YYYY_MM_DD_HH_MM_SS);
            try {
                entity.setEnjoinOverDate(simpleDateFormat.parse("2099-12-31 23:59:59"));
            } catch (ParseException e) {
                log.error("有效时间格式转化失败");
            }
        }
        entity.setCollectDate(new Date());
        int count = confineContentMapper.insertSelective(entity);
        return count > 0;
    }

    @Override
    public boolean updateConfineContent(ConfineContent entity) {
        if (entity == null) {
            throw new BusinessException("根据id修改,传入对象为空", 1004);
        }
        Integer contentId = entity.getContentId();
        if (contentId == null) {
            throw new BusinessException("根据id修改,传入对象 contentId 为空", 1004);
        }

        int count = confineContentMapper.updateByPrimaryKeySelective(entity);
        return count > 0;
    }

    @Override
    public boolean removeConfineContentByIds(Integer[] contentIds) {
        List<Integer> list = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(contentIds)) {
            list = Arrays.asList(contentIds);
        }

        int count = 0;
        Example example = new Example(ConfineContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("contentId", list);
        confineContentMapper.deleteByExample(example);
        count++;
        return count > 0;
    }
}
