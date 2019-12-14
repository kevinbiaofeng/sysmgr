package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Mobilekinditem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName MobilekinditemDTO
 * @Author 大帅
 * @Date 2019/6/30 15:50
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MobilekinditemDTO extends Mobilekinditem {

    private static final long serialVersionUID = 1L;

    private String clientversionDesc;

    private String nullityDesc;

    private String kindmarkDesc;

    private String kindids;



}
