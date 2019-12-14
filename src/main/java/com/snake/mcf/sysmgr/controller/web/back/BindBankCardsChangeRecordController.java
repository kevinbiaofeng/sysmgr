package com.snake.mcf.sysmgr.controller.web.back;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindBankCardsChangeRecordDto;
import com.snake.mcf.sysmgr.repertory.entity.vo.BindBankCardsChangeRecordVo;
import com.snake.mcf.sysmgr.service.back.impl.BindBankCardsChangeRecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BindBankCardsChangeRecordController
 * @Author robin
 * @Date 2019/10/21 16:00
 */
@Slf4j
@RestController
@RequestMapping(path = "/back/BindBankCardsChangeRecord")
public class BindBankCardsChangeRecordController extends BaseController {

    @Autowired
    private BindBankCardsChangeRecordServiceImpl bindBankCardsChangeRecordService;

    /**
     * 分页查询银行卡变更记录
     *
     * @param pageFilter
     * @param vo
     * @return
     */
    @PostMapping(path = "/queryBindBankCardsChangeRecordWithPage")
    public ResponseEntity<PageResult<BindBankCardsChangeRecordDto>> queryBindBankCardsChangeRecordWithPage(EasyPageFilter pageFilter, BindBankCardsChangeRecordVo vo) {
        PageResult<BindBankCardsChangeRecordDto> pageResult = bindBankCardsChangeRecordService.queryBindBankCardsChangeRecordWithPage(pageFilter, vo);
        return ResponseEntity.ok(pageResult);
    }


}
