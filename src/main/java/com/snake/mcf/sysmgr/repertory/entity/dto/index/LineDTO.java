package com.snake.mcf.sysmgr.repertory.entity.dto.index;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LineDTO
 * @Author 大帅
 * @Date 2019/7/19 10:36
 */
@Data
public abstract class LineDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 横坐标
     */
    private String abscissa;


}
