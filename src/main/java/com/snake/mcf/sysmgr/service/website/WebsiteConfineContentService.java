package com.snake.mcf.sysmgr.service.website;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.ConfineContent;

public interface WebsiteConfineContentService extends BaseService {

    PageResult<ConfineContent> queryConfineContentService(EasyPageFilter pageFilter, ConfineContent entity);

    ConfineContent queryConfineContentById(ConfineContent entity);

    boolean saveConfineContent(ConfineContent entity);

    boolean updateConfineContent(ConfineContent entity);

    boolean removeConfineContentByIds(Integer[] contentIds);
}
