package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Apppayconfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ApppayconfigDTO
 * @Author 大帅
 * @Date 2019/6/27 18:26
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ApppayconfigDTO extends Apppayconfig {

    private static final long serialVersionUID = 1L;

    /**
     * 充值类型
     */
    private String paytypeDesc;

    /**
     * 首充类型
     */
    private String payidentityDesc;

    /**
     * 充值货币类型
     */
    private String scoretypeDesc;

    /**
     *  额外赠送(首充/普通)
     */
    private String extra;

    /**
     * 充值到账数值
     */
    private Double scoreDouble = 0.0;
    
    /**
 	 * 首冲额外赠送 页面展现
     */
    private String scoreText = "0.0";
    
    /**
 	 * 首冲额外赠送 页面展现
     */
    private String fristpresentText = "0.0";

}
