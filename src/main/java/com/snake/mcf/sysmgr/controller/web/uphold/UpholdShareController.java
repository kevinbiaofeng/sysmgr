package com.snake.mcf.sysmgr.controller.web.uphold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import com.snake.mcf.sysmgr.repertory.entity.dto.ShareconfigDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.SharelogDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdShareService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdShareController
 * @Author 大帅
 * @Date 2019/7/6 10:32
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/share")
public class UpholdShareController extends BaseController {

    @Autowired
    private UpholdShareService upholdShareService;

    /**
     * 每日分享配置 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryShareConfigWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ShareconfigDTO>> queryShareConfigWithPage(EasyPageFilter pageFilter,ShareconfigDTO dto){
        PageResult<ShareconfigDTO> pageResult = upholdShareService.queryShareConfigWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 每日分享配置
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryShareConfigById")
    @ResponseBody
    public ResponseEntity<Result> queryShareConfigById(ShareconfigDTO dto){
        dto = upholdShareService.queryShareConfigById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 每日分享配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增每日分享配置" , description = "系统设置->每日分享管理->新增每日分享配置功能:新增每日分享配置")
    @PostMapping(path = "/saveShareConfig")
    @ResponseBody
    public ResponseEntity<Result> saveShareConfig(ShareconfigDTO dto){
        boolean fg = upholdShareService.isExistShareConfig(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("已存在每日分享配置"));
        }
        boolean flag = upholdShareService.saveShareConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存每日分享配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 编辑 每日分享配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改每日分享配置" , description = "系统设置->每日分享管理->修改每日分享配置功能:修改每日分享配置")
    @PutMapping(path = "/updateShareConfig")
    @ResponseBody
    public ResponseEntity<Result> updateShareConfig(ShareconfigDTO dto){
        boolean flag = upholdShareService.updateShareConfig(dto);
        Result result = flag ? Result.succ() : Result.fail("保存每日分享配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 分页查询分享记录
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryShareLogWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<SharelogDTO>> queryShareLogWithPage(EasyPageFilter pageFilter,SharelogDTO dto){
        PageResult<SharelogDTO> pageResult = upholdShareService.queryShareLogWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }




}
