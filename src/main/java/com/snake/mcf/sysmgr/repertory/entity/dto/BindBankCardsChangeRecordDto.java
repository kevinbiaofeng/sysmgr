package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.BindBankCardsChangeRecord;
import lombok.Data;

@Data
public class BindBankCardsChangeRecordDto extends BindBankCardsChangeRecord {
    private String bankChoiceDesc;
}
