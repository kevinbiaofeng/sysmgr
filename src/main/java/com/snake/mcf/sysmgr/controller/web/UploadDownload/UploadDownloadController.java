package com.snake.mcf.sysmgr.controller.web.UploadDownload;

import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.FileUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.media.MediaFile;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.repertory.entity.Ads;
import com.snake.mcf.sysmgr.repertory.entity.Gamepackagegoods;
import com.snake.mcf.sysmgr.repertory.entity.Gameproperty;
import com.snake.mcf.sysmgr.repertory.entity.Gamerule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(value = "/uploadDownload")
public class UploadDownloadController {

    @Autowired
    private ConfigResource configResource;

    //上传图片
    @RequestMapping(value = "/uploadImage", method = {RequestMethod.POST})
    public ResponseEntity<Result> uploadImage(@RequestParam(value = "image") MultipartFile file, @RequestParam(value = "resourceUrl") String picturePath) {
        try {
            log.info("参数路径picturePath:{}", picturePath);
            MediaFile mediaFile = FileUtils.getMediaFile(file);
            if (mediaFile != null) {
                //保存图片路径的前缀
                String prefixPath = configResource.getUserFacePath();
                log.info("prefixPath:{}", prefixPath);

                //是否是管理员  不是则加一个商户号命名的文件夹
                if (!ShiroUtils.isAdminSessionUserId()) {
                    picturePath = picturePath + "/" + ShiroUtils.getSessionMerchant();
                }

                StringBuffer buffer = new StringBuffer();
                buffer.append(prefixPath + "/");
                buffer.append(picturePath);
                log.info("buffer:{}", buffer);

                String path = FileUtils.uploadFile(mediaFile, buffer.toString());
                log.info("返回要被保存的path:{}", path);
                if (StringUtils.isBlank(path)) {
                    ResponseEntity.ok(Result.fail("上传文件失败"));
                }
                //Dota代表保存的是道具方面的图片
                //Advert代表保存的是广告方面的图片
                //GameRule代表保存的是游戏规则的图片
                if (path.indexOf("dota") != -1) {
                    Gameproperty gameproperty = new Gameproperty();
                    gameproperty.setImgUrl(path);
                    return ResponseEntity.ok(Result.succ(gameproperty));
                } else if (path.indexOf("advert") != -1) {
                    Ads ads = new Ads();
                    ads.setResourceurl(path);
                    return ResponseEntity.ok(Result.succ(ads));
                } else if (path.indexOf("gameRule") != -1) {
                    Gamerule gamerule = new Gamerule();
                    gamerule.setRuleimg(path);
                    return ResponseEntity.ok(Result.succ(gamerule));
                } else if (path.indexOf("sign") != -1) {
                    Gamepackagegoods gamepackagegoods = new Gamepackagegoods();
                    gamepackagegoods.setResourceurl(path);
                    return ResponseEntity.ok(Result.succ(gamepackagegoods));
                }
            }
        } catch (Exception e) {
            log.error("上传文件失败");
            return ResponseEntity.ok(Result.fail(e.getMessage()));
        }
        return ResponseEntity.ok(Result.fail("上传文件失败"));
    }

    //删除文件/图片
    @RequestMapping(value = "/deleteFile", method = {RequestMethod.GET})
    public ResponseEntity<Result> deleteFile(@RequestParam(value = "imgUrl") String filePath) {
        log.info("参数路径：{}", filePath);
        Boolean flag = FileUtils.deleteFile(filePath);
        Result result = flag ? Result.succ("文件删除成功") : Result.fail("文件不存在");
        return ResponseEntity.ok(result);
    }
}
