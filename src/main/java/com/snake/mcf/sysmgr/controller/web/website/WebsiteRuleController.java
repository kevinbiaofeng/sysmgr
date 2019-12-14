package com.snake.mcf.sysmgr.controller.web.website;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.GameruleDTO;
import com.snake.mcf.sysmgr.service.website.WebsiteRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName WebsiteRuleController
 * @Author 大帅
 * @Date 2019/7/8 17:34
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/rule")
public class WebsiteRuleController extends BaseController {

    @Autowired
    private WebsiteRuleService websiteRuleService;

    /**
     * 分页查询 游戏规则
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGameRuleWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GameruleDTO>> queryGameRuleWithPage(EasyPageFilter pageFilter, GameruleDTO dto){
        PageResult<GameruleDTO> pageResult = websiteRuleService.queryGameRuleWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 游戏规则
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGameRuleById")
    @ResponseBody
    public ResponseEntity<Result> queryGameRuleById(GameruleDTO dto){
        dto = websiteRuleService.queryGameRuleById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增/修改 游戏规则
     *
     * @param file
     * @param dto
     * @return
     */
    @AopLog(module = "新增/修改游戏规则" , description = "网站系统->游戏规则->新增/修改游戏规则功能:新增/修改游戏规则")
    @PostMapping(path = "/saveGameRule")
    @ResponseBody
    public ResponseEntity<Result> saveGameRule(GameruleDTO dto){
        try {
            //是否存在
            Integer id = dto.getId();
            if(id == null){
                //主键不存在新增需要校验
                boolean fg = websiteRuleService.isExistGameRule(dto);
                if(fg){
                    return ResponseEntity.ok(Result.fail("游戏规则已存在"));
                }
            }

            log.info("dto : {}" , JsonUtils.toString(dto));
            boolean flag = websiteRuleService.saveGameRule(dto);
            Result result = flag ? Result.succ() : Result.fail("新增/修改游戏规则失败");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(Result.fail("新增/修改游戏规则失败"));
    }

    /**
     * 批量禁用启用
     *
     * @param dto
     * @return
     */
    @AopLog(module = "批量禁用/启用游戏规则" , description = "网站系统->游戏规则->批量禁用/启用游戏规则功能:批量禁用/启用游戏规则")
    @PutMapping(path = "/batchNullityUpdate")
    @ResponseBody
    public ResponseEntity<Result> batchNullityUpdate(GameruleDTO dto){
        boolean flag = websiteRuleService.batchNullityUpdate(dto);
        Result result = flag ? Result.succ() : Result.fail("批量禁用/启用游戏规则失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AopLog(module = "批量删除游戏规则" , description = "网站系统->游戏规则->批量删除游戏规则功能:批量删除游戏规则")
    @DeleteMapping(path = "/removeGameRuleByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGameRuleByIds(Integer[] ids){
        boolean flag = websiteRuleService.removeGameRuleByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("批量删除游戏规则失败");
        return ResponseEntity.ok(result);
    }


}
