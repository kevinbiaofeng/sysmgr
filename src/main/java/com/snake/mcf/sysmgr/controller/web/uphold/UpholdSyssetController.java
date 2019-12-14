package com.snake.mcf.sysmgr.controller.web.uphold;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemstatusinfoDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdSyssetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdSyssetController
 * @Author 大帅
 * @Date 2019/7/1 20:23
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/sysset")
public class UpholdSyssetController extends BaseController {

    @Autowired
    private UpholdSyssetService upholdSyssetService;

    /**
     * 打开首页
     *
     * @param modelMap
     * @return
     */
    @GetMapping(path = "/index")
    public String index(ModelMap modelMap){
        List<SystemstatusinfoDTO> list = upholdSyssetService.querySysStatusInfo();
        modelMap.put("list",list);
        return "uphold/sysset/index";
    }

    /**
     * 根据id加载
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySysStatusInfoByName")
    @ResponseBody
    public ResponseEntity<Result> querySysStatusInfoByName(SystemstatusinfoDTO dto){
        dto = upholdSyssetService.querySysStatusInfoByName(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改系统设置
     *
     * @param dto
     * @return
     */
    @PutMapping(path = "/updateSysStatusInfo")
    @ResponseBody
    public ResponseEntity<Result> updateSysStatusInfo(SystemstatusinfoDTO dto){
        boolean flag = upholdSyssetService.updateSysStatusInfo(dto);
        Result result = flag ? Result.succ() : Result.fail("修改系统设置成功");
        return ResponseEntity.ok(result);
    }
}
