package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamepackagegoods;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamepackagegoodsDTO
 * @Author 大帅
 * @Date 2019/7/4 14:41
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamepackagegoodsDTO extends Gamepackagegoods {

    private static final long serialVersionUID = 1L;

    /**
     * 礼包名称
     */
    private String packageName;

    /**
     * 物品类型
     */
    private String typeidDesc;

    /**
     * 道具
     */
    private String propertyidDesc;

    /**
     * 文件所在地址
     */
    private String uploadUrl;

    /**
     * 物品数量
     */
    private Double goodsnumDouble;


}
