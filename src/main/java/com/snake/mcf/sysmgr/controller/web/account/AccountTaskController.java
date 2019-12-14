package com.snake.mcf.sysmgr.controller.web.account;

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
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TaskinfoDTO;
import com.snake.mcf.sysmgr.service.account.AccountTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AccountTaskController
 * @Author 大帅
 * @Date 2019/6/27 11:53
 */
@Slf4j
@Controller
@RequestMapping(path = "/account/task")
public class AccountTaskController extends BaseController {

    @Autowired
    private AccountTaskService accountTaskService;

    /**
     * 分页查询任务
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryTaskWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<TaskinfoDTO>> queryTaskWithPage(EasyPageFilter pageFilter,TaskinfoDTO dto){
        log.info("分页参数:{},查询参数:{}", JsonUtils.toString(pageFilter) , JsonUtils.toString(dto));
        PageResult<TaskinfoDTO> pageResult = accountTaskService.queryTaskWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 查询所有游戏下拉
     *
     * @param nullity
     * @return
     */
    @GetMapping(path = "/loadGameKindItemComboData")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadGameKindItemComboData(Integer nullity){
        List<ComboBoxDTO> list = accountTaskService.loadGameRoomComboData(nullity);
        return ResponseEntity.ok(list);
    }

    /**
     * 根据kindid查询房间信息
     *
     * @param kindid
     * @return
     */
    @GetMapping(path = "/loadGameRoomInfoComboData")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadGameRoomInfoComboData(Integer kindid){
        List<ComboBoxDTO> list = accountTaskService.loadGameRoomInfoComboData(kindid);
        return ResponseEntity.ok(list);
    }

    /**
     * 新增任务
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增任务" , description = "用户系统->任务管理->新增任务功能:新增任务")
    @PostMapping(path = "/saveTask")
    @ResponseBody
    public ResponseEntity<Result> saveTask(TaskinfoDTO dto){
        boolean flag = accountTaskService.saveTask(dto);
        Result result = flag ? Result.succ() : Result.fail("新增任务失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryTaskById")
    @ResponseBody
    public ResponseEntity<Result> queryTaskById(TaskinfoDTO dto){
        dto = accountTaskService.queryTaskById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改任务
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改任务" , description = "用户系统->任务管理->修改任务功能:修改任务")
    @PutMapping(path = "/updateTask")
    @ResponseBody
    public ResponseEntity<Result> updateTask(TaskinfoDTO dto){
        boolean flag = accountTaskService.updateTask(dto);
        Result result = flag ? Result.succ() : Result.fail("修改任务失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除任务
     *
     * @param taskids
     * @return
     */
    @AopLog(module = "删除任务" , description = "用户系统->任务管理->删除任务功能:删除任务")
    @DeleteMapping(path = "/removeTaskByIds")
    @ResponseBody
    public ResponseEntity<Result> removeTaskByIds(Integer[] taskids){
        log.info("taskids={}",JsonUtils.toString(taskids));
        boolean flag = accountTaskService.removeTaskByIds(taskids);
        Result result = flag ? Result.succ() : Result.fail("删除任务失败");
        return ResponseEntity.ok(result);
    }



}
