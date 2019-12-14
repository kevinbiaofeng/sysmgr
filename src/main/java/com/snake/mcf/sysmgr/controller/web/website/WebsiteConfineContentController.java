package com.snake.mcf.sysmgr.controller.web.website;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.ConfineContent;
import com.snake.mcf.sysmgr.repertory.mapper.ConfineContentMapper;
import com.snake.mcf.sysmgr.service.website.WebsiteConfineContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName WebsiteConfineContentController
 * @Author robin
 * @Date 2019/10/03 20:10
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/confineContent")
public class WebsiteConfineContentController extends BaseController {

    @Autowired
    private WebsiteConfineContentService websiteConfineContentService;

    @Autowired
    private ConfineContentMapper confineContentMapper;

    /**
     * 分页查询 敏感词汇
     */
    @GetMapping(path = "/queryConfineContentWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ConfineContent>> querySystemNoticeWithPage(EasyPageFilter pageFilter, ConfineContent entity) {
        PageResult<ConfineContent> pageResult = websiteConfineContentService.queryConfineContentService(pageFilter, entity);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 保存 敏感词汇
     *
     * @param entity
     * @return
     */
    @AopLog(module = "新增敏感词汇", description = "网站系统->敏感词管理->新增敏感词汇功能:新增敏感词汇")
    @PostMapping(path = "/saveConfineContent")
    @ResponseBody
    public ResponseEntity<Result> saveConfineContent(ConfineContent entity) {
        boolean flag = websiteConfineContentService.saveConfineContent(entity);
        Result result = flag ? Result.succ() : Result.fail("新增失败,敏感词汇已存在！");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键查询 敏感词汇
     *
     * @param entity
     * @return
     */
    @GetMapping(path = "/queryConfineContentById")
    @ResponseBody
    public ResponseEntity<Result> queryConfineContentById(ConfineContent entity) {
        entity = websiteConfineContentService.queryConfineContentById(entity);
        Result result = entity == null ? Result.fail("根据id查询失败") : Result.succ(entity);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改并保存 敏感词汇
     *
     * @param entity
     * @return
     */
    @AopLog(module = "修改敏感词汇", description = "网站系统->敏感词管理->修改敏感词汇功能:修改敏感词汇")
    @PutMapping(path = "/updateConfineContent")
    @ResponseBody
    public ResponseEntity<Result> updateConfineContent(ConfineContent entity) {
        boolean flag = websiteConfineContentService.updateConfineContent(entity);
        Result result = flag ? Result.succ() : Result.fail("修改敏感词汇失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键删除 敏感词汇
     *
     * @param contentIds
     * @return
     */
    @AopLog(module = "删除敏感词汇", description = "网站系统->敏感词管理->删除敏感词汇功能:删除敏感词汇")
    @DeleteMapping(path = "/removeConfineContentByIds")
    @ResponseBody
    public ResponseEntity<Result> removeConfineContentByIds(Integer[] contentIds) {
        boolean flag = websiteConfineContentService.removeConfineContentByIds(contentIds);
        Result result = flag ? Result.succ() : Result.fail("删除敏感词汇");
        return ResponseEntity.ok(result);
    }

}
