package com.snake.mcf.sysmgr.controller.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgranttreasureDTO;
import com.snake.mcf.sysmgr.service.account.AccountGivegoldService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AccountGivegoldController
 * @Author 大帅
 * @Date 2019/6/26 19:03
 */
@Slf4j
@Controller
@RequestMapping(path = "/account/givegold")
public class AccountGivegoldController extends BaseController {

    @Autowired
    private AccountGivegoldService accountGivegoldService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGivegoldWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordgranttreasureDTO>> queryGivegoldWithPage(EasyPageFilter pageFilter, RecordgranttreasureDTO dto){
        PageResult<RecordgranttreasureDTO> pageResult = accountGivegoldService.queryGivegoldWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }



}
