package com.snake.mcf.sysmgr.service.website;

import java.util.Map;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.QuestionDTO;

public interface WebsiteQuestionService extends BaseService {


    PageResult<QuestionDTO> queryQuestionWithPage(EasyPageFilter pageFilter, QuestionDTO dto);

    QuestionDTO queryQuestionById(QuestionDTO dto);

    boolean saveQuestion(QuestionDTO dto);

    boolean updateQuestion(QuestionDTO dto);

    boolean removeQuestionByIds(Integer[] ids);
    
    /**
     * 根据商户号获取常见问题
     * @return
     */
    Map<String, Object> queryQuestionList(String _merchant, Integer apiVersion);
}
