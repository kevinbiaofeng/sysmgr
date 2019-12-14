package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Personalroomscoreinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName PersonalroomscoreinfoDTO
 * @Author 大帅
 * @Date 2019/7/13 22:48
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PersonalroomscoreinfoDTO extends Personalroomscoreinfo {

    private static final long serialVersionUID = 1L;

    private Integer recordid;

    private Integer gameid;

    private String nickname;

    private Double scoreDouble;

    private Long allCount;

}
