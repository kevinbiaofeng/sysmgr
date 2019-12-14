package com.snake.mcf.sysmgr.controller.web.uphold;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.*;
import com.snake.mcf.sysmgr.service.uphold.UpholdSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @ClassName UpholdSignController
 * @Author 大帅
 * @Date 2019/7/4 10:04
 */
@Slf4j
@Controller
@RequestMapping(path = "/uphold/sign")
public class UpholdSignController extends BaseController {

    @Autowired
    private UpholdSignService upholdSignService;

    /**
     * 签到礼包配置
     */
    /**
     * 签到礼包配置分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamePackageWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamepackageDTO>> queryGamePackageWithPage(EasyPageFilter pageFilter, GamepackageDTO dto){
        PageResult<GamepackageDTO> pageResult = upholdSignService.queryGamePackageWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 签到礼包配置
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamePackageById")
    @ResponseBody
    public ResponseEntity<Result> queryGamePackageById(GamepackageDTO dto){
        dto = upholdSignService.queryGamePackageById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增签到礼包配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增签到礼包配置" , description = "系统设置->签到管理->新增签到礼包配置功能:新增签到礼包配置")
    @PostMapping(path = "/saveGamePackage")
    @ResponseBody
    public ResponseEntity<Result> saveGamePackage(GamepackageDTO dto){
        boolean flag = upholdSignService.saveGamePackage(dto);
        Result result = flag ? Result.succ() : Result.fail("新增签到礼包配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 编辑签到礼包配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改签到礼包配置" , description = "系统设置->签到管理->修改签到礼包配置功能:修改签到礼包配置")
    @PutMapping(path = "/updateGamePackage")
    @ResponseBody
    public ResponseEntity<Result> updateGamePackage(GamepackageDTO dto){
        boolean flag = upholdSignService.updateGamePackage(dto);
        Result result = flag ? Result.succ() : Result.fail("编辑签到礼包配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量删除 签到礼包配置
     *
     * @param packageids
     * @return
     */
    @AopLog(module = "批量删除签到礼包配置" , description = "系统设置->签到管理->批量删除签到礼包配置功能:批量删除签到礼包配置")
    @DeleteMapping(path = "/removeGamePackageByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGamePackageByIds(Integer[] packageids){
        boolean flag = upholdSignService.removeGamePackageByIds(packageids);
        Result result = flag ? Result.succ() : Result.fail("删除签到礼包配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 签到物品配置
     */
    /**
     * 签到物品配置 分页查询
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamePackageGoodsWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamepackagegoodsDTO>> queryGamePackageGoodsWithPage(EasyPageFilter pageFilter, GamepackagegoodsDTO dto){
        PageResult<GamepackagegoodsDTO> pageResult = upholdSignService.queryGamePackageGoodsWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 签到物品配置
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGamePackageGoodsById")
    @ResponseBody
    public ResponseEntity<Result> queryGamePackageGoodsById(GamepackagegoodsDTO dto){
        dto = upholdSignService.queryGamePackageGoodsById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 加载 礼包名称
     *
     * @param nullity
     * @return
     */
    @GetMapping(path = "/loadGamePackageData")
    @ResponseBody
    public ResponseEntity<List<ComboBoxDTO>> loadGamePackageData(Integer nullity){
        List<ComboBoxDTO> list = upholdSignService.loadGamePackageData(nullity);
        return ResponseEntity.ok(list);
    }

    /**
     * 新增/修改 签到物品配置
     *
     * @return
     */
    @AopLog(module = "新增/修改签到物品配置" , description = "系统设置->签到管理->新增/修改签到物品配置功能:新增签到物品配置")
    @PostMapping(path = "/saveGamePackageGoods")
    @ResponseBody
    public ResponseEntity<Result> saveGamePackageGoods(GamepackagegoodsDTO dto){
        try {
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            log.info("dto : {}" , JsonUtils.toString(dto));
            boolean flag = upholdSignService.saveGamePackageGoods(dto);
            Result result = flag ? Result.succ() : Result.fail("新增/修改签到物品配置失败");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("新增/修改签到物品配置失败");
        }
        return ResponseEntity.ok(Result.fail("新增/修改签到物品配置失败"));
    }

    /**
     * 批量删除 签到物品配置
     *
     * @param goodsids
     * @return
     */
    @AopLog(module = "批量删除签到物品配置" , description = "系统设置->签到管理->批量删除签到物品配置功能:批量删除签到物品配置")
    @DeleteMapping(path = "/removeGamePackageGoodsByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGamePackageGoodsByIds(Integer[] goodsids){
        boolean flag = upholdSignService.removeGamePackageGoodsByIds(goodsids);
        Result result = flag ? Result.succ() : Result.fail("删除签到物品配置成功");
        return ResponseEntity.ok(result);
    }
    /**
     * 签到配置
     */
    /**
     * 分页查询 签到配置
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGameSignInWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<GamesigninDTO>> queryGameSignInWithPage(EasyPageFilter pageFilter, GamesigninDTO dto){
        PageResult<GamesigninDTO> pageResult = upholdSignService.queryGameSignInWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 签到配置
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryGameSignInById")
    @ResponseBody
    public ResponseEntity<Result> queryGameSignInById(GamesigninDTO dto){
        dto = upholdSignService.queryGameSignInById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 签到配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增签到配置" , description = "系统设置->签到管理->新增签到配置功能:新增签到配置")
    @PostMapping(path = "/saveGameSignIn")
    @ResponseBody
    public ResponseEntity<Result> saveGameSignIn(GamesigninDTO dto){
        boolean flag = upholdSignService.saveGameSignIn(dto);
        Result result = flag ? Result.succ() : Result.fail("新增签到配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 编辑 签到配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改签到配置" , description = "系统设置->签到管理->修改签到配置功能:修改签到配置")
    @PutMapping(path = "/updateGameSignIn")
    @ResponseBody
    public ResponseEntity<Result> updateGameSignIn(GamesigninDTO dto){
        boolean flag = upholdSignService.updateGameSignIn(dto);
        Result result = flag ? Result.succ() : Result.fail("编辑签到配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 批量删除 签到配置
     *
     * @param signids
     * @return
     */
    @AopLog(module = "批量删除签到配置" , description = "系统设置->签到管理->批量删除签到配置功能:批量删除签到配置")
    @DeleteMapping(path = "/removeGameSignInByIds")
    @ResponseBody
    public ResponseEntity<Result> removeGameSignInByIds(Integer[] signids){
        boolean flag = upholdSignService.removeGameSignInByIds(signids);
        Result result = flag ? Result.succ() : Result.fail("删除签到配置成功");
        return ResponseEntity.ok(result);
    }

    /**
     * 签到记录 签到配置
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryRecordGameSignInWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<RecordgamesigninDTO>> queryRecordGameSignInWithPage(EasyPageFilter pageFilter,RecordgamesigninDTO dto){
        PageResult<RecordgamesigninDTO> pageResult = upholdSignService.queryRecordGameSignInWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }






}
