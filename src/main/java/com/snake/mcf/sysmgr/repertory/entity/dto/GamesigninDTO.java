package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamesignin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamesigninDTO
 * @Author 大帅
 * @Date 2019/7/5 10:15
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamesigninDTO extends Gamesignin {

    private static final long serialVersionUID = 1L;

    private String packageName;

    private String probabilityDesc;

    private String typeidDesc;

    private String nullityDesc;



}
