package com.snake.mcf.sysmgr.service.website;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.ConfiginfoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface WebsiteStandService extends BaseService {


    PageResult<ConfiginfoDTO> queryConfigInfoWithPage(EasyPageFilter pageFilter, ConfiginfoDTO dto);

    ConfiginfoDTO queryConfigInfoById(ConfiginfoDTO dto);

    Configinfo queryConfigInfoByConfigKey(String configKey);

    /**
     * true 存在 false 不存在
     * @param dto
     * @return
     */
    boolean isExistConfigInfo(ConfiginfoDTO dto);

    boolean saveConfigInfo(MultipartFile file, ConfiginfoDTO dto);

    boolean updateConfigInfo(ConfiginfoDTO dto);

    boolean removeConfigInfoByIds(Integer[] configids);
}
