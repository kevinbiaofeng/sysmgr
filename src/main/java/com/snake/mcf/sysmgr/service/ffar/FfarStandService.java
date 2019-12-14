package com.snake.mcf.sysmgr.service.ffar;

import java.util.List;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupoptionDTO;

public interface FfarStandService extends BaseService {

    List<ImgroupoptionDTO> queryIMGroupOptionList();

    ImgroupoptionDTO queryIMGroupOptionInfoByName(ImgroupoptionDTO dto);

    boolean updateIMGroupOption(ImgroupoptionDTO dto);


    //PageResult<ImgroupoptionDTO> queryIMGroupOptionWithPage(EasyPageFilter pageFilter, ImgroupoptionDTO dto);

    //ImgroupoptionDTO queryIMGroupOptionById(ImgroupoptionDTO dto);

    /**
     * 是否存在 配置  true 存在 false 不存在
     *
     * @param dto
     * @return
     */
    //boolean isExistIMGroupOption(ImgroupoptionDTO dto);

    //boolean saveIMGroupOption(ImgroupoptionDTO dto);

    //boolean removeIMGroupOptionByIds(String[] optionnames);
}
