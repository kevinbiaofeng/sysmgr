package com.snake.mcf.sysmgr.controller.web.invest;

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
import com.snake.mcf.sysmgr.repertory.entity.dto.ApppayconfigDTO;
import com.snake.mcf.sysmgr.service.invest.InverstSetupService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName InverstSetupController
 * @Author 大帅
 * @Date 2019/6/27 18:29
 */
@Slf4j
@Controller
@RequestMapping(path = "/invest/setup")
public class InverstSetupController extends BaseController {

    @Autowired
    private InverstSetupService inverstSetupService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySetupWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ApppayconfigDTO>> querySetupWithPage(EasyPageFilter pageFilter,ApppayconfigDTO dto){
        PageResult<ApppayconfigDTO> pageResult = inverstSetupService.querySetupWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据id查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryAppConfigById")
    @ResponseBody
    public ResponseEntity<Result> queryAppConfigById(ApppayconfigDTO dto){
        dto = inverstSetupService.queryAppConfigById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 保存充值配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增充值配置" , description = "充值系统->充值配置->新增充值配置功能:新增充值配置")
    @PostMapping(path = "/saveAppConfig")
    @ResponseBody
    public ResponseEntity<Result> saveAppConfig(ApppayconfigDTO dto){
        //校验
        /*boolean fg = inverstSetupService.isExistAppleid(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("苹果产品标识已存在"));
        }*/
        boolean flag = inverstSetupService.saveAppConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存充值配置操作失败");
        return ResponseEntity.ok(result);
    }

    /**
         * 修改充值配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改充值配置" , description = "充值系统->充值配置->修改充值配置功能:修改充值配置")
    @PutMapping(path = "/updateAppConfig")
    @ResponseBody
    public ResponseEntity<Result> updateAppConfig(ApppayconfigDTO dto){
        //校验
       /* boolean fg = inverstSetupService.isExistAppleid(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("苹果产品标识已存在"));
        }*/
        boolean flag = inverstSetupService.updateAppConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存充值配置操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除配置
     *
     * @param configids
     * @return
     */
    @AopLog(module = "删除充值配置" , description = "充值系统->充值配置->删除充值配置功能:删除充值配置")
    @DeleteMapping(path = "/removeAppConfigIds")
    @ResponseBody
    public ResponseEntity<Result> removeAppConfigIds(Integer[] configids){
        boolean flag = inverstSetupService.removeAppConfigIds(configids);
        Result result = flag ? Result.succ() : Result.fail("删除充值配置操作失败");
        return ResponseEntity.ok(result);
    }


}
