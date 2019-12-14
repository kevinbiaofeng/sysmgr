package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Taskinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TaskinfoDTO
 * @Author 大帅
 * @Date 2019/6/27 11:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TaskinfoDTO extends Taskinfo {

    private static final long serialVersionUID = 1L;

    private String tasktypeDesc;

    private String usertypeDesc;

    private String timetypeDesc;

    private String nullityDesc;

    private String taskawardDesc;

    private Long gold;

    private Long diamond;

    private Long medal;




}
