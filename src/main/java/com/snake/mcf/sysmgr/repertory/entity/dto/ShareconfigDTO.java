package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Shareconfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ShareconfigDTO
 * @Author 大帅
 * @Date 2019/7/6 10:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ShareconfigDTO extends Shareconfig {

    private static final long serialVersionUID = 1L;

    private String nullityDesc;

    /**
     * 每日分享获得金币
     */
    private Double timesharegoldDouble;


}
