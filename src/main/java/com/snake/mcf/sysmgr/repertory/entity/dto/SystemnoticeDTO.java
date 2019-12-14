package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Systemnotice;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName SystemnoticeDTO
 * @Author 大帅
 * @Date 2019/7/10 12:28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SystemnoticeDTO extends Systemnotice {

    private static final long serialVersionUID = 1L;

    private String nullityDesc;

    private String platformtypeDesc;


}
