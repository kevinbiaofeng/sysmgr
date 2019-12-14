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
import com.snake.mcf.sysmgr.repertory.entity.dto.QuestionDTO;
import com.snake.mcf.sysmgr.service.website.WebsiteQuestionService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName WebsiteQuestionController
 * @Author 大帅
 * @Date 2019/7/10 16:23
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/question")
public class WebsiteQuestionController extends BaseController {

    @Autowired
    private WebsiteQuestionService websiteQuestionService;

    /**
     * 分页查询 常见问题
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryQuestionWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<QuestionDTO>> queryQuestionWithPage(EasyPageFilter pageFilter, QuestionDTO dto){
        PageResult<QuestionDTO> pageResult = websiteQuestionService.queryQuestionWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据主键查询 常见问题
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryQuestionById")
    @ResponseBody
    public ResponseEntity<Result> queryQuestionById(QuestionDTO dto){
        dto = websiteQuestionService.queryQuestionById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 常见问题
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增常见问题" , description = "网站系统->常见问题->新增常见问题功能:新增常见问题")
    @PostMapping(path = "/saveQuestion")
    @ResponseBody
    public ResponseEntity<Result> saveQuestion(QuestionDTO dto){
        boolean flag = websiteQuestionService.saveQuestion(dto);
        Result result = flag ? Result.succ() : Result.fail("新增常见问题失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 修改 常见问题
     *
     * @param dto
     * @return
     */
    @AopLog(module = "修改常见问题" , description = "网站系统->常见问题->修改常见问题功能:修改常见问题")
    @PutMapping(path = "/updateQuestion")
    @ResponseBody
    public ResponseEntity<Result> updateQuestion(QuestionDTO dto){
        boolean flag = websiteQuestionService.updateQuestion(dto);
        Result result = flag ? Result.succ() : Result.fail("修改常见问题失败");
        return ResponseEntity.ok(result);
    }

    /**
     * 删除 常见问题
     *
     * @param ids
     * @return
     */
    @AopLog(module = "删除常见问题" , description = "网站系统->常见问题->删除常见问题功能:删除常见问题")
    @DeleteMapping(path = "/removeQuestionByIds")
    @ResponseBody
    public ResponseEntity<Result> removeQuestionByIds(Integer[] ids){
        boolean flag = websiteQuestionService.removeQuestionByIds(ids);
        Result result = flag ? Result.succ() : Result.fail("删除常见问题失败");
        return ResponseEntity.ok(result);
    }





}
