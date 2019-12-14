package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName GameInfoGroup
 * @Author 大帅
 * @Date 2019/7/1 14:25
 */
@Data
public class GameInfoGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String text;

    private String state;

    private boolean checked = false;

    private List<GameInfoGroup> children;

}
