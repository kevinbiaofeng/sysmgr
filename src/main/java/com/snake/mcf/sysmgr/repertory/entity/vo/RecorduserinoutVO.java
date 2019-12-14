package com.snake.mcf.sysmgr.repertory.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecorduserinoutVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //id
    private Integer gameid;
    //游戏名称
    private String kindname;
    //用户昵称
    private String nickName;
    //进入时间
    private String entertimeEndStr;
    //结束时间
    private String entertimeStartStr;

}
