package com.snake.mcf.sysmgr.controller.web.account;

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
import com.snake.mcf.sysmgr.repertory.entity.dto.RegistergiveDTO;
import com.snake.mcf.sysmgr.service.account.AccountReggiveService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AccountReggiveController
 * @Author 大帅
 * @Date 2019/6/27 9:58
 */
@Slf4j
@Controller
@RequestMapping(path = "/account/reggive")
public class AccountReggiveController extends BaseController {

    @Autowired
    private AccountReggiveService accountReggiveService;

    /**
     * 分页查询注册赠送
     *
     * @param filter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryReggiveWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RegistergiveDTO>> queryReggiveWithPage(EasyPageFilter filter , RegistergiveDTO dto){
        PageResult<RegistergiveDTO> pageResult = accountReggiveService.queryReggiveWithPage(filter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryReggiveById")
    @ResponseBody
    public ResponseEntity<Result> queryReggiveById(RegistergiveDTO dto){
        dto = accountReggiveService.queryReggiveById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 保存注册赠送
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增注册赠送" , description = "用户系统->注册赠送->新增注册赠送功能:新增注册赠送")
    @PostMapping(path = "/saveReggive")
    @ResponseBody
    public ResponseEntity<Result> saveReggive(RegistergiveDTO dto){
        boolean existPlatform = accountReggiveService.isExistPlatform(dto);
        if(existPlatform){
            return ResponseEntity.ok(Result.fail("已存在平台注册"));
        }
        boolean flag = accountReggiveService.saveReggive(dto);
        Result result = flag ? Result.succ() : Result.fail("保存注册赠送失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改注册赠送
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改注册赠送" , description = "用户系统->注册赠送->修改注册赠送功能:修改注册赠送")
    @PutMapping(path = "/updateReggive")
    @ResponseBody
    public ResponseEntity<Result> updateReggive(RegistergiveDTO dto){
        /*boolean existPlatform = accountReggiveService.isExistPlatform(dto);
        if(existPlatform){
            return ResponseEntity.ok(Result.fail("已存在平台注册"));
        }*/
        boolean flag = accountReggiveService.updateReggive(dto);
        Result result = flag ? Result.succ() : Result.fail("注册赠送操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除注册赠送
     *
     * @param dto
     * @return
     */
    @AopLog(module = "删除注册赠送" , description = "用户系统->注册赠送->删除注册赠送功能:删除注册赠送")
    @DeleteMapping(path = "/removeReggiveByIds")
    @ResponseBody
    public ResponseEntity<Result> removeReggiveByIds(RegistergiveDTO dto){
        boolean flag = accountReggiveService.removeReggiveByIds(dto);
        Result result = flag ? Result.succ() : Result.fail("注册赠送操作失败");
        return ResponseEntity.ok(result);
    }


}
