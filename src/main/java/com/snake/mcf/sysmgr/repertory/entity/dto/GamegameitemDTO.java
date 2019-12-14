package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamegameitem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamegameitemDTO
 * @Author 大帅
 * @Date 2019/6/29 17:18
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamegameitemDTO extends Gamegameitem {

    private static final long serialVersionUID = 1L;

    private String clientversionDesc;

    private String serverversionDesc;




}
