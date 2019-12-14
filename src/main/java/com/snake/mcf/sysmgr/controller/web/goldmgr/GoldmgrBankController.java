package com.snake.mcf.sysmgr.controller.web.goldmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordinsureDTO;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrBankService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GoldmgrBankController
 * @Author 大帅
 * @Date 2019/7/11 15:42
 */
@Slf4j
@Controller
@RequestMapping(path = "/goldmgr/bank")
public class GoldmgrBankController extends BaseController {

    @Autowired
    private GoldmgrBankService goldmgrBankService;

    /**
     * 分页查询 银行记录
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRecordInsureWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordinsureDTO>> queryRecordInsureWithPage(EasyPageFilter pageFilter, RecordinsureDTO dto){
        log.info("{}","queryRecordInsureWithPage");
        PageResult<RecordinsureDTO> pageResult = goldmgrBankService.queryRecordInsureWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

}
