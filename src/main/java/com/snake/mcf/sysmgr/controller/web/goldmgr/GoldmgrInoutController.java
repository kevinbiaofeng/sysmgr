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
import com.snake.mcf.sysmgr.repertory.entity.dto.RecorduserinoutDTO;
import com.snake.mcf.sysmgr.repertory.entity.vo.RecorduserinoutVO;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrInoutService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GoldmgrInoutController
 * @Author 大帅
 * @Date 2019/7/12 10:05
 */
@Slf4j
@Controller
@RequestMapping(path = "/goldmgr/inout")
public class GoldmgrInoutController extends BaseController {

    @Autowired
    private GoldmgrInoutService goldmgrInoutService;

    /**
     * 分页查询 进出记录
     *
     * @param pageFilter
     * @param vo
     * @return
     */
    @GetMapping(path = "/queryRecordUserInoutWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecorduserinoutDTO>> queryRecordUserInoutWithPage(EasyPageFilter pageFilter, RecorduserinoutVO vo) {
        log.info("{}","queryRecordUserInoutWithPage");
        PageResult<RecorduserinoutDTO> pageResult = goldmgrInoutService.queryRecordUserInoutWithPage(pageFilter,vo);

        return ResponseEntity.ok(pageResult);
    }


}
