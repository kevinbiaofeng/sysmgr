package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordgrantgameid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordgrantgameidDTO
 * @Author 大帅
 * @Date 2019/6/25 21:09
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordgrantgameidDTO extends Recordgrantgameid {

    private static final long serialVersionUID = 1L;

    private String masterName;



}
