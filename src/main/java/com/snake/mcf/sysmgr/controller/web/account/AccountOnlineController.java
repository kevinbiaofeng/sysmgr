package com.snake.mcf.sysmgr.controller.web.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamescorelockerDTO;
import com.snake.mcf.sysmgr.service.account.AccountOnlineService;

import lombok.extern.slf4j.Slf4j;

/**
 * 在线玩家
 *
 * @ClassName AccountOnlineController
 * @Author 大帅
 * @Date 2019/6/26 15:00
 */
@Slf4j
@Controller
@RequestMapping(path = "/account/online")
public class AccountOnlineController extends BaseController {

    @Autowired
    private AccountOnlineService accountOnlineService;

    /**
     * 加载游戏房间
     *
     * @param nullity
     * @return
     */
    @GetMapping(path = "/loadGameRoomComboData")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadGameRoomComboData(Integer nullity){
        List<ComboBoxDTO> list = accountOnlineService.loadGameRoomComboData(nullity);
        return ResponseEntity.ok(list);
    }

    /**
     * 分页查询在线人数信息
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamescorelockerWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamescorelockerDTO>> queryGamescorelockerWithPage(EasyPageFilter pageFilter, GamescorelockerDTO dto){
        PageResult<GamescorelockerDTO> pageResult = accountOnlineService.queryGamescorelockerWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 清除卡线玩家
     *
     * @param dto
     * @return
     */
    @AopLog(module = "清除卡线玩家" , description = "用户系统->在线玩家->清除卡线功能:将用户踢出下线")
    @PutMapping(path = "/batchCleanLocker")
    @ResponseBody
    public ResponseEntity<Result> batchCleanLocker(GamescorelockerDTO dto){
        boolean flag = accountOnlineService.batchCleanLocker(dto);
        Result result = flag ? Result.succ() : Result.fail("卡线清除失败");
        return ResponseEntity.ok(result);
    }




}
