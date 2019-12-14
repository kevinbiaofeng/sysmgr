package com.snake.mcf.sysmgr.controller.web.website;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.dto.AdsDTO;
import com.snake.mcf.sysmgr.service.website.WebsiteAdvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName WebsiteAdvertController
 * @Author 大帅
 * @Date 2019/7/9 15:28
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/advert")
public class WebsiteAdvertController extends BaseController {

    @Autowired
    private WebsiteAdvertService websiteAdvertService;

    /**
     * 分页查询 广告管理
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryAdsWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<AdsDTO>> queryAdsWithPage(EasyPageFilter pageFilter, AdsDTO dto){
        PageResult<AdsDTO> pageResult = websiteAdvertService.queryAdsWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 广告管理
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryAdsById")
    @ResponseBody
    public ResponseEntity<Result> queryAdsById(AdsDTO dto){
        dto = websiteAdvertService.queryAdsById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增/修改 广告信息
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增/修改广告信息" , description = "网站系统->广告管理->新增/修改广告信息功能:新增/修改广告信息")
    @PostMapping(path = "/saveAds")
    @ResponseBody
    public ResponseEntity<Result> saveAds( AdsDTO dto){
        try {
            log.info("dto : {}" , JsonUtils.toString(dto));
            websiteAdvertService.saveAds(dto);
            Result result = Result.succ();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(Result.fail("新增/修改广告信息失败"));
        }
    }

    /**
     * 删除 广告信息
     *
     * @param ids
     * @return
     */
    @AopLog(module = "删除广告信息" , description = "网站系统->广告管理->删除广告信息功能:删除广告信息")
    @DeleteMapping(path = "/removeAdsByIds")
    @ResponseBody
    public ResponseEntity<Result> removeAdsByIds(Integer[] ids){
        boolean flag = websiteAdvertService.removeAdsByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("批量广告信息失败");
        return ResponseEntity.ok(result);
    }



}
