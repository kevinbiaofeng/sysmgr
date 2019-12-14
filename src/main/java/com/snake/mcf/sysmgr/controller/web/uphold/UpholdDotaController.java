package com.snake.mcf.sysmgr.controller.web.uphold;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.Gameproperty;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamepropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordbuynewpropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordusepropertyDTO;
import com.snake.mcf.sysmgr.service.uphold.UpholdDotaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName UpholdDotaController
 * @Author 大帅
 * @Date 2019/7/2 17:56
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/dota")
public class UpholdDotaController extends BaseController {

    @Autowired
    private UpholdDotaService upholdDotaService;

    /**
     * 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryDotaWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamepropertyDTO>> queryDotaWithPage(EasyPageFilter pageFilter, GamepropertyDTO dto){
        PageResult<GamepropertyDTO> pageResult = upholdDotaService.queryDotaWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询道具
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryDotaById")
    @ResponseBody
    public ResponseEntity<Result> queryDotaById(GamepropertyDTO dto){
        dto = upholdDotaService.queryDotaById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增/修改道具配置
     *
     * @param gameproperty
     * @return
     */
    @AopLog(module = "新增/修改道具配置" , description = "系统设置->道具管理->新增/修改道具配置功能:新增/修改道具配置")
    @PostMapping(path = "/saveOrUpdateDota")
    @ResponseBody
    public ResponseEntity<Result> saveOrUpdateDota(Gameproperty gameproperty){
        boolean result = upholdDotaService.saveOrUpdateDota(gameproperty);
        if (result == true) {
            return ResponseEntity.ok(Result.succ());
        }
        return ResponseEntity.ok(Result.fail("新增/修改道具信息失败"));
    }

    /**
     * 查询购买道具记录
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryBuyDotaWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordbuynewpropertyDTO>> queryBuyDotaWithPage(EasyPageFilter pageFilter, RecordbuynewpropertyDTO dto){
        PageResult<RecordbuynewpropertyDTO> pageResult = upholdDotaService.queryBuyDotaWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 分頁查詢道具使用記錄
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryUseDotaWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordusepropertyDTO>> queryUseDotaWithPage(EasyPageFilter pageFilter, RecordusepropertyDTO dto){
        PageResult<RecordusepropertyDTO> pageResult = upholdDotaService.queryUseDotaWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }




}
