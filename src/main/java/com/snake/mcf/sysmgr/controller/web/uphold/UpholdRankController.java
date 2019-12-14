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
import com.snake.mcf.sysmgr.repertory.entity.dto.CacherankawardDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RankingconfigDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdRankService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdRankController
 * @Author 大帅
 * @Date 2019/7/2 10:49
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/rank")
public class UpholdRankController extends BaseController {

    @Autowired
    private UpholdRankService upholdRankService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRankWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RankingconfigDTO>> queryRankWithPage(EasyPageFilter pageFilter,RankingconfigDTO dto){
        PageResult<RankingconfigDTO> pageResult = upholdRankService.queryRankWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRankById")
    @ResponseBody
    public ResponseEntity<Result> queryRankById(RankingconfigDTO dto){
        dto = upholdRankService.queryRankById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 保存排行配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增排行配置" , description = "系统设置->排行管理->新增排行配置功能:新增排行配置")
    @PostMapping(path = "/saveRank")
    @ResponseBody
    public ResponseEntity<Result> saveRank(RankingconfigDTO dto){
        boolean fg = upholdRankService.isExistRank(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("相同配置信息已存在"));
        }
        boolean flag = upholdRankService.saveRank(dto);
        Result result = flag ? Result.succ() : Result.fail("保存排行配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改排行配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改排行配置" , description = "系统设置->排行管理->修改排行配置功能:修改排行配置")
    @PutMapping(path = "/updateRank")
    @ResponseBody
    public ResponseEntity<Result> updateRank(RankingconfigDTO dto){
        boolean fg = upholdRankService.isExistRank(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("相同配置信息已存在"));
        }
        boolean flag = upholdRankService.updateRank(dto);
        Result result = flag ? Result.succ() : Result.fail("修改排行配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除排行配置
     *
     * @param configids
     * @return
     */
    @AopLog(module = "批量删除排行配置" , description = "系统设置->排行管理->批量删除排行配置功能:批量删除排行配置")
    @DeleteMapping(path = "/removeRankByIds")
    @ResponseBody
    public ResponseEntity<Result> removeRankByIds(Integer[] configids){
        boolean flag = upholdRankService.removeRankByIds(configids);
        Result result = flag ? Result.succ() : Result.fail("删除排行配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRankAwardWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<CacherankawardDTO>> queryRankAwardWithPage(EasyPageFilter pageFilter,CacherankawardDTO dto){
        PageResult<CacherankawardDTO> pageResult = upholdRankService.queryRankAwardWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }


}
