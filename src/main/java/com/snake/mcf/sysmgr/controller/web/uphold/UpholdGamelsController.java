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
import com.snake.mcf.sysmgr.repertory.entity.dto.MobilekinditemDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdGamelsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdGamelsController
 * @Author 大帅
 * @Date 2019/6/30 15:51
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/gamels")
public class UpholdGamelsController extends BaseController {

    @Autowired
    private UpholdGamelsService upholdGamelsService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamelsWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<MobilekinditemDTO>> queryGamelsWithPage(EasyPageFilter pageFilter, MobilekinditemDTO dto){
        PageResult<MobilekinditemDTO> pageResult = upholdGamelsService.queryGamelsWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 加载游戏模块
     *
     * @return
     */
    @GetMapping(path = "/loadGameModuleData")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadGameModuleData(){
        List<ComboBoxDTO> list = upholdGamelsService.loadGameModuleData();
        return ResponseEntity.ok(list);
    }

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增游戏列表" , description = "系统设置->游戏列表->新增游戏列表功能:新增游戏列表")
    @PostMapping(path = "/saveGamels")
    @ResponseBody
    public ResponseEntity<Result> saveGamels(MobilekinditemDTO dto){
        boolean fg = upholdGamelsService.isExistGamels(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("添加的游戏已存在"));
        }
        boolean flag = upholdGamelsService.saveGamels(dto);
        Result result = flag ? Result.succ() : Result.fail("添加游戏操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamelsById")
    @ResponseBody
    public ResponseEntity<Result> queryGamelsById(MobilekinditemDTO dto){
        dto = upholdGamelsService.queryGamelsById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改游戏列表" , description = "系统设置->游戏列表->修改游戏列表功能:修改游戏列表")
    @PutMapping(path = "/updateGamels")
    @ResponseBody
    public ResponseEntity<Result> updateGamels(MobilekinditemDTO dto){
        boolean flag = upholdGamelsService.updateGamels(dto);
        Result result = flag ? Result.succ() : Result.fail("修改游戏操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量操作
     *
     * @param dto
     * @return
     */
    @AopLog(module = "批量启用禁用游戏列表" , description = "系统设置->游戏列表->批量启用禁用游戏列表功能:批量启用禁用游戏列表")
    @PutMapping(path = "/batchTransfer")
    @ResponseBody
    public ResponseEntity<Result> batchTransfer(MobilekinditemDTO dto){
        boolean flag = upholdGamelsService.batchTransfer(dto);
        Result result = flag ? Result.succ() : Result.fail("批量操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量删除
     *
     * @param kindids
     * @return
     */
    @AopLog(module = "删除游戏列表" , description = "系统设置->游戏列表->删除游戏列表功能:删除游戏列表")
    @DeleteMapping(path = "/removeGamelsByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGamelsByIds(Integer[] kindids){
        boolean flag = upholdGamelsService.removeGamelsByIds(kindids);
        Result result = flag ? Result.succ() : Result.fail("删除操作失败");
        return ResponseEntity.ok(result);
    }

}
