package com.snake.mcf.sysmgr.controller.web.uphold;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamegameitemDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdGamemdService;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏模块
 *
 * @ClassName UpholdGamemdController
 * @Author 大帅
 * @Date 2019/6/29 16:53
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/gamemd")
public class UpholdGamemdController extends BaseController {

    @Autowired
    private UpholdGamemdService upholdGamemdService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamemdWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamegameitemDTO>> queryGamemdWithPage(EasyPageFilter pageFilter,GamegameitemDTO dto){
        PageResult<GamegameitemDTO> pageResult = upholdGamemdService.queryGamemdWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamemdById")
    @ResponseBody
    public ResponseEntity<Result> queryGamemdById(GamegameitemDTO dto){
        dto = upholdGamemdService.queryGamemdById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 加载下拉数据源
     *
     * @return
     */
    @GetMapping(path = "/loadDataBaseInfo")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadDataBaseInfo(){
        List<ComboBoxDTO> list = upholdGamemdService.loadDataBaseInfo();
        return ResponseEntity.ok(list);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增游戏模块" , description = "系统设置->游戏模块->新增游戏模块功能:新增游戏模块")
    @PostMapping(path = "/saveGamemd")
    @ResponseBody
    public ResponseEntity<Result> saveGamemd(GamegameitemDTO dto){
        boolean fg = upholdGamemdService.isExistGamemd(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("模块标识已存在"));
        }
        boolean flag = upholdGamemdService.saveGamemd(dto);
        Result result = flag ? Result.succ() : Result.fail("保存模块信息失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改游戏模块" , description = "系统设置->游戏模块->修改游戏模块功能:修改游戏模块")
    @PutMapping(path = "/updateGamemd")
    @ResponseBody
    public ResponseEntity<Result> updateGamemd(GamegameitemDTO dto){
        boolean flag = upholdGamemdService.updateGamemd(dto);
        Result result = flag ? Result.succ() : Result.fail("修改模块信息失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除
     *
     * @param gameids
     * @return
     */
    @AopLog(module = "删除游戏模块" , description = "系统设置->游戏模块->删除游戏模块功能:删除游戏模块")
    @DeleteMapping(path = "/removeGamemdByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGamemdByIds(Integer[] gameids){
        boolean flag = upholdGamemdService.removeGamemdByIds(gameids);
        Result result = flag ? Result.succ() : Result.fail("删除模块信息失败");
        return ResponseEntity.ok(result);
    }





}
