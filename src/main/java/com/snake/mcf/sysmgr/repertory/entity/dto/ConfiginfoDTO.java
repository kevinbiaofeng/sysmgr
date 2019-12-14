package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ConfiginfoDTO
 * @Author 大帅
 * @Date 2019/6/25 17:06
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ConfiginfoDTO extends Configinfo {

    private static final long serialVersionUID = 1L;

    private String uploadUrl;



}
