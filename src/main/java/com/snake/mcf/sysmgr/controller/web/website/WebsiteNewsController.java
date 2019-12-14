package com.snake.mcf.sysmgr.controller.web.website;

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
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemnoticeDTO;
import com.snake.mcf.sysmgr.service.website.WebsiteNewsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName WebsiteNewsController
 * @Author 大帅
 * @Date 2019/7/10 12:30
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/news")
public class WebsiteNewsController extends BaseController {

    @Autowired
    private WebsiteNewsService websiteNewsService;

    /**
     * 分页查询 新闻公告
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySystemNoticeWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<SystemnoticeDTO>> querySystemNoticeWithPage(EasyPageFilter pageFilter, SystemnoticeDTO dto){
        PageResult<SystemnoticeDTO> pageResult = websiteNewsService.querySystemNoticeWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 新闻公告
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/querySystemNoticeById")
    @ResponseBody
    public ResponseEntity<Result> querySystemNoticeById(SystemnoticeDTO dto){
        dto = websiteNewsService.querySystemNoticeById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 保存 新闻公告
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增新闻公告" , description = "网站系统->新闻公告->新增新闻公告功能:新增新闻公告")
    @PostMapping(path = "/saveSystemNotice")
    @ResponseBody
    public ResponseEntity<Result> saveSystemNotice(SystemnoticeDTO dto){
        boolean flag = websiteNewsService.saveSystemNotice(dto);
        Result result = flag ? Result.succ() : Result.fail("新增新闻公告失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改 新闻公告
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改新闻公告" , description = "网站系统->新闻公告->修改新闻公告功能:修改新闻公告")
    @PutMapping(path = "/updateSystemNotice")
    @ResponseBody
    public ResponseEntity<Result> updateSystemNotice(SystemnoticeDTO dto){
        boolean flag = websiteNewsService.updateSystemNotice(dto);
        Result result = flag ? Result.succ() : Result.fail("修改新闻公告失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 根据主键删除 新闻公告
     *
     * @param noticeids
     * @return
     */
    @AopLog(module = "删除新闻公告" , description = "网站系统->新闻公告->删除新闻公告功能:删除新闻公告")
    @DeleteMapping(path = "/removeSystemNoticeByIds")
    @ResponseBody
    public ResponseEntity<Result> removeSystemNoticeByIds(Integer[] noticeids){
        boolean flag = websiteNewsService.removeSystemNoticeByIds(noticeids);
        Result result = flag ? Result.succ() : Result.fail("删除新闻公告失败");
        return ResponseEntity.ok(result);
    }



}
