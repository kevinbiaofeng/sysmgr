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
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinewechatDTO;
import com.snake.mcf.sysmgr.service.invest.InverstOnlineService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName InverstOnlineController
 * @Author 大帅
 * @Date 2019/6/28 18:12
 */
@Slf4j
@Controller
@RequestMapping(path = "/invest/online")
public class InverstOnlineController extends BaseController {

    @Autowired
    private InverstOnlineService inverstOnlineService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryOnLineWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<OnlinewechatDTO>> queryOnLineWithPage(EasyPageFilter pageFilter,OnlinewechatDTO dto){
        PageResult<OnlinewechatDTO> pageResult = inverstOnlineService.queryOnLineWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 保存配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增在线充值配置" , description = "充值系统->在线充值配置->删除兑换管理功能:新增在线充值配置")
    @PostMapping(path = "/saveOnLineWeChat")
    @ResponseBody
    public ResponseEntity<Result> saveOnLineWeChat(OnlinewechatDTO dto){
        boolean flag = inverstOnlineService.saveOnLineWeChat(dto);
        Result result = flag ? Result.succ() : Result.fail("保存配置失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据id查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryOnLineWeChatById")
    @ResponseBody
    public ResponseEntity<Result> queryOnLineWeChatById(OnlinewechatDTO dto){
        dto = inverstOnlineService.queryOnLineWeChatById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改在线充值配置" , description = "充值系统->在线充值配置->修改兑换管理功能:修改在线充值配置")
    @PutMapping(path = "/updateOnLineWeChat")
    @ResponseBody
    public ResponseEntity<Result> updateOnLineWeChat(OnlinewechatDTO dto){
        boolean flag = inverstOnlineService.updateOnLineWeChat(dto);
        Result result = flag ? Result.succ() : Result.fail("保存配置失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除
     *
     * @param configids
     * @return
     */
    @AopLog(module = "删除在线充值配置" , description = "充值系统->在线充值配置->删除兑换管理功能:删除在线充值配置")
    @DeleteMapping(path = "/removeOnLineByIds")
    @ResponseBody
    public ResponseEntity<Result> removeOnLineByIds(Integer[] configids){
        boolean flag = inverstOnlineService.removeOnLineByIds(configids);
        Result result = flag ? Result.succ() : Result.fail("删除配置失败");
        return ResponseEntity.ok(result);
    }





}
