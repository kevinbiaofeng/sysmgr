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
import com.snake.mcf.sysmgr.repertory.entity.dto.RecorddrawinfoDTO;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrGamerdService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GoldmgrGamerdController
 * @Author 大帅
 * @Date 2019/7/12 14:29
 */
@Slf4j
@Controller
@RequestMapping(path = "/goldmgr/gamerd")
public class GoldmgrGamerdController extends BaseController {

    @Autowired
    private GoldmgrGamerdService goldmgrGamerdService;

    /**
     * 分页查询 游戏记录
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRecordDrawInfoWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecorddrawinfoDTO>> queryRecordDrawInfoWithPage(EasyPageFilter pageFilter, RecorddrawinfoDTO dto){
        log.info("{}","queryRecordUserInoutWithPage");
        PageResult<RecorddrawinfoDTO> pageResult = goldmgrGamerdService.queryRecordDrawInfoWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }


}
