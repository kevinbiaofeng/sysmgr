package com.snake.mcf.sysmgr.controller.web.uphold;

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
import com.snake.mcf.sysmgr.repertory.entity.dto.SpreadtypepropertyDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdSpreadService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdSpreadController
 * @Author 大帅
 * @Date 2019/7/6 15:36
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/spread")
public class UpholdSpreadController extends BaseController {

    @Autowired
    private UpholdSpreadService upholdSpreadService;

    /**
     * 分页查询 推广类型设置
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySpreadTypeWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<SpreadtypepropertyDTO>> querySpreadTypeWithPage(EasyPageFilter pageFilter, SpreadtypepropertyDTO dto){
        PageResult<SpreadtypepropertyDTO> pageResult = upholdSpreadService.querySpreadTypeWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据id查询 推广类型设置
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySpreadTypeById")
    @ResponseBody
    public ResponseEntity<Result> querySpreadTypeById(SpreadtypepropertyDTO dto){
        dto = upholdSpreadService.querySpreadTypeById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 推广类型设置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增推广类型设置" , description = "系统设置->推广类型设置->新增推广类型设置功能:新增推广类型设置")
    @PostMapping(path = "/saveSpreadType")
    @ResponseBody
    public ResponseEntity<Result> saveSpreadType(SpreadtypepropertyDTO dto){
        boolean fg = upholdSpreadService.isExistSpreadType(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("已存在推广类型级别"));
        }
        boolean flag = upholdSpreadService.saveSpreadType(dto);
        Result result = flag ? Result.succ() : Result.fail("保存推广类型设置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改 推广类型设置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改推广类型设置" , description = "系统设置->推广类型设置->修改推广类型设置功能:修改推广类型设置")
    @PutMapping(path = "/updateSpreadType")
    @ResponseBody
    public ResponseEntity<Result> updateSpreadType(SpreadtypepropertyDTO dto){
        /*boolean fg = upholdSpreadService.isExistSpreadType(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("已存在每日分享配置"));
        }*/
        boolean flag = upholdSpreadService.updateSpreadType(dto);
        Result result = flag ? Result.succ() : Result.fail("保存推广类型设置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除 推广类型设置
     *
     * @param ids
     * @return
     */
    @AopLog(module = "删除推广类型设置" , description = "系统设置->推广类型设置->删除推广类型设置功能:删除推广类型设置")
    @DeleteMapping(path = "/removeSpreadTypeByIds")
    @ResponseBody
    public ResponseEntity<Result> removeSpreadTypeByIds(Integer[] ids){
        boolean flag = upholdSpreadService.removeSpreadTypeByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("删除推广类型设置成功");
        return ResponseEntity.ok(result);
    }

}
