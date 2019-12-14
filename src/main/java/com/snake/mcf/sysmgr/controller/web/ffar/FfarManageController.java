package com.snake.mcf.sysmgr.controller.web.ffar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupmemberDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgrouppropertyDTO;
import com.snake.mcf.sysmgr.service.ffar.FfarManageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName FfarManageController
 * @Author 大帅
 * @Date 2019/7/15 12:09
 */
@Slf4j
@Controller
@RequestMapping(path = "/ffar/manage")
public class FfarManageController extends BaseController {

    @Autowired
    private FfarManageService ffarManageService;

    /**
     * 分页查询 亲友圈管理
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryIMGroupPropertyWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ImgrouppropertyDTO>> queryIMGroupPropertyWithPage(EasyPageFilter pageFilter,ImgrouppropertyDTO dto){
        log.info("{}","queryIMGroupOptionWithPage");
        PageResult<ImgrouppropertyDTO> pageResult = ffarManageService.queryIMGroupPropertyWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 禁用/启用 亲友圈管理
     *
     * @param dto
     * @return
     */
    @AopLog(module = "亲友圈管理" , description = "亲友圈系统->亲友圈管理->禁用/启用亲友圈功能:禁用/启用亲友圈")
    @PutMapping(path = "/groupstatusUpate")
    @ResponseBody
    public ResponseEntity<Result> groupstatusUpate(ImgrouppropertyDTO dto){
        log.info("{}",dto);
        boolean flag = ffarManageService.groupstatusUpate(dto);
        Result result = flag ? Result.succ() : Result.fail("禁用/启用亲友圈管理失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 打开 查看亲友 界面
     * @param groupid
     * @return
     */
    @GetMapping(path = "/toGroupMemberPage")
    public String toGroupMemberPage(Long groupid, ModelMap modelMap){
        modelMap.put("groupid",groupid);
        return "ffar/manage/manage_mbr";
    }

    /**
     * 分页查询 亲友
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGroupMemberWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ImgroupmemberDTO>> queryGroupMemberWithPage(EasyPageFilter pageFilter,ImgroupmemberDTO dto){
        log.info("{}","queryGroupMemberWithPage");
        PageResult<ImgroupmemberDTO> pageResult = ffarManageService.queryGroupMemberWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

}
