package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamepackage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamepackageDTO
 * @Author 大帅
 * @Date 2019/7/4 10:10
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamepackageDTO extends Gamepackage {

    private static final long serialVersionUID = 1L;

    private String nullityDesc;

    private String platformkindDesc;

    private String typeidDesc;



}
