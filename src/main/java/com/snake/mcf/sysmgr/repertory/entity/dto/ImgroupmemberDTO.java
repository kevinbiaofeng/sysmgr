package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Imgroupmember;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ImgroupmemberDTO
 * @Author 大帅
 * @Date 2019/7/15 20:38
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ImgroupmemberDTO extends Imgroupmember {

    private static final long serialVersionUID = 1L;

    private Integer gameid;

    private String nickname;




}
