package com.snake.mcf.sysmgr.controller.web.website;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.FileUtils;
import com.snake.mcf.common.web.media.MediaFile;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.ConfiginfoDTO;
import com.snake.mcf.sysmgr.service.website.WebsiteStandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName websiteStandController
 * @Author 大帅
 * @Date 2019/7/7 17:26
 */
@Slf4j
@Controller
@RequestMapping(path = "/website/stand")
public class WebsiteStandController extends BaseController {

    @Autowired
    private WebsiteStandService websiteStandService;

    /**
     * 分页查询 站点配置
     *
     * @param pageFilter
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryConfigInfoWithPage")
    @ResponseBody
    public ResponseEntity<PageResult<ConfiginfoDTO>> queryConfigInfoWithPage(EasyPageFilter pageFilter, ConfiginfoDTO dto){
        PageResult<ConfiginfoDTO> pageResult = websiteStandService.queryConfigInfoWithPage(pageFilter,dto);
        return ResponseEntity.ok(pageResult);
    }

    /**
     *  根据id查询 站点配置
     *
     * @param dto
     * @return
     */
    @GetMapping(path = "/queryConfigInfoById")
    @ResponseBody
    public ResponseEntity<Result> queryConfigInfoById(ConfiginfoDTO dto){
        dto = websiteStandService.queryConfigInfoById(dto);
        Result result = dto == null ? Result.fail("根据id查询失败") : Result.succ(dto);
        return ResponseEntity.ok(result);
    }

    /**
     *  根据ConfigKey查询 站点配置
     *
     * @param configKey
     * @return
     */
    @GetMapping(path = "/queryConfigInfoByConfigKey")
    @ResponseBody
    public ResponseEntity<Result> queryConfigInfoByConfigKey(String configKey){
        Configinfo configInfo = websiteStandService.queryConfigInfoByConfigKey(configKey);
        Result result = configInfo == null ? Result.fail("根据id查询失败") : Result.succ(configInfo);
        return ResponseEntity.ok(result);
    }

    /**
     * 新增 站点配置
     *
     * @param dto
     * @return
     */
    @AopLog(module = "新增/修改站点配置" , description = "网站系统->站点配置->新增/修改站点配置功能:新增站点配置")
    @PostMapping(path = "/saveConfigInfo")
    @ResponseBody
    public ResponseEntity<Result> saveConfigInfo(@RequestParam(name = "image") MultipartFile file , ConfiginfoDTO dto){
        try {
            Integer configid = dto.getConfigid();
            if(configid == null){
                //新增
                boolean fg = websiteStandService.isExistConfigInfo(dto);
                if(fg){
                    return ResponseEntity.ok(Result.fail("已存在站点配置"));
                }
            }

            //获取附件信息
            log.info("file : {}" , file);
            if(file != null){
                MediaFile mediaFile = FileUtils.getMediaFile(file);
                if(mediaFile != null){
                    String fileExt = mediaFile.getFileExt();
                    boolean img = FileUtils.isImg(fileExt);
                    if(!img){
                        return ResponseEntity.ok(Result.fail("只支持上传图片jpg,jpeg,png"));
                    }
                }
            }

            boolean flag = websiteStandService.saveConfigInfo(file,dto);
            Result result = flag ? Result.succ() : Result.fail("新增/修改站点配置失败");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(Result.fail("新增/修改站点配置失败"));
    }

    /**
     * 编辑 站点配置
     *
     * @param dto
     * @return
     */
    /*@AopLog(module = "修改站点配置" , description = "网站系统->站点配置->修改站点配置功能:修改站点配置")
    @PutMapping(path = "/updateConfigInfo")
    @ResponseBody
    public ResponseEntity<Result> updateConfigInfo(ConfiginfoDTO dto){
        boolean fg = websiteStandService.isExistConfigInfo(dto);
        if(fg){
            return ResponseEntity.ok(Result.fail("已存在站点配置"));
        }
        boolean flag = websiteStandService.updateConfigInfo(dto);
        Result result = flag ? Result.succ() : Result.fail("修改站点配置失败");
        return ResponseEntity.ok(result);
    }*/

    /**
     * 删除 站点配置
     *
     * @param configids
     * @return
     */
    @AopLog(module = "删除站点配置" , description = "网站系统->站点配置->删除站点配置功能:删除站点配置")
    @DeleteMapping(path = "/removeConfigInfoByIds")
    @ResponseBody
    public ResponseEntity<Result> removeConfigInfoByIds(Integer[] configids){
        boolean flag = websiteStandService.removeConfigInfoByIds(configids);
        Result result = flag ? Result.succ() : Result.fail("删除站点配置失败");
        return ResponseEntity.ok(result);
    }
}
