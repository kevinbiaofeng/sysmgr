package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamescorelocker;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamescorelockerDTO
 * @Author 大帅
 * @Date 2019/6/26 16:22
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamescorelockerDTO extends Gamescorelocker {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户游戏ID
     */
    private Integer gameId;

    /**
     * 所在游戏
     */
    private String kindName;

    /**
     * 所在房间
     */
    private String serverName;

    private String userIds;

}
