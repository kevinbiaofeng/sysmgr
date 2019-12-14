package com.snake.mcf.sysmgr.service.index;

import java.util.Map;

import com.snake.mcf.sysmgr.base.BaseService;

/**
 * 首页查询
 */
public interface IndexService extends BaseService {


    /**
     * 查询首页展示信息
     *
     * @return
     */
    Map<String, Object> queryIndexFront();
}
