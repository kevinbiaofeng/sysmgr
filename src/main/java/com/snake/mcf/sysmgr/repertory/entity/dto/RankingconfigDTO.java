package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Rankingconfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RankingconfigDTO
 * @Author 大帅
 * @Date 2019/7/2 10:47
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RankingconfigDTO extends Rankingconfig {

    private static final long serialVersionUID = 1L;

    private String typeidDesc;

    private String ranktypeDesc;

    /**
     * 奖励金币数
     */
    private Double goldDouble;



}
