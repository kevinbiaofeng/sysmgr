package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Onlinewechat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName OnlinewechatDTO
 * @Author 大帅
 * @Date 2019/6/28 18:04
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OnlinewechatDTO extends Onlinewechat {

    private static final long serialVersionUID = 1L;

    /**
     * 标签类型
     */
    private String tagidDesc;

    /**
     * 禁用标识
     */
    private String nullityDesc;



}
