package com.snake.mcf.sysmgr.controller.web.invest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinepayorderDTO;
import com.snake.mcf.sysmgr.service.invest.InverstRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName InverstRecordController
 * @Author 大帅
 * @Date 2019/6/28 22:01
  * 充值记录功能
 */
@Slf4j
@Controller
@RequestMapping(path = "/invest/record")
public class InverstRecordController extends BaseController {

    @Autowired
    private InverstRecordService inverstRecordService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRecordWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<OnlinepayorderDTO>> queryRecordWithPage(EasyPageFilter pageFilter , OnlinepayorderDTO dto){
        PageResult<OnlinepayorderDTO> pageResult = inverstRecordService.queryRecordWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }


}
