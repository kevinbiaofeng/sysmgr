package com.snake.mcf.sysmgr.controller.web.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.service.back.BackLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName BackLogController
 * @Author 大帅
 * @Date 2019/7/16 10:56
 */
@Slf4j
@Controller
@RequestMapping(path = "/back/log")
public class BackLogController extends BaseController {

    @Autowired
    private BackLogService backLogService;

    /**
     * 分页查询日志
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySysLogWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<TbSysLogDTO>> querySysLogWithPage(EasyPageFilter pageFilter,TbSysLogDTO dto){
        PageResult<TbSysLogDTO> pageResult = backLogService.querySysLogWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }


}
