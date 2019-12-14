package com.snake.mcf.sysmgr.controller.web.ffar;

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
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupoptionDTO;
import com.snake.mcf.sysmgr.service.ffar.FfarStandService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName FfarStandController
 * @Author 大帅
 * @Date 2019/7/15 10:33
 */
@Slf4j
@Controller
@RequestMapping(path = "/ffar/stand")
public class FfarStandController extends BaseController {

    @Autowired
    private FfarStandService ffarStandService;

    @GetMapping(path = "/index")
    public String index(ModelMap modelMap){
        List<ImgroupoptionDTO> list = ffarStandService.queryIMGroupOptionList();
        modelMap.put("list",list);
        return "ffar/stand/index";
    }

    /**
     * 根据名称加载
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryIMGroupOptionInfoByName")
    @ResponseBody
    public ResponseEntity<Result> queryIMGroupOptionInfoByName(ImgroupoptionDTO dto){
        dto = ffarStandService.queryIMGroupOptionInfoByName(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 编辑 亲友圈配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "亲友圈配置" , description = "亲友圈系统->亲友圈配置->修改亲友圈配置功能:修改亲友圈配置")
    @PutMapping(path = "/updateIMGroupOption")
    @ResponseBody
    public ResponseEntity<Result> updateIMGroupOption(ImgroupoptionDTO dto){
        log.info("{}",dto);
        boolean flag = ffarStandService.updateIMGroupOption(dto);
        Result result = flag ? Result.succ() : Result.fail("编辑亲友圈配置失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 分页查询 亲友圈配置
     *
     * @param pageFilter
     * @param dto
     * @return
     */
   /* @GetMapping(path = "/queryIMGroupOptionWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ImgroupoptionDTO>> queryIMGroupOptionWithPage(EasyPageFilter pageFilter, ImgroupoptionDTO dto){
        log.info("{}","queryIMGroupOptionWithPage");
        PageResult<ImgroupoptionDTO> pageResult = ffarStandService.queryIMGroupOptionWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }*/

    /**
     * 根据主键查询 亲友圈配置
     *
     * @param dto
     * @return
     */
    /*@GetMapping(path = "/queryIMGroupOptionById")
    @ResponseBody
    public ResponseEntity<Result> queryIMGroupOptionById(ImgroupoptionDTO dto){
        dto = ffarStandService.queryIMGroupOptionById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }*/

    /**
     * 新增 亲友圈配置
     *
     * @param dto
     * @return
     */
    /*@PostMapping(path = "/saveIMGroupOption")
    @ResponseBody
    public ResponseEntity<Result> saveIMGroupOption(ImgroupoptionDTO dto){
        log.info("{}",dto);
        boolean fg = ffarStandService.isExistIMGroupOption(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("已存在亲友圈配置"));
        }
        boolean flag = ffarStandService.saveIMGroupOption(dto);
        Result result = flag ? Result.succ() : Result.fail("新增亲友圈配置失败");
        return ResponseEntity.ok(result);
    }*/

    /**
     * 删除 亲友圈配置
     *
     * @param optionnames
     * @return
     */
    /*@DeleteMapping(path = "/removeIMGroupOptionByIds")
    @ResponseBody
    public ResponseEntity<Result> removeIMGroupOptionByIds(String[] optionnames){
        log.info("{}",optionnames);
        boolean flag = ffarStandService.removeIMGroupOptionByIds(optionnames);
        Result result = flag ? Result.succ() : Result.fail("删除亲友圈配置失败");
        return ResponseEntity.ok(result);
    }*/

}
