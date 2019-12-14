package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Currencyexchconfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName CurrencyexchconfigDTO
 * @Author 大帅
 * @Date 2019/6/28 15:07
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CurrencyexchconfigDTO extends Currencyexchconfig {

    private static final long serialVersionUID = 1L;

    /**
     * 赠送金币数
     */
    private Double exchgoldDouble;




}
