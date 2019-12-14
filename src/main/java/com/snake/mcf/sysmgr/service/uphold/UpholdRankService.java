package com.snake.mcf.sysmgr.service.uphold;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.CacherankawardDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RankingconfigDTO;

public interface UpholdRankService extends BaseService {


    PageResult<RankingconfigDTO> queryRankWithPage(EasyPageFilter pageFilter, RankingconfigDTO dto);

    boolean saveRank(RankingconfigDTO dto);

    boolean isExistRank(RankingconfigDTO dto);

    RankingconfigDTO queryRankById(RankingconfigDTO dto);

    boolean updateRank(RankingconfigDTO dto);

    boolean removeRankByIds(Integer[] configids);

    PageResult<CacherankawardDTO> queryRankAwardWithPage(EasyPageFilter pageFilter, CacherankawardDTO dto);
}
