package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Cacherankaward;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName CacherankawardDTO
 * @Author 大帅
 * @Date 2019/7/2 16:02
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CacherankawardDTO extends Cacherankaward {

    private static final long serialVersionUID = 1L;

    private String collectdateStartStr;

    private String collectdateEndStr;

    private String typeidDesc;

    /**
     * 奖励金币数
     */
    private Double goldDouble;

}
