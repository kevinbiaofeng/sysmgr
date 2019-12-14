package com.snake.mcf.sysmgr.service.website;

import java.util.Map;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.GameruleDTO;

public interface WebsiteRuleService extends BaseService {

    PageResult<GameruleDTO> queryGameRuleWithPage(EasyPageFilter pageFilter, GameruleDTO dto);

    /**
     * 是否存在游戏规则
     *
     * @param dto
     * @return true 存在 false 不存在
     */
    boolean isExistGameRule(GameruleDTO dto);

    boolean saveGameRule(GameruleDTO dto);

    GameruleDTO queryGameRuleById(GameruleDTO dto);

    boolean batchNullityUpdate(GameruleDTO dto);

    boolean removeGameRuleByIds(Integer[] ids);
    
    /**
     * 获取游戏规则 客户端API接口
     * @param _userId	添加用户游戏规则，暂未实现功能
     * @param apiVersion
     * @return
     */
    public Map<String, Object> queryGameRuleList(String _userId, Integer apiVersion);
}
