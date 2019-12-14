package com.snake.mcf.sysmgr.controller.web.goldmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.PersonalroomscoreinfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.StreamcreatetablefeeinfoDTO;
import com.snake.mcf.sysmgr.service.goldmgr.GoldmgrRoommdService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName GoldmgrRoommdController
 * @Author 大帅
 * @Date 2019/7/13 18:09
 */
@Slf4j
@Controller
@RequestMapping(path = "/goldmgr/roommd")
public class GoldmgrRoommdController extends BaseController {

    @Autowired
    private GoldmgrRoommdService goldmgrRoommdService;

    /**
     * 分页查询房间模式
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryStreamCreateTableFeeInfoWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<StreamcreatetablefeeinfoDTO>> queryStreamCreateTableFeeInfoWithPage(EasyPageFilter pageFilter, StreamcreatetablefeeinfoDTO dto){
        log.info("{}","queryStreamCreateTableFeeInfoWithPage");
        PageResult<StreamcreatetablefeeinfoDTO> pageResult = goldmgrRoommdService.queryStreamCreateTableFeeInfoWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 打开查看战绩页面
     *
     * @param recordid
     * @param modelMap
     * @return
     */
    @GetMapping(path = "/toRoommdScorePage")
    public String toRoommdScorePage(Integer recordid, ModelMap modelMap){
        modelMap.put("recordid",recordid);
        return "goldmgr/roommd/roommd_score";
    }

    /**
     * 分页查询战绩
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryPersonalRoomScoreInfoWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<PersonalroomscoreinfoDTO>> queryPersonalRoomScoreInfoWithPage(EasyPageFilter pageFilter, PersonalroomscoreinfoDTO dto){
        log.info("{}","queryPersonalRoomScoreInfoWithPage");
        PageResult<PersonalroomscoreinfoDTO> pageResult = goldmgrRoommdService.queryPersonalRoomScoreInfoWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }


}
