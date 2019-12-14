package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gamerule;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GameruleDTO
 * @Author 大帅
 * @Date 2019/7/8 17:41
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GameruleDTO extends Gamerule {

    private static final long serialVersionUID = 1L;

    private String nullityDesc;

    private String typeDesc;

    private String uploadUrl;

    private String ids;







}
