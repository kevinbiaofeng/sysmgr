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
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemmessageDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.GameInfoGroup;
import com.snake.mcf.sysmgr.service.uphold.UpholdSysmsgService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdSysmsgController
 * @Author 大帅
 * @Date 2019/7/1 11:13
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/sysmsg")
public class UpholdSysmsgController extends BaseController {

    @Autowired
    private UpholdSysmsgService upholdSysmsgService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySysmsgWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<SystemmessageDTO>> querySysmsgWithPage(EasyPageFilter pageFilter,SystemmessageDTO dto){
        PageResult<SystemmessageDTO> pageResult = upholdSysmsgService.querySysmsgWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 加载游戏信息
     *
     * @return
     */
    @GetMapping(path = "/loadGameIndoData")
    @ResponseBody
    public ResponseEntity<List<GameInfoGroup>> loadGameIndoData(Integer id){
        List<GameInfoGroup> list = upholdSysmsgService.loadGameIndoData(id);
        return ResponseEntity.ok(list);
    }

    /**
     * 保存系统消息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增系统消息" , description = "系统设置->系统消息->新增系统消息功能:新增系统消息")
    @PostMapping(path = "/saveSysmsg")
    @ResponseBody
    public ResponseEntity<Result> saveSysmsg(SystemmessageDTO dto){
        boolean flag = upholdSysmsgService.saveSysmsg(dto);
        Result result = flag ? Result.succ() : Result.fail("保存系统消息成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySysmsgById")
    @ResponseBody
    public ResponseEntity<Result> querySysmsgById(SystemmessageDTO dto){
        dto = upholdSysmsgService.querySysmsgById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改系统消息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改系统消息" , description = "系统设置->系统消息->修改系统消息功能:修改系统消息")
    @PutMapping(path = "/updateSysmsg")
    @ResponseBody
    public ResponseEntity<Result> updateSysmsg(SystemmessageDTO dto){
        boolean flag = upholdSysmsgService.updateSysmsg(dto);
        Result result = flag ? Result.succ() : Result.fail("修改系统消息成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量启用 禁用 系统消息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "批量启用/禁用系统消息" , description = "系统设置->系统消息->批量启用/禁用系统消息功能:批量启用/禁用系统消息,0是启用 1是禁用")
    @PutMapping(path = "/batchTransfer")
    @ResponseBody
    public ResponseEntity<Result> batchTransfer(SystemmessageDTO dto){
        boolean flag = upholdSysmsgService.batchTransfer(dto);
        Result result = flag ? Result.succ() : Result.fail("修改系统消息成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除系统消息
     *
     * @param ids
     * @return
     */
    @AopLog(module = "批量删除系统消息" , description = "系统设置->系统消息->批量删除系统消息功能:批量删除系统消息")
    @DeleteMapping(path = "/removeSysmsgByIds")
    @ResponseBody
    public ResponseEntity<Result> removeSysmsgByIds(Integer[] ids){
        boolean flag = upholdSysmsgService.removeSysmsgByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("修改系统消息成功");
        return ResponseEntity.ok(result);
    }


}
