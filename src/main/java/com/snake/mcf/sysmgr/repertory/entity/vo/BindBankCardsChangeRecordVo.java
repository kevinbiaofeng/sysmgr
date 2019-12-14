package com.snake.mcf.sysmgr.repertory.entity.vo;

import com.snake.mcf.sysmgr.repertory.entity.BindBankCardsChangeRecord;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BindBankCardsChangeRecordVo extends BindBankCardsChangeRecord {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

}
