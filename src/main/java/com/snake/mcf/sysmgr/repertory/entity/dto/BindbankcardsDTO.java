package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Bindbankcards;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName BindbankcardsDTO
 * @Author 大帅
 * @Date 2019/7/24 17:43
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BindbankcardsDTO extends Bindbankcards {

    private static final long serialVersionUID = 1L;

    private String bankchoiceDesc;

    private String nullityDesc;

    private String typeNameDesc;

}
