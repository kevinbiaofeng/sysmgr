package com.snake.mcf.sysmgr.service.website.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.ui.paging.query.PagingQuery;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.website.WebsiteQuestionMapper;
import com.snake.mcf.sysmgr.repertory.entity.Question;
import com.snake.mcf.sysmgr.repertory.entity.dto.QuestionDTO;
import com.snake.mcf.sysmgr.repertory.mapper.QuestionMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteQuestionService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName WebsiteQuestionServiceImpl
 * @Author 大帅
 * @Date 2019/7/10 16:25
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WebsiteQuestionServiceImpl extends BaseServiceImpl implements WebsiteQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private PagingQuery<EasyPageFilter> easyPageQuery;

    @Override
    public PageResult<QuestionDTO> queryQuestionWithPage(EasyPageFilter pageFilter, QuestionDTO dto) {
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<QuestionDTO> pageResult = this.queryQuestionWithPage1(pageFilter,dto);
        log.info("分页结果:{}",JsonUtils.toString(pageResult));
        if(pageResult instanceof EasyPageResult) {
            EasyPageResult<QuestionDTO> result = (EasyPageResult<QuestionDTO>) pageResult;
            List<QuestionDTO> rows = result.getRows();

            rows.forEach( (rd) -> {

            });
        }
        return pageResult;
    }

    private PageResult<QuestionDTO> queryQuestionWithPage1(EasyPageFilter filter, QuestionDTO dto) {
        log.info("服务端:page={}",filter.getPage());
        log.info("服务端:rows={}",filter.getRows());
        log.info("easy ui 分页 easyPageQuery = {}" , easyPageQuery);
        //开始分页
        easyPageQuery.startPage(filter);
        //过滤
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        if(dto != null){

            //是否是管理员  不是 需要加一个字段过滤
            if(!ShiroUtils.isAdminSessionUserId()) {
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
        List<Question> list = questionMapper.selectByExample(example);
        //结果
        PageResult<QuestionDTO> pageResult = easyPageQuery.queryResult(list, QuestionDTO.class);
        log.info("分页结果:pageResult={}",JsonUtils.toString(pageResult));
        return pageResult;
    }
    
    @Override
    public Map<String, Object> queryQuestionList(String _merchant, Integer apiVersion){
    	Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
    	EasyPageFilter filter = new EasyPageFilter();
        easyPageQuery.startPage(filter);
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        if(_merchant != null && StringUtils.isNotEmpty(_merchant)) {
        	criteria.andEqualTo("merchant", _merchant);
        }else {
        	criteria.andIsNull("merchant");
        }
        example.selectProperties("id", "questiontitle", "answer", "sortid", "updateat");
        easyPageQuery.sortOrderByClause(filter);
        example.setOrderByClause("sortid ASC");
        List<Question> list = questionMapper.selectByExample(example);
    	List<Map<String, Object>> dataList = null;
    	if(list != null && list.size() > 0) {
    		dataList = new ArrayList<Map<String,Object>>();
    		Map<String, Object> dataMap = null;
    		for (Question question : list) {
    			dataMap = new HashMap<String, Object>();
    			dataMap.put("id", question.getId());
    			dataMap.put("questionTitle", question.getQuestiontitle());
    			dataMap.put("answer", question.getAnswer());
    			dataMap.put("sortId", question.getSortid());
				dataMap.put("startTime", DateUtils.format(question.getUpdateat(), "yyyy-MM-dd HH:mm:ss"));
    			dataList.add(dataMap);
			}
    	}
        data.put("list", dataList);
        return data;
    }

    @Override
    public QuestionDTO queryQuestionById(QuestionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 noticeid 为空", 1004);
        }
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            return null;
        }
        QuestionDTO record = new QuestionDTO();
        CommonBeans.copyPropertiesIgnoreNull(question,record);
        return record;
    }

    @Override
    public boolean saveQuestion(QuestionDTO dto) {

        dto.setCreatedDate(new Date());
        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            dto.setMerchant(ShiroUtils.getSessionMerchant());
        }

        Question record = new Question();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = questionMapper.insertSelective(record);
        return count > 0;
    }

    @Override
    public boolean updateQuestion(QuestionDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入对象为空", 1004);
        }
        Integer id = dto.getId();
        if(id == null){
            throw new BusinessException("根据id查询,传入对象 noticeid 为空", 1004);
        }

        dto.setUpdatedDate(new Date());
        dto.setAccount(ShiroUtils.getSessionLoginName());

        Question record = new Question();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = questionMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    @Override
    public boolean removeQuestionByIds(Integer[] ids) {

        List<Integer> list = new ArrayList<>();
        if(ArrayUtils.isNotEmpty(ids)){
            list = Arrays.asList(ids);
        }

        int count = 0;
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",list);
        questionMapper.deleteByExample(example);
        count ++;
        return count > 0;
    }
}
