package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Ads;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName AdsDTO
 * @Author 大帅
 * @Date 2019/7/9 15:26
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AdsDTO extends Ads {

    private static final long serialVersionUID = 1L;

    private String typeDesc;

    private String platformtypeDesc;

    private String linkurl2;

    private String linkurl1;

    private String uploadUrl;



}
