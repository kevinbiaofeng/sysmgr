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
import com.snake.mcf.sysmgr.repertory.entity.dto.CurrencyexchconfigDTO;
import com.snake.mcf.sysmgr.service.invest.InverstExchgService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName InverstExchgController
 * @Author 大帅
 * @Date 2019/6/28 15:09
 */
@Slf4j
@Controller
@RequestMapping(path = "/invest/exchg")
public class InverstExchgController extends BaseController {

    @Autowired
    private InverstExchgService inverstExchgService;

    /**
     * 分頁查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryExchgWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<CurrencyexchconfigDTO>> queryExchgWithPage(EasyPageFilter pageFilter,CurrencyexchconfigDTO dto){
        PageResult<CurrencyexchconfigDTO> pageResult = inverstExchgService.queryExchgWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 保存修改配置信息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增兑换管理" , description = "充值系统->兑换管理->新增兑换管理功能:新增兑换管理")
    @PostMapping(path = "/saveExchConfig")
    @ResponseBody
    public ResponseEntity<Result> saveExchConfig(CurrencyexchconfigDTO dto){
        /*boolean fg = inverstExchgService.isExistDiamond(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("抱歉，相同额度的钻石已存在"));
        }*/
        boolean flag = inverstExchgService.saveExchConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存修改配置信息失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryExchgById")
    @ResponseBody
    public ResponseEntity<Result> queryExchgById(CurrencyexchconfigDTO dto){
        dto = inverstExchgService.queryExchgById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改兑换管理" , description = "充值系统->兑换管理->修改兑换管理功能:修改兑换管理")
    @PutMapping(path = "/updateExchConfig")
    @ResponseBody
    public ResponseEntity<Result> updateExchConfig(CurrencyexchconfigDTO dto){
        //校验
        /*boolean fg = inverstExchgService.isExistDiamond(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("抱歉，相同额度的钻石已存在"));
        }*/
        boolean flag = inverstExchgService.updateExchConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存充值配置操作失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除配置
     *
     * @param configids
     * @return
     */
    @AopLog(module = "删除兑换管理" , description = "充值系统->兑换管理->删除兑换管理功能:删除兑换管理")
    @DeleteMapping(path = "/removeExchgConfigIds")
    @ResponseBody
    public ResponseEntity<Result> removeExchgConfigIds(Integer[] configids){
        boolean flag = inverstExchgService.removeExchgConfigIds(configids);
        Result result = flag ? Result.succ() : Result.fail("删除充值配置操作失败");
        return ResponseEntity.ok(result);
    }






}
